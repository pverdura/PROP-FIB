package Codi.Persistencia;

import Codi.Excepcions.*;
import Codi.Util.DocumentLlegit;

import java.io.*;

import java.util.ArrayList;

public class CtrlPersistencia {

    ///////////////////////////////////////////////////////////
    ///                     VARIABLES                       ///
    ///////////////////////////////////////////////////////////
    private final GestorDades gestorDades;
    private int numDocs;
    private final String path = new File("src/Codi/Persistencia/Documents").getPath();
    private final String pathStopWords = new File("src/Codi/stopWords.csv").getAbsolutePath();
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
     * Importa una sèrie de documents al Gestor de Documents
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
     * @param doc  Indica al document que es vol exportar
     */
    public void exportarDocument(DocumentLlegit doc) {
        gestorDades.exportarDocument(doc);
    }


    /**
     * Llegeix els documents guardats en el Gestor de Documents en la carpeta Documents, i si no existeix, la crea
     *
     * @return  {@code ArrayList<DocumentLlegit>} Retorna els documents guardats en l'aplicació
     * @throws CarpetaNoCreadaException Si no s'ha pogut crear la carpeta
     * @throws CarpetaBuidaException Si no hi ha guardat cap document en el sistema
     */
    public ArrayList<DocumentLlegit> carregaDocuments() throws CarpetaNoCreadaException, CarpetaBuidaException{
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
     */
    public ArrayList<String> carregaExpressionsBooleanes() {
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
     */
    public void guardaDocument(DocumentLlegit doc) {
        gestorDades.guardaDocument(doc);
    }

    /**
     * Elimina un document del Gestor de Documents de la carpeta Documents
     *
     * @param path Indica la path on està guardat el document a eliminar
     */
    public void eliminaDocument(String path) {
        gestorDades.esborraFitxer(path);
    }


    /**
     * Guarda les expressions booleanes en el Gestor de Documents en la carpeta Documents
     *
     * @param expressions {@code ArrayList<String> expressions} Indica les expressions a guardar
     */
    public void guardaExpressionsBooleanes (ArrayList<String> expressions) {
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
     * @param file Indica on està guardat el document a importar
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
                if (!doc.equals("expressions.txt")) {
                    DocumentLlegit D = gestorDades.llegeixDocument(path + "/" + doc);
                    try {
                        String[] s = doc.split("\\.");
                        int n = Integer.parseInt(s[0]);
                        if (n > numDocs) numDocs = n;
                        if (D != null) documents.add(D);
                    } catch (Exception e){}
                }
            }
        }
        return documents;
    }
}

