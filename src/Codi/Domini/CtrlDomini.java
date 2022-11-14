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

    private HashMap<SimpleEntry<String,String>,Document> Documents;         // Estructura on es guarden els documents
    private Trie<String> Autors;                                            // Estructura on es guarden els autors (serveix per trobar el prefix)
    private HashMap<String,ArrayList<String>> DocumentsAutor;               // Estructura on es guarden els títols dels documents creats per un autor
    private HashMap<String,ArrayList<String>> TitolAutors;                  // Estructura on es guarden els autors que han creat un document amb un títol en concret
    private HashMap<String,ArrayList<SimpleEntry<String,String>>> Paraules; // Estructura on es guarden els documents que contenen una paraula en concret
    private HashMap<String,ExpressioBooleana> ExpressionsBooleanes;         // Estructura on es guarden totes les expression booleanes

    public HashMap<String,ArrayList<SimpleEntry<String,String>>> getParaules() {
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

    // Crea i inicialitza els altres controladors i estructures
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

    /* Afegeix un nou document al sistema
     * Pre: El Document identificat per (titol,autor) no existeix
     * Post: S'ha creat el document identificat per (titol,autor)
     */
    public void creaDocument(String titol, String autor) throws DocumentJaExisteixException {
        CDdoc.creaDocument(titol,autor,Documents,Autors,DocumentsAutor,TitolAutors);
    }

    /* Elimina un document del sistema
     * Pre: El Document identificat per (titol,autor) existeix
     * Post: S'ha eliminat el document identificat per (titol,autor) del sistema
     */
    public void eliminaDocument(String titol, String autor) throws DocumentInexistentException {
        CDdoc.eliminaDocument(titol,autor,Documents,Autors,DocumentsAutor,TitolAutors,Paraules);
    }

    /* Modifica el titol d'un document
     * Pre: El Document identificat per (titolVell,autor) existeix
     * Post: El Document (titolVell,autor) ara està identificat per (titolNou,autor)
     */
    public void setTitol(String titolVell, String titolNou, String autor) throws DocumentJaExisteixException, DocumentInexistentException {
        CDdoc.setTitol(titolVell,titolNou,autor,Documents,DocumentsAutor,TitolAutors,Paraules);
    }

    /* Modifica el autor d'un document
     * Pre: El Document identificat per (titol,autorVell) existeix
     * Post: El Document (titol,autorVell) ara està està identificat per (titol,autorNou)
     */
    public void setAutor(String titol, String autorVell, String autorNou) throws DocumentJaExisteixException, DocumentInexistentException  {
        CDdoc.setAutor(titol,autorVell,autorNou,Documents,Autors,DocumentsAutor,TitolAutors,Paraules);
    }

    /* Modifica el contingut d'un document
     * Pre: El Document identificat per (titol,autor) existeix
     * Post: El contingut del Document (titol,autor) és igual al paràmetre contingut
     */
    public void setContingut(String titol, String autor, String contingut) throws DocumentInexistentException {
        CDdoc.setContingut(titol,autor,contingut,Documents,Paraules);
    }

    /* Obté el contingut d'un document
     * Pre: El Document identificat per (titol,autor) existeix
     * Post: S'obté el contingut del document identificat per (títol, autor)
     */
    public String getContingut(String titol, String autor) throws DocumentInexistentException {
        return CDdoc.getContingut(titol,autor,Documents);
    }

    /* Modifica el path d'un Document
     * Pre: El Document identificat per (titol,autor) existeix
     * Post: Es modifica el path del document identificat per (titol,autor)
     */
    public void setPath(String titol, String autor, String path) throws DocumentInexistentException {
        CDdoc.setPath(titol,autor,path,Documents);
    }

    /* Obté el path d'un Document
     * Pre: El Document identificat per (titol,autor) existeix
     * Post: S'obté el path del Document identificat per (títol, autor)
     */
    public String getPath(String titol, String autor) throws DocumentInexistentException {
        return CDdoc.getPath(titol,autor,Documents);
    }

    /* Modifica l'extensió d'un Document
     * Pre: El Document identificat per (titol,autor) existeix
     * Post: S'ha modificat l'extensió del document identificat per (titol,autor)
     */
    public void setExtensio(String titol, String autor, TipusExtensio ext) throws DocumentInexistentException {
        CDdoc.setExtensio(titol,autor,ext,Documents);
    }

    /* Obté l'extensió d'un Document
     * Pre: El Document identificat per (titol,autor) existeix
     * Post:S'obté l'extensió del Document identificat per (titol,autor)
     */
    public TipusExtensio getExtensio(String titol, String autor) throws DocumentInexistentException {
        return CDdoc.getExtensio(titol,autor,Documents);
    }

    /* Obté el pes d'un Document
     * Pre: El Document identificat per (titol,autor) existeix
     * Post: S'obté el pes del Document identificat per (tiol,autor)
     */
    public Integer getPes(String titol, String autor) throws DocumentInexistentException {
        return CDdoc.getPes(titol,autor,Documents);
    }

    /* Obtenen les StopWords
     * Pre: True
     * Post: S'obtenen les StopWords
     */
    public ArrayList<String> getStopWords() {
        return CDdoc.getStopWords();
    }

    /* Modifica les StopWords
     * Pre: True
     * Post: Les Stop Words passen a ser les paraules del array StopWords
     */
    public void setStopWords(ArrayList<String> StopWords) {
        CDdoc.setStopWords(StopWords);
    }

    /* S'obtenen el nombre de documents del sistema
     * Pre: True
     * Post: S'obté el nombre de documents del sistema
     */
    public Integer getNombreDocuments() {
        return CDdoc.getNombreDocuments(Documents);
    }


    ///////////////////////////////////////////////////////////
    ///             FUNCIONS CTRL_DOMINI_CERCA              ///
    ///////////////////////////////////////////////////////////

    /* Llista els identificadors del documents (títol,autor) creats per l'autor "autor"
     * Pre: True
     * Post: Un llistat dels documents que ha creat l'autor "autor"
     */
    public ArrayList<SimpleEntry<String,String>> cercaAutor(String autor, TipusOrdenacio ord) throws AutorNoExisteixException {
        return CDcer.cercaAutor(autor,DocumentsAutor,ord,Documents);
    }

    /* Llista els identificadors dels documents (titol,autor) que tenen com a títol "titol"
     * Pre: True
     * Post: Un llistat dels documents que tenen com a titol "titol"
     */
    public ArrayList<SimpleEntry<String,String>> cercaTitol(String titol, TipusOrdenacio ord) throws TitolNoExisteixException{
        return CDcer.cercaTitol(titol,TitolAutors,ord,Documents);
    }

    /* Obté un document
     * Pre: El document identificat per (titol,autor) existeix
     * Post: Obté el document identificat per (titol,autor)
     */
    public Document cercaTitolAutor(String titol, String autor) throws DocumentInexistentException {
        return CDcer.cercaTitolAutor(titol,autor,Documents);
    }

    /* Llista els autors que contenen el prefix "prefix"
     * Pre: True
     * Post: Un llistat dels autors que tenen el prefix "prefix"
     */
    public ArrayList<String> cercaPrefix(String prefix, TipusOrdenacio ord) throws DocumentInexistentException {
        return CDcer.cercaPrefix(prefix,Autors,ord);
    }

    /* Llista els k documents més semblants al document D
     * Pre: El Document D no té el contingut buit i k > 0
     * Post: Un arraylist de longitud k amb els identificadors dels documents més semblants a D
     */
    public ArrayList<SimpleEntry<String,String>> cercaSemblant(String titol, String autor, Integer k) throws ArrayDeParaulesBuitException, NombreMassaPetitDocumentsException {
        return CDcer.cercaSemblant(titol,autor,k,Paraules,Documents);
    }

    /* Llista els k documents més rellevants segons l'array de paraules
     * Pre: L'array no és buit i k > 0
     * Post: Un arraylist de longitud k amb els identificadors dels documents més rellevants a l'array paraules
     */
    public ArrayList<SimpleEntry<String,String>> cercaParaules(String paraules, Integer k) throws ArrayDeParaulesBuitException, NombreMassaPetitDocumentsException {
        return CDcer.cercaParaules(paraules,k,Paraules,Documents);
    }

    /* Llista els documents que compleixen la condició booleana expr
     * Pre: La expressio booleana expr no existeix
     * Post: Un arraylist dels documents que compleixen l'expressió expr
     */
    public ArrayList<SimpleEntry<String,String>> cercaBooleana(String expr, TipusOrdenacio ord) throws ExpressioBooleanaInexistentException {
        return CDcer.cercaBooleana(expr,Documents,ExpressionsBooleanes,ord);
    }

    /* Llista tots els documents del sistema
     * Pre: True
     * Post: S'obté una llista dels identificadors de tots els documents del sistema
     */
    public ArrayList<SimpleEntry<String,String>> cercaAllDocuments(TipusOrdenacio ord){
        return CDcer.cercaAllDocuments(Documents,ord);
    }

    /* Llista totes les expressions booleanes
     * Pre: True
     * Post: S'obté una llista de totes les expressions booleanes del sistema
     */
    public ArrayList<String> cercaAllExpressionsBool(TipusOrdenacio ord) {
        return CDcer.cercaAllExpressionsBool(ExpressionsBooleanes,ord);
    }


    ///////////////////////////////////////////////////////////
    ///           FUNCIONS CTRL_DOMINI_EXPR.BOOL.           ///
    ///////////////////////////////////////////////////////////

    /* Crea una expressió booleana
     * Pre: La expressió booleana ja existeix
     * Post: S'ha creat una expressió booleana formada per l'expressió expr
     */
    public void creaExpressioBool(String expr) throws ExpressioBooleanaJaExistentException {
        CDeb.creaExpressioBool(expr,ExpressionsBooleanes);
    }

    /* Elimina una expressió booleana
     * Pre: La expressió booleana expr no existeix
     * Post: S'ha eliminat l'expressió booleana formada per l'expressió expr
     */
    public void eliminaExpressioBool(String expr) throws ExpressioBooleanaInexistentException {
        CDeb.eliminaExpressioBool(expr,ExpressionsBooleanes);
    }

    /* Modifica una expressió booleana
     * Pre: La expressió booleana
     * Post: L'expressió exprAnt passa a ser exprNova
     */
    public void modificaExpressioBool(String exprAnt, String exprNova) throws ExpressioBooleanaInexistentException, ExpressioBooleanaJaExistentException {
        CDeb.modificaExpressioBool(exprAnt,exprNova,ExpressionsBooleanes);
    }

    public int getNombreExprssioBool() {
        return CDeb.getNombreExpressionsBooleanes(ExpressionsBooleanes);
    }
}
