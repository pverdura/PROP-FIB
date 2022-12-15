package Codi.Domini;

import Codi.Excepcions.DocumentInexistentException;
import Codi.Excepcions.DocumentJaExisteixException;
import Codi.Util.DocumentLlegit;
import Codi.Util.TipusExtensio;
import Codi.Util.Trie;

import java.util.AbstractMap.SimpleEntry;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *  Classe per gestionar els documents a la capa de domini
 *
 * @author Jordi Palomera
 * @since 13-12-2022
 */

public class CtrlDominiDocument {

    /**
     * Crea un document a partir del seu titol i autor i l'afegeix a les estructures de dades
     *
     * @param titol Titol del document
     * @param autor Autor del document
     * @param documents Estructura de dades {@code HashMap<SimpleEntry<String, String>>} dels documents
     * @param autors Estructura de dades {@code Trie<String>} dels autors
     * @param documentsAutor Estructura de dades {@code HashMap<String, ArrayList<String>>} dels documents que ha creat cada autor
     * @param titolAutors Estructura de dades dels {@code HashMap<String, ArrayList<String>>} autors que han creat un document de cada titol
     * @throws DocumentJaExisteixException si ja existeix un document amb el mateix titol i autor
     */
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

        //afegir titol del document a la llista de documents creats per un autor
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

    /**
     * Modifica el titol i/o l'autor d'un document
     *
     * @param idVell Antic identificador del document
     * @param idNou Nou identificador del document
     * @param documents Estructura de dades {@code HashMap<SimpleEntry<String, String>>} dels documents
     * @param documentsAutor Estructura de dades {@code HashMap<String, ArrayList<String>>} dels documents que ha creat cada autor
     * @param autors Estructura de dades {@code Trie<String>} dels autors
     * @param titolAutors Estructura de dades dels {@code HashMap<String, ArrayList<String>>} autors que han creat un document de cada titol
     * @param paraules Estructura de dades {HashMap<String, ArrayList<SimpleEntry<String, String>>>} de quins documents contenen cada paraula
     * @throws DocumentJaExisteixException si ja existeix un document amb l'identificador nou
     * @throws DocumentInexistentException si no existeix cap document amb l'identificador vell
     */
    public void modificarIdentificador (SimpleEntry<String, String> idVell, SimpleEntry<String, String> idNou, HashMap<SimpleEntry<String, String>, Document> documents,
                                        HashMap<String,ArrayList<String>> documentsAutor, Trie<String> autors,
                                        HashMap<String,ArrayList<String>> titolAutors, HashMap<String,ArrayList<SimpleEntry<String,String>>> paraules) throws DocumentJaExisteixException, DocumentInexistentException {
        String titolVell = idVell.getKey();
        String titolNou = idNou.getKey();
        String autorVell = idVell.getValue();
        String autorNou = idNou.getValue();

        if (documents.containsKey(idNou)) throw new DocumentJaExisteixException(titolNou, autorNou);
        if (!documents.containsKey(idVell)) throw new DocumentInexistentException(titolVell, autorVell);

        boolean titolModificat = (!titolVell.equals(titolNou));
        boolean autorModificat = (!autorVell.equals(autorNou));

        Document d = documents.get(idVell);
        if (titolModificat) d.setTitol(titolNou);
        if (autorModificat) d.setAutor(autorNou);

        //documentsAutor
        if (autorModificat) {
            //titol i autor
            documentsAutor.get(autorVell).remove(titolVell);
            if (documentsAutor.get(autorVell).isEmpty()) documentsAutor.remove(autorVell);

            if (documentsAutor.containsKey(autorNou)) {
                documentsAutor.get(autorNou).add(titolNou);
            } else {
                ArrayList<String> aux = new ArrayList<>();
                aux.add(titolNou);
                documentsAutor.put(autorNou, aux);
            }
        } else if (titolModificat) {
            //nomes titol
            documentsAutor.get(autorNou).remove(titolVell);
            documentsAutor.get(autorNou).add(titolNou);
        }

        //titolAutors
        if (titolModificat) {
            //titol i autor
            titolAutors.get(titolVell).remove(autorVell);
            if (titolAutors.get(titolVell).isEmpty()) titolAutors.remove(titolVell);

            if (titolAutors.containsKey(titolNou)) {
                titolAutors.get(titolNou).add(autorNou);
            } else {
                ArrayList<String> aux = new ArrayList<>();
                aux.add(autorNou);
                titolAutors.put(titolNou, aux);
            }
        } else if (autorModificat) {
            //nomes titol
            titolAutors.get(titolNou).remove(autorVell);
            titolAutors.get(titolNou).add(autorNou);
        }

        //autors trie
        autors.esborrar(autorVell);
        autors.afegir(autorNou);

        //paraules
        ArrayList<String> p = documents.get(idVell).getParaules();

        for (String paraula : p) {
            paraules.get(paraula).remove(idVell);
            paraules.get(paraula).add(idNou);
        }

        //documents
        documents.remove(idVell);
        documents.put(idNou, d);
    }

