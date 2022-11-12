package Codi.Domini;


import Codi.Excepcions.AutorInexistent;
import Codi.Util.Trie;

import java.util.ArrayList;

public class CercaPrefix implements Cerca{

    //@Override
    //public void cercaDoc() {}

    public static ArrayList<String> cercaDoc(String prefix, Trie<String> autors) throws AutorInexistent {
        ArrayList<String> resultat = autors.getParaules(prefix);
        if (resultat.isEmpty()) throw new AutorInexistent(prefix);
        return autors.getParaules(prefix);
    }

}
