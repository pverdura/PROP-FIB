package Codi.Domini;

import Codi.Util.Pair;
import Codi.Util.TipusExtensio;

import java.util.HashMap;

public class CtrlDominiDocument {

    public CtrlDominiDocument () {

    }

    //Crea un document a partir del títol i de l’autor.
    public void creaDocument (String titol, String autor, HashMap<Pair<String, String>, Document> documents) {
        Pair<String, String> id = new Pair<>(titol, autor);
        Document doc = new Document(titol, autor);

        documents.put(id, doc);
    }

    //retorna false si ja existeix un document amb aquest títol i autor, true si s'ha modificat correctament
    public boolean setTitol (String titolVell, String autor, String titolNou, HashMap<Pair<String, String>, Document> documents) {
        Pair<String, String> idNou = new Pair<>(titolNou, autor);
        if (documents.containsKey(idNou)) return false;

        Pair<String, String> idVell = new Pair<>(titolVell, autor);
        Document d = documents.get(idVell);
        d.setTitol(titolNou);

        documents.remove(idVell);
        documents.put(idNou, d);

        return true;
    }

    //retorna false si ja existeix un document amb aquest títol i autor, true si s'ha modificat correctament
    public boolean setAutor (String titol, String autorVell, String autorNou, HashMap<Pair<String, String>, Document> documents) {
        Pair<String, String> idNou = new Pair<>(titol, autorNou);
        if (documents.containsKey(idNou)) return false;

        Pair<String, String> idVell = new Pair<>(titol, autorVell);
        Document d = documents.get(idVell);
        d.setAutor(autorNou);

        documents.remove(idVell);
        documents.put(idNou, d);

        return true;
    }

    //Modifica el contingut d’un document.
    public void setContingut (String titol, String autor, String contingut, HashMap<Pair<String, String>, Document> documents) {
        Pair<String, String> id = new Pair<>(titol, autor);

        documents.get(id).setContingut(contingut);
    }

    //Obté el contingut d’un document.
    public String getContingut (String titol, String autor, HashMap<Pair<String, String>, Document> documents) {
        Pair<String, String> id = new Pair<>(titol, autor);

        return documents.get(id).getContingut();
    }

    //Modifica la path d'un document.
    public void setPath (String titol, String autor, String path, HashMap<Pair<String, String>, Document> documents) {
        Pair<String, String> id = new Pair<>(titol, autor);

        documents.get(id).setPath(path);
    }

    //Obté la path d’un document.
    public String getPath (String titol, String autor, HashMap<Pair<String, String>, Document> documents) {
        Pair<String, String> id = new Pair<>(titol, autor);

        return documents.get(id).getPath();
    }

    //Modifica l'extensió d'un document
    public void setExtensio (String titol, String autor, TipusExtensio tipusExtensio, HashMap<Pair<String, String>, Document> documents) {
        Pair<String, String> id = new Pair<>(titol, autor);

        documents.get(id).setExtensio(tipusExtensio);
    }

    //Obté l'extensió d’un document.
    public TipusExtensio getExtensio (String titol, String autor, HashMap<Pair<String, String>, Document> documents) {
        Pair<String, String> id = new Pair<>(titol, autor);

        return documents.get(id).getExtensio();
    }

    //Obté el pes d’un document.
    public int getPes (String titol, String autor, HashMap<Pair<String, String>, Document> documents) {
        Pair<String, String> id = new Pair<>(titol, autor);

        return documents.get(id).getPes();
    }

    //Elimina un document.
    public void eliminaDocument (String titol, String autor, HashMap<Pair<String, String>, Document> documents) {
        Pair<String, String> id = new Pair<>(titol, autor);

        documents.remove(id);
    }

    //COSES SOBRE PERSISTÈNCIA
    /*
        void exportaDocument (String, String, HashMap<Pair<String, String>, Document>): ??????
        void importaDocument (String, String, HashMap<Pair<String, String>, Document>):
     */
}



