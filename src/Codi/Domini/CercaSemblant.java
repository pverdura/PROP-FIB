// Cerca que implementa l'obtenció d'un llistat de k documents més semblants al document D

package Codi.Domini;

import Codi.Util.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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
    public static ArrayList<Pair<String,String>> cercaDoc(Document D, int k,  HashMap<String,ArrayList<Pair<String,String>>> DocumentsParaules,
                                                   HashMap<Pair<String,String>, Document> Documents) {

        // Array on es posaran els identificadors dels documents que cerquem
        ArrayList<Pair<String,String>> docs = new ArrayList<Pair<String,String>>();

        // Array on guardem les paraules del document D i el nombre seu idf
        ArrayList<Pair<String,Double>> paraulesIDF = new ArrayList<Pair<String,Double>>();

        int N = Documents.size();   // Nombre de documents

        for (String p : D.getAparicions().keySet()) {   // Afegim en l'array les paraules i el seu idf
            int aparicions = DocumentsParaules.get(p).size();
            double idf = Math.log((double) N/aparicions)/Math.log(2);   // Calculem log2(#Docs/#DocsApareix)
            Pair<String,Double> elem = new Pair<String,Double>(p,idf);
            paraulesIDF.add(elem);
        }

        // Guardem els documents i el seu TF-IDF (NUM MAX DE POSICIONS: K)
        ArrayList<Pair<Pair<String,String>,Double>> semblants = new ArrayList<Pair<Pair<String,String>,Double>>();
        boolean primera = true;

        for (Pair<String,String> idDocument : Documents.keySet()) {     // Iterem tots els Documents del sistema
            Document DCons = Documents.get(idDocument);                 // Obtenim el document X
            double sembl = EspaiVec.calculaTF_IDF(paraulesIDF, DCons);   // Calculem el ft-idf del document X
            Pair<Pair<String,String>,Double> elem = new Pair<Pair<String,String>,Double>(idDocument,sembl);

            if (primera) {
                semblants.add(elem);
                primera = false;
            }
            else {
                int n = semblants.size()-1;
                int idx = 0;

                // Mirem que la posició que mirem estigui en l'array i que l'element de la posició idx tingui
                // una semblança major al document iterat
                while (idx < n && semblants.get(idx).getSecond() > sembl) {
                    ++idx;
                }
                if (idx < n) { // Si la posició idx està en l'array, afegim l'element en aquest
                    semblants.add(idx,elem);
                    if(semblants.size() > k) {  // Si el tamany de l'array és més gran que k, treiem elements
                        semblants.remove(k);
                    }
                }
            }
        }

        for (Pair<Pair<String,String>,Double> p : semblants) {
            docs.add(p.getFirst());
        }

        return docs;
    }
}
