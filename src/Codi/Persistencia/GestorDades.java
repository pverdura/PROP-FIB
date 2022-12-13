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
     * Crea un fitxer en el path indicat
     *
     * @param path Indica el lloc on es crea el fitxer
     * @throws FitxerNoCreatException Si no s'ha pogut crear el fitxer
     */
    public void creaFitxer(String path) throws FitxerNoCreatException {
        File F = new File(path);
        try {
            boolean creada = F.createNewFile();

            // Mirem si s'ha creat el fitxer
            if(creada && F.isFile()) {
                System.out.println("S'ha creat el fitxer "+ path +" correctament");
            } else {  // Hi ha hagut un problema en crear el fitxer
                throw new FitxerNoCreatException(path);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina el fitxer amb el path indicat
     *
     * @param path Indica on està situal el fitxer que volem eliminar
     * @throws FitxerNoEliminatException Si no s'ha pogut eliminar el fitxer
     */
    public void eliminaFitxer(String path) throws FitxerNoEliminatException {
        File doc = new File(path);

        // No hi ha cap problema en eliminar el fitxer
        if (doc.delete()) {
            System.out.println("S'ha eliminat el fitxer " + path + " correctament");
        } else {  // Hi ha hagut algun problema en eliminar el fitxer
            throw new FitxerNoEliminatException(path);
        }
    }

    /**
     * Informa si existeix el fitxer amb el path indicat
     *
     * @param path Indica el fitxer que volem saber si existeix
     * @return Retorna true si existeix el fitxer en el path indicat, false altrament
     */
    public Boolean existeixFitxer(String path) {
        File doc = new File(path);
        return doc.exists() && doc.isFile();
    }

    /**
     * Crea el directori amb el path indicat
     *
     * @param path Indica el path del directori que es vol crear
     * @param carpeta Objecte del directori que es vol crear
     * @throws CarpetaNoCreadaException Si no s'ha pogut crear la carpeta en el path indicat
     */
    public void creaDirectori(String path, File carpeta) throws CarpetaNoCreadaException {
        // No hi ha cap error en crear el directori
        if(carpeta.mkdirs()) {
            System.out.println("S'ha creat el fitxer correctament");
        } else {  // Hi ha un error al crear el directori
            throw new CarpetaNoCreadaException(path);
        }
    }

    /**
     * Indica si existeix el fitxer amb el path indicat
     *
     * @param path Indica el directori que volem saber si existeix
     * @return Retorna true si existeix el fitxer en el path indicat, false altrament
     * @throws CarpetaNoCreadaException Si s'ha intentat crear el directori i no s'ha pogut crear
     */
    public Boolean existeixDirectori(String path) throws CarpetaNoCreadaException {
        File carpeta = new File(path);
        boolean existeix = false;

        // Primer mirem si existeix el directori on guardem els documents, expressions i stopWords
        if(carpeta.exists() && carpeta.isDirectory()) {
            existeix = true;
        } else {  // Si no existeix el directori, el creem
            creaDirectori(path,carpeta);
        }
        return existeix;
    }

    /**
     * Llegeix un document en format .txt
     *
     * @param PATH Indica el path del fitxer .txt que es llegirà
     * @return Retorna un DocumentLlegit que conté l'autor, títol, formai i contingut del fitxer lleegit
     */
    private DocumentLlegit llegeixDocumentTXT(Path PATH) {
        DocumentLlegit D = new DocumentLlegit();

        // Lector que ens llegirà el document
        try (BufferedReader lector = Files.newBufferedReader(PATH,StandardCharsets.UTF_8)) {
            String contingut = "";      // Concatenació de línies per llegir el contingut
            String linia;               // Ens ajuda a llegir línies del document
            int num_linia = 0;          // Nombre de línies llegides

            // Llegim el document mentre hi hagi línies
            while ((linia = lector.readLine()) != null) {
                if (num_linia == 0) {   // En aquesta línia hi ha l'autor
                    D.setAutor(linia.trim());
                }
                else if (num_linia == 1) {   // En aquesta línia hi ha el títol
                    D.setTitol(linia.trim());
                }
                else {  // En aquestes línies hi ha el contingut
                    if (!contingut.equals("")) contingut = contingut.concat("\n" + linia);
                    else contingut = linia;
                }
                ++num_linia;
            }
            D.setContingut(contingut);
            D.setExtensio(TipusExtensio.TXT);
            D.setPath(PATH.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return D;
    }

    /**
     * Llegeix un document en format .xml
     *
     * @param PATH Indica el path del fitxer .xml que es llegirà
     * @return Retorna un DocumentLlegit que conté l'autor, títol, formai i contingut del fitxer lleegit
     */
    private DocumentLlegit llegeixDocumentXML(Path PATH) {
        DocumentLlegit D = new DocumentLlegit();

        // Lector que ens llegirà el document
        try (BufferedReader lector = Files.newBufferedReader(PATH, StandardCharsets.UTF_8)) {
            String contingut = "";      // Concatenació de línies per llegir el contingut
            String linia;               // Ens ajuda a llegir línies del document
            boolean c = false;          // Ens indica si estem llegint el contingut

            // Llegim el document mentre hi hagi línies
            while ((linia = lector.readLine()) != null) {
                if (c) { // Concatenem les línies per obtenir el contingut
                    if(linia.endsWith("</contingut>")) {
                       c = false;
                    }
                    else { // Treiem la tabulació
                        linia = linia.substring(2);
                        contingut = contingut + "\n" + linia;
                    }
                }
                else {
                    if(linia.contains("<autor>") && linia.contains("</autor>")) {
                        // Agafem el text que està entre <autor> i </autor>
                        D.setAutor(linia.substring(11,linia.length()-8).trim());
                    }
                    else if(linia.contains("<titol>") && linia.contains("</titol>")) {
                        // Agafem el text que està entre <titol> i </titol>
                        D.setTitol(linia.substring(11,linia.length()-8).trim());
                    }
                    else if(linia.contains("\t<contingut>")) {
                        c = true;
                    }
                }
            }
            D.setContingut(contingut);
            D.setExtensio(TipusExtensio.XML);
            D.setPath(PATH.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return D;
    }

    /**
     * Llegeix un document en format .bol
     *
     * @param PATH Indica el path del fitxer .bol que es llegirà
     * @return Retorna un DocumentLlegit que conté l'autor, títol, formai i contingut del fitxer lleegit
     */
    private DocumentLlegit llegeixDocumentBOL(Path PATH) {
        DocumentLlegit D = new DocumentLlegit();

        // Lector que ens llegirà el document
        try (BufferedReader lector = Files.newBufferedReader(PATH, StandardCharsets.UTF_8)) {
            String contingut = "";      // Concatenació de línies per llegir el contingut
            String linia;               // Ens ajuda a llegir línies del document
            int espais = 0;             // Ens indica el nombre de "----" que hem llegit

            // Llegim el document mentre hi hagi línies
            while ((linia = lector.readLine()) != null) {
                if(linia.equals("----")) {
                    ++espais;
                }
                else if(espais == 0) {
                    D.setAutor(linia.trim());
                }
                else if(espais == 1) {
                    D.setTitol(linia.trim());
                }
                else if(espais == 2) {
                    if (contingut.equals("")) contingut = contingut + "\n" + linia;
                    else contingut = linia;
                }
                else break;
            }
            D.setContingut(contingut);
            D.setExtensio(TipusExtensio.BOL);
            D.setPath(PATH.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return D;
    }

    /**
     * Llegeix un document en format .csv
     *
     * @param PATH Indica el path del fitxer .csv que es llegirà
     * @return Retorna un array de les paraules que conté el fitxer
     */
    private ArrayList<String> llegeixDocumentCSV (Path PATH) {
        ArrayList<String> doc = new ArrayList<String>();

        // Lector que ens llegirà el document
        try (BufferedReader lector = Files.newBufferedReader(PATH, StandardCharsets.UTF_8)) {
            String linia;   // Ens ajuda a llegir línies del document

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

    /*
     * Funció que llegeix tots els documents d'un directori
     *
     * @param path Indica el directori on estan situats els documents
     * @return Retorna un array de DocumentsLlegits on en cada objecte hi ha l'autor, títol, format i contingut
     *         el document llegit
     * @throws TipusExtensioIncorrectaException Si hi ha algun document en el directori path que no té
     *         l'extensió .txt, .xml o .bol
     *
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
     */

    /**
     * Llegeix el contingut del fitxer expressions.csv per a obtenir les expressions booleanes guardades
     * en aquests fitxer
     *
     * @param path Indica el path del fitxer expressions.csv
     * @return Retorna un array de les expressions booleanes guardades
     * @throws FitxerNoCreatException Si s'ha intentat crear el fitxer expressions.csv i no s'ha pogut
     */
    public ArrayList<String> llegeixExpressions(String path) throws FitxerNoCreatException {
        ArrayList<String> expressions = new ArrayList<String>();

        // Mirem que el fitxer on guardem les expressions existeixi
        if(existeixFitxer(path)) {
            Path PATH = Paths.get(path);

            // Lector que ens llegirà el fitxer
            try (BufferedReader lector = Files.newBufferedReader(PATH,StandardCharsets.UTF_8)) {
                String linia;

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

    /**
     * Llegeix les paraules que es consideren StopWords
     *
     * @param path Indica el document on estan guardades les StopWords
     * @return Retorna un array de paraules, on cada paraula és una StopWord
     * @throws FitxerNoCreatException Si el docuemnt on estan les StopWords s'ha intentat crear i no s'ha pogut
     */
    public ArrayList<String> llegeixStopWords(String path) throws FitxerNoCreatException {
        ArrayList<String> stopWords = new ArrayList<String>();

        // Mirem que el fitxer on guardem les stop words existeixi
        if(existeixFitxer(path)) {
            Path PATH = Paths.get(path);
            stopWords = llegeixDocumentCSV(PATH);
        }
        else {  // Si no existeix, el creem
            creaFitxer(path);
        }
        return stopWords;
    }

    /**
     * Escriu en el fitxer doc els paràmetres de la funció (titol,autor,contingut) en format TXT
     *
     * @param titol Indica el títol que identifica el document
     * @param autor Indica l'autor que identifica el document
     * @param contingut Indica el contingut del document
     * @param path Indica el path del fitxer que volem escriure
     * @throws FitxerNoCreatException Si no s'ha pogut crear el fitxer on es guarda el document
     */
    private void guardaDocumentTXT(String titol, String autor, String contingut, String path) throws
            FitxerNoCreatException {
        creaFitxer(path);

        Path PATH = Paths.get(path);

        try (BufferedWriter escriptor = Files.newBufferedWriter(PATH,StandardCharsets.UTF_8)) {
            escriptor.write(autor + "\n" + titol + "\n" + contingut);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Escriu en el fitxer doc els paràmetres de la funció (titol,autor,contingut) en format XML
     *
     * @param titol Indica el títol que identifica el document
     * @param autor Indica l'autor que identifica el document
     * @param contingut Indica el contingut del document
     * @param path Indica el path del fitxer que volem escriure
     * @throws FitxerNoCreatException Si no s'ha pogut crear el fitxer on es guarda el document
     */
    private void guardaDocumentXML(String titol, String autor, String contingut, String path) throws
            FitxerNoCreatException {
        creaFitxer(path);

        Path PATH = Paths.get(path);

        try (BufferedWriter escriptor = Files.newBufferedWriter(PATH,StandardCharsets.UTF_8)) {
            escriptor.write("<document>\n");
            escriptor.write("\t<autor>" + autor + "</autor>\n");
            escriptor.write("\t<titol>" + titol + "</titol>\n");
            escriptor.write("\t<contingut>");

            String[] lines_contingut = contingut.split("\n");

            for(String linia : lines_contingut) {
                escriptor.write("\n\t\t" + linia);
            }

            escriptor.write("\n\t</contingut>");
            escriptor.write("\n</document>");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Escriu en el fitxer doc els paràmetres de la funció (titol,autor,contingut) en format BOL
     *
     * @param titol Indica el títol que identifica el document
     * @param autor Indica l'autor que identifica el document
     * @param contingut Indica el contingut del document
     * @param path Indica el path del fitxer que volem escriure
     * @throws FitxerNoCreatException Si no s'ha pogut crear el fitxer on es guarda el document
     */
    private void guardaDocumentBOL(String titol, String autor, String contingut, String path) throws
            FitxerNoCreatException {
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

    /**
     * Escriu l' expressió booleana expr en el document situat a path
     *
     * @param expr Indica l'expressió booleana que volem guardar
     * @param PATH Indica en quina posició està emmagatzemat el document
     * @param primera Indica si és la primera expressió booleana del docu
     */
    public void escriuExpressio(String expr, Path PATH, Boolean primera) {
        try (BufferedWriter escriptor = Files.newBufferedWriter(PATH, StandardCharsets.UTF_8)) {
            if(primera) {
                escriptor.write(expr);
            }
            else escriptor.write("\n" + expr);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Guarda totes les expressions de l'array en el document situat en el path
     *
     * @param expressions Array d'expressions booleanes
     * @param path Indica en quina posició està emmagatzemat el document
     */
    public void guardaExpressions(ArrayList<String> expressions, String path) {
        Path PATH = Paths.get(path);
        boolean primera = true;

        for(String expressio : expressions) {
            escriuExpressio(expressio,PATH,primera);
            if(primera) primera = false;
        }
    }

    /**
     * Guarda una sola expressió booleana en el document situat en el path
     *
     * @param expr Indica una expressió booleana que es guardarà
     * @param path Indica en quina posició està emmagatzemat el document
     * @throws ExpressioBooleanaJaExistentException Si l'expressió booleana expr ja està guardada en el document
     * @throws FitxerNoCreatException Si s'ha intentat crear el fitxer i no s'ha pogut
     */
    public void guardaExpressio(String expr, String path) throws ExpressioBooleanaJaExistentException,
            FitxerNoCreatException {
        Path PATH = Paths.get(path);
        boolean buit = false;

        if(!existeixFitxer(path)) { // El document no existeix i, per tant, el creem
            creaFitxer(path);
            //Com el document és buit no cal comprovar repeticions
            buit = true;
        }
        else {  // El document existeix i, per tant, cal comprobar si ja existeix l'expressió en el document
            ArrayList<String> expressions = llegeixExpressions(path);
            for(String expressio : expressions) {
                if(expressio.equals(expr)) {    // Ja existeix l'expressió
                    throw new ExpressioBooleanaJaExistentException(expr);
                }
            }
            // L'expressió no existeix i, per tant, es pot afegir
            if(expressions.size() == 0) buit = true;
        }
        escriuExpressio(expr,PATH,buit);
    }

    /**
     * Modifica l'expressió booleana exprAnt per exprNova del document situat en path
     *
     * @param exprAnt Indica l'expressió booleana que volem treure
     * @param exprNova Indica l'expressió booleana que volem posar
     * @param path Indica en quina posició està el document que volem modificar
     * @param elimina Ens indica si volem eliminar l'expressió exprAnt del fitxer path
     * @throws FitxerNoEliminatException Si s'ha intentat eliminar el fitxer on estan les expressions i no s'ha pogut
     * @throws FitxerNoCreatException Si s'ha intentat crear el fitxer on estan les expressions i no s'ha pogut
     * @throws ExpressioBooleanaInexistentException Si no existeix l'expressió exprAnt en el document que volem modificar
     */
    public void modificaExpressio(String exprAnt, String exprNova, String path, Boolean elimina)
            throws FitxerNoEliminatException, FitxerNoCreatException, ExpressioBooleanaInexistentException {
        // Llegim les expressions per eliminar exprAnt i posar exprNova
        ArrayList<String> expressions = llegeixExpressions(path);

        if(!expressions.remove(exprAnt)) {
            throw new ExpressioBooleanaInexistentException(exprAnt);
        }
        if(!elimina) expressions.add(exprNova);  // Afegim l'expressió al llistat d'expressions si no es vol eliminar

        eliminaFitxer(path);
        creaFitxer(path);
        guardaExpressions(expressions, path);
    }


    ///////////////////////////////////////////////////////////
    ///                 FUNCIONS PÚBLIQUES                  ///
    ///////////////////////////////////////////////////////////

    /*
     * Llegeix els documents guardats en la carpeta path, i si no existeix, crea la carpeta
     *
     * @param path Indica el path relatiu de la carpeta on estan situats els documents
     * @return Retorna un array amb els documents guardats si existeix algun en la carpeta path, altrament retorna null
     * @throws CarpetaNoCreadaException Si no s'ha pogut crear la carpeta en el path indicat
     * @throws TipusExtensioIncorrectaException Si hi ha algun document amb una extensió no coneguda
     *
    public ArrayList<DocumentLlegit> carregaDocuments(String path) throws CarpetaNoCreadaException,
            CarpetaBuidaException, TipusExtensioIncorrectaException {
        ArrayList<DocumentLlegit> documents;
        boolean existeix = existeixDirectori(path);

        if(existeix) documents = llegeixDocuments(path);
        else throw new CarpetaBuidaException();

        return documents;
    }
    */


    /**
     * Llegeix el contingut del fitxer expressions.csv per a obtenir les expressions booleanes guardades
     * en aquest fitxer, i si no existeix, crea els fitxers
     *
     * @param path Indica el path relatiu de la carpeta on està situat el fitxer amb les expressions booleanes
     * @return Retorna un array amb les expressions guardades si existeix el fitxer
     * @throws CarpetaNoCreadaException Si no s'ha pogut crear la carpeta en el path indicat
     * @throws FitxerNoCreatException Si no s'ha pogut crear el fitxer en la carpeta del path indicat
     */
    public ArrayList<String> carregaExpressionsBooleanes(String path) throws CarpetaNoCreadaException,
            FitxerNoCreatException, CarpetaBuidaException {
        ArrayList<String> expressions;
        boolean existeix = existeixDirectori(path);

        if(existeix) expressions = llegeixExpressions(path+"/expressions.txt");
        else throw new CarpetaBuidaException();

        return expressions;
    }

    /**
     * Llegeix les StopWords que estan guardades en el path indicat
     *
     * @param path Indica en quin lloc estàn guardades les stopWords
     * @return Retorna un array amb paraules stopWords
     * @throws CarpetaNoCreadaException Si s'ha intentat crear la carpeta i no s'ha pogut
     * @throws FitxerNoCreatException Si S'ha intentat crear el fitxer i no s'ha pogut
     */
    public ArrayList<String> carregaStopWords(String path) throws CarpetaNoCreadaException, FitxerNoCreatException {
        ArrayList<String> stopWords;
        boolean existeix = existeixDirectori(path);

        if(existeix) stopWords = llegeixStopWords(path+"/stopWords.csv");
        else throw new CarpetaBuidaException();

        return stopWords;
    }

    private void guardaDocumentLlegit(DocumentLlegit D) throws FitxerNoCreatException, TipusExtensioIncorrectaException {
        String autor = D.getAutor();
        String titol = D.getTitol();
        TipusExtensio ext = D.getExtensio();
        String contingut = D.getContingut();
        String path = D.getPath();

        switch(ext.toString()) {
            case("TXT"):
                guardaDocumentTXT(titol,autor,contingut,path);
                break;
            case("XML"):
                guardaDocumentXML(titol,autor,contingut,path);
                break;
            case("BOL"):
                guardaDocumentBOL(titol,autor,contingut,path);
                break;
            default:
                throw new TipusExtensioIncorrectaException(ext.toString());
        }
    }

    /*
     * Guarda un document amb extensió .txt, .xml o .bol en el directori indicat
     *
     * @param titol Indica el títol del document que volem guardar
     * @param autor Indica l'autor del document que volem guardar
     * @param ext Indica l'extensió del document que volem guardar
     * @param contingut Indica el contingut del document que volem guardar
     * @param path Indica el path del directori on guardarem el document
     * @return Retorna el nou path del document
     * @throws FitxerNoEliminatExeption Si s'ha intentat eliminar el fitxer i no s'ha pogut
     * @throws CarpetaNoCreadaException Si s'ha intentat crear la carpeta i no s'ha pogut
     * @throws TipusExtensioIncorrectaException Si l'extensió indicada no és .txt, .xml ni .bol
     */
    public void guardaDocument(DocumentLlegit D)
            throws FitxerNoEliminatException, TipusExtensioIncorrectaException, FitxerNoCreatException {
        String path = D.getPath();

        if(existeixFitxer(path)) { // El document existeix i, per tant, cal eliminar-lo per actualitzar-ho
            eliminaFitxer(path);
        }
        // Guardem el document
        guardaDocumentLlegit(D);
    }

    public void exportarDocument(DocumentLlegit D) throws TipusExtensioIncorrectaException, FitxerNoCreatException {
        String path = D.getPath();
        int longitud_path = path.length();
        String path_doc = path.substring(0,longitud_path-4);
        String ext = D.getExtensio().toString().toLowerCase();
        int num_copia = 1;

        while(existeixFitxer(path)) {
            // El document existeix i, per tant, cal modificar el seu path per a no eliminar el document existent
            path = path_doc + "_" + num_copia + "." + ext;
            ++num_copia;
        }
        D.setPath(path);
        // Guardem el document
        guardaDocumentLlegit(D);
    }

    /**
     * Guarda una expressió booleana en el fitxer indicat
     *
     * @param exprAnt Indica la expressió que volem modificar, o null si és una expressió nova
     * @param exprNova Indica la expressió que volem guardar en el sistema
     * @param path Indica en quin lloc està el fitxer on guardem les expressions
     * @throws ExpressioBooleanaJaExistentException Si ja hi ha l'expressió booleana exprNova en el fitxer
     * @throws ExpressioBooleanaInexistentException Si no està l'expressió booleana exprAnt abans de modificar el fitxer
     */
    public void guardaExpressioBool(String exprAnt, String exprNova, String path)
            throws ExpressioBooleanaJaExistentException, ExpressioBooleanaInexistentException, FitxerNoCreatException,
            FitxerNoEliminatException {
        if(exprAnt.equals("")) {   // L'expressió és nova
            guardaExpressio(exprNova,path);
        }
        else {  // La expressió és modificada
            modificaExpressio(exprAnt,exprNova,path,false);
        }
    }

    public void eliminaExpressio(String expr, String path) throws ExpressioBooleanaInexistentException,
            FitxerNoEliminatException, FitxerNoCreatException {
        modificaExpressio(expr,"",path,true);
    }

    /**
     * Busca el document identificat per títol i autor
     *
     * @param titol Indica el títol del document
     * @param autor Indica l'autor del document
     * @return Retorna el document que té com a títol i autor els indicats, si no existeix retorna null
     */
    public File buscaDocument(String titol, String autor, String path) {
        try {
            String id = titol + "_" + autor;
            File[] candidats = new File(path).listFiles();

            if (candidats != null) {
                for (File doc : candidats) {
                    // Obtenim el nom del document
                    String document = doc.getName();

                    // Filtrem els possibles documents que puguin tenir com a títol i autor els indicats
                    // (format dels documents guardats: #doc_titol_autor.extensió).
                    // Treiem l'extensió i mirem que acabi en titol_autor
                    if(document.substring(0,document.length()-3).endsWith(id)) {
                        DocumentLlegit D = llegeixDocument(doc.getPath());

                        // Mirem que el seu títol i autor siguin els que busquem
                        if (D.getTitol().equals(titol) && D.getAutor().equals(autor)) return doc;
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public DocumentLlegit llegeixDocument(String path) throws TipusExtensioIncorrectaException {
        // Mirem que el document que volem llegir existeixi
        if(existeixFitxer(path)) {
            Path PATH = Paths.get(path);

            // Mirem el tipus d'extensió del document
            int l = path.length();
            // Mirem que no siguin backups creats per emacs, nano o vi
            if(!path.endsWith("~")) {
                String ext = path.substring(l-4,l);

                switch (ext) {
                    case ".txt":
                        return llegeixDocumentTXT(PATH);
                    case ".xml":
                        return llegeixDocumentXML(PATH);
                    case ".bol":
                        return llegeixDocumentBOL(PATH);
                    default:
                        throw new TipusExtensioIncorrectaException(ext);
                }
            }
        }
        return null;
    }
}