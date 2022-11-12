package Codi.Domini;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;

public class CercaBooleana implements Cerca {

    public static ArrayList<SimpleEntry<String,String>> cercaDoc(ExpressioBooleana expressio, HashMap<SimpleEntry<String,String>,Document> docs) {

        ArrayList <SimpleEntry<String,String>> docs_valids = new ArrayList<>();

        for (Map.Entry<SimpleEntry<String,String>,Document> doc : docs.entrySet()) {

            if (expressio.compleixCerca(doc.getValue().getContingut())) {
                docs_valids.add(
                        new SimpleEntry<String, String>(doc.getValue().getTitol(), doc.getValue().getAutor()));
            }
        }

        return docs_valids;
    }
}
