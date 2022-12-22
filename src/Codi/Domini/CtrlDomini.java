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
 * @author Pol Verdura
 */
public class CtrlDomini {

    ///////////////////////////////////////////////////////////
    ///                     ESTRUCTURES                     ///
    ///////////////////////////////////////////////////////////

    /** Estructura on es guarden els documents */
    private final HashMap<SimpleEntry<String,String>,Document> Documents;

    /** Estructura on es guarden els autors (serveix per trobar el prefix) */
    private final Trie Autors;

    /** Estructura on es guarden els titols dels documents creats per un autor */
    private final HashMap<String,ArrayList<String>> DocumentsAutor;

    /** Estructura on es guarden els autors que han creat un document amb un titol en concret */
    private final HashMap<String,ArrayList<String>> TitolAutors;

    /** Estructura on es guarden els documents que contenen una paraula en concret */
    private final HashMap<String,ArrayList<SimpleEntry<String,String>>> Paraules;

    /** Estructura on es guarden totes les expression booleanes */
    private final HashMap<String,ExpressioBooleana> ExpressionsBooleanes;

    /** Getter de l'atribut Documents
     *
      * @return Retorna un hash map on la clau es una paraula el valor un array de parelles de titols i autors que
     *  identifiquen els documents on apareix
     */
    public HashMap<String,ArrayList<SimpleEntry<String,String>>> getParaules() {
        return Paraules;
    }


    ///////////////////////////////////////////////////////////
    ///                     AGREGACIONS                     ///
    ///////////////////////////////////////////////////////////

    /** Agregacio del controlador d'expressio booleana */
    private final CtrlDominiExprBool CDeb;

    /** Agregacio del controlador de document */
    private final CtrlDominiDocument CDdoc;

    /** Agregacio del controlador de cerca */
    private final CtrlDominiCerca CDcer;

    /* Agregacio del controladaor de persistencia */
    private final CtrlPersistencia CP;


    ///////////////////////////////////////////////////////////
    ///                      CREADORES                      ///
    ///////////////////////////////////////////////////////////

    /**
     * Crea les estructures de dades i els altres controladors
     */
    public CtrlDomini() {
        // Agreguem els altres controladors
        CDeb = new CtrlDominiExprBool();
        CDdoc = new CtrlDominiDocument();
        CDcer = new CtrlDominiCerca();
        CP = new CtrlPersistencia();

        // Inicialitzem les estructures de dades que conte
        Documents = new HashMap<SimpleEntry<String,String>,Document>();
        Autors = new Trie();
        DocumentsAutor = new HashMap<String,ArrayList<String>>();
        TitolAutors = new HashMap<String,ArrayList<String>>();
        Paraules = new HashMap<String,ArrayList<SimpleEntry<String,String>>>();
        ExpressionsBooleanes = new HashMap<String,ExpressioBooleana>();
    }

