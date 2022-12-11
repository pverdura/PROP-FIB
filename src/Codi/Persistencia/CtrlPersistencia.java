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
    private final String path = new File("src/Codi/Persistencia/Documents").getAbsolutePath();
    private final String path_stopWords = new File("src/Codi/stop_words.txt").getAbsolutePath();

    public CtrlPersistencia(){
        gestorDades = new GestorDades();
        gestorDades.nombre_documents(path);
    }

    public ArrayList<DocumentLlegit> importarDocuments(ArrayList<File> files){
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

    private DocumentLlegit importa(File file){
        String path = file.getAbsolutePath();
        DocumentLlegit doc;
        doc = gestorDades.llegeixDocument(path);
        gestorDades.guardaDocument(doc.getTitol(), doc.getAutor(), doc.getExtensio(), doc.getContingut(), doc.getPath());
        return doc;
    }



    public void exportaDocument(String titol, String autor, File file) throws FileNoExisteixException{
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

    public String guardaDocument(String titol, String autor, TipusExtensio ext, String contingut, String path)
            throws FitxerNoEliminatExeption, CarpetaNoCreadaException, TipusExtensioIncorrectaException{
        String path_nou = path;

        if (path.isEmpty()) path_nou = gestorDades.guardaDocument(titol,autor,ext,contingut,this.path,true);
        else gestorDades.guardaDocument(titol,autor,ext,contingut,path, false);

        return path_nou;
    }

    public void eliminaDocument(String titol, String autor, String path){
        gestorDades.eliminaDocument(titol,autor,path);
    }


    public void guardaExpressioBool(String exprAnt, String exprNova)
            throws ExpressioBooleanaJaExistentException, ExpressioBooleanaInexistentException{
        gestorDades.guardaExpressioBool(exprAnt,exprNova,path);
    }

    public void eliminaExpressioBool(String expr, String path){
        gestorDades.eliminaExpressio(expr,path);
    }

}

