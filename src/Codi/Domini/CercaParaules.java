// Cerca que implementa l'obtenció d'un llistat de k documents més rellevants al document D

package Codi.Domini;

import Codi.Util.Pair;
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
    public static ArrayList<Pair<String,String>> cercaDoc(ArrayList<String> paraules, int k, HashMap<String,ArrayList<Pair<String,String>>> DocumentsParaules,
                                                   HashMap<Pair<String,String>,Document> Documents) {
        // Array on es posaran els identificadors dels documents que cerquem
        ArrayList<Pair<String,String>> docs = new ArrayList<Pair<String,String>>();

        // Array on guardem les paraules de la query i el nombre de document on apareix
        ArrayList<Pair<String,Double>> paraulesIDF = new ArrayList<Pair<String,Double>>();

        int N = Documents.size();   // Nombre de documents

        for (String p : paraules) {   // Afegim en l'array les paraules i el seu idf
            int aparicions = DocumentsParaules.get(p).size();
            double idf = Math.log((double) N/aparicions)/Math.log(2);   // Calculem log2(#Docs/#DocsApareix)
            Pair<String,Double> elem = new Pair<String,Double>(p,idf);
            paraulesIDF.add(elem);
        }

        for(Pair<String,String> idConsulta : Documents.keySet()) {  // Iterem tots els Documents del sistema
            Document Dcons = Documents.get(idConsulta); // Obtenim el document X

            Double calc = EspaiVec.calculaTF_IDF(paraulesIDF, Dcons); // Calculem el ft-idf del document X

        }

        return docs;
    }
}
