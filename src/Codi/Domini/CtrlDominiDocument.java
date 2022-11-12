package Codi.Domini;

import Codi.Excepcions.DocumentInexistentException;
import Codi.Excepcions.DocumentJaExisteixException;
import Codi.Util.TipusExtensio;
import Codi.Util.Trie;

import java.util.AbstractMap.SimpleEntry;

import java.util.ArrayList;
import java.util.HashMap;

public class CtrlDominiDocument {

    public CtrlDominiDocument () {

    }

    //Crea un document a partir del títol i de l’autor.
    public void creaDocument (String titol, String autor, HashMap<SimpleEntry<String, String>, Document> documents,
                              Trie<String> autors, HashMap<String,ArrayList<String>> documentsAutor,
                              HashMap<String,ArrayList<String>> titolAutors) throws DocumentJaExisteixException {
        SimpleEntry<String, String> id = new SimpleEntry<>(titol, autor);
        if (documents.containsKey(id)) throw new DocumentJaExisteixException(titol, autor);

        Document doc = new Document(titol, autor);

        documents.put(id, doc);     //afegir document a documents

        //afegir autor al trie de prefixos
        if (!autors.conteParaula(autor)) {
            autors.afegir(autor);
        }

        //afegir títol del document a la llista de documents creats per un autor
        if (!documentsAutor.containsKey(autor)) {
            ArrayList<String> aux = new ArrayList<>();
            aux.add(titol);
            documentsAutor.put(autor, aux);
        } else {
            documentsAutor.get(autor).add(titol);
        }

        //afegir autor a llista d'autors que han creat un document amb aquest nom
        if (!titolAutors.containsKey(titol)) {
            ArrayList<String> aux = new ArrayList<>();
            aux.add(autor);
            titolAutors.put(titol, aux);
        } else {
            titolAutors.get(titol).add(autor);
        }
    }

    //retorna false si ja existeix un document amb aquest títol i autor, true si s'ha modificat correctament
    public void setTitol (String titolVell, String titolNou, String autor, HashMap<SimpleEntry<String, String>, Document> documents,
                          HashMap<String,ArrayList<String>> documentsAutor,
                          HashMap<String,ArrayList<String>> titolAutors, HashMap<String,ArrayList<SimpleEntry<String,String>>> paraules) throws DocumentJaExisteixException, DocumentInexistentException {
        SimpleEntry<String, String> idNou = new SimpleEntry<>(titolNou, autor);
        if (documents.containsKey(idNou)) throw new DocumentJaExisteixException(titolVell, autor);

        SimpleEntry<String, String> idVell = new SimpleEntry<>(titolVell, autor);
        if (!documents.containsKey(idVell)) throw new DocumentInexistentException(titolVell, autor);
        Document d = documents.get(idVell);
        d.setTitol(titolNou);

        //actualitzar documentsAutor
        documentsAutor.get(autor).remove(titolVell);
        documentsAutor.get(autor).add(titolNou);

        //actualitzar titolAutors
        if (titolAutors.containsKey(titolNou)) {
            ArrayList<String> aux = titolAutors.get(titolVell);
            titolAutors.remove(titolVell);
            titolAutors.get(titolNou).addAll(aux);
        } else {
            ArrayList<String> aux = titolAutors.get(titolVell);
            titolAutors.remove(titolVell);
            titolAutors.put(titolNou, aux);
        }

        //actualitzar paraules
        ArrayList<String> p = documents.get(idVell).getParaules();

        for (String paraula : p) {
            paraules.get(paraula).remove(idVell);
            paraules.get(paraula).add(idNou);
        }

        documents.remove(idVell);
        documents.put(idNou, d);
    }

