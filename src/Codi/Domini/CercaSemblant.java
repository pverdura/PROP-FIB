// Cerca que implementa l'obtenció d'un llistat de k documents més semblants al document D

package Codi.Domini;

import Codi.Excepcions.ArrayDeParaulesBuitException;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

public class CercaSemblant implements Cerca {

    ///////////////////////////////////////////////////////////
    ///                      CREADORES                      ///
    ///////////////////////////////////////////////////////////

    // Creadora per defecte
    public CercaSemblant() {}


    ///////////////////////////////////////////////////////////
    ///                      FUNCIONS                       ///
    ///////////////////////////////////////////////////////////

    /* S'obtenen els k documents més semblants al document D
     * Pre: El contingut del document D no és buit, k >= 0
     * Post: Un llistat de k Documents (títol,autor), més semblants al document D
     */
    public static ArrayList<SimpleEntry<String,String>> cercaDoc(Document D, int k,  HashMap<String,ArrayList<SimpleEntry<String,String>>> DocumentsParaules,
                                                   HashMap<SimpleEntry<String,String>, Document> Documents) throws ArrayDeParaulesBuitException {
        /*if (k <= 0) {
            throw new NombreMassaPetitDocumentsError(k);
        }*/

        // Array on es posaran els identificadors dels documents que cerquem
        ArrayList<SimpleEntry<String,String>> docs = new ArrayList<SimpleEntry<String,String>>();

        // Array on guardem les paraules del document D i el nombre seu idf
        ArrayList<SimpleEntry<String,Double>> paraulesIDF = new ArrayList<SimpleEntry<String,Double>>();

        int N = Documents.size();   // Nombre de documents

        for (String p : D.getAparicions().keySet()) {   // Afegim en l'array les paraules i el seu idf
            int aparicions = DocumentsParaules.get(p).size()+1; // Se li suma 1 per si no apareix en cap a part del document D (no es pot dividir per 0)
            double idf = Math.log((double) N/aparicions)/Math.log(2);   // Calculem log2(#Docs/#DocsApareix)
            SimpleEntry<String,Double> elem = new SimpleEntry<String,Double>(p,idf);
            paraulesIDF.add(elem);
        }

        // Guardem els documents i el seu TF-IDF (NÚM MAX DE POSICIONS: K)
        ArrayList<SimpleEntry<SimpleEntry<String,String>,Double>> semblants = new ArrayList<SimpleEntry<SimpleEntry<String,String>,Double>>();
        boolean primera = true;

        for (SimpleEntry<String,String> idDocument : Documents.keySet()) {     // Iterem tots els Documents del sistema
            Document DCons = Documents.get(idDocument);                 // Obtenim el document X
            double sembl = EspaiVec.calculaTF_IDF(paraulesIDF, DCons);  // Calculem el tf-idf del document X
            SimpleEntry<SimpleEntry<String,String>,Double> elem = new SimpleEntry<SimpleEntry<String,String>,Double>(idDocument,sembl);

            if (primera) {
                semblants.add(elem);
                primera = false;
            }
            else {
                int n = semblants.size()-1;
                int idx = 0;

                // Mirem que la posició que mirem estigui en l'array i que l'element de la posició idx tingui
                // una semblança major al document iterat
                while (idx < n && semblants.get(idx).getValue() > sembl) {
                    ++idx;
                }
                semblants.add(idx,elem);
                if(semblants.size() > k) {  // Si el tamany de l'array és més gran que k, treiem elements
                    semblants.remove(k);

                }
            }
        }

        for (SimpleEntry<SimpleEntry<String,String>,Double> p : semblants) {
            docs.add(p.getKey());
        }

        return docs;
    }
}