    /**
     * Inicialitza les estructures de dades amb el que li passa CtrlPersistencia
     *
     * @throws Exception Si hi ha hagut algun error al inicialitzar les estructures
     */
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
        // Llegim les StopWords
        CDdoc.setStopWords(CP.carregaStopWords());

    }


    ///////////////////////////////////////////////////////////
    ///              FUNCIONS CTRL_PERSISTeNCIA             ///
    ///////////////////////////////////////////////////////////

    /**
     * Ens importa un conjunt de documents en el nostre sistema
     *
     * @param documents Indica els documents que volem importar
     * @throws DocumentJaExisteixException Si un dels documents importats ja existeix en el sistema
     * @throws FitxerNoCreatException Si s'ha intentat crear un document en el sistema i no s'ha pogut
     * @throws FitxerNoEliminatException Si s'ha intentat eliminar un document del sistema i no s'ha pogut
     */
    public void importarDocuments(ArrayList<File> documents) throws DocumentJaExisteixException, FitxerNoCreatException,
            FitxerNoEliminatException {
        // Llegim els documents importats
        ArrayList<DocumentLlegit> docs = CP.importarDocuments(documents);

        for(DocumentLlegit doc : docs) {
            // Guardem el seu nou path calculat
            doc.setPath(CP.getNovaPath());

            // Llegim el document i ho posem en les nostres estructures
            CDdoc.llegirDocument(doc,Documents,Autors,DocumentsAutor,TitolAutors,Paraules);
            doc.setPath(CDdoc.getPath(doc.getTitol(), doc.getAutor(), Documents));

            // Guardem el document el el sistema en el path calculat anteriorment
            CP.guardaDocument(doc);
        }
    }

    /**
     * Exporta un document fora del nostre sistema
     *
     * @param titol Indica el titol que identifica el document que es vol exportar
     * @param autor Indica l'autor que identifica el document que es vol exportar
     * @param path Hi ha el path del directori on anira el document exportat
     * @throws FitxerNoCreatException Si s'ha intentat crear el document i no s'ha pogut
     */
    public void exportarDocument(String titol, String autor, File path) throws FitxerNoCreatException {
        Document D = Documents.get(new SimpleEntry<String,String>(titol,autor));
        DocumentLlegit DL = new DocumentLlegit();

        // Llegim el document que volem exportar i ho posem en un DocumentLlegit
        DL.setTitol(titol);
        DL.setAutor(autor);
        DL.setExtensio(D.getExtensio());
        DL.setContingut(D.getContingut());
        // El seu nom es el titol i autor que identifica el document (si no hi ha repetits en el directori path)
        DL.setPath(path.getPath()+"/"+titol+"_"+autor+"."+D.getExtensio().toString().toLowerCase());

        // Exportem el document
        CP.exportarDocument(DL);
    }


    ///////////////////////////////////////////////////////////
    ///            FUNCIONS CTRL_DOMINI_DOCUMENT            ///
    ///////////////////////////////////////////////////////////

    /**
     * Afegeix un nou document al sistema identificat per titol i autor
     *
     * @param titol Indica el titol que identifica el document
     * @param autor Indica l'autor que identifica el document
     * @throws DocumentJaExisteixException Si el document identificat per titol i autor ja existeix
     */
    public void creaDocument(String titol, String autor) throws DocumentJaExisteixException {
        CDdoc.creaDocument(titol,autor,Documents,Autors,DocumentsAutor,TitolAutors);
        CDdoc.setPath(titol, autor, CP.getNovaPath(), Documents);
    }

    /**
     * Guarda el document identificat per titol i autor en el sistema
     *
     * @param titol Indica el titol que identifica el document
     * @param autor Indica l'autor que identifica el document
     * @throws FitxerNoEliminatException Si s'ha intentat eliminar el fitxer i no s'ha pogut
     * @throws FitxerNoCreatException Si s'ha intentat crear el fitxer i no s'ha pogut
     */
    public void guardaDocument(String titol, String autor) throws FitxerNoEliminatException, FitxerNoCreatException {
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
     * @param titol Indica el titol que identifica el document
     * @param autor Indica l'autor que identifica el document
     * @throws FitxerNoEliminatException Si el document identificat per titol i autor no s'ha pogut eliminar
     */
    public void eliminaDocument(String titol, String autor) throws FitxerNoEliminatException {
        CP.eliminaDocument(CDdoc.getPath(titol,autor,Documents));
        CDdoc.eliminaDocument(titol,autor,Documents,Autors,DocumentsAutor,TitolAutors,Paraules);
    }

    /**
     * Modifica l'identificador del document identificat per idVell
     * 
     * @param idVell Indica l'identificador que tenia el document
     * @param idNou Indica l'identificador que te el document un cop executada la funcio
     * @throws DocumentJaExisteixException Si el document identificat per idVell ja existeix abans d'executar la funcio
     * @throws DocumentInexistentException Si el document identificat per idNou no existeix abans d'executar la funcio
     */
    public void modificarIdentificador(SimpleEntry<String,String> idVell, SimpleEntry<String,String> idNou) 
            throws DocumentJaExisteixException, DocumentInexistentException {
        CDdoc.modificarIdentificador(idVell,idNou,Documents,DocumentsAutor,Autors,TitolAutors,Paraules);
    }

    /**
     * Modifica el contingut del document identificat per titol i autor
     *
     * @param titol Indica el titol que identifica el document
     * @param autor Indica l'autor que identifica el document
     * @param contingut Indica el nou contingut que tindra el document
     * @throws DocumentInexistentException Si el document identificat per titol i autor no existeix
     */
    public void setContingut(String titol, String autor, String contingut) throws DocumentInexistentException {
        CDdoc.setContingut(titol,autor,contingut,Documents,Paraules);
    }

    /**
     * Modifica l'extensio del document identificat per titol i autor
     *
     * @param titol Indica el titol que identifica el document
     * @param autor Indica l'autor que identifica el document
     * @param ext Indica l'extensio que tindra el document
     * @throws DocumentInexistentException Si el document identificat per titol i autor no existeix
     */
    public void setExtensio(String titol, String autor, TipusExtensio ext) throws DocumentInexistentException {
        CDdoc.setExtensio(titol,autor,ext,Documents);
    }

    /**
     * Obte el contingut del document identificat per titol i autor
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
     * Obte l'extensio del document identificat per titol i autor
     *
     * @param titol Indica el titol que identifica el document
     * @param autor Indica l'autor que identifica el document
     * @return Retorna el tipus d'extensio que te el document identificat per titol i autor
     * @throws DocumentInexistentException Si el document identificat per titol i autor no existeix
     */
    public TipusExtensio getExtensio(String titol, String autor) throws DocumentInexistentException {
        return CDdoc.getExtensio(titol,autor,Documents);
    }

    /**
     * Obte el pes del document identificat per tiol i autor
     *
     * @param titol Indica el titol que identifica el document
     * @param autor Indica l'autor que identifica el document titol
     * @return Retorna el pes que te el document identificat per titol i autor
     * @throws DocumentInexistentException Si el document identificat per titol i autor no existeix
     */
    public Integer getPes(String titol, String autor) throws DocumentInexistentException {
        return CDdoc.getPes(titol,autor,Documents);
    }

    /**
     * Obte el nombre de document del sistema
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
    public ArrayList<SimpleEntry<String,String>> cercaTitol(String titol, TipusOrdenacio ord) throws TitolNoExisteixException {
        return CDcer.cercaTitol(titol,TitolAutors,ord,Documents);
    }

    /**
     * Llista els autors que contenen el prefix prefix
     *
     * @param prefix Indica el prefix que cercarem entre els autors del sistema
     * @param ord Indica l'ordre en que volem que es llistin els autors que cerquem
     * @return Retorna els autors que comencen per el prefix prefix
     * @throws PrefixNoExisteixException Si no hi ha cap document que tingui un autor que comenci pel prefix indicat
     */
    public ArrayList<String> cercaPrefix(String prefix, TipusOrdenacio ord) throws PrefixNoExisteixException {
        return CDcer.cercaPrefix(prefix,Autors,ord);
    }

    /**
     * Llista els k documents mes semblants al document identificat per titol i autor
     *
     * @param titol Indica el titol que identifica el document que utilitzarem per fer la cerca
     * @param autor Indica l'autor que identifica el document que utilitzarem per fer la cerca
     * @param k Indica el nombre de documents que volem que ens retorni
     * @return Retorna els k documents mes semblants al document identificat per titol i autor
     * @throws ArrayDeParaulesBuitException Si el contingut del document identificat per titol i autor es buit
     * @throws NombreMassaPetitDocumentsException Si k <= 0
     */
    public ArrayList<SimpleEntry<String,String>> cercaSemblant(String titol, String autor, Integer k) throws
            ArrayDeParaulesBuitException, NombreMassaPetitDocumentsException {
        return CDcer.cercaSemblant(titol,autor,k,Paraules,Documents);
    }

    /**
     * Llista els k documents mes rellevants a l'array paraules
     *
     * @param paraules Indica les paraules que utilitzarem per fer la cerca
     * @param k Indica el nombre de documents que volem que ens retori
     * @return Retorna els k documents mes rellevants a l'array paraules
     * @throws ArrayDeParaulesBuitException Si l'array paraules es buit
     * @throws NombreMassaPetitDocumentsException Si k <0
     */
    public ArrayList<SimpleEntry<String,String>> cercaParaules(String paraules, Integer k) throws
            ArrayDeParaulesBuitException, NombreMassaPetitDocumentsException {
        return CDcer.cercaParaules(paraules,k,Paraules,Documents);
    }

    /**
     * Llista els documents que compleixen la condicio booleana expr
     *
     * @param expr Expressio booleana que s'utilitza per fer la cerca
     * @param ord Indica l'ordre que volem que ens retorni la cerca
     * @return Retorna els documents que compleixen l'expressio booleana expr
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
    public ArrayList<SimpleEntry<String,String>> cercaAllDocuments(TipusOrdenacio ord) {
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
     * @param ord Indica el nou ordre que es fara a la cerca
     * @return Retorna la cerca en l'ordre indicat
     */
    public ArrayList<SimpleEntry<String,String>> ordenarCerca(ArrayList<SimpleEntry<String,String>> cerca, TipusOrdenacio ord) {
        return CDcer.ordenarCerca(cerca,ord,Documents);
    }

    /**
     * Ordena el resultat d'una cerca
     * 
     * @param cerca Indica la cerca obtinguda
     * @param ord Indica el nou ordre que es fara a la cerca
     * @return Retorna la cerca en l'ordre indicat
     */
    public ArrayList<String> ordenarCercaSimple(ArrayList<String> cerca, TipusOrdenacio ord) {
        return CDcer.ordenarCercaSimple(cerca,ord);
    }


    ///////////////////////////////////////////////////////////
    ///           FUNCIONS CTRL_DOMINI_EXPR.BOOL.           ///
    ///////////////////////////////////////////////////////////

    /**
     * Crea l'expressio booleana formada per el string expr
     *
     * @param expr Indica l'expressio booleana que guardarem en el sistema
     * @throws ExpressioBooleanaJaExistentException Si ja hi ha una expressio booleana formada per l'expressio expr
     * @throws FitxerNoEliminatException Si s'ha intentat eliminar el fitxer on es guarden les expressions booleanes i no s'ha pogut
     * @throws FitxerNoCreatException  Si s'ha intentat crear el fitxer on es guarden les expressions booleanes i no s'ha pogut
     */
    public void creaExpressioBool(String expr) throws ExpressioBooleanaJaExistentException, FitxerNoEliminatException,
            FitxerNoCreatException {
        // Guardem l'expressio en les nostes estructures de dades
        CDeb.creaExpressioBool(expr,ExpressionsBooleanes);

        // Agafem les expressions en string i el posem en un array
        ArrayList<String> expressions = new ArrayList<String>(ExpressionsBooleanes.keySet());

        // Guardem l'array fisicament
        CP.guardaExpressionsBooleanes(expressions);
    }

    /**
     * Elimina l'expressio booleana formada per el string expr
     *
     * @param expr Indica l'expressio booleana que eliminem del sistema
     * @throws ExpressioBooleanaInexistentException Si no hi ha cap expressio booleana formada per l'expressio expr
     * @throws FitxerNoEliminatException Si s'ha intentat eliminar el fitxer on es guarden les expressions booleanes i no s'ha pogut
     * @throws FitxerNoCreatException  Si s'ha intentat crear el fitxer on es guarden les expressions booleanes i no s'ha pogut
     */
    public void eliminaExpressioBool(String expr) throws ExpressioBooleanaInexistentException, FitxerNoEliminatException,
            FitxerNoCreatException {
        // Eliminem l'experssio de l'estructura de dades
        CDeb.eliminaExpressioBool(expr,ExpressionsBooleanes);

        // Agafem les expressions en string i el posem en un array
        ArrayList<String> expressions = new ArrayList<String>(ExpressionsBooleanes.keySet());

        // Guardem l'array fisicament
        CP.guardaExpressionsBooleanes(expressions);
    }

    /**
     * Modifica la expressio booleana formada per el string exprAnt
     *
     * @param exprAnt Indica l'expressio booleana que modificarem
     * @param exprNova Indica el nou valor que tindra l'expressio
     * @throws  ExpressioBooleanaInexistentException Si no hi ha cap expressio booleana formada per l'expressio expr
     * @throws ExpressioBooleanaJaExistentException Si ja hi ha una expressio booleana formada per l'expressio expr
     * @throws FitxerNoEliminatException Si s'ha intentat eliminar el fitxer on es guarden les expressions booleanes i no s'ha pogut
     * @throws FitxerNoCreatException  Si s'ha intentat crear el fitxer on es guarden les expressions booleanes i no s'ha pogut
     */
    public void modificaExpressioBool(String exprAnt, String exprNova) throws ExpressioBooleanaInexistentException,
            ExpressioBooleanaJaExistentException, FitxerNoEliminatException, FitxerNoCreatException {
        // Modifiquem l'expressio en les estructures de crtlDomini
        CDeb.modificaExpressioBool(exprAnt,exprNova,ExpressionsBooleanes);

        // Agafem les expressions en string i el posem en un array
        ArrayList<String> expressions = new ArrayList<String>(ExpressionsBooleanes.keySet());

        // Guardem l'array fisicament
        CP.guardaExpressionsBooleanes(expressions);
    }
}