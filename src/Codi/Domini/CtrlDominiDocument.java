package Codi.Domini;

import Codi.Util.DocumentInexistentException;
import Codi.Util.DocumentJaExisteixException;
import Codi.Util.TipusExtensio;
import java.util.AbstractMap.SimpleEntry;

import java.util.ArrayList;
import java.util.HashMap;

public class CtrlDominiDocument {

    public CtrlDominiDocument () {

    }

    //Crea un document a partir del títol i de l’autor.
    public void creaDocument (String titol, String autor, HashMap<SimpleEntry<String, String>, Document> documents) throws DocumentJaExisteixException {
        SimpleEntry<String, String> id = new SimpleEntry<>(titol, autor);
        if (documents.containsKey(id)) throw new DocumentJaExisteixException(titol, autor);

        Document doc = new Document(titol, autor);

        documents.put(id, doc);
    }

    //retorna false si ja existeix un document amb aquest títol i autor, true si s'ha modificat correctament
    public void setTitol (String titolVell, String autor, String titolNou, HashMap<SimpleEntry<String, String>, Document> documents) throws DocumentJaExisteixException, DocumentInexistentException {
        SimpleEntry<String, String> idNou = new SimpleEntry<>(titolNou, autor);
        if (documents.containsKey(idNou)) throw new DocumentJaExisteixException(titolVell, autor);

        SimpleEntry<String, String> idVell = new SimpleEntry<>(titolVell, autor);
        if (!documents.containsKey(idVell)) throw new DocumentInexistentException(titolVell, autor);
        Document d = documents.get(idVell);
        d.setTitol(titolNou);

        documents.remove(idVell);
        documents.put(idNou, d);
    }

    //retorna false si ja existeix un document amb aquest títol i autor, true si s'ha modificat correctament
    public void setAutor (String titol, String autorVell, String autorNou, HashMap<SimpleEntry<String, String>, Document> documents) throws DocumentJaExisteixException, DocumentInexistentException {
        SimpleEntry<String, String> idNou = new SimpleEntry<>(titol, autorNou);
        if (documents.containsKey(idNou)) throw new DocumentJaExisteixException(titol, autorNou);

        SimpleEntry<String, String> idVell = new SimpleEntry<>(titol, autorVell);
        if (!documents.containsKey(idVell)) throw new DocumentInexistentException(titol, autorVell);
        Document d = documents.get(idVell);
        d.setAutor(autorNou);

        documents.remove(idVell);
        documents.put(idNou, d);
    }

    //Modifica el contingut d’un document.
    public void setContingut (String titol, String autor, String contingut, HashMap<SimpleEntry<String, String>, Document> documents) throws DocumentInexistentException {
        SimpleEntry<String, String> id = new SimpleEntry<>(titol, autor);
        if (!documents.containsKey(id)) throw new DocumentInexistentException(titol, autor);

        documents.get(id).setContingut(contingut);
    }

    //Obté el contingut d’un document.
    public String getContingut (String titol, String autor, HashMap<SimpleEntry<String, String>, Document> documents) throws DocumentInexistentException {
        SimpleEntry<String, String> id = new SimpleEntry<>(titol, autor);
        if (!documents.containsKey(id)) throw new DocumentInexistentException(titol, autor);

        return documents.get(id).getContingut();
    }

    //Modifica la path d'un document.
    public void setPath (String titol, String autor, String path, HashMap<SimpleEntry<String, String>, Document> documents) throws DocumentInexistentException {
        SimpleEntry<String, String> id = new SimpleEntry<>(titol, autor);
        if (!documents.containsKey(id)) throw new DocumentInexistentException(titol, autor);

        documents.get(id).setPath(path);
    }

    //Obté la path d’un document.
    public String getPath (String titol, String autor, HashMap<SimpleEntry<String, String>, Document> documents) throws DocumentInexistentException {
        SimpleEntry<String, String> id = new SimpleEntry<>(titol, autor);
        if (!documents.containsKey(id)) throw new DocumentInexistentException(titol, autor);

        return documents.get(id).getPath();
    }

    //Modifica l'extensió d'un document
    public void setExtensio (String titol, String autor, TipusExtensio tipusExtensio, HashMap<SimpleEntry<String, String>, Document> documents) throws DocumentInexistentException {
        SimpleEntry<String, String> id = new SimpleEntry<>(titol, autor);
        if (!documents.containsKey(id)) throw new DocumentInexistentException(titol, autor);

        documents.get(id).setExtensio(tipusExtensio);
    }

    //Obté l'extensió d’un document.
    public TipusExtensio getExtensio (String titol, String autor, HashMap<SimpleEntry<String, String>, Document> documents) throws DocumentInexistentException {
        SimpleEntry<String, String> id = new SimpleEntry<>(titol, autor);
        if (!documents.containsKey(id)) throw new DocumentInexistentException(titol, autor);

        return documents.get(id).getExtensio();
    }

    //Obté el pes d’un document.
    public int getPes (String titol, String autor, HashMap<SimpleEntry<String, String>, Document> documents) throws DocumentInexistentException {
        SimpleEntry<String, String> id = new SimpleEntry<>(titol, autor);
        if (!documents.containsKey(id)) throw new DocumentInexistentException(titol, autor);

        return documents.get(id).getPes();
    }

    //Elimina un document.
    public void eliminaDocument (String titol, String autor, HashMap<SimpleEntry<String, String>, Document> documents) throws DocumentInexistentException {
        SimpleEntry<String, String> id = new SimpleEntry<>(titol, autor);
        if (!documents.containsKey(id)) throw new DocumentInexistentException(titol, autor);

        documents.remove(id);
    }

    //Obté les stop words.
    public ArrayList<String> getStopWords () {
        return Document.getStopWords();
    }
}



