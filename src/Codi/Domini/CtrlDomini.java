package Codi.Domini;

import Codi.Persistencia.CtrlPersistencia;
import Codi.Util.*;
import Codi.Excepcions.*;

import java.io.File;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe que controla la capa de domini
 *
 * @author pol
 * @since 15/12/2022
 */
public class CtrlDomini {

    ///////////////////////////////////////////////////////////
    ///                     ESTRUCTURES                     ///
    ///////////////////////////////////////////////////////////

    private final HashMap<SimpleEntry<String,String>,Document> Documents;         // Estructura on es guarden els documents
    private final Trie<String> Autors;                                            // Estructura on es guarden els autors (serveix per trobar el prefix)
    private final HashMap<String,ArrayList<String>> DocumentsAutor;               // Estructura on es guarden els títols dels documents creats per un autor
    private final HashMap<String,ArrayList<String>> TitolAutors;                  // Estructura on es guarden els autors que han creat un document amb un títol en concret
    private final HashMap<String,ArrayList<SimpleEntry<String,String>>> Paraules; // Estructura on es guarden els documents que contenen una paraula en concret
    private final HashMap<String,ExpressioBooleana> ExpressionsBooleanes;         // Estructura on es guarden totes les expression booleanes

    public HashMap<String,ArrayList<SimpleEntry<String,String>>> getParaules() {
        return Paraules;
    }

    ///////////////////////////////////////////////////////////
    ///                     AGREGACIONS                     ///
    ///////////////////////////////////////////////////////////

    private final CtrlDominiExprBool CDeb;    // Agregació del controlador d'expressió booleana
    private final CtrlDominiDocument CDdoc;   // Agregació del controlador de document
    private final CtrlDominiCerca CDcer;      // Agregació del controlador de cerca
    private final CtrlPersistencia CP;        // Agregació del controladaor de persistència


    ///////////////////////////////////////////////////////////
    ///                      CREADORES                      ///
    ///////////////////////////////////////////////////////////

    /**
     * Crea i inicialitza els altres controladors i estructures
     */
    public CtrlDomini() {
        CDeb = new CtrlDominiExprBool();
        CDdoc = new CtrlDominiDocument();
        CDcer = new CtrlDominiCerca();
        CP = new CtrlPersistencia();

        Documents = new HashMap<SimpleEntry<String,String>,Document>();
        Autors = new Trie<String>();
        DocumentsAutor = new HashMap<String,ArrayList<String>>();
        TitolAutors = new HashMap<String,ArrayList<String>>();
        Paraules = new HashMap<String,ArrayList<SimpleEntry<String,String>>>();
        ExpressionsBooleanes = new HashMap<String,ExpressioBooleana>();
    }

    public void inicialitza() throws Exception {
        // Llegim els documents
        ArrayList<DocumentLlegit> docs = CP.carregaDocuments();
        for(DocumentLlegit doc : docs) {
            try {
                CDdoc.llegirDocument(doc,Documents,Autors,DocumentsAutor,TitolAutors,Paraules);
            } catch (DocumentJaExisteixException e) {
                CP.eliminaDocument(doc.getPath());
            }
        }
        // Llegim les expression booleanes
        ArrayList<String> ebs = CP.carregaExpressionsBooleanes();
        for(String eb : ebs) {
            CDeb.creaExpressioBool(eb,ExpressionsBooleanes);
        }
        // Llegim lesstopWords
        CDdoc.setStopWords(CP.carregaStopWords());

    }


    ///////////////////////////////////////////////////////////
    ///              FUNCIONS CTRL_PERSISTÈNCIA             ///
    ///////////////////////////////////////////////////////////

    public void importarDocuments(ArrayList<File> documents) throws DocumentJaExisteixException, FitxerNoCreatException,
            FitxerNoEliminatException {
        // Llegim els documents importats
        ArrayList<DocumentLlegit> docs = CP.importarDocuments(documents);

        for(DocumentLlegit doc : docs) {
            // Guardem el seu nou path
            doc.setPath(CP.getNovaPath());
            // Llegim el document i ho posem en les nostres estructures
            CDdoc.llegirDocument(doc,Documents,Autors,DocumentsAutor,TitolAutors,Paraules);
            doc.setPath(CDdoc.getPath(doc.getTitol(), doc.getAutor(), Documents));
            // Guardem el document el el sistema
            CP.guardaDocument(doc);
        }
    }

