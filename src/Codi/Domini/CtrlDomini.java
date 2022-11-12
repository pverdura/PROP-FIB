// Classe controladora del domini

package Codi.Domini;

import Codi.Util.*;
import Codi.Excepcions.*;

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
    private HashMap<String,ExpressioBooleana> ExpressionsBooleanes; // Estructura on es guarden totes les expression booleanes

    public HashMap<String,ArrayList<SimpleEntry<String,String>>> ListgetParaules() {
        return Paraules;
    }

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
        Documents = new HashMap<SimpleEntry<String,String>,Document>();
        Autors = new Trie<String>();
        DocumentsAutor = new HashMap<String,ArrayList<String>>();
        TitolAutors = new HashMap<String,ArrayList<String>>();
        Paraules = new HashMap<String,ArrayList<SimpleEntry<String,String>>>();
        ExpressionsBooleanes = new HashMap<String,ExpressioBooleana>();

        CDeb = new CtrlDominiExprBool();
        CDdoc = new CtrlDominiDocument();
        CDcer = new CtrlDominiCerca();
    }


    ///////////////////////////////////////////////////////////
    ///            FUNCIONS CTRL_DOMINI_DOCUMENT            ///
    ///////////////////////////////////////////////////////////

    /* Afegeix un nou document amb paràmetres (títol, autor)
     * Pre: Els String títol i autor no són buits
     * Post: S'ha creat el document identificat per (titol,autor)
     */
    public void creaDocument(String titol, String autor) throws DocumentJaExisteixException {
        CDdoc.creaDocument(titol,autor,Documents,Autors,DocumentsAutor,TitolAutors);
    }

    /* Elimina el document identificat per {títol, autor} del sistema
     * Pre: Els String títol i autor no són buits
     * Post: True si s'ha eliminat el document, false si no s'ha eliminat
     */
    public void eliminaDocument(String titol, String autor) {
        CDdoc.eliminaDocument(titol,autor,Documents,Autors,DocumentsAutor,TitolAutors,Paraules);
    }

    public void setTitol(String titolVell, String autor, String titolNou) throws DocumentJaExisteixException, DocumentInexistentException {
        CDdoc.setTitol(titolVell,autor,titolNou,Documents,DocumentsAutor,TitolAutors,Paraules);
    }


    public void setAutor(String titol, String autorVell, String autorNou) throws DocumentJaExisteixException, DocumentInexistentException  {
        CDdoc.setAutor(titol,autorVell,autorNou,Documents,Autors,DocumentsAutor,TitolAutors,Paraules);
    }

    /* Modifica el contingut del document identificat per {títol, autor}
     * Pre: Els String títol i autor no són buits
     * Post: True si s'ha modificat el contingut del document, false si no s'ha modificat
     */
    public void setContingut(String titol, String autor, String contingut) throws DocumentInexistentException {
        CDdoc.setContingut(titol,autor,contingut,Documents,Paraules);
    }

    /* Obté el contingut del document identificat per {títol, autor}
     * Pre: Els String títol i autor no són buits
     * Post: El contingut del document identificat per {títol, autor}
     */
    public String getContingut(String titol, String autor) throws DocumentInexistentException {
        return CDdoc.getContingut(titol,autor,Documents);
    }

    /* Modifica el path del document identificat per {títol, autor}
     * Pre: Els String títol i autor no són buits, i el path existeix
     * Post: True si s'ha modificat el path del document, false si no s'ha modificat
     */
    public void setPath(String titol, String autor, String path) throws DocumentInexistentException {
        CDdoc.setPath(titol,autor,path,Documents);
    }

    /* Obté el path del document identificat per {títol, autor}
     * Pre: Els String títol i autor no són buits
     * Post: El path del document identificat per {títol, autor}
     */
    public String getPath(String titol, String autor) throws DocumentInexistentException {
        return CDdoc.getPath(titol,autor,Documents);
    }

    /* Modifica l'extensió del document identificat per {títol, autor}
     * Pre: Els String títol i autor no són buits
     * Post: True si s'ha modificat l'extensió del document, false si no s'ha modificat
     */
    public void setExtensio(String titol, String autor, TipusExtensio ext) throws DocumentInexistentException {
        CDdoc.setExtensio(titol,autor,ext,Documents);
    }

    /* Obté l'extensió del document identificat per {títol, autor}
     * Pre: Els String títol i autor no són buits
     * Post: L'extensió del document
     */
    public TipusExtensio getExtensio(String titol, String autor) throws DocumentInexistentException {
        return CDdoc.getExtensio(titol,autor,Documents);
    }

    /* Obté el pes del document identificat per {títol, autor}
     * Pre: Els String títol i autor no són buits
     * Post: El pes del document
     */
    public Integer getPes(String titol, String autor) throws DocumentInexistentException {
        return CDdoc.getPes(titol,autor,Documents);
    }



    public ArrayList<String> getStopWords() {
        return CDdoc.getStopWords();
    }

    public void setStopWords(ArrayList<String> StopWords) {
        CDdoc.setStopWords(StopWords);
    }

    public Integer getNombreDocuments() {
        return CDdoc.getNombreDocuments(Documents);
    }


    ///////////////////////////////////////////////////////////
    ///             FUNCIONS CTRL_DOMINI_CERCA              ///
    ///////////////////////////////////////////////////////////

    /* Llista els documents (títol,autor) creats per l'autor "autor"
     * Pre:
     * Post:
     */
    public ArrayList<SimpleEntry<String,String>> cercaAutor(String autor, TipusOrdenacio ord) {
        return CDcer.cercaAutor(autor,DocumentsAutor,ord,Documents);
    }

    /* Llista els documents que tenen com a títol "titol"
     * Pre:
     * Post:
     */
    public ArrayList<SimpleEntry<String,String>> cercaTitol(String titol, TipusOrdenacio ord) {
        return CDcer.cercaTitol(titol,TitolAutors,ord,Documents);
    }

    /* Obté el document identificat per titol, autor
     * Pre:
     * Post:
     */
    public Document cercaTitolAutor(String titol, String autor) throws DocumentInexistentException {
        return CDcer.cercaTitolAutor(titol,autor,Documents);
    }

    /* Llista els autors que contenen el prefix "prefix"
     * Pre:
     * Post:
     */
    public ArrayList<String> cercaPrefix(String prefix, TipusOrdenacio ord) {
        return CDcer.cercaPrefix(prefix,Autors,ord);
    }

    /* Llista els k documents més semblants al document D
     * Pre: El Document D no té contingut buit, k > 0
     * Post: Un arraylist de longitud k amb els identificadors dels documents més semblants a D
     */
    public ArrayList<SimpleEntry<String,String>> cercaSemblant(String titol, String autor, Integer k) throws ArrayDeParaulesBuitException, NombreMassaPetitDocumentsException {
        return CDcer.cercaSemblant(titol,autor,k,Paraules,Documents);
    }

    /* Llista els k documents més rellevants segons l'array de paraules
     * Pre: L'array no és buit, k > 0
     * Post: Un arraylist de longitud k amb els identificadors dels documents més rellevants a l'array paraules
     */
    public ArrayList<SimpleEntry<String,String>> cercaParaules(String paraules, Integer k) throws ArrayDeParaulesBuitException, NombreMassaPetitDocumentsException {
        return CDcer.cercaParaules(paraules,k,Paraules,Documents);
    }

    /* Llista els documents que compleixen la condició booleana expr
     * Pre: ??? expr no és buida
     * Post: Els documents que compleixen l'expressió expr
     */
    public ArrayList<SimpleEntry<String,String>> cercaBooleana(String expr, TipusOrdenacio ord) throws ExpressioBooleanaInexistentException {
        return CDcer.cercaBooleana(expr,Documents,ExpressionsBooleanes,ord);
    }

    public ArrayList<SimpleEntry<String,String>> cercaAllDocuments(TipusOrdenacio ord){
        return CDcer.cercaAllDocuments(Documents,ord);
    }

    public ArrayList<String> cercaAllExpressionsBool(TipusOrdenacio ord) {
        return CDcer.cercaAllExpressionsBool(ExpressionsBooleanes,ord);
    }


    ///////////////////////////////////////////////////////////
    ///           FUNCIONS CTRL_DOMINI_EXPR.BOOL.           ///
    ///////////////////////////////////////////////////////////

    /* Crea una expressió booleana formada per l'expressió expr
     * Pre: El String expr no és buit
     * Post: True si s'ha creat l'expressió, false si no s'ha creat
     */
    public void creaExpressioBool(String expr) throws ExpressioBooleanaJaExistentException {
        CDeb.creaExpressioBool(expr,ExpressionsBooleanes);
    }

    /* Elimina l'expressió booleana formada per l'expressió expr
     * Pre: El String expr no és buit
     * Post: True si s'ha eliminat el document, false si no s'ha eliminat
     */
    public void eliminaExpressioBool(String expr) throws ExpressioBooleanaInexistentException {
        CDeb.eliminaExpressioBool(expr,ExpressionsBooleanes);
    }

    /* Modifica l'expressió booleana formada per l'expressió exprAnt
     * Pre: Els String exprAnt i exprNova no són buits
     * Post: True si la expressió passa a ser exprNova, false si la expressió continua sent exprAnt
     */
    public void modificaExpressioBool(String exprAnt, String exprNova) throws ExpressioBooleanaInexistentException, ExpressioBooleanaJaExistentException {
        CDeb.modificaExpressioBool(exprAnt,exprNova,ExpressionsBooleanes);
    }

}
