package Codi.Domini;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;

public class CercaAllDocuments implements Cerca {

    //@Override
    //public static void cercaDoc() {}
    public static ArrayList<AbstractMap.SimpleEntry<String, String>> cercaDoc (HashMap<AbstractMap.SimpleEntry<String, String>, Document> documents) {
        ArrayList<AbstractMap.SimpleEntry<String, String>> resultat = new ArrayList<AbstractMap.SimpleEntry<String, String>>(documents.keySet());

        return resultat;
    }
}