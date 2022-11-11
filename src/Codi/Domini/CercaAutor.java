package Codi.Domini;

import Codi.Util.Pair;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

public class CercaAutor implements Cerca{

    //@Override
    //public void cercaDoc() {}

    public static ArrayList<SimpleEntry<String,String>> cercaDoc(String autor, HashMap<String, ArrayList<String>> Autors){

        ArrayList<SimpleEntry<String,String>> resultat = new ArrayList<>();

        ArrayList<String> llistaTitols = Autors.get(autor);
        for (String t : llistaTitols) {
            SimpleEntry<String, String> aux = new SimpleEntry<>(t,autor);
            resultat.add(aux);
        }

        return resultat;

    }
}
