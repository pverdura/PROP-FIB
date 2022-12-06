package Codi.Domini;

import Codi.Excepcions.ArrayDeParaulesBuitException;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

public class EspaiVec {

    /**
     * Calcula el IDF de cada paraula de l'array paraules
     *
     * @param paraules Array de paraules que s'utilitza per fer la cerca
     * @param N Indica el nombre de documents del sistema
     * @param DocumentsParaules Hi ha les paraules amb els identificadors dels documents on apareix
     * @return Retorna el calcul log_2(N/#DocsApareix) del conjunt de paraules del document D
     */
    public static ArrayList<SimpleEntry<String,Double>> obteIDFparaules(ArrayList<String> paraules, int N,
                                                                 HashMap<String,ArrayList<SimpleEntry<String,String>>> DocumentsParaules) {
        // Array on guardem les paraules de l'array paraules i el nombre seu idf
        ArrayList<SimpleEntry<String,Double>> paraulesIDF = new ArrayList<SimpleEntry<String,Double>>();

        for (String p : paraules) {   // Afegim en l'array les paraules i el seu idf
            int aparicions = 0;

            if (DocumentsParaules.containsKey(p)) { // Mirem si la paraula apaeix en algun docuement
                aparicions = DocumentsParaules.get(p).size();
            }

            // Calculem log2(N/aparicions), se li suma 1, ja que es pot donar el cas que no aparegui en cap
            // i no es pot dividir entre 0
            double idf = Math.log((double) N / (aparicions+1)) / Math.log(2);

            SimpleEntry<String, Double> elem = new SimpleEntry<String, Double>(p, idf);
            paraulesIDF.add(elem);
        }
        return paraulesIDF;
    }

    /**
     * Calcula el TF-IDF del DocCerca respecte l'array de paraules
     *
     * @param paraulesIDF Array amb el IDF de les paraules del sistema
     * @param DocCerca Document que s'utilitza per calcular el seu TF-IDF
     * @return Retorna el TF-IDF del DocCerca respecte l'array paraulesIDF
     */
    public static Double calculaTF_IDF(ArrayList<SimpleEntry<String,Double>> paraulesIDF, Document DocCerca) {
        HashMap<String, Integer> paraulesDoc = DocCerca.getAparicions();    // Obtenim les paraules del document DocCerca
        double tf_idf = 0;
        int numParaulesDoc = 0; // Nombre de paraules del document DocCerca

        for (String paraulaDoc : paraulesDoc.keySet()) {    // Contem el nombre total de paraules del document DocCerca
            numParaulesDoc += paraulesDoc.get(paraulaDoc);  // Sumem quants cops apareix la paraula en el document
        }

        for (SimpleEntry<String,Double> p : paraulesIDF) {  // Calculem el tf_idf de les paraules
            if(paraulesDoc.containsKey(p.getKey())) {       // Si el document conté la paraula p calculem el tf i la sumem
                double tf = (double) paraulesDoc.get(p.getKey())/numParaulesDoc;    // Calculem (AparicionsPDoc/numParaulesDoc)
                tf_idf += tf*p.getValue();  // Calculem SUMATORI(tf * idf)
            }
            // Si no conté la paraula no cal sumar-ho ja que es sumaria 0 (0*x = 0)
        }
        return tf_idf;
    }

    /**
     * Cerca els k documents més rellevants a l'array paraules
     *
     * @param k Indica el nombre de documents que volem obtenir
     * @param Documents Hi ha els documents del sistema
     * @param paraules Aquest és l'array de paraules que s'utilitza per fer la cerca
     * @param DocumentsParaules Hi ha es paraules amb els identificadors dels documents on apareix
     * @return Retorna un llistat de k Documents (títol,autor) més rellevants a l'array paraules
     */
    public static ArrayList<SimpleEntry<String,String>> cercaDoc(int k, HashMap<SimpleEntry<String,String>, Document> Documents,
                                                                 ArrayList<String> paraules, HashMap<String,ArrayList<SimpleEntry<String,String>>> DocumentsParaules) {
        int N = Documents.size();   // Nombre de documents del sistema

        ArrayList<SimpleEntry<String,Double>> paraulesIDF = obteIDFparaules(paraules,N,DocumentsParaules);

        // Array on es posaran els identificadors dels documents que cerquem
        ArrayList<SimpleEntry<String, String>> docs = new ArrayList<SimpleEntry<String, String>>();
        // Guardem els documents amb el seu TF-IDF
        ArrayList<SimpleEntry<SimpleEntry<String, String>, Double>> semblants = new ArrayList<SimpleEntry<SimpleEntry<String, String>, Double>>();
        boolean primera = true;

        // Iterem tots els Documents del sistema
        for (SimpleEntry<String, String> idDocument : Documents.keySet()) {
            Document DCons = Documents.get(idDocument);         // Obtenim el document X
            double sembl = calculaTF_IDF(paraulesIDF, DCons);   // Calculem el tf-idf del document X
            SimpleEntry<SimpleEntry<String, String>, Double> elem = new SimpleEntry<SimpleEntry<String, String>, Double>(idDocument, sembl);

            if (primera) {
                semblants.add(elem);
                primera = false;
            }
            else {
                int n = semblants.size() - 1;
                int idx = 0;

                // Mirem que la posició que mirem estigui en l'array i que l'element de la posició idx tingui
                // una semblança major al document iterat
                while (idx < n && semblants.get(idx).getValue() > sembl) {
                    ++idx;
                }

                semblants.add(idx, elem);
                if (semblants.size() > k) {  // Si la longitud de l'array és més gran que k, treiem l'úlitm element de l'array
                    semblants.remove(k);
                }
            }
        }

        // Treiem el TF-IDF de l'array obtingut
        for (SimpleEntry<SimpleEntry<String, String>, Double> p : semblants) {
            docs.add(p.getKey());
        }

        return docs;
    }
}
