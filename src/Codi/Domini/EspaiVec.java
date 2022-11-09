package Codi.Domini;

import Codi.Util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class EspaiVec {

    /* Calcula el tf-idf del DocCerca respecte l'array de paraules
     * Pre: L'array de paraules no és buit, els Integer i N no poden ser negatius
     * Post: El tf-idf del DocCerca
     */
    public static Double calculaTF_IDF(ArrayList<Pair<String,Double>> paraulesIDF, Document DocCerca) {
        HashMap<String, Integer> paraulesDoc = DocCerca.getAparicions(); // Obtenim les paraules del document DocCerca
        double tf_idf = 0;
        int numParaulesDoc = 0; // Nombre de paraules del document DocCerca

        for (String paraulaDoc : paraulesDoc.keySet()) {    // Contem el nombre total de paraules del document DocCerca
            numParaulesDoc += paraulesDoc.get(paraulaDoc);  // Sumem quants cops apareix la paraula en el document
        }

        for (Pair<String,Double> p : paraulesIDF) {     // Calculem el tf_idf de les paraules
            double tf = 0;      // Per defecte posem que el document no conté la paraula i el seu tf és 0

            if(paraulesDoc.containsKey(p.getFirst())) { // Si el document conté la paraula p calculem el tf
                tf = (double) paraulesDoc.get(p.getFirst())/numParaulesDoc;    // Calculem (AparicionsP/numParaulesDoc)
            }

            tf_idf += tf*p.getSecond(); // Calculem SUMATORI(tf * idf)
        }
        return tf_idf;
    }
}
