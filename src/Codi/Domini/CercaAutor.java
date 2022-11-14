package Codi.Domini;

import Codi.Excepcions.AutorNoExisteixException;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

public class CercaAutor implements Cerca{

    public static ArrayList<SimpleEntry<String,String>> cercaDoc(String autor, HashMap<String, ArrayList<String>> autorTitols) throws AutorNoExisteixException {

        ArrayList<SimpleEntry<String,String>> resultat = new ArrayList<>();

        if (!autorTitols.containsKey(autor)) throw new AutorNoExisteixException(autor);

        ArrayList<String> llistaTitols = autorTitols.get(autor);
        for (String t : llistaTitols) {
            SimpleEntry<String, String> aux = new SimpleEntry<>(t,autor);
            resultat.add(aux);
        }

        return resultat;

    }
}
