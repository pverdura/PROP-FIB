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
    }

    public ArrayList<DocumentLlegit> importarDocument(ArrayList<File> files){
        ArrayList<DocumentLlegit> docs = new ArrayList<>();
        DocumentLlegit doc;
        String path;
        if (files.size() > 1){
            for(File f: files){
                path = f.getAbsolutePath();
                doc = gestorDades.llegeixDocument(path);
                gestorDades.guardaDocument(doc.getTitol(), doc.getAutor(), doc.getExtensio(), doc.getContingut(), doc.getPath());
                docs.add(doc);
            }
        } else{
            path = files.get(0).getAbsolutePath();
            doc = gestorDades.llegeixDocument(path);
            gestorDades.guardaDocument(doc.getTitol(), doc.getAutor(), doc.getExtensio(), doc.getContingut(), doc.getPath());
            docs.add(doc);
        }
        return docs;
    }

    public void exportarDocument(ArrayList<SimpleEntry<String,String>> id, File file) throws FileNoExisteixException{
        try{
            if (id.size() > 1){
                for (SimpleEntry<String,String> s: id){
                    exporta(s.getKey(), s.getValue(), path, file);
                }

            } else {exporta(id.get(0).getKey(), id.get(0).getValue(), path, file); }
        } catch (IOException e) { System.out.println(e.toString());
        }
    }

    private void exporta(String titol, String autor, String path, File file) throws FileNoExisteixException{
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

    public void escriuExpressio(String expr, String path, Boolean primera){
        Path PATH = Paths.get(path);
        gestorDades.escriuExpressio(expr, PATH, primera);
    }

    public void guardaExpressions(ArrayList<String> expressions, String path){
        gestorDades.guardaExpressions(expressions,path);
    }

    public void guardaExpressio(String expr, String path) throws ExpressioBooleanaJaExistentException {
        gestorDades.guardaExpressio(expr, path);
    }

    public void modificaExpressio(String exprAnt, String exprNova, String path) throws FitxerNoEliminatExeption, FitxerNoCreatException, ExpressioBooleanaInexistentException {
        gestorDades.modificaExpressio(exprAnt, exprNova, path);
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

    public void guardaDocument(String titol, String autor, TipusExtensio ext, String contingut, String path)
            throws FitxerNoEliminatExeption, CarpetaNoCreadaException, TipusExtensioIncorrectaException{
        gestorDades.guardaDocument(titol,autor,ext,contingut,path);
    }

    public void guardaExpressioBool(String exprAnt, String exprNova, String path)
            throws ExpressioBooleanaJaExistentException, ExpressioBooleanaInexistentException{
        gestorDades.guardaExpressioBool(exprAnt,exprNova,path);
    }

}

