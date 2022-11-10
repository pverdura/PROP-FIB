package Codi.Domini;

import Codi.Util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class CercaAutor implements Cerca{

    //@Override
    //public void cercaDoc() {}

    public static ArrayList<Pair<String , String >> cercaDoc(String autor,  HashMap<String, ArrayList<String>> Autors){

        ArrayList<Pair<String, String>> resultat = new ArrayList<>();

        ArrayList<String> llistaTitols = Autors.get(autor);
        for (String t : llistaTitols) {
            Pair<String, String> aux = new Pair<>(t, autor);
            resultat.add(aux);
        }

        return resultat;

    }
}
