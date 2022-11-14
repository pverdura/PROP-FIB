package Codi.Domini;


import Codi.Excepcions.PrefixNoExisteixException;
import Codi.Util.Trie;

import java.util.ArrayList;

public class CercaPrefix implements Cerca{

    public static ArrayList<String> cercaDoc(String prefix, Trie<String> autors) throws PrefixNoExisteixException {
        ArrayList<String> resultat = autors.getParaules(prefix);
        if (resultat.isEmpty()) throw new PrefixNoExisteixException(prefix);
        return autors.getParaules(prefix);
    }

}
