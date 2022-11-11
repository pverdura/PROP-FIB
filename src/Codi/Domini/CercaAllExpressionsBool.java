package Codi.Domini;

import java.util.ArrayList;

public class CercaAllExpressionsBool {
    public static ArrayList<String> cercaBool (ArrayList<ExpressioBooleana> ExpressionsBooleanes) {
        ArrayList<String> resultat = new ArrayList<String>();

        for(ExpressioBooleana eb : ExpressionsBooleanes) {
            resultat.add(eb.getExpressio());
        }

        return resultat;
    }
}
