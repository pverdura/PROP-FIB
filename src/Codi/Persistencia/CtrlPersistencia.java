package Codi.Persistencia;

import Codi.Excepcions.*;
import Codi.Util.DocumentLlegit;
import Codi.Util.TipusExtensio;

import java.io.*;
import java.util.AbstractMap.SimpleEntry;

import javax.print.Doc;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayList;

public class CtrlPersistencia {

    private GestorDades gestorDades;
    private int numDocs;
    private final String path = new File("src/Codi/Persistencia/Documents").getPath();
    private final String path_stopWords = new File("src/Codi").getAbsolutePath();
    int n;
    public CtrlPersistencia(){
        gestorDades = new GestorDades();
        //numDocs = gestorDades.nombre_documents(path);
        n = 1;
    }

    public ArrayList<DocumentLlegit> importarDocuments(ArrayList<File> files) throws FitxerNoEliminatException, TipusExtensioIncorrectaException, FitxerNoCreatException {
        ArrayList<DocumentLlegit> docs = new ArrayList<>();
        DocumentLlegit doc;
        if (files.size() > 1){
            for(File f: files){
                doc = importa(f);
                docs.add(doc);
            }
        } else{
            doc = importa(files.get(0));
            docs.add(doc);
        }
        return docs;
    }

    private DocumentLlegit importa(File file) throws FitxerNoEliminatException, TipusExtensioIncorrectaException, FitxerNoCreatException{
        String path = file.getAbsolutePath();
        DocumentLlegit doc;
        doc = gestorDades.llegeixDocument(path);
        return doc;
    }



    public void exportarDocument(DocumentLlegit doc) throws FileNoExisteixException{
        //gestorDades.guardaDocument(doc);
    }


    public ArrayList<DocumentLlegit> carregaDocuments() throws CarpetaNoCreadaException,
            CarpetaBuidaException, TipusExtensioIncorrectaException{
        return gestorDades.carregaDocuments(path);
    }

    public ArrayList<String> carregaExpressionsBooleanes() throws CarpetaNoCreadaException,
            FitxerNoCreatException, CarpetaBuidaException{
        return gestorDades.carregaExpressionsBooleanes(path);
    }

    public ArrayList<String> carregaStopWords() throws CarpetaNoCreadaException, FitxerNoCreatException{
        return gestorDades.carregaStopWords(path_stopWords);
    }

    public void guardaDocument(DocumentLlegit doc)
            throws FitxerNoEliminatException, TipusExtensioIncorrectaException, FitxerNoCreatException{
        //gestorDades.guardaDocument(doc);
    }

    public void eliminaDocument(String path) throws FitxerNoEliminatException{
        gestorDades.eliminaFitxer(path);
    }


    public void guardaExpressioBool(String exprAnt, String exprNova)
            throws ExpressioBooleanaJaExistentException, ExpressioBooleanaInexistentException, FitxerNoCreatException,
            FitxerNoEliminatException{
        gestorDades.guardaExpressioBool(exprAnt,exprNova,path);
    }

    public void eliminaExpressioBool(String expr) throws ExpressioBooleanaInexistentException,
            FitxerNoEliminatException, FitxerNoCreatException{
        gestorDades.eliminaExpressio(expr,path);
    }

    public String getNovaPath(){
        ++n;
        String nouPath = path + n + ".bol";
        return nouPath;
    }
}

