// Cerca que implementa l'obtenció d'un llistat de k documents més rellevants al document D

package Codi.Domini;

import Codi.Util.Pair;
import java.util.ArrayList;
import java.util.HashMap;

public class CercaParaules implements Cerca {

    @Override
    public void cercaDoc() {}

    /* Pre: paraules.size() > 0, k >= 0
     * Post: Un llistat de k Documents (títol,autor), més rellevants segons el array de paraules
     */
    public ArrayList<Pair<String,String>> cercaDoc(ArrayList<String> paraules, int k, HashMap<String,ArrayList<String>> DocumentsParaules, Boolean tipus) {
        if (tipus) { // Cerca tf-idf
            int a = 5;
        }
        else {       // Cerca quantitat de paraules
            int a = 6;
        }
        return null;
    }
}
