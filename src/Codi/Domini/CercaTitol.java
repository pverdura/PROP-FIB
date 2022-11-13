package Codi.Domini;

import Codi.Excepcions.TitolNoExisteixException;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

public class CercaTitol implements Cerca {

    public static ArrayList<SimpleEntry<String, String>> cercaDoc (String titol,  HashMap<String, ArrayList<String>> titolAutors) throws TitolNoExisteixException {
        ArrayList<SimpleEntry<String, String>> resultat = new ArrayList<>();

        if (!titolAutors.containsKey(titol)) throw new TitolNoExisteixException(titol);

        ArrayList<String> llistaAutors = titolAutors.get(titol);

        for (String autor : llistaAutors) {
            SimpleEntry<String, String> aux = new SimpleEntry<>(titol, autor);
            resultat.add(aux);
        }

        return resultat;
    }
}
