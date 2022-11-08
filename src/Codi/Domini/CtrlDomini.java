// Classe controladora del domini

package Codi.Domini;

import Codi.Util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class CtrlDomini {

    private HashMap<Pair<String,String>,Document> Documents; // Estructura on es guarden els documents
    private Trie<String> Autors; // Estructura on es guarden els autors (serveix per trobar el prefix)
    private HashMap<String,ArrayList<String>> DocumentsAutor; // Estructura on es guarden els títols dels documents creats per un autor
    private HashMap<String,ArrayList<String>> TitolAutors; // Estructura on es guarden els autors que han creat un document amb un títol
    private HashMap<String,ArrayList<Pair<String,String>>> Paraules; // Estructura on es guarden els documents que contenen la paraula
    private ArrayList<ExpressioBooleana> ExpressionsBooleanes; // Estructura on es guarden totes les expression booleanes

    public CtrlDomini() {}

    /* Pre: -
     * Post: Retorna un llistat de tots els Documents del sistema
     */
    protected HashMap<Pair<String,String>,Document> getDocuments() {
        return Documents;
    }

    /* Pre: -
     * Post: Retorna un Arbre amb tots els autors del sistema
     */
    protected Trie<String> getAutors() {
        return Autors;
    }

    /* Pre: -
     * Post: Retorna un llistat dels títols de cada autor
     */
    protected HashMap<String,ArrayList<String>> getDocumentsAutor() {
        return DocumentsAutor;
    }

    /* Pre: -
     * Post: Retorna un llistat dels autors que han creat cada títol
     */
    protected HashMap<String,ArrayList<String>> getTítolAutors() {
        return TitolAutors;
    }

    /* Pre: -
     * Post: Retorna les paraules de tots els documents i on apareix
     */
    protected HashMap<String,ArrayList<Pair<String,String>>> getParaules() {
        return Paraules;
    }

    /* Pre: -
     * Post: Retorna totes les expressions booleanes del sistema
     */
    protected ArrayList<ExpressioBooleana> getExpressionsBooleanes() {
        return ExpressionsBooleanes;
    }

    /* Afegeix un nou document amb paràmetres (títol, autor)
     * Pre: Els String titol i autor no són buits
     * Post: True si s'ha creat el document, false si el document identificat per {titol,autor} ja existeix en el sistema
     */
    public Boolean creaDocument(String titol, String autor) {
        return null;
    }
}