    /**
     * Crea un document a partir d'un {@link DocumentLlegit}
     *
     * @param doc {@code DocumentLlegit}
     * @param documents Estructura de dades {@code HashMap<SimpleEntry<String, String>>} dels documents
     * @param autors Estructura de dades {@code Trie<String>} dels autors
     * @param documentsAutor Estructura de dades {@code HashMap<String, ArrayList<String>>} dels documents que ha creat cada autor
     * @param titolAutors Estructura de dades dels {@code HashMap<String, ArrayList<String>>} autors que han creat un document de cada titol
     * @param paraules Estructura de dades {HashMap<String, ArrayList<SimpleEntry<String, String>>>} de quins documents contenen cada paraula
     * @throws DocumentJaExisteixException si ja existeix un document amb el titol i l'autor del {@code DocumentLlegit}
     */
    public void llegirDocument (DocumentLlegit doc, HashMap<SimpleEntry<String, String>, Document> documents,
                                Trie<String> autors, HashMap<String,ArrayList<String>> documentsAutor,
                                HashMap<String,ArrayList<String>> titolAutors, HashMap<String,ArrayList<SimpleEntry<String,String>>> paraules) throws DocumentJaExisteixException {
        this.creaDocument(doc.getTitol(), doc.getAutor(), documents, autors, documentsAutor, titolAutors);
        this.setContingut(doc.getTitol(), doc.getAutor(), doc.getContingut(), documents, paraules);
        this.setPath(doc.getTitol(), doc.getAutor(), doc.getPath(), documents);
        this.setExtensio(doc.getTitol(), doc.getAutor(), doc.getExtensio(), documents);
    }

