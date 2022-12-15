package Codi.Domini;


import Codi.Excepcions.PrefixNoExisteixException;
import Codi.Util.Trie;

import java.util.ArrayList;

/**
 * Classe que implementa la cerca d'autors per prefix
 *
 * @author Judit Serna
 */
public class CercaPrefix implements Cerca{

    /**
     * Cerca que retorna una llista d'autors que comencen per un prefix donat
     *
     * @param prefix Indica el prefix pel qual han de començar els noms dels autors
     * @param autors Estructura on estan guardats tots els noms dels autors de tots els documents
     * @return Llista amb noms d'autor que tenen el prefix donat
     * @throws PrefixNoExisteixException Si no existeix cap autor que comenci amb el prefix donat
     */
    public static ArrayList<String> cercaDoc(String prefix, Trie autors) throws PrefixNoExisteixException {
        ArrayList<String> resultat = autors.getParaules(prefix);
        if (resultat.isEmpty()) throw new PrefixNoExisteixException(prefix);
        return autors.getParaules(prefix);
    }

}
