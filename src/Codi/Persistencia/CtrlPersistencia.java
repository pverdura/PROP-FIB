package Codi.Persistencia;

import Codi.Excepcions.*;
import Codi.Util.DocumentLlegit;

import java.io.*;

import java.util.ArrayList;

/**
 * Classe que controla la capa de persistencia
 *
 * @author Judit Serna
 */
public class CtrlPersistencia {

    ///////////////////////////////////////////////////////////
    ///                     VARIABLES                       ///
    ///////////////////////////////////////////////////////////

    /**
     * Instancia de la classe de gestor de dades
     */
    private final GestorDades gestorDades;

    /**
     * Indica el nombre de documents creats al Gestor de Documents (des de la primera inicialitzacio)
     */
    private int numDocs;

    /**
     * Path de la Carpeta Documents per guardar documents
     */
    private final String path = new File("src/Codi/Persistencia/Documents").getPath();

    /**
     * Path on estan guardades les StopWords
     */
    private final String pathStopWords = new File("src/Codi/stopWords.csv").getAbsolutePath();

    /**
     * Path on estan guardades les expressions booleanes
     */
    private final String pathExpressions = new File("src/Codi/Persistencia/Documents/expressions.txt").getPath();

    ///////////////////////////////////////////////////////////
    ///                      CONSTRUCTORA                   ///
    ///////////////////////////////////////////////////////////

    /**
     * Creadora per defecte
     */
    public CtrlPersistencia(){
        gestorDades = new GestorDades();
        numDocs = 0;
    }

    ///////////////////////////////////////////////////////////
    ///                  MÈTODES PÚBLICS                    ///
    ///////////////////////////////////////////////////////////

    /**
     * Importa una serie de documents al Gestor de Documents a la carpeta Documents
     *
     * @param files {@code ArrayList<File>} Indica on estan guardats els documents que s'han d'importar
     * @return {@code ArrayList<DocumentLlegit>} Retorna els documents importats
     */
    public ArrayList<DocumentLlegit> importarDocuments(ArrayList<File> files) {
        ArrayList<DocumentLlegit> docs = new ArrayList<>();
        for(File f: files){
            DocumentLlegit doc = importa(f);
            docs.add(doc);
        }
        return docs;
    }

    /**
     * Exporta un document fora del Gestor de Documents
     *
     * @param doc  Indica el document que es vol exportar
     * @throws FitxerNoCreatException Si no s'ha pogut exportar el document
     */
    public void exportarDocument(DocumentLlegit doc) throws FitxerNoCreatException {
        gestorDades.exportarDocument(doc);
    }

    /**
     * Llegeix els documents guardats en el Gestor de Documents en la carpeta Documents, i si no existeix, la crea
     *
     * @return  {@code ArrayList<DocumentLlegit>} Retorna els documents guardats en el Gestor de Documents
     * @throws CarpetaNoCreadaException Si no s'ha pogut crear la carpeta
     */
    public ArrayList<DocumentLlegit> carregaDocuments() throws CarpetaNoCreadaException {
        ArrayList<DocumentLlegit> documents = new ArrayList<>();
        boolean existeix = gestorDades.existeixDirectori(path);

        if(existeix) documents = llegeixDocuments(path);
        else gestorDades.creaDirectori(path);

        return documents;
    }

    /**
     * Llegeix les expressions booleanes guardades en el Gestor de Documents en la carpeta Documents
     *
     * @return {@code ArrayLis<String>} Retorna les expressions booleanes guardades en el Gestor de Documents
     * @throws FitxerNoCreatException Si no s'ha creat el document on es guarden les expressions
     */
    public ArrayList<String> carregaExpressionsBooleanes() throws FitxerNoCreatException {
        ArrayList<String> expressions = new ArrayList<>();
        boolean existeix = gestorDades.existeixFitxer(pathExpressions);

        if(existeix) expressions = gestorDades.llegeixExpressions(pathExpressions);
        else gestorDades.creaFitxer(pathExpressions);

        return expressions;
    }

    /**
     * Llegeix les StopWords que estan guardades al Gestor de Documents
     *
     * @return {@code ArrayLis<String>} Retorna les StopWords guardades
     */
    public ArrayList<String> carregaStopWords() {
        ArrayList<String> stopWords;
        stopWords = gestorDades.llegeixStopWords(pathStopWords);
        return stopWords;
    }

    /**
     * Guarda un document al Gestor de Documents en la carpeta Documents
     *
     * @param doc Indica el document que es vol guardar
     * @throws FitxerNoEliminatException Si el document ja existia i no s'ha pogut eliminar
     * @throws FitxerNoCreatException Si no s'ha pogut guardar el document
     */
    public void guardaDocument(DocumentLlegit doc) throws FitxerNoEliminatException, FitxerNoCreatException {
        gestorDades.guardaDocument(doc);
    }

    /**
     * Elimina un document del Gestor de Documents de la carpeta Documents
     *
     * @param path Indica la path on esta guardat el document a eliminar
     * @throws FitxerNoEliminatException Si no s'ha pogut eliminar el document
     */
    public void eliminaDocument(String path) throws FitxerNoEliminatException {
        gestorDades.esborraFitxer(path);
    }

    /**
     * Guarda les expressions booleanes en el Gestor de Documents en la carpeta Documents
     *
             * @param expressions {@code ArrayList<String> expressions} Indica les expressions a guardar
     * @throws FitxerNoEliminatException Si no s'ha pogut eliminar el document on es guarden les expressions booleanes
     * @throws FitxerNoCreatException Si no s'ha pogut guardar el document amb les expressions booleanes
     */
    public void guardaExpressionsBooleanes (ArrayList<String> expressions) throws FitxerNoEliminatException, FitxerNoCreatException {
        gestorDades.guardaExpressionsBooleanes(expressions, pathExpressions);
    }

    /**
     * Retorna una nova path per a guardar un nou document
     *
     * @return {@code String} Retorna la següent path que es pot utilitzar
     */
    public String getNovaPath(){
        return path + "/" + ++numDocs + ".bol";
    }

    ///////////////////////////////////////////////////////////
    ///                  MÈTODES PRIVATS                    ///
    ///////////////////////////////////////////////////////////

    /**
     * Importa un document al Gestor de Documents
     *
     * @param file Indica on esta guardat el document a importar
     * @return {@code DocumentLlegit} Retorna el document importat
     */
    private DocumentLlegit importa(File file) {
        String path = file.getAbsolutePath();
        DocumentLlegit doc;
        doc = gestorDades.llegeixDocument(path);
        return doc;
    }

    /**
     * Llegeix tots els documents guardats en el Gestor de Documents en la carpeta Documents
     *
     * @param path Indica la path del directori on estan els documents
     * @return {@code ArrayList<DocumentLlegit>} Retorna els documents guardats en la path indicada
     */
    private ArrayList<DocumentLlegit> llegeixDocuments(String path) {
        ArrayList<DocumentLlegit> documents = new ArrayList<>();

        File carpeta = new File(path);
        String[] docs = carpeta.list(); // Obtenim tots els documents de la carpeta situada en el path

        if(docs != null && docs.length != 0) {
            // Llegim tots els documents que estan en la carpeta situada en el path
            for (String doc : docs) {
                DocumentLlegit D = gestorDades.llegeixDocument(path + "/" + doc);
                try {
                    String[] s = doc.split("\\.");
                    int n = Integer.parseInt(s[0]);
                    if (n > numDocs) numDocs = n;
                    if (D != null) documents.add(D);
                } catch (Exception e){}

            }
        }
        return documents;
    }
}

