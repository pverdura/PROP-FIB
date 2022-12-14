package Codi.Domini;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *  Classe que implementa la cerca de tots els documents existents
 */

public class CercaAllDocuments implements Cerca {
    /**
     * Cerca tots els documents existents
     *
     * @param documents Estructura de dades {@code HashMap<SimpleEntry<String, String>>} dels documents
     * @return {@code ArrayList<SimpleEntry<String, String>>} el resultat de la cerca
     */
    public static ArrayList<SimpleEntry<String, String>> cercaDoc (HashMap<SimpleEntry<String, String>, Document> documents) {

        return new ArrayList<SimpleEntry<String, String>>(documents.keySet());
    }
}