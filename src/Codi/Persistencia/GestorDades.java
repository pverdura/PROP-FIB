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
    ///                 FUNCIONS DOCUMENT                   ///
    ///////////////////////////////////////////////////////////

    /**
     * Crea un fitxer en el path indicat
     *
     * @param path Indica el lloc on es crea el fitxer (nom inclòs)
     */
    public void creaFitxer(String path) {
        File F = new File(path);
        try {
            boolean creada = F.createNewFile();

            if(creada && F.isFile()) {  // Mirem si s'ha creat el fitxer
                System.out.println("S'ha creat el fitxer "+ path +" correctament");
            }
            else {  // Hi ha hagut un problema en crear el fitxer
                throw new FitxerNoCreatException(path);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Crea el directori amb el path indicat
     *
     * @param path Indica el path del directori que es vol crear
     * @throws CarpetaNoCreadaException Si no s'ha pogut crear la carpeta en el path indicat
     */
    public void creaDirectori(String path) throws CarpetaNoCreadaException {
        // No hi ha cap error en crear el directori
        File dir = new File(path);
        if(dir.mkdirs()) {
            System.out.println("S'ha creat el fitxer correctament");
        } else {  // Hi ha un error al crear el directori
            throw new CarpetaNoCreadaException(path);
        }
    }

    /**
     * Elimina el fitxer amb el path indicat (nom inclòs)
     *
     * @param path Indica on està situat el fitxer que volem eliminar
     * @throws FitxerNoEliminatException Si no s'ha pogut eliminar el fitxer
     */
    private void eliminaFitxer(String path) throws FitxerNoEliminatException {
        File doc = new File(path);

        // No hi ha cap problema en eliminar el fitxer
        if (doc.delete()) {
            System.out.println("S'ha eliminat el fitxer " + path + " correctament");
        } else {  // Hi ha hagut algun problema en eliminar el fitxer
            throw new FitxerNoEliminatException(path);
        }
    }

    /**
     * Elimina tots els fitxers amb extensions diferents que tinguin com a nom el fitxer del path indicat
     *
     * @param path Indica el nom del fitxer que volem eliminar (sense tenir en compte l'extensió del path)
     * @throws FitxerNoEliminatException Si s'ha intentat eliminar el fitxer i no s'ha pogut
     */
    public void esborraFitxer(String path) throws FitxerNoEliminatException {
        String path_doc = path.substring(0, path.length()-3);

        if(existeixFitxer(path_doc+"txt")) {
            eliminaFitxer(path_doc+"txt");
        }
        else if(existeixFitxer(path_doc+"xml")) {
            eliminaFitxer(path_doc+"xml");
        }
        else if(existeixFitxer(path_doc+"bol")) {
            eliminaFitxer(path_doc+"bol");
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
     * Indica si existeix el directori amb el path indicat
     *
     * @param path Indica el directori que volem saber si existeix
     * @return Retorna true si existeix el directori en el path indicat, false altrament
     */
    public Boolean existeixDirectori(String path) {
        File carpeta = new File(path);

        // Primer mirem si existeix el directori on guardem els documents, expressions i stopWords
        return (carpeta.exists() && carpeta.isDirectory());
    }

    /**
     * Llegeix un document en format .txt
     *
     * @param PATH Indica el path del fitxer .txt que es llegirà
     * @return Retorna un DocumentLlegit que conté l'autor, títol, extensió i contingut del fitxer llegit
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
                    if (num_linia == 3) contingut = linia;
                    else contingut = contingut.concat("\n" + linia);
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
     * Escriu en el fitxer amb extensió .txt situat a path, els paràmetres de la funció: titol, autor i contingut
     *
     * @param titol Indica el títol que identifica el document
     * @param autor Indica l'autor que identifica el document
     * @param contingut Indica el contingut del document
     * @param path Indica el path del fitxer .txt que volem escriure
     */
    private void guardaDocumentTXT(String titol, String autor, String contingut, String path) {
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
     * Llegeix un document en format .xml
     *
     * @param PATH Indica el path del fitxer .xml que es llegirà
     * @return Retorna un DocumentLlegit que conté l'autor, títol, format i contingut del fitxer llegit
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
                        D.setAutor(linia.substring(8,linia.length()-8).trim());
                    }
                    else if(linia.contains("<titol>") && linia.contains("</titol>")) {
                        // Agafem el text que està entre <titol> i </titol>
                        D.setTitol(linia.substring(8,linia.length()-8).trim());
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
     * Escriu en el fitxer amb extensió .xml situat a path, els paràmetres de la funció: titol, autor i contingut
     *
     * @param titol Indica el títol que identifica el document
     * @param autor Indica l'autor que identifica el document
     * @param contingut Indica el contingut del document
     * @param path Indica el path del fitxer .xml que volem escriure
     */
    private void guardaDocumentXML(String titol, String autor, String contingut, String path) {
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
     * Llegeix un document en format .bol
     *
     * @param PATH Indica el path del fitxer .bol que es llegirà
     * @return Retorna un DocumentLlegit que conté l'autor, títol, format i contingut del fitxer llegit
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
     * Escriu en el fitxer amb extensió .bol situat a path, els paràmetres de la funció: titol, autor i contingut
     *
     * @param titol Indica el títol que identifica el document
     * @param autor Indica l'autor que identifica el document
     * @param contingut Indica el contingut del document
     * @param path Indica el path del fitxer .bol que volem escriure
     */
    private void guardaDocumentBOL(String titol, String autor, String contingut, String path) {
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

    /**
     * Llegeix un document en qualsevol extensió (.txt, .xml, .bol)
     *
     * @param path Indica el path del document que volem llegir
     * @return Retorna un DocumentLlegit amb les dades del document
     */
    public DocumentLlegit llegeixDocument(String path) {
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
                }
            }
        }
        return null;
    }

    /**
     * Guarda un document en qualsevol format en el path indicat el el document
     *
     * @param D Indica el document que es vol guardar
     */
    private void guardaDocumentLlegit(DocumentLlegit D) {
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
        }
    }

    /**
     * Guarda un document en qualsevol format en el sistema
     *
     * @param D Indica el document que es vol guardar
     * @throws FitxerNoEliminatException Si s'ha intentat eliminar el document i no s'ha pogut
     * @throws FitxerNoCreatException Si s'ha intentat crear el document i no s'ha pogut
     */
    public void guardaDocument(DocumentLlegit D)
            throws FitxerNoEliminatException, FitxerNoCreatException {
        String path = D.getPath();
        esborraFitxer(path);    // Si el document existeix, cal eliminar-lo per actualitzar-ho

        // Guardem el document
        guardaDocumentLlegit(D);
    }

    /**
     * Exporta un document fora del sistema
     *
     * @param D Indica el document que es vol exportar
     * @throws FitxerNoCreatException Si s'ha intentat crear el document i no s'ha pogut
     */
    public void exportarDocument(DocumentLlegit D) throws FitxerNoCreatException {
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


    ///////////////////////////////////////////////////////////
    ///                 FUNCIONS EXPR BOOL                  ///
    ///////////////////////////////////////////////////////////

    /**
     * Llegeix el contingut del fitxer en el path indicat per a obtenir les expressions booleanes guardades
     * en aquest fitxer
     *
     * @param path Indica el path del fitxer on es guarden les expressions
     * @return Retorna un array de les expressions booleanes guardades
     */
    public ArrayList<String> llegeixExpressions(String path) {
        ArrayList<String> expressions = new ArrayList<String>();

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
        return expressions;
    }

    /**
     * Guarda totes les expressions de l'array en el fitxer situat en el path
     *
     * @param expressions Indica l'array amb les expressions booleanes
     * @param path Indica en quin fitxer es guarden les expressions booleanes
     */
    public void guardaExpressionsBooleanes(ArrayList<String> expressions, String path) throws FitxerNoEliminatException {
        // Si existeix el document l'eliminem per sobre escriure les expressions
        if(existeixFitxer(path)) {
            eliminaFitxer(path);
        }
        // El tornem a crear
        creaFitxer(path);
        Path PATH = Paths.get(path);
        try (BufferedWriter escriptor = Files.newBufferedWriter(PATH, StandardCharsets.UTF_8)) {
            for(String expressio : expressions) {
                escriptor.write(expressio+'\n');
                System.out.println(expressio);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    ///////////////////////////////////////////////////////////
    ///                FUNCIONS STOP WORDS                  ///
    ///////////////////////////////////////////////////////////

    /**
     * Llegeix les paraules que es consideren StopWords
     *
     * @param path Indica el document on estan guardades les StopWords
     * @return Retorna un array de paraules, on cada paraula és una StopWord
     */
    public ArrayList<String> llegeixStopWords(String path) {
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
}