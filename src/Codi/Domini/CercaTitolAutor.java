package Codi.Domini;

import Codi.Util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class CercaTitolAutor implements Cerca{
    //@Override
    //public static void cercaDoc() {}
    public static Document cercaDoc (String titol, String autor,HashMap<Pair<String, String>, Document> documents) {
        Pair<String, String> id = new Pair<String, String>(titol, autor);

        return documents.get(id);
    }
}
