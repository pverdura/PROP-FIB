package Codi.Domini;

import Codi.Excepcions.AutorNoExisteixException;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe que implementa la cerca de documents per l'autor
 *
 * @author Judit Serna
 */
public class CercaAutor implements Cerca{

    /**
     * Cerca que retorna una llista amb els títols dels documents fets per un autor
     *
     * @param autor Indica el nom de l'autor dels documents a cercar
     * @param autorTitols Estructura de dades on cada autor té un llistat de títols
     * @return Llista dels títols dels documents de l'autor indicat
     * @throws AutorNoExisteixException Si l'autor indicat no existeix
     */
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
