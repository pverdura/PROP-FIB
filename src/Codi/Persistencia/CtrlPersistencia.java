package Codi.Persistencia;

import Codi.Excepcions.*;
import Codi.Util.DocumentLlegit;

import java.io.*;

import java.util.ArrayList;

public class CtrlPersistencia {

    private final GestorDades gestorDades;
    private int numDocs;
    private final String path = new File("src/Codi/Persistencia/Documents").getPath();
    private final String pathStopWords = new File("src/Codi/stopWords.csv").getAbsolutePath();
    private final String pathExpressions = new File("src/Codi/Persistencia/Documents/expressions.txt").getPath();
    public CtrlPersistencia(){
        gestorDades = new GestorDades();
        numDocs = 0;
    }

    public ArrayList<DocumentLlegit> importarDocuments(ArrayList<File> files) {
        ArrayList<DocumentLlegit> docs = new ArrayList<>();
        for(File f: files){
            DocumentLlegit doc = importa(f);
            docs.add(doc);
        }
        return docs;
    }

    private DocumentLlegit importa(File file) {
        String path = file.getAbsolutePath();
        DocumentLlegit doc;
        doc = gestorDades.llegeixDocument(path);
        return doc;
    }



    public void exportarDocument(DocumentLlegit doc) {
        gestorDades.exportarDocument(doc);
    }

    /*
     * Llegeix els documents guardats en la carpeta path, i si no existeix, crea la carpeta
     *
     * @param path Indica el path relatiu de la carpeta on estan situats els documents
     * @return Retorna un array amb els documents guardats si existeix algun en la carpeta path, altrament retorna null
     * @throws CarpetaNoCreadaException Si no s'ha pogut crear la carpeta en el path indicat
     * @throws TipusExtensioIncorrectaException Si hi ha algun document amb una extensió no coneguda
     */
    public ArrayList<DocumentLlegit> carregaDocuments() throws CarpetaNoCreadaException, CarpetaBuidaException{
        ArrayList<DocumentLlegit> documents = new ArrayList<>();
        boolean existeix = gestorDades.existeixDirectori(path);

        if(existeix) documents = llegeixDocuments(path);
        else gestorDades.creaDirectori(path);

        return documents;
    }


    /*
     * Funció que llegeix tots els documents d'un directori
     *
     * @param path Indica el directori on estan situats els documents
     * @return Retorna un array de DocumentsLlegits on en cada objecte hi ha l'autor, títol, format i contingut
     *         el document llegit
     * @throws TipusExtensioIncorrectaException Si hi ha algun document en el directori path que no té
     *         l'extensió .txt, .xml o .bol
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

    public ArrayList<String> carregaExpressionsBooleanes() {
        ArrayList<String> expressions = new ArrayList<>();
        boolean existeix = gestorDades.existeixFitxer(pathExpressions);

        if(existeix) expressions = gestorDades.llegeixExpressions(pathExpressions);
        else gestorDades.creaFitxer(pathExpressions);

        return expressions;
    }

    /*
     * Llegeix les StopWords que estan guardades en el path indicat
     *
     * @param path Indica en quin lloc estan guardades les stopWords
     * @return Retorna un array amb paraules stopWords
     * @throws CarpetaNoCreadaException Si s'ha intentat crear la carpeta i no s'ha pogut
     * @throws FitxerNoCreatException Si S'ha intentat crear el fitxer i no s'ha pogut
     */
    public ArrayList<String> carregaStopWords() {
        ArrayList<String> stopWords;
        stopWords = gestorDades.llegeixStopWords(pathStopWords);
        return stopWords;
    }


    public void guardaDocument(DocumentLlegit doc) throws FitxerNoEliminatException, FitxerNoCreatException{
        gestorDades.guardaDocument(doc);
    }

    public void eliminaDocument(String path) throws FitxerNoEliminatException{
        gestorDades.esborraFitxer(path);
    }


    public void guardaExpressionsBooleanes (ArrayList<String> expressions) throws FitxerNoEliminatException{
        gestorDades.guardaExpressionsBooleanes(expressions, pathExpressions);
    }



    public String getNovaPath(){
        return path + "/" + ++numDocs + ".bol";
    }
}

