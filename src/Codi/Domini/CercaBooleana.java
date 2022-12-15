package Codi.Domini;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;

/**
 * Classe que gestiona les cerques d'una expressio booleana
 * @author PauVi
 */
public class CercaBooleana implements Cerca {

    /**
     * Funció que realitza una cerca dels documents aplicant una expressio booleana
     * @param expressio Expressio booleana que realitza la cerca
     * @param docs Documents a cercar si compleixen l'expressio booleana
     * @return Retorna ArrayList dels documents que compleixen l'expressio booleana
     */
    public static ArrayList<SimpleEntry<String,String>> cercaDoc(ExpressioBooleana expressio, HashMap<SimpleEntry<String,String>,Document> docs) {

        ArrayList <SimpleEntry<String,String>> docs_valids = new ArrayList<>();

        //Per cada document del gestor comprovar si compleix l expressio booleana segons el seu contingut
        for (Map.Entry<SimpleEntry<String,String>,Document> doc : docs.entrySet()) {

            if (expressio.compleixCerca(doc.getValue().getContingut())) {
                docs_valids.add(
                        new SimpleEntry<String, String>(doc.getValue().getTitol(), doc.getValue().getAutor()));
            }
        }

        return docs_valids;
    }
}