    public void exportarDocument(String titol, String autor, File file) throws FitxerNoCreatException {
        Document D = Documents.get(new SimpleEntry<String,String>(titol,autor));
        DocumentLlegit DL = new DocumentLlegit();

        DL.setTitol(titol);
        DL.setAutor(autor);
        DL.setExtensio(D.getExtensio());
        DL.setContingut(D.getContingut());
        DL.setPath(file.getPath()+"/"+titol+"_"+autor+"."+D.getExtensio().toString().toLowerCase());
        CP.exportarDocument(DL);
    }

    ///////////////////////////////////////////////////////////
    ///            FUNCIONS CTRL_DOMINI_DOCUMENT            ///
    ///////////////////////////////////////////////////////////

    /**
     * Afegeix un nou document al sistema identificat per titol i autor
     *
     * @param titol Indica el títol del document
     * @param autor Indica l'autor del document
     * @throws DocumentJaExisteixException Si el document identificat per títol i autor ja existeix
     */
    public void creaDocument(String titol, String autor) throws DocumentJaExisteixException, DocumentInexistentException {
        CDdoc.creaDocument(titol,autor,Documents,Autors,DocumentsAutor,TitolAutors);
        CDdoc.setPath(titol, autor, CP.getNovaPath(), Documents);
    }

    public void guardaDocument(String titol, String autor) throws FitxerNoCreatException, FitxerNoEliminatException {
        // Obtenim el document
        Document D = Documents.get(new SimpleEntry<String,String>(titol,autor));
        DocumentLlegit DL = new DocumentLlegit();

        DL.setAutor(D.getAutor());
        DL.setTitol(D.getTitol());
        DL.setExtensio(D.getExtensio());
        DL.setContingut(D.getContingut());
        DL.setPath(D.getPath());

        // Guardem el document i actualitzem el seu path
        CP.guardaDocument(DL);
    }

    /**
     * Elimina el document del sistema identificat per titol i autor
     *
     * @param titol Indica el títol del document
     * @param autor Indica l'autor del document
     * @throws DocumentInexistentException Si el document identificat per titol i autor no existeix
     */
    public void eliminaDocument(String titol, String autor) throws DocumentInexistentException, FitxerNoEliminatException {
        CP.eliminaDocument(CDdoc.getPath(titol,autor,Documents));
        CDdoc.eliminaDocument(titol,autor,Documents,Autors,DocumentsAutor,TitolAutors,Paraules);
    }

    public void modificarIdentificador(SimpleEntry<String,String> idVell, SimpleEntry<String,String> idNou)
            throws DocumentJaExisteixException, DocumentInexistentException {
        CDdoc.modificarIdentificador(idVell,idNou,Documents,DocumentsAutor,Autors,TitolAutors,Paraules);
    }

    /**
     * Modifica el contingut del document identificat per titol i autor
     *
     * @param titol Indica el titol que identifica el document
     * @param autor Indica l'autor que identifica el document
     * @param contingut Indica el nou contingut que tindrà el document
     * @throws DocumentInexistentException Si el document identificat per titol i autor no existeix
     */
    public void setContingut(String titol, String autor, String contingut) throws DocumentInexistentException {
        CDdoc.setContingut(titol,autor,contingut,Documents,Paraules);
    }

    /**
     * Modifica l'extensió del document identificat per titol i autor
     *
     * @param titol Indica el titol que identifica el document
     * @param autor Indica l'autor que identifica el document
     * @param ext Indica l'extensió que tindrà el document
     * @throws DocumentInexistentException Si el document identificat per titol i autor no existeix
     */
    public void setExtensio(String titol, String autor, TipusExtensio ext) throws DocumentInexistentException {
        CDdoc.setExtensio(titol,autor,ext,Documents);
    }

    /**
     * Obté el contingut del document identificat per titol i autor
     *
     * @param titol Indica el titol que identifica el document
     * @param autor Indica l'autor que identifica el document
     * @return Retorna el contingut del document identificat per titol i autor
     * @throws DocumentInexistentException Si el document identificat per titol i autor no existeix
     */
    public String getContingut(String titol, String autor) throws DocumentInexistentException {
        return CDdoc.getContingut(titol,autor,Documents);
    }

    /**
     * Obté l'extensió del document identificat per titol i autor
     *
     * @param titol Indica el titol que identifica el document
     * @param autor Indica l'autor que identifica el document
     * @return Retorna el tipus d'extensió que té el document identificat per titol i autor
     * @throws DocumentInexistentException Si el document identificat per titol i autor no existeix
     */
    public TipusExtensio getExtensio(String titol, String autor) throws DocumentInexistentException {
        return CDdoc.getExtensio(titol,autor,Documents);
    }

