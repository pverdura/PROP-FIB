package Codi.Domini;


import Codi.Util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class CercaTitol implements Cerca{

    @Override
    public void cercaDoc() {}
    public ArrayList<Pair<String, String>> cercaDoc (String titol,  HashMap<String, ArrayList<String>> titolAutors,
                                                     HashMap<String, ArrayList<String>> documents) {
        ArrayList<Pair<String, String>> resultat = new ArrayList<Pair<String, String>>();

        ArrayList<String> llistaAutors = titolAutors.get(titol);
        int n = llistaAutors.size();
        for (int i = 0; i < n; ++i) {
            Pair<String, String> aux = new Pair<String, String>(titol, llistaAutors.get(i));
            resultat.add(aux);
        }

        return resultat;
    }
}
