package Codi.Domini;

import java.util.ArrayList;
import java.util.HashMap;

public class CercaAllExpressionsBool implements Cerca {
    public static ArrayList<String> cercaDoc (HashMap<String,ExpressioBooleana> ExpressionsBooleanes) {
        ArrayList<String> resultat = new ArrayList<String>(ExpressionsBooleanes.keySet());

        return resultat;
    }
}
