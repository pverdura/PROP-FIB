package Codi.Domini;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe que cerca totes les expressions booleanes del sistema
 *
 * @author pol
 * @since 15/12/2022
 */
public class CercaAllExpressionsBool implements Cerca {

    /**
     * Cerca totes les expressions booleanes guardades
     *
     * @param ExpressionsBooleanes Ens dona les expressions que hi ha en el sistema
     * @return Retorna el array d'expressions booleanes que hi ha en el sistema
     */
    public static ArrayList<String> cercaDoc (HashMap<String,ExpressioBooleana> ExpressionsBooleanes) {
        return new ArrayList<String>(ExpressionsBooleanes.keySet());
    }
}
