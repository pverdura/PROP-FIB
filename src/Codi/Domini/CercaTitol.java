package Codi.Domini;

import Codi.Excepcions.TitolNoExisteixException;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *  Classe que implementa la cerca de documents pel títol
 *
 * @author Jordi Palomera
 * @since 13-12-2022
 */

public class CercaTitol implements Cerca {

    /**
     * Cerca els documents pel títol
     *
     * @param titol Títol dels documents que es volen cercar
     * @param titolAutors Estructura de dades dels {@code HashMap<String, ArrayList<String>>} autors que han creat un document de cada títol
     * @return {@code ArrayList<SimpleEntry<String, String>>} el resultat de la cerca
     * @throws TitolNoExisteixException si no hi ha cap document amb aquest títol
     */
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
