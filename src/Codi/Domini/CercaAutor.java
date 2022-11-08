package Codi.Domini;

import Codi.Util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class CercaAutor implements Cerca{

    @Override
    public void cercaDoc() {}

    public ArrayList<Pair<String , String >> cercaDoc(String autor,  HashMap<String, ArrayList<String>> Autors){

        ArrayList<Pair<String, String>> resultat = new ArrayList<Pair<String, String>>();

        ArrayList<String> llistaTitols = Autors.get(autor);
        int n = llistaTitols.size();
        for (int i = 0; i < n; ++i) {
            Pair<String, String> aux = new Pair<String, String>(llistaTitols.get(i), autor);
            resultat.add(aux);
        }

        return resultat;

    }
}