    /**
     * Elimina un document i les seves aparicions a les estructures de dades
     *
     * @param titol titol del document a eliminar
     * @param autor Autor del document a eliminar
     * @param documents Estructura de dades {@code HashMap<SimpleEntry<String, String>>} dels documents
     * @param autors Estructura de dades {@code Trie<String>} dels autors
     * @param documentsAutor Estructura de dades {@code HashMap<String, ArrayList<String>>} dels documents que ha creat cada autor
     * @param titolAutors Estructura de dades dels {@code HashMap<String, ArrayList<String>>} autors que han creat un document de cada titol
     * @param paraules Estructura de dades {HashMap<String, ArrayList<SimpleEntry<String, String>>>} de quins documents contenen cada paraula
     * @throws DocumentInexistentException si el document que s'intenta eliminar no existeix
     */
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
            if (paraules.containsKey(paraula)) {
                paraules.get(paraula).remove(id);
                if (paraules.get(paraula).size() == 0) {
                    paraules.remove(paraula);
                }
            }
        }

        documents.remove(id); //eliminar document de documents
    }

    /**
     * Modifica el contingut d'un document
     *
     * @param titol titol del document
     * @param autor Autor del document
     * @param contingut Nou contingut del document
     * @param documents Estructura de dades {@code HashMap<SimpleEntry<String, String>>} dels documents
     * @param paraules Estructura de dades {HashMap<String, ArrayList<SimpleEntry<String, String>>>} de quins documents contenen cada paraula
     * @throws DocumentInexistentException si el document el contingut del qual s'intenta modificar no existeix
     */
    public void setContingut (String titol, String autor, String contingut, HashMap<SimpleEntry<String, String>, Document> documents,
                              HashMap<String, ArrayList<SimpleEntry<String,String>>> paraules) throws DocumentInexistentException {
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
            if (paraules.containsKey(paraula)) {
                if (!paraules.get(paraula).contains(id)) {
                    paraules.get(paraula).add(id);
                }
            }
            else {
                ArrayList<SimpleEntry<String, String>> aux = new ArrayList<>();
                aux.add(id);
                paraules.put(paraula, aux);
            }
        }
    }

    /**
     * Obte el contingut d'un document
     *
     * @param titol titol del document
     * @param autor Autor del document
     * @param documents Estructura de dades {@code HashMap<SimpleEntry<String, String>>} dels documents
     * @return  El contingut del document
     * @throws DocumentInexistentException si el document el contingut del qual s'intenta obtenir no existeix
     */
    public String getContingut (String titol, String autor, HashMap<SimpleEntry<String, String>, Document> documents) throws DocumentInexistentException {
        SimpleEntry<String, String> id = new SimpleEntry<>(titol, autor);
        if (!documents.containsKey(id)) throw new DocumentInexistentException(titol, autor);

        return documents.get(id).getContingut();
    }

    /**
     * Modifica la path d'un document
     *
     * @param titol titol del document
     * @param autor Autor del document
     * @param path Nova path del document
     * @param documents Estructura de dades {@code HashMap<SimpleEntry<String, String>>} dels documents
     * @throws DocumentInexistentException si el document la path del qual s'intenta modificar no existeix
     */
    public void setPath (String titol, String autor, String path, HashMap<SimpleEntry<String, String>, Document> documents) throws DocumentInexistentException {
        SimpleEntry<String, String> id = new SimpleEntry<>(titol, autor);
        if (!documents.containsKey(id)) throw new DocumentInexistentException(titol, autor);

        documents.get(id).setPath(path);
    }

    /**
     * Obte la path d'un document
     *
     * @param titol titol del document
     * @param autor Autor del document
     * @param documents Estructura de dades {@code HashMap<SimpleEntry<String, String>>} dels documents
     * @return La path del document
     * @throws DocumentInexistentException si el document la path del qual s'intenta obtenir no existeix
     */
    public String getPath (String titol, String autor, HashMap<SimpleEntry<String, String>, Document> documents) throws DocumentInexistentException {
        SimpleEntry<String, String> id = new SimpleEntry<>(titol, autor);
        if (!documents.containsKey(id)) throw new DocumentInexistentException(titol, autor);

        return documents.get(id).getPath();
    }

    /**
     * Modifica l'extensio d'un document
     *
     * @param titol titol del document
     * @param autor Autor del document
     * @param tipusExtensio Nova {@link TipusExtensio} extensio del document
     * @param documents Estructura de dades {@code HashMap<SimpleEntry<String, String>>} dels documents
     * @throws DocumentInexistentException si el document l'extensio del qual s'intenta modificar no existeix
     */
    public void setExtensio (String titol, String autor, TipusExtensio tipusExtensio, HashMap<SimpleEntry<String, String>, Document> documents) throws DocumentInexistentException {
        SimpleEntry<String, String> id = new SimpleEntry<>(titol, autor);
        if (!documents.containsKey(id)) throw new DocumentInexistentException(titol, autor);

        documents.get(id).setExtensio(tipusExtensio);
    }

    /**
     * Obte l'extensio d'un document
     *
     * @param titol titol del document
     * @param autor Autor del document
     * @param documents Estructura de dades {@code HashMap<SimpleEntry<String, String>>} dels documents
     * @return L'extensio del document
     * @throws DocumentInexistentException si el document l'extensio del qual s'intenta obtenir no existeix
     */
    public TipusExtensio getExtensio (String titol, String autor, HashMap<SimpleEntry<String, String>, Document> documents) throws DocumentInexistentException {
        SimpleEntry<String, String> id = new SimpleEntry<>(titol, autor);
        if (!documents.containsKey(id)) throw new DocumentInexistentException(titol, autor);

        return documents.get(id).getExtensio();
    }

    /**
     * Obte el pes d'un document
     *
     * @param titol
     * @param autor
     * @param documents Estructura de dades {@code HashMap<SimpleEntry<String, String>>} dels documents
     * @return El pes (quantitat de caracters) d'un document
     * @throws DocumentInexistentException si el document el pes del qual s'intenta obtenir no existeix
     */
    public int getPes (String titol, String autor, HashMap<SimpleEntry<String, String>, Document> documents) throws DocumentInexistentException {
        SimpleEntry<String, String> id = new SimpleEntry<>(titol, autor);
        if (!documents.containsKey(id)) throw new DocumentInexistentException(titol, autor);

        return documents.get(id).getPes();
    }

    /**
     * Modifica les stop words dels documents
     *
     * @param s {@code ArrayList<String>} noves stop words
     */
    public void setStopWords (ArrayList<String> s) {
        Document.setStopWords(s);
    }

    /**
     * Obte el nombre de documents existents a l'aplicacio
     *
     * @param documents Estructura de dades {@code HashMap<SimpleEntry<String, String>>} dels documents
     * @return El nombre de documents existents a l'aplicacio
     */
    public int getNombreDocuments (HashMap<SimpleEntry<String, String>, Document> documents) {
        return documents.size();
    }
}



