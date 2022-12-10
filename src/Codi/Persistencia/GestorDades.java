package Codi.Persistencia;

import Codi.Excepcions.*;
import Codi.Util.DocumentLlegit;
import Codi.Util.TipusExtensio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class GestorDades {

    ///////////////////////////////////////////////////////////
    ///                 FUNCIONS PRIVADES                   ///
    ///////////////////////////////////////////////////////////

    /**
     * Crea un fitxer en el path path
     *
     * @param path Indica el lloc on es crea el fitxer
     * @throws FitxerNoCreatException Si no s'ha pogut crear el fitxer
     */
    private void creaFitxer(String path) throws FitxerNoCreatException {
        File F = new File(path);
        try {
            boolean creada = F.createNewFile();

            // Mirem si s'ha creat el fitxer
            if(creada && F.isFile()) {
                System.out.println("S'ha creat el fitxer "+ path +" correctament");
            }
            else {
                throw new FitxerNoCreatException(path);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void eliminaFitxer(String path) {
        File doc = new File(path);
        if (doc.delete()) {
            System.out.println("S'ha eliminat el fitxer " + path + " correctament");
        } else {
            throw new FitxerNoEliminatExeption(path);
        }
    }

    private Boolean existeixFixter(String path) {
        File doc = new File(path);
        return doc.exists() && doc.isFile();
    }

    /**
     * Crea el directori on s'emmagatzemen les deades del sistema
     *
     * @param path Indica el path de la carpeta on aniran els fitxers .csv
     * @param carpeta Objecte de la carpeta on aniran les dades
     * @throws CarpetaNoCreadaException Si no s'ha pogut crear la carpeta en el path indicat
     */
    private void creaDirectori(String path, File carpeta) throws CarpetaNoCreadaException {
        // Error en crear la carpeta
        if(carpeta.mkdirs()) {
            System.out.println("S'ha creat el fitxer correctament");
        }
        else {
            throw new CarpetaNoCreadaException(path);
        }
    }

    private Boolean existeixDirectori(String path) {
        File carpeta = new File(path);
        boolean existeix = false;

        // Primer mirem si existeix el directori on guardem els documents, expressions i stopWords
        if(carpeta.exists() && carpeta.isDirectory()) existeix = true;
            // Si no existeix el directori, el creem
        else creaDirectori(path,carpeta);

        return existeix;
    }

    private DocumentLlegit llegeixDocumentTXT(Path PATH) {
        DocumentLlegit D = new DocumentLlegit();

        // Lector que ens llegirà el document
        try (BufferedReader lector = Files.newBufferedReader(PATH,StandardCharsets.UTF_8)) {
            String contingut = null;    // Concatenació de línies per llegir el contingut
            String linia = null;        // Ens ajuda a llegir línies del document
            int num_linia = 0;          // Nombre de línies llegides

            // Llegim el document mentre hi hagi línies
            while ((linia = lector.readLine()) != null) {
                if (num_linia == 0) {   // En aquesta línia hi ha l'autor
                    D.setAutor(linia);
                }
                else if (num_linia == 1) {   // En aquesta línia hi ha el títol
                    D.setTitol(linia);
                }
                else {  // En aquestes línies hi ha el contingut
                    if (contingut != null) contingut = contingut.concat("\n" + linia);
                    else contingut = linia;
                }
                ++num_linia;
            }
            D.setContingut(contingut);
            D.setExtensio(TipusExtensio.TXT);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return D;
    }

    private DocumentLlegit llegeixDocumentXML(Path PATH) {
        DocumentLlegit D = new DocumentLlegit();

        // Lector que ens llegirà el document
        try (BufferedReader lector = Files.newBufferedReader(PATH, StandardCharsets.UTF_8)) {
            String contingut = null;    // Concatenació de línies per llegir el contingut
            String linia = null;        // Ens ajuda a llegir línies del document
            boolean c = false;          // Ens indica si estem llegint el contingut

            // Llegim el document mentre hi hagi línies
            while ((linia = lector.readLine()) != null) {
                if(linia.startsWith("<autor>") && linia.endsWith("</autor>")) {
                    // Agafem el text que està entre <autor> i </autor>
                    D.setTitol(linia.substring(7,linia.length()-8));
                }
                else if(linia.startsWith("<titol>") && linia.endsWith("</titol>")) {
                    // Agafem el text que està entre <titol> i </titol>
                    D.setAutor(linia.substring(7,linia.length()-8));
                }
                else if(linia.startsWith("<contingut>")) {
                    c = true;
                }
                else if (c) { // Concatenem les línies per obtenir el contingut
                    if(!linia.endsWith("</contingut>")) {
                        // Si hi ha la tabulació la treiem
                        if(linia.startsWith("\t")) linia = linia.substring(1);

                        if(contingut == null) contingut = linia;
                        else contingut = contingut + "\n" + linia;
                    }
                    else c = false;
                }
                else break;
            }
            D.setContingut(contingut);
            D.setExtensio(TipusExtensio.XML);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return D;
    }

    private DocumentLlegit llegeixDocumentBOL(Path PATH) {
        DocumentLlegit D = new DocumentLlegit();

        // Lector que ens llegirà el document
        try (BufferedReader lector = Files.newBufferedReader(PATH, StandardCharsets.UTF_8)) {
            String contingut = null;    // Concatenació de línies per llegir el contingut
            String linia = null;        // Ens ajuda a llegir línies del document
            int espais = 0;             // Ens indica el nombre de "----" que hem llegit

            // Llegim el document mentre hi hagi línies
            while ((linia = lector.readLine()) != null) {
                if(linia.equals("----")) {
                    ++espais;
                }
                else if(espais == 0) {
                    D.setAutor(linia);
                }
                else if(espais == 1) {
                    D.setTitol(linia);
                }
                else if(espais == 2) {
                    if (contingut != null) contingut = contingut + "\n" + linia;
                    else contingut = linia;
                }
                else break;
            }
            D.setContingut(contingut);
            D.setExtensio(TipusExtensio.BOL);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return D;
    }

    private ArrayList<String> llegeixDocumentCSV (Path PATH) {
        ArrayList<String> doc = new ArrayList<String>();

        // Lector que ens llegirà el document
        try (BufferedReader lector = Files.newBufferedReader(PATH, StandardCharsets.UTF_8)) {
            String linia = null;        // Ens ajuda a llegir línies del document

            // Llegim el document mentre hi hagi línies
            while ((linia = lector.readLine()) != null) {
                String[] linia_i = linia.split(",");

                doc.addAll(Arrays.asList(linia_i));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    private DocumentLlegit llegeixDocument(String path) throws TipusExtensioIncorrectaException {
        DocumentLlegit D = null;

        // Mirem que el document que volem llegir existeixi
        if(existeixFixter(path)) {
            Path PATH = Paths.get(path);

            // Mirem el tipus d'extensió del document
            int l = path.length();
            // Mirem que no siguin backups creats per emacs, nano o vi
            if(!path.endsWith("~")) {
                String ext = path.substring(l - 4, l);

                switch (ext) {
                    case ".txt":
                        D = llegeixDocumentTXT(PATH);
                        break;
                    case ".xml":
                        D = llegeixDocumentXML(PATH);
                        break;
                    case ".bol":
                        D = llegeixDocumentBOL(PATH);
                        break;
                    default:
                        throw new TipusExtensioIncorrectaException(ext);
                }
            }
        }
        return D;
    }

    private ArrayList<DocumentLlegit> llegeixDocuments(String path) throws TipusExtensioIncorrectaException {
        ArrayList<DocumentLlegit> documents = new ArrayList<DocumentLlegit>();
        File carpeta = new File(path);
        String[] docs = carpeta.list(); // Obtenim tots els documents de la carpeta situada en el path

        if(docs != null && docs.length != 0) {
            // Llegim tots els documents que estan en la carpeta situada en el path
            for (String doc : docs) {
                DocumentLlegit D = llegeixDocument(path+"/"+doc);
                if(D != null) documents.add(D);
            }
        }
        else {
            throw new CarpetaBuidaException();
        }
        return documents;
    }

    /**
     * Llegeix el contingut del fitxer expressions.csv per a obtenir les expressions booleanes guardades
     * en aquests fitxer
     *
     * @param path Indica el path del fitxer expressions.csv
     * @return Retorna un array de les expressions booleanes guardades
     */
    private ArrayList<String> llegeixExpressions(String path) {
        ArrayList<String> expressions = new ArrayList<String>();

        // Mirem que el fitxer on guardem les expressions existeixi
        if(existeixFixter(path)) {
            Path PATH = Paths.get(path);

            // Lector que ens llegirà el fitxer
            try (BufferedReader lector = Files.newBufferedReader(PATH,StandardCharsets.UTF_8)) {
                String linia = null;

                // Llegim el fitxer mentre hi hagi línies
                while((linia = lector.readLine()) != null) {
                    // Afegim l'expressió en el llistat
                    expressions.add(linia);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {  // Si no existeix, el creem
            creaFitxer(path);
        }
        return expressions;
    }

    private ArrayList<String> llegeixStopWords(String path) {
        ArrayList<String> stopWords = null;

        // Mirem que el fitxer on guardem les stop words existeixi
        if(existeixFixter(path)) {
            Path PATH = Paths.get(path);
            stopWords = llegeixDocumentCSV(PATH);
        }
        else {  // Si no existeix, el creem
            creaFitxer(path);
        }
        return stopWords;
    }

    /**
     * Escriu en el fitxer doc els paràmetres de la funció (titol,autor,ext,contingut) en format CSV
     *
     * @param titol Indica el títol que identifica el document
     * @param autor Indica l'autor que identifica el document
     * @param contingut Indica el contingut del document
     * @param new_path Indica el path del fitxer que volem escriure
     */
    private void guardaDocumentTXT(String titol, String autor, String contingut, String new_path) {
        String path = new_path + "txt";
        creaFitxer(path);

        Path PATH = Paths.get(path);

        try (BufferedWriter escriptor = Files.newBufferedWriter(PATH,StandardCharsets.UTF_8)) {
            escriptor.write(autor + "\n" + titol + "\n" + contingut);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardaDocumentXML(String titol, String autor, String contingut, String new_path) {
        String path = new_path + "xml";
        creaFitxer(path);

        Path PATH = Paths.get(path);

        try (BufferedWriter escriptor = Files.newBufferedWriter(PATH,StandardCharsets.UTF_8)) {
            escriptor.write("<autor>" + autor + "</autor>\n");
            escriptor.write("<titol>" + titol + "</titol>\n");
            escriptor.write("<contingut>");

            String[] lines_contingut = contingut.split("\n");

            for(String linia : lines_contingut) {
                escriptor.write("\n\t" + linia);
            }

            escriptor.write("\n</contingut>");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardaDocumentBOL(String titol, String autor, String contingut, String new_path) {
        String path = new_path + "bol";
        creaFitxer(path);

        Path PATH = Paths.get(path);

        try (BufferedWriter escriptor = Files.newBufferedWriter(PATH,StandardCharsets.UTF_8)) {
            escriptor.write(autor);
            escriptor.write("\n----\n");
            escriptor.write(titol);
            escriptor.write("\n----\n");
            escriptor.write(contingut);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardaDocumentCSV(ArrayList<String> paraules, String path) {
        creaFitxer(path);
        Path PATH = Paths.get(path);

        try (BufferedWriter escriptor = Files.newBufferedWriter(PATH,StandardCharsets.UTF_8)) {
            boolean primera = true;

            for(String paraula : paraules) {
                if(primera) {
                    primera = false;
                    escriptor.write(paraula);
                }
                else escriptor.write(","+paraula);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void guardaExpressions(ArrayList<String> paraules, String path) {

    }

    public void guardaExpressio(String expr, String path) {
        Path PATH = Paths.get(path);

        try (BufferedWriter escriptor = Files.newBufferedWriter(PATH, StandardCharsets.UTF_8)) {
            escriptor.write("\n"+expr);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void modificaExpressio(String exprAnt, String exprNova, String path) {

    }


    ///////////////////////////////////////////////////////////
    ///                 FUNCIONS PÚBLIQUES                  ///
    ///////////////////////////////////////////////////////////

    /**
     * Llegeix el contingut del fitxer documents.csv per a obtenir els documents guardats
     * en aquest fitxer, i si no existeix, crea els fitxers
     *
     * @param path Indica el path relatiu de la carpeta on està situat el fitxer
     * @return Retorna un array amb els documents guardats si existeix el fitxer, altrament retorna null
     * @throws CarpetaNoCreadaException Si no s'ha pogut crear la carpeta en el path indicat
     * @throws FitxerNoCreatException Si no s'ha pogut crear el fitxer en la carpeta del path indicat
     * @throws TipusExtensioIncorrectaException Si hi ha algun document amb una extensió no coneguda
     */
    public ArrayList<DocumentLlegit> carregaDocuments(String path) throws CarpetaBuidaException {
        ArrayList<DocumentLlegit> documents = null;
        boolean existeix = existeixDirectori(path);

        if(existeix) documents = llegeixDocuments(path);
        else throw new CarpetaBuidaException();

        return documents;
    }

    /**
     * Llegeix el contingut del fitxer expressions.csv per a obtenir les expressions booleanes guardades
     * en aquest fitxer, i si no existeix, crea els fitxers
     *
     * @param path Indica el path relatiu de la carpeta on està situat el fitxer
     * @return Retorna un array amb les expressions guardades si existeix el fitxer
     * @throws CarpetaNoCreadaException Si no s'ha pogut crear la carpeta en el path indicat
     * @throws FitxerNoCreatException Si no s'ha pogut crear el fitxer en la carpeta del path indicat
     */
    public ArrayList<String> carregaExpressionsBooleanes(String path) throws CarpetaNoCreadaException,
            FitxerNoCreatException {
        ArrayList<String> expressions = null;
        boolean existeix = existeixDirectori(path);

        if(existeix) expressions = llegeixExpressions(path+"/expressions.txt");
        else throw new CarpetaBuidaException();

        return expressions;
    }

    public ArrayList<String> carregaStopWords(String path) throws CarpetaNoCreadaException, FitxerNoCreatException {
        ArrayList<String> stopWords = null;
        boolean existeix = existeixDirectori(path);

        if(existeix) stopWords = llegeixStopWords(path+"/stopWords.csv");
        else throw new CarpetaBuidaException();

        return stopWords;
    }

    public void guardaDocument(String titol, String autor, TipusExtensio ext, String contingut, String path) {
        // Primer mirem si ja existeix el document, i si existeix l'elimniem
        // d'aquesta manera, podem canviar el format dels documents a més a més
        if(existeixFixter(path)) eliminaFitxer(path);
        String new_path = path.substring(0,path.length()-3);

        // Guardem el document
        switch(ext.toString()) {
            case("TXT"):
                guardaDocumentTXT(titol,autor,contingut,new_path);
                break;
            case("XML"):
                guardaDocumentXML(titol,autor,contingut,new_path);
                break;
            case("BOL"):
                guardaDocumentBOL(titol,autor,contingut,new_path);
                break;
            default:
                throw new TipusExtensioIncorrectaException(ext.toString());
        }
    }

    public void guardaExpressioBool(String exprAnt, String exprNova, String path) {
        if(exprAnt == null) {   // La expressió és nova
            guardaExpressio(exprNova,path);
        }
        else {  // La expressió és modificada
            modificaExpressio(exprAnt,exprNova,path);
        }
    }
    public void guardaStopWords(ArrayList<String> paraules, String path) {
        // Primer mirem si ja existeix el document, i si existeix l'eliminem
        if(existeixFixter(path)) eliminaFitxer(path);

        // Guardem les stopWords
        guardaDocumentCSV(paraules, path);
    }
}
