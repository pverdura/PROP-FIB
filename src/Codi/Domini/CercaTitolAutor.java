package Codi.Domini;



import Codi.Excepcions.DocumentInexistentException;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;

public class CercaTitolAutor implements Cerca {
    //@Override
    //public static void cercaDoc() {}
    public static Document cercaDoc (String titol, String autor, HashMap<SimpleEntry<String, String>, Document> documents) throws DocumentInexistentException {
        SimpleEntry<String, String> id = new SimpleEntry<>(titol, autor);
        if (!documents.containsKey(id)) throw new DocumentInexistentException(titol, autor);

        return documents.get(id);
    }
    public static Document cercaDoc (SimpleEntry<String, String> id, HashMap<SimpleEntry<String, String>, Document> documents) throws DocumentInexistentException {
        if (!documents.containsKey(id)) throw new DocumentInexistentException(id.getKey(), id.getValue());

        return documents.get(id);
    }
}