    /**
     * Obté el pes del document identificat per tiol i autor
     *
     * @param titol Indica el titol que identifica el document
     * @param autor Indica l'autor que identifica el document @param titol
     * @return Retorna el pes que té el document identificat per titol i autor
     * @throws DocumentInexistentException Si el document identificat per titol i autor no existeix
     */
    public Integer getPes(String titol, String autor) throws DocumentInexistentException {
        return CDdoc.getPes(titol,autor,Documents);
    }

    /**
     * Obté el nombre de document del sistema
     *
     * @return Retorn el nombre de documents que hi ha en el sistema
     */
    public Integer getNombreDocuments() {
        return CDdoc.getNombreDocuments(Documents);
    }


    ///////////////////////////////////////////////////////////
    ///             FUNCIONS CTRL_DOMINI_CERCA              ///
    ///////////////////////////////////////////////////////////

    /**
     * Llista els identificadors dels documents creats per l'autor autor
     *
     * @param autor Indica l'autor del que buscarem els seus documents
     * @param ord Indica el tipus d'ordre que volem que surtin el documents
     * @return Retorna els documents que estan identificats amb l'autor autor
     * @throws AutorNoExisteixException Si no hi ha cap document que tingui com a autor autor
     */
    public ArrayList<SimpleEntry<String,String>> cercaAutor(String autor, TipusOrdenacio ord) throws AutorNoExisteixException {
        return CDcer.cercaAutor(autor,DocumentsAutor,ord,Documents);
    }

    /**
     * Llista els identificadors dels documents que tenen com a titol titol
     *
     * @param titol Indica el titol que cercarem entre els documents
     * @param ord Indica el tipus d'ordre que volem que surtin els documents
     * @return Retorna els documents que estan identificats amb el titol titol
     * @throws TitolNoExisteixException Si no hi ha cap document que tingui com a titol titol
     */
    public ArrayList<SimpleEntry<String,String>> cercaTitol(String titol, TipusOrdenacio ord) throws TitolNoExisteixException{
        return CDcer.cercaTitol(titol,TitolAutors,ord,Documents);
    }

    /**
     * Llista els autors que contenen el prefix prefix
     *
     * @param prefix Indica el prefix que cercarem entre els autors del sistema
     * @param ord Indica l'ordre en que volem que es llistin els autors que cerquem
     * @return Retorna els autors que comencen per el prefix prefix
     * @throws DocumentInexistentException Si no hi ha cap document que tingui un autor que comenci per prefix
     */
    public ArrayList<String> cercaPrefix(String prefix, TipusOrdenacio ord) throws DocumentInexistentException {
        return CDcer.cercaPrefix(prefix,Autors,ord);
    }

    /**
     * Llista els k documents més semblants al document identificat per titol i autor
     *
     * @param titol Indica el titol que identifica el document que utilitzarem per fer la cerca
     * @param autor Indica l'autor que identifica el document que utilitzarem per fer la cerca
     * @param k Indica el nombre de documents que volem que ens retorni
     * @return Retorna els k documents més semblants al document identificat per titol i autor
     * @throws ArrayDeParaulesBuitException Si el contingut del document identificat per titol i autor és buit
     * @throws NombreMassaPetitDocumentsException Si k <= 0
     */
    public ArrayList<SimpleEntry<String,String>> cercaSemblant(String titol, String autor, Integer k) throws
            ArrayDeParaulesBuitException, NombreMassaPetitDocumentsException {
        return CDcer.cercaSemblant(titol,autor,k,Paraules,Documents);
    }

    /**
     * Llista els k documents més rellevants a l'array paraules
     *
     * @param paraules Indica les paraules que utilitzarem per fer la cerca
     * @param k Indica el nombre de documents que volem que ens retori
     * @return Retorna els k documents més rellevants a l'array paraules
     * @throws ArrayDeParaulesBuitException Si l'array paraules és buit
     * @throws NombreMassaPetitDocumentsException Si k <0
     */
    public ArrayList<SimpleEntry<String,String>> cercaParaules(String paraules, Integer k) throws
            ArrayDeParaulesBuitException, NombreMassaPetitDocumentsException {
        return CDcer.cercaParaules(paraules,k,Paraules,Documents);
    }

