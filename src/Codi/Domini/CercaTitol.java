package Codi.Domini;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

public class CercaTitol implements Cerca {

    //@Override
    //public static void cercaDoc() {}
    public static ArrayList<SimpleEntry<String, String>> cercaDoc (String titol,  HashMap<String, ArrayList<String>> titolAutors) {
        ArrayList<SimpleEntry<String, String>> resultat = new ArrayList<>();

        ArrayList<String> llistaAutors = titolAutors.get(titol);

        for (String autor : llistaAutors) {
            SimpleEntry<String, String> aux = new SimpleEntry<>(titol, autor);
            resultat.add(aux);
        }

        return resultat;
    }
}
