// Cerca que implementa l'obtenció d'un llistat de k documents més rellevants al document D

package Codi.Domini;

import Codi.Excepcions.ArrayDeParaulesBuitException;
import Codi.Excepcions.NombreMassaPetitDocumentsException;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

public class CercaParaules implements Cerca {

    ///////////////////////////////////////////////////////////
    ///                      CREADORES                      ///
    ///////////////////////////////////////////////////////////

    // Creadora per defecte
    public CercaParaules() {}

    ///////////////////////////////////////////////////////////
    ///                      FUNCIONS                       ///
    ///////////////////////////////////////////////////////////

    // Reimplementació de la funció cercaDoc
    /* Pre: paraules.size() > 0, k >= 0
     * Post: Un llistat de k Documents (títol,autor), més rellevants segons el array de paraules
     */
    public static ArrayList<SimpleEntry<String,String>> cercaDoc(ArrayList<String> paraules, int k, HashMap<String,ArrayList<SimpleEntry<String,String>>> DocumentsParaules,
                                                                             HashMap<SimpleEntry<String,String>,Document> Documents) throws ArrayDeParaulesBuitException, NombreMassaPetitDocumentsException {
        if (k <= 0) {
            throw new NombreMassaPetitDocumentsException();
        }

        // Array on es posaran els identificadors dels documents que cerquem
        ArrayList<SimpleEntry<String,String>> docs = new ArrayList<SimpleEntry<String,String>>();

        // Array on guardem les paraules de la query i el nombre de document on apareix
        ArrayList<SimpleEntry<String,Double>> paraulesIDF = new ArrayList<SimpleEntry<String,Double>>();

        int N = Documents.size();   // Nombre de documents

        for (String p : paraules) {   // Afegim en l'array les paraules i el seu idf
            int aparicions;
            if (DocumentsParaules.containsKey(p)) {
                aparicions = DocumentsParaules.get(p).size();
            }
            else {
                aparicions = 0;
            }
            double idf = Math.log((double) N / (aparicions+1)) / Math.log(2);   // Calculem log2(#Docs/#DocsApareix)
            SimpleEntry<String, Double> elem = new SimpleEntry<String, Double>(p, idf);
            paraulesIDF.add(elem);
        }

        // Guardem els documents i el seu TF-IDF
        ArrayList<SimpleEntry<SimpleEntry<String,String>,Double>> semblants = new ArrayList<SimpleEntry<SimpleEntry<String,String>,Double>>();
        boolean primera = true;

        for (SimpleEntry<String,String> idDocument : Documents.keySet()) {     // Iterem tots els Documents del sistema
            Document DCons = Documents.get(idDocument);                 // Obtenim el document X
            double sembl = EspaiVec.calculaTF_IDF(paraulesIDF, DCons);   // Calculem el tf-idf del document X
            SimpleEntry<SimpleEntry<String,String>,Double> elem = new SimpleEntry<SimpleEntry<String,String>,Double>(idDocument,sembl);

            if(primera) {
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