    /**
     * Llista els documents que compleixen la condició booleana expr
     *
     * @param expr Expressió booleana que s'utilitza per fer la cerca
     * @param ord Indica l'ordre que volem que ens retorni la cerca
     * @return Retorna els documents que compleixen l'expressió booleana expr
     * @throws ExpressioBooleanaInexistentException Si l'expressio booleana expr no existeix en el sistema
     */
    public ArrayList<SimpleEntry<String,String>> cercaBooleana(String expr, TipusOrdenacio ord) throws
            ExpressioBooleanaInexistentException {
        return CDcer.cercaBooleana(expr,Documents,ExpressionsBooleanes,ord);
    }

    /**
     * Llista tots els documents del sistema
     *
     * @param ord Indica l'ordre en que volem que ens retorni la consulta
     * @return Retorna tots els documents del sistema
     */
    public ArrayList<SimpleEntry<String,String>> cercaAllDocuments(TipusOrdenacio ord){
        return CDcer.cercaAllDocuments(Documents,ord);
    }

    /**
     * Llista totes les expressions booleanes del sistema
     *
     * @param ord Indica l'ordre en que volem que ens retorni la consulta
     * @return Retorna totes les expressions booleanes del sistema
     */
    public ArrayList<String> cercaAllExpressionsBool(TipusOrdenacio ord) {
        return CDcer.cercaAllExpressionsBool(ExpressionsBooleanes,ord);
    }

    /**
     * Ordena el resultat d'una cerca
     *
     * @param cerca Indica la cerca obtinguda
     * @param ord Indica el nou ordre que es farà a la cerca
     * @return Retorna la cerca en l'ordre indicat
     */
    public ArrayList<SimpleEntry<String,String>> ordenarCerca(ArrayList<SimpleEntry<String,String>> cerca, TipusOrdenacio ord) {
        return CDcer.ordenarCerca(cerca,ord,Documents);
    }

    public ArrayList<String> ordenarCercaSimple(ArrayList<String> cerca, TipusOrdenacio ord) {
        return CDcer.ordenarCercaSimple(cerca,ord);
    }


    ///////////////////////////////////////////////////////////
    ///           FUNCIONS CTRL_DOMINI_EXPR.BOOL.           ///
    ///////////////////////////////////////////////////////////

    /**
     * Crea l'expressió booleana formada per el string expr
     *
     * @param expr Indica l'expressió booleana que guardarem en el sistema
     * @throws ExpressioBooleanaJaExistentException Si ja hi ha una expressio booleana formada per l'expressio expr
     */
    public void creaExpressioBool(String expr) throws ExpressioBooleanaJaExistentException, FitxerNoEliminatException,
            FitxerNoCreatException {
        // Guardem l'expressió en les nostes estructures de dades
        CDeb.creaExpressioBool(expr,ExpressionsBooleanes);

        ArrayList<String> expressions = new ArrayList<String>(ExpressionsBooleanes.keySet());

        // Guardem l'expressió en el sistema
        CP.guardaExpressionsBooleanes(expressions);
    }

    /**
     * Elimina l'expressió booleana formada per el string expr
     *
     * @param expr Indica l'expressió booleana que eliminem del sistema
     * @throws ExpressioBooleanaInexistentException Si no hi ha cap expressio booleana formada per l'expressio expr
     */
    public void eliminaExpressioBool(String expr) throws ExpressioBooleanaInexistentException, FitxerNoEliminatException,
            FitxerNoCreatException {
        CDeb.eliminaExpressioBool(expr,ExpressionsBooleanes);

        ArrayList<String> expressions = new ArrayList<String>(ExpressionsBooleanes.keySet());

        CP.guardaExpressionsBooleanes(expressions);
    }

    /**
     * Modifica la expressió booleana formada per el string exprAnt
     *
     * @param exprAnt Indica l'expressió booleana que modificarem
     * @param exprNova Indica el nou valor que tindrà l'expressió
     * @throws  ExpressioBooleanaInexistentException Si no hi ha cap expressio booleana formada per l'expressio expr
     * @throws ExpressioBooleanaJaExistentException Si ja hi ha una expressio booleana formada per l'expressio expr
     */
    public void modificaExpressioBool(String exprAnt, String exprNova) throws ExpressioBooleanaInexistentException,
            ExpressioBooleanaJaExistentException, FitxerNoEliminatException, FitxerNoCreatException {
        // Modifiquem l'expressió en les estructures de crtlDomini
        CDeb.modificaExpressioBool(exprAnt,exprNova,ExpressionsBooleanes);

        ArrayList<String> expressions = new ArrayList<String>(ExpressionsBooleanes.keySet());

        CP.guardaExpressionsBooleanes(expressions);
    }
}