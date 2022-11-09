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

    /* Pre: El contingut del document D no és buit, k >= 0
     * Post: Un llistat de k Documents (títol,autor), més semblants al document D
     */
    public static ArrayList<Pair<String,String>> cercaDoc(Document D, int k,  HashMap<String,ArrayList<Pair<String,String>>> DocumentsParaules,
                                                   HashMap<Pair<String,String>, Document> Documents) {
        ArrayList<Pair<String,String>> docs = new ArrayList<Pair<String,String>>(k);    // Array on es posaran els identificadors dels documents que cerquem
        HashMap<String,Integer> paraulesD = D.getAparicions();      // Array on guardem les paraules rellevants del document D (sense repetides)

        HashMap<Pair<String,String>,Double> taula_tfidf = new HashMap<Pair<String,String>,Double> ();

        for(Pair<String,String> idConsulta : Documents.keySet()) {  // Iterem tots els Documents del sistema
            if (!idConsulta.getFirst().equals(D.getTitol()) || !idConsulta.getSecond().equals(D.getAutor())) {
                // Mirem que el Document que iterem no sigui el que estiguem utilitzant per fer la cerca
                HashMap<String, Integer> paraulesDCons = Documents.get(idConsulta).getAparicions(); // Obtenim les paraules del document X consultat
                HashMap<String, Double> taula_tf = new HashMap<String, Double>(); // Map on guardem el tf de cada paraula
                HashMap<String, Double> taula_idf = new HashMap<String, Double>(); // Map on guardem el idf de cada paraula
                int numParaules = 0;

                for (String paraulaDCons : paraulesDCons.keySet()) { // Contem el nombre total de paraules
                    numParaules += paraulesDCons.get(paraulaDCons);
                }

                for (String paraulaD : paraulesD.keySet()) { // Calculem el tf de les paraules del document X
                    if (paraulesDCons.containsKey(paraulaD)) {   // Conté la paraula
                        Double tf = (double) paraulesDCons.get(paraulaD)/numParaules;    // El nombre d'aparicions de la paraulaD en el document X
                        taula_tf.put(paraulaD, tf);  // Afegim la paraula amb el càlcul tf corresponent
                    }
                    else {  // La paraula no existeix en el document
                        taula_tf.put(paraulaD, 0.0);
                    }
                    int N = DocumentsParaules.get(paraulaD).size(); // Obtenim en quants documents aparareix la paraulaD
                    Double idf = Math.log((double) Documents.size()/N);//log2(Documents.size() / N); // Calculem el idf de cada paraula
                    taula_idf.put(paraulaD, idf); // Afegim la paraula amb el seu idf corresponent
                }
                double tf_idf = 0;
                for (String paraula : taula_tf.keySet()) { // calculem el tf_idf del document X consultat
                    double a = taula_tf.get(paraula);
                    double b = taula_idf.get(paraula);
                    tf_idf += (a * b);
                }
                taula_tfidf.put(idConsulta, tf_idf); // taula amb els tf-idf de tots els documents
            }
        }
        return null;
    }

    private static Double log2(int n) {
        return Math.log(n)/Math.log(2);
    }
}
