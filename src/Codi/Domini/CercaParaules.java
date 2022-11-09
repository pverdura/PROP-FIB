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
        for(Pair<String,String> idConsulta : Documents.keySet()) {  // Iterem tots els Documents del sistema
            // HashMap<String,Integer> paraulesDConsulta = Documents.get(idConsulta).getAparicions();
            int totalParaulesConsultat = 0;

        }

        return null;
    }
}
