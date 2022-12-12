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

    public CtrlPersistencia(){
        gestorDades = new GestorDades();
        numDocs = gestorDades.nombre_documents(path);
    }

    public ArrayList<DocumentLlegit> importarDocuments(ArrayList<File> files) throws FitxerNoEliminatExeption, TipusExtensioIncorrectaException, FitxerNoCreatException {
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

    private DocumentLlegit importa(File file) throws FitxerNoEliminatExeption, TipusExtensioIncorrectaException, FitxerNoCreatException{
        String path = file.getAbsolutePath();
        DocumentLlegit doc;
        doc = gestorDades.llegeixDocument(path);
        return doc;
    }



    public void exportarDocument(String titol, String autor, File file) throws FileNoExisteixException{
        try{
            if (!file.exists()) throw new FileNoExisteixException(file);

            FileWriter fw = new FileWriter(file.getAbsolutePath());

            File file_or = gestorDades.buscaDocument(titol, autor, path);
            FileReader fr = new FileReader(file_or);

            String str = "";
            int i;

            while ((i = fr.read()) != -1){
                str += (char)i;
            }
            fw.write(str);

            fr.close();
            fw.close();
        } catch (IOException e) {System.out.println(e.toString());}
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

    public String guardaDocument(DocumentLlegit doc)
            throws FitxerNoEliminatExeption, TipusExtensioIncorrectaException, FitxerNoCreatException{
        String path_nou = doc.getPath();

        if (path_nou.isEmpty()) {
            doc.setPath(path);
            path_nou = gestorDades.guardaDocument(doc,false);
        }
        else gestorDades.guardaDocument(doc, true);

        return path_nou;
    }

    public void eliminaDocument(String path) throws FitxerNoEliminatExeption{
        gestorDades.eliminaFitxer(path);
    }


    public void guardaExpressioBool(String exprAnt, String exprNova)
            throws ExpressioBooleanaJaExistentException, ExpressioBooleanaInexistentException, FitxerNoCreatException,
            FitxerNoEliminatExeption{
        gestorDades.guardaExpressioBool(exprAnt,exprNova,path);
    }

    public void eliminaExpressioBool(String expr) throws ExpressioBooleanaInexistentException,
            FitxerNoEliminatExeption, FitxerNoCreatException{
        gestorDades.eliminaExpressio(expr,path);
    }

    public String getNouPath(){
        int num = gestorDades.nombre_documents(path);
        String nouPath = path + num + ".nou";
        return nouPath;
    }
}

