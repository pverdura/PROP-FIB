package Codi.Domini;

import Codi.Util.Pair;
import Codi.Util.TipusExtensio;

import java.util.HashMap;

public class CtrlDominiDocument {

    public CtrlDominiDocument () {

    }

    //Crea un document a partir del títol i de l’autor.
    public void creaDocument (String titol, String autor, HashMap<Pair<String, String>, Document> documents) {
        Pair<String, String> id = new Pair<String, String>(titol, autor);
        Document doc = new Document(titol, autor);

        documents.put(id, doc);
    }

    //Modifica el contingut d’un document.
    public void setContingut (String titol, String autor, String contingut, HashMap<Pair<String, String>, Document> documents) {
        Pair<String, String> id = new Pair<String, String>(titol, autor);

        documents.get(id).setContingut(contingut);
    }

    //Obté el contingut d’un document.
    public String getContingut (String titol, String autor, HashMap<Pair<String, String>, Document> documents) {
        Pair<String, String> id = new Pair<String, String>(titol, autor);

        return documents.get(id).getContingut();
    }

    //Modifica la path d'un document.
    public void setPath (String titol, String autor, String path, HashMap<Pair<String, String>, Document> documents) {
        Pair<String, String> id = new Pair<String, String>(titol, autor);

        documents.get(id).setPath(path);
    }

    //Obté la path d’un document.
    public String getPath (String titol, String autor, HashMap<Pair<String, String>, Document> documents) {
        Pair<String, String> id = new Pair<String, String>(titol, autor);

        return documents.get(id).getPath();
    }

    //Modifica l'extensió d'un document
    public void setExtensio (String titol, String autor, TipusExtensio tipusExtensio, HashMap<Pair<String, String>, Document> documents) {
        Pair<String, String> id = new Pair<String, String>(titol, autor);

    }

    //Obté l'extensió d’un document.
    public TipusExtensio getExtensio (String titol, String autor, HashMap<Pair<String, String>, Document> documents) {
        Pair<String, String> id = new Pair<String, String>(titol, autor);

        return documents.get(id).getExtensio();
    }

    //Obté el pes d’un document.
    public int getPes (String titol, String autor, HashMap<Pair<String, String>, Document> documents) {
        Pair<String, String> id = new Pair<String, String>(titol, autor);

        return documents.get(id).getPes();
    }

    //Elimina un document.
    public void eliminaDocument (String titol, String autor, HashMap<Pair<String, String>, Document> documents) {
        Pair<String, String> id = new Pair<String, String>(titol, autor);

        documents.remove(id);
    }

    //COSES SOBRE PERSISTÈNCIA
    /*
        void exportaDocument (String, String, HashMap<Pair<String, String>, Document>): ??????
        void importaDocument (String, String, HashMap<Pair<String, String>, Document>):
     */
}



