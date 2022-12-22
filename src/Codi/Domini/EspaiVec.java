package Codi.Domini;

import javax.print.Doc;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe que ens calcula els TF-IDF dels documents del sistema, i ens fa la cerca d'aquests (de mes rellevant a menys)
 * 
 * @author Pol Verdura
 */
public class EspaiVec {

    /**
     * Calcula el IDF de cada paraula de l'array paraules (la freqüencia inversa de lo comuna que és la paraula en el
     * corpus: quan més freqüent -> menys IDF, quan menys freüent -> més IDF)
     *
     * @param paraules Array de paraules que s'utilitza per fer la cerca
     * @param N Indica el nombre de documents del sistema
     * @param DocumentsParaules Hi ha les paraules amb els identificadors dels documents on apareix
     * @return Retorna el calcul log_2(N/#DocsApareix) del conjunt de paraules del document D
     */
    public static ArrayList<SimpleEntry<String,Double>> obteIDFparaules(ArrayList<String> paraules, int N, 
                                                                        HashMap<String,ArrayList<SimpleEntry<String,String>>> DocumentsParaules) {
        // Array on guardem les paraules de l'array paraules i el nombre seu IDF
        ArrayList<SimpleEntry<String,Double>> paraulesIDF = new ArrayList<SimpleEntry<String,Double>>();

        for (String p : paraules) {   // Afegim en l'array creat les paraules i el seu IDF
            int aparicions = 0;

            if (DocumentsParaules.containsKey(p)) { // Mirem si la paraula apareix en algun document
                aparicions = DocumentsParaules.get(p).size();
            }

            // Calculem log2(N/aparicions), se li suma 1, ja que es pot donar el cas que no aparegui en cap
            // i no es pot dividir entre 0
            double IDF = Math.log((double) N / (aparicions+1)) / Math.log(2);

            SimpleEntry<String, Double> elem = new SimpleEntry<String, Double>(p, IDF);
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
        double TF_IDF = 0;      // Indica el TF_IDF del document
        int numParaulesDoc = 0; // Nombre de paraules del document DocCerca

        for (String paraulaDoc : paraulesDoc.keySet()) {    // Contem el nombre total de paraules del document DocCerca
            numParaulesDoc += paraulesDoc.get(paraulaDoc);  // Sumem quants cops apareix la paraula en el document
        }

        for (SimpleEntry<String,Double> p : paraulesIDF) {  // Calculem el TF_IDF de les paraules
            if(paraulesDoc.containsKey(p.getKey())) {       // Si el document conte la paraula p calculem el TF i la sumem
                double TF = (double) paraulesDoc.get(p.getKey())/numParaulesDoc;    // Calculem (AparicionsPDoc/numParaulesDoc)
                TF_IDF += TF*p.getValue();  // Calculem SUMATORI(TF * IDF)
            }
            // Si no conte la paraula no cal sumar-ho ja que es sumaria 0 (0*x = 0)
        }
        return TF_IDF;
    }

    /**
     * Cerca els k documents mes rellevants a l'array paraules
     *
     * @param k Indica el nombre de documents que volem obtenir
     * @param Documents Hi ha els documents del sistema
     * @param paraules Aquest es l'array de paraules que s'utilitza per fer la cerca
     * @param DocumentsParaules Hi ha es paraules amb els identificadors dels documents on apareix
     * @return Retorna un llistat de k Documents (titol,autor) mes rellevants a l'array paraules
     */
    public static ArrayList<SimpleEntry<String,String>> cercaDoc(int k, HashMap<SimpleEntry<String,String>, Document> Documents,
                                                                 ArrayList<String> paraules, HashMap<String,ArrayList<SimpleEntry<String,String>>> DocumentsParaules) {
        int N = Documents.size();   // Nombre de documents del sistema

        // Obtenim el IDF de les paraules introduides
        ArrayList<SimpleEntry<String,Double>> paraulesIDF = obteIDFparaules(paraules,N,DocumentsParaules);

        // Array on es posaran els identificadors dels documents que cerquem
        ArrayList<SimpleEntry<String, String>> docs = new ArrayList<SimpleEntry<String, String>>();

        // Guardem els documents amb el seu TF-IDF
        ArrayList<SimpleEntry<SimpleEntry<String, String>, Double>> semblants = new ArrayList<SimpleEntry<SimpleEntry<String, String>, Double>>();
        boolean primera = true;

        // Iterem tots els Documents del sistema
        for (SimpleEntry<String, String> idDocument : Documents.keySet()) {
            Document DCons = Documents.get(idDocument);         // Obtenim el document X
            double sembl = calculaTF_IDF(paraulesIDF, DCons);   // Calculem el TF-IDF del document X
            SimpleEntry<SimpleEntry<String, String>, Double> elem = new SimpleEntry<SimpleEntry<String, String>, Double>(idDocument, sembl);

            if (primera) {
                semblants.add(elem);
                primera = false;
            }
            else {
                int n = semblants.size() - 1;
                int idx = 0;

                // Mirem que la posicio que mirem estigui en l'array i que l'element de la posicio idx tingui
                // una semblança major al document iterat
                while (idx <= n && semblants.get(idx).getValue() > sembl) {
                    ++idx;
                }

                semblants.add(idx, elem);
                if (semblants.size() > k) {  // Si la longitud de l'array es mes gran que k, traiem l'ulitm element de l'array
                    semblants.remove(k);
                }
            }
        }
        // Traiem el TF-IDF de l'array obtingut
        for (SimpleEntry<SimpleEntry<String, String>, Double> p : semblants) {
            docs.add(p.getKey());
        }
        return docs;
    }
}
