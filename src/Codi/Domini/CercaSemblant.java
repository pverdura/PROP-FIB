// Cerca que implementa l'obtenció d'un llistat de k documents més semblants al document D

package Codi.Domini;

import Codi.Util.Pair;
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

        for(Pair<String,String> idConsulta : Documents.keySet()) {  // Iterem tots els Documents del sistema
            Document Dcons = Documents.get(idConsulta); // Obtenim el document X

            // Mirem que el Document que iterem no sigui el que estiguem utilitzant per fer la cerca
            if(!idConsulta.getFirst().equals(D.getTitol()) || !idConsulta.getSecond().equals(D.getAutor())) {
                Double calc = EspaiVec.calculaTF_IDF(paraulesIDF, Dcons); // Calculem el ft-idf del document X

            }
        }

        return docs;
    }
}