    //retorna false si ja existeix un document amb aquest títol i autor, true si s'ha modificat correctament
    public void setAutor (String titol, String autorVell, String autorNou, HashMap<SimpleEntry<String, String>, Document> documents,
                          Trie<String> autors, HashMap<String,ArrayList<String>> documentsAutor,
                          HashMap<String,ArrayList<String>> titolAutors, HashMap<String,ArrayList<SimpleEntry<String,String>>> paraules) throws DocumentJaExisteixException, DocumentInexistentException {
        SimpleEntry<String, String> idNou = new SimpleEntry<>(titol, autorNou);
        if (documents.containsKey(idNou)) throw new DocumentJaExisteixException(titol, autorNou);

        SimpleEntry<String, String> idVell = new SimpleEntry<>(titol, autorVell);
        if (!documents.containsKey(idVell)) throw new DocumentInexistentException(titol, autorVell);
        Document d = documents.get(idVell);
        d.setAutor(autorNou);

        //actualitzar trie
        autors.esborrar(autorVell);
        autors.afegir(autorNou);

        //actualitzar documentsAutor
        if (documentsAutor.containsKey(autorNou)) {
            ArrayList<String> aux = documentsAutor.get(autorVell);
            documentsAutor.remove(autorVell);
            documentsAutor.get(autorNou).addAll(aux);
        } else {
            ArrayList<String> aux = documentsAutor.get(autorVell);
            documentsAutor.remove(autorVell);
            documentsAutor.put(autorNou, aux);
        }

        //actualitzar titolAutors
        titolAutors.get(titol).remove(autorVell);
        titolAutors.get(titol).add(autorNou);

        //actualitzar paraules
        ArrayList<String> p = documents.get(idVell).getParaules();

        for (String paraula : p) {
            paraules.get(paraula).remove(idVell);
            paraules.get(paraula).add(idNou);
        }

        documents.remove(idVell);
        documents.put(idNou, d);
    }

    //Modifica el contingut d’un document.
    public void setContingut (String titol, String autor, String contingut, HashMap<SimpleEntry<String, String>, Document> documents,
                              HashMap<String,ArrayList<SimpleEntry<String,String>>> paraules) throws DocumentInexistentException {
        SimpleEntry<String, String> id = new SimpleEntry<>(titol, autor);
        if (!documents.containsKey(id)) throw new DocumentInexistentException(titol, autor);

        //esborrar paraules d'abans
        ArrayList<String> p = documents.get(id).getParaules();

        for (String paraula : p) {
            if (paraules.containsKey(paraula)) {
                paraules.get(paraula).remove(id);
                if (paraules.get(paraula).size() == 0) {
                    paraules.remove(paraula);
                }
            }
        }

        //actualitzar contingut
        documents.get(id).setContingut(contingut);

        //afegir paraules d'ara
        p = documents.get(id).getParaules();

        for (String paraula : p) {
            if (paraules.containsKey(paraula))
                if (!paraules.get(paraula).contains(id))
                    paraules.get(paraula).add(id);
            else {
                ArrayList<SimpleEntry<String, String>> aux = new ArrayList<>();
                aux.add(id);
                paraules.put(paraula, aux);
            }
        }
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
    public void eliminaDocument (String titol, String autor, HashMap<SimpleEntry<String, String>, Document> documents,
                                 Trie<String> autors, HashMap<String,ArrayList<String>> documentsAutor,
                                 HashMap<String,ArrayList<String>> titolAutors, HashMap<String,ArrayList<SimpleEntry<String,String>>> paraules) throws DocumentInexistentException {
        SimpleEntry<String, String> id = new SimpleEntry<>(titol, autor);
        if (!documents.containsKey(id)) throw new DocumentInexistentException(titol, autor);

        //eliminar autor de titolAutors
        titolAutors.get(titol).remove(autor);
        if (titolAutors.get(titol).size() == 0) titolAutors.remove(titol);

        //eliminar document de documentsAutor
        documentsAutor.get(autor).remove(titol);
        if (documentsAutor.get(autor).size() == 0) {
            documentsAutor.remove(autor);
            autors.esborrar(autor);  //si cal, eliminar autor del trie
        }

        //eliminar paraules
        ArrayList<String> p = documents.get(id).getParaules();

        for (String paraula : p) {
            paraules.get(paraula).remove(id);
            if (paraules.get(paraula).size() == 0) {
                paraules.remove(paraula);
            }
        }

        documents.remove(id); //eliminar document de documents
    }

    //Obté les stop words.
    public ArrayList<String> getStopWords () {
        return Document.getStopWords();
    }

    public void setStopWords (ArrayList<String> s) {
        Document.setStopWords(s);
    }

    public int getNombreDocuments (HashMap<SimpleEntry<String, String>, Document> documents) {
        return documents.size();
    }
}



