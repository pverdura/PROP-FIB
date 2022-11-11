//Calcula el tf_idf del document DocCerca
package Codi.Domini;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

public class EspaiVec {

    /* Calcula el tf-idf del DocCerca respecte l'array de paraules
     * Pre: L'array de paraules no és buit, els Integer no poden ser negatius
     * Post: El tf-idf del DocCerca
     */
    public static Double calculaTF_IDF(ArrayList<SimpleEntry<String,Double>> paraulesIDF, Document DocCerca) {
        HashMap<String, Integer> paraulesDoc = DocCerca.getAparicions(); // Obtenim les paraules del document DocCerca
        double tf_idf = 0;
        int numParaulesDoc = 0; // Nombre de paraules del document DocCerca

        for (String paraulaDoc : paraulesDoc.keySet()) {    // Contem el nombre total de paraules del document DocCerca
            numParaulesDoc += paraulesDoc.get(paraulaDoc);  // Sumem quants cops apareix la paraula en el document
        }

        for (SimpleEntry<String,Double> p : paraulesIDF) {     // Calculem el tf_idf de les paraules
            if(paraulesDoc.containsKey(p.getKey())) { // Si el document conté la paraula p calculem el tf i la sumem
                double tf = (double) paraulesDoc.get(p.getKey())/numParaulesDoc;    // Calculem (AparicionsPDoc/numParaulesDoc)
                tf_idf += tf*p.getValue(); // Calculem SUMATORI(tf * idf)
            }
            // Si no conté la paraula no cal sumar-ho ja que es sumaria 0 (0*x = 0)
        }
        return tf_idf;
    }
}
