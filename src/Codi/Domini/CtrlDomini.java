// Classe controladora del domini

package Codi.Domini;

import Codi.Util.Pair;
import Codi.Util.TipusExtensio;
import Codi.Util.Trie;

import java.util.ArrayList;
import java.util.HashMap;

public class CtrlDomini {

    ///////////////////////////////////////////////////////////
    ///                      ATRIBUTS                       ///
    ///////////////////////////////////////////////////////////
    private HashMap<Pair<String,String>,Document> Documents; // Estructura on es guarden els documents
    private Trie<String> Autors; // Estructura on es guarden els autors (serveix per trobar el prefix)
    private HashMap<String,ArrayList<String>> DocumentsAutor; // Estructura on es guarden els títols dels documents creats per un autor
    private HashMap<String,ArrayList<String>> TitolAutors; // Estructura on es guarden els autors que han creat un document amb un títol
    private HashMap<String,ArrayList<Pair<String,String>>> Paraules; // Estructura on es guarden els documents que contenen la paraula
    private ArrayList<ExpressioBooleana> ExpressionsBooleanes; // Estructura on es guarden totes les expression booleanes

    private CtrlDominiExprBool CDeb;    // Agregació del controlador d'expressió booleana

    private CtrlDominiDocument CDdoc;   // Agregació del controlador de document

    private CtrlDominiCerca CDcer;      // Agregació del controlador de cerca


    ///////////////////////////////////////////////////////////
    ///                      CREADORES                      ///
    ///////////////////////////////////////////////////////////

    // Crea i inicialitza els altres controladors
    public CtrlDomini() {
        CDeb = new CtrlDominiExprBool();
        CDdoc = new CtrlDominiDocument();
        CDcer = new CtrlDominiCerca();
    }


    ///////////////////////////////////////////////////////////
    ///                       GETTERS                       ///
    ///////////////////////////////////////////////////////////

    /* Pre: -
     * Post: Retorna un llistat de tots els Documents del sistema
     */
    public HashMap<Pair<String,String>,Document> getDocuments() {
        return Documents;
    }

    /* Pre: -
     * Post: Retorna un Arbre amb tots els autors del sistema
     */
    public Trie<String> getAutors() {
        return Autors;
    }

    /* Pre: -
     * Post: Retorna un llistat dels títols de cada autor
     */
    public HashMap<String,ArrayList<String>> getDocumentsAutor() {
        return DocumentsAutor;
    }

    /* Pre: -
     * Post: Retorna un llistat dels autors que han creat cada títol
     */
    public HashMap<String,ArrayList<String>> getTitolAutors() {
        return TitolAutors;
    }

    /* Pre: -
     * Post: Retorna les paraules de tots els documents i on apareix
     */
    public HashMap<String,ArrayList<Pair<String,String>>> getParaules() {
        return Paraules;
    }

    /* Pre: -
     * Post: Retorna totes les expressions booleanes del sistema
     */
    public ArrayList<ExpressioBooleana> getExpressionsBooleanes() {
        return ExpressionsBooleanes;
    }


    ///////////////////////////////////////////////////////////
    ///            FUNCIONS CTRL_DOMINI_DOCUMENT            ///
    ///////////////////////////////////////////////////////////

    /* Afegeix un nou document amb paràmetres (títol, autor)
     * Pre: Els String títol i autor no són buits
     * Post: True si s'ha creat el document, false si el document identificat per {títol,autor} ja existeix en el sistema
     */
    public Boolean creaDocument(String titol, String autor) {
        CDdoc.creaDocument(titol,autor,Documents);
        return true;
    }

    /* Modifica el contingut del document identificat per {títol, autor}
     * Pre: Els String títol i autor no són buits
     * Post: True si s'ha modificat el contingut del document, false si si no s'ha modificat
     */
    public Boolean setContingut(String titol, String autor, String contingut) {
        CDdoc.setContingut(titol,autor,contingut,Documents);
        return true;
    }

    /* Obté el contingut del document identificat per {títol, autor}
     * Pre: Els String títol i autor no són buits
     * Post: El contingut del document identificat per {títol, autor}
     */
    public String getContingut(String titol, String autor) {
        return CDdoc.getContingut(titol,autor,Documents);
    }

    /* Modifica el path del document identificat per {títol, autor}
     * Pre: Els String títol i autor no són buits, i el path existeix
     * Post: True si s'ha modificat el path del document, false si si no s'ha modificat
     */
    public Boolean setPath(String titol, String autor, String path) {
        CDdoc.setPath(titol,autor,path,Documents);
        return true;
    }

    /* Obté el path del document identificat per {títol, autor}
     * Pre: Els String títol i autor no són buits
     * Post: El path del document identificat per {títol, autor}
     */
    public String getPath(String titol, String autor) {
        return CDdoc.getPath(titol,autor,Documents);
    }

    /* Modifica l'extensió del document identificat per {títol, autor}
     * Pre: Els String títol i autor no són buits
     * Post: True si s'ha modificat l'extensió del document, false si si no s'ha modificat
     */
    public Boolean setExtensio(String titol, String autor, TipusExtensio ext) {
        CDdoc.setExtensio(titol,autor,ext,Documents);
        return true;
    }

    /* Obté l'extensió del document identificat per {títol, autor}
     * Pre: Els String títol i autor no són buits
     * Post: L'extensió del document
     */
    public TipusExtensio getExtensio(String titol, String autor) {
        return CDdoc.getExtensio(titol,autor,Documents);
    }

    /* Obté el pes del document identificat per {títol, autor}
     * Pre: Els String títol i autor no són buits
     * Post: El pes del document
     */
    public Integer getPes(String titol, String autor) {
        return CDdoc.getPes(titol,autor,Documents);
    }

    /* Elimina el document identificat per {títol, autor} del sistema
     * Pre: Els String títol i autor no són buits
     * Post: True si s'ha eliminat el document, false si si no s'ha eliminat
     */
    public Boolean eliminaDocument(String titol, String autor) {
        CDdoc.eliminaDocument(titol,autor,Documents);
        return true;
    }


    ///////////////////////////////////////////////////////////
    ///             FUNCIONS CTRL_DOMINI_CERCA              ///
    ///////////////////////////////////////////////////////////

    //...


    ///////////////////////////////////////////////////////////
    ///           FUNCIONS CTRL_DOMINI_EXPR.BOOL.           ///
    ///////////////////////////////////////////////////////////

    //
}
