package Codi.Domini;


import Codi.Excepcions.AutorInexistentException;
import Codi.Util.Trie;

import java.util.ArrayList;

public class CercaPrefix implements Cerca{

    //@Override
    //public void cercaDoc() {}

    public static ArrayList<String> cercaDoc(String prefix, Trie<String> autors) throws AutorInexistentException {
        ArrayList<String> resultat = autors.getParaules(prefix);
        if (resultat.isEmpty()) throw new AutorInexistentException(prefix);
        return autors.getParaules(prefix);
    }

}
