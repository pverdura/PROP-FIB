// Classe controladora del domini

package Codi.Domini;

import Codi.Util.TipusExtensio;
import Codi.Util.Trie;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

public class CtrlDomini {

    ///////////////////////////////////////////////////////////
    ///                     ESTRUCTURES                     ///
    ///////////////////////////////////////////////////////////
    private HashMap<SimpleEntry<String,String>,Document> Documents; // Estructura on es guarden els documents
    private Trie<String> Autors; // Estructura on es guarden els autors (serveix per trobar el prefix)
    private HashMap<String,ArrayList<String>> DocumentsAutor; // Estructura on es guarden els títols dels documents creats per un autor
    private HashMap<String,ArrayList<String>> TitolAutors; // Estructura on es guarden els autors que han creat un document amb un títol
    private HashMap<String,ArrayList<SimpleEntry<String,String>>> Paraules; // Estructura on es guarden els documents que contenen la paraula
    private ArrayList<ExpressioBooleana> ExpressionsBooleanes; // Estructura on es guarden totes les expression booleanes


    ///////////////////////////////////////////////////////////
    ///                     AGREGACIONS                     ///
    ///////////////////////////////////////////////////////////

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
    ///             FUNCIONS DE LES ESTRUCTURES             ///
    ///////////////////////////////////////////////////////////

    /* Pre: -
     * Post: Retorna un llistat de tots els Documents del sistema
     */
    public HashMap<SimpleEntry<String,String>,Document> getDocuments() {
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
    public HashMap<String,ArrayList<SimpleEntry<String,String>>> getParaules() {
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
     * Post: True si s'ha modificat el contingut del document, false si no s'ha modificat
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
     * Post: True si s'ha modificat el path del document, false si no s'ha modificat
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
     * Post: True si s'ha modificat l'extensió del document, false si no s'ha modificat
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
     * Post: True si s'ha eliminat el document, false si no s'ha eliminat
     */
    public Boolean eliminaDocument(String titol, String autor) {
        CDdoc.eliminaDocument(titol,autor,Documents);
        return true;
    }


    ///////////////////////////////////////////////////////////
    ///             FUNCIONS CTRL_DOMINI_CERCA              ///
    ///////////////////////////////////////////////////////////

    /* Llista els documents (títol,autor) creats per l'autor "autor"
     * Pre:
     * Post:
     */
    public ArrayList<SimpleEntry<String,String>> cercaAutor(String autor) {
        return CDcer.cercaAutor(autor,DocumentsAutor);
    }

    /* Llista els documents que tenen com a títol "titol"
     * Pre:
     * Post:
     */
    public ArrayList<SimpleEntry<String,String>> cercaTitol(String titol) {
        return CDcer.cercaTitol(titol,TitolAutors);
    }

    /* Obté el document identificat per titol, autor
     * Pre:
     * Post:
     */
    public Document cercaTitolAutor(String titol, String autor) {
        return CDcer.cercaTitolAutor(titol,autor,Documents);
    }

    /* Llista els autors que contenen el prefix "prefix"
     * Pre:
     * Post:
     */
    public ArrayList<String> cercaPrefix(String prefix) {
        return CDcer.cercaPrefix(prefix,Autors);
    }

    /* Llista els k documents més semblants al document D
     * Pre: El Document D no té contingut buit, k > 0
     * Post: Un arraylist de longitud k amb els identificadors dels documents més semblants a D
     */
    public ArrayList<SimpleEntry<String,String>> cercaSemblant(Document D, Integer k) {
        return CDcer.cercaSemblant(D,k,Paraules,Documents);
    }

    /* Llista els k documents més rellevants segons l'array de paraules
     * Pre: L'array no és buit, k > 0
     * Post: Un arraylist de longitud k amb els identificadors dels documents més rellevants a l'array paraules
     */
    public ArrayList<SimpleEntry<String,String>> cercaParaules(ArrayList<String> paraules, Integer k) {
        return CDcer.cercaParaules(paraules,k,Paraules,Documents);
    }

    /* Llista els documents que compleixen la condició booleana expr
     * Pre: ??? expr no és buida
     * Post: Els documents que compleixen l'expressió expr
     */
    public Integer cercaBooleana(int expr) {
        return CDcer.cercaBooleana(expr);
    }


    ///////////////////////////////////////////////////////////
    ///           FUNCIONS CTRL_DOMINI_EXPR.BOOL.           ///
    ///////////////////////////////////////////////////////////

    /* Crea una expressió booleana formada per l'expressió expr
     * Pre: El String expr no és buit
     * Post: True si s'ha creat l'expressió, false si no s'ha creat
     */
    public Boolean creaExpressioBool(String expr) {
        CDeb.creaExpressioBool(expr,ExpressionsBooleanes);
        return true;
    }

    /* Elimina l'expressió booleana formada per l'expressió expr
     * Pre: El String expr no és buit
     * Post: True si s'ha eliminat el document, false si no s'ha eliminat
     */
    public Boolean eliminaExpressioBool(String expr) {
        CDeb.eliminaExpressioBool(expr,ExpressionsBooleanes);
        return true;
    }

    /* Modifica l'expressió booleana formada per l'expressió exprAnt
     * Pre: Els String exprAnt i exprNova no són buits
     * Post: True si la expressió passa a ser exprNova, false si la expressió continua sent exprAnt
     */
    public Boolean modificaExpressioBool(String exprAnt, String exprNova) {
        CDeb.modificaExpressioBool(exprAnt,exprNova,ExpressionsBooleanes);
        return true;
    }

}
