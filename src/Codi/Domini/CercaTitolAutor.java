package Codi.Domini;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;

public class CercaTitolAutor implements Cerca{
    //@Override
    //public static void cercaDoc() {}
    public static Document cercaDoc (String titol, String autor, HashMap<SimpleEntry<String, String>, Document> documents) {
        SimpleEntry<String, String> id = new SimpleEntry<>(titol, autor);
        //System.out.println(documents.get(id).getAutor());
        return documents.get(id);
    }
}
