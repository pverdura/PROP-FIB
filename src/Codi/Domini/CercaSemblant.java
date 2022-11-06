// Cerca que implementa l'obtenció d'un llistat de k documents més semblants al document D

package Codi.Domini;

import Codi.Util.Pair;
import java.util.ArrayList;
import java.util.HashMap;

public class CercaSemblant implements Cerca {

    public CercaSemblant() {}

    @Override
    public void cercaDoc() {}

    /* Pre: El contingut del document D no és buit, k >= 0
     * Post: Un llistat de k Documents (títol,autor), més semblants al document D
     */
    public ArrayList<Pair<String,String>> cercaDoc(Document D, int k, HashMap<Pair<String,String>, Document> Documents, Boolean tipus) {
        if (tipus) { // Cerca tf-idf
            int a = 1;
        }
        else {       // Cerca quantitat de paraules
            int b = 2;
        }
        return null;
    }
}
