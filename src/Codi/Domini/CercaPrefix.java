package Codi.Domini;


import Codi.Util.Trie;

import java.util.ArrayList;

public class CercaPrefix implements Cerca{

    //@Override
    //public void cercaDoc() {}

    public static ArrayList<String> cercaDoc(String prefix, Trie<String> autors){
        return autors.getParaules(prefix);
    }

}
