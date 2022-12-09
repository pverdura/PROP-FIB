package Codi.Persistencia;

import Codi.Domini.*;
import Codi.Excepcions.CarpetaNoCreadaException;
import Codi.Excepcions.FitxerNoCreatException;
import Codi.Excepcions.TipusExtensioIncorrectaException;
import Codi.Util.TipusExtensio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

public class GestorDades {

    ///////////////////////////////////////////////////////////
    ///                 FUNCIONS PRIVADES                   ///
    ///////////////////////////////////////////////////////////

    /**
     * Busca la línia on el primer atribut és títol, i el segon autor del fitxer doc
     *
     * @param titol Indica el títol del document que volem
     * @param autor Indica l'autor del document que volem
     * @param PATH Indica el path del fitxer que volem consultar
     * @return Retorna el número de línia on està primer el string títol i llavors el string autor,
     *         si no hi ha cap línia amb les propietats anteriors, retorna -1
     */
    private int buscaLinia(String titol, String autor, Path PATH) {
        // Lector que ens llegirà el fitxer
        try (BufferedReader lector = Files.newBufferedReader(PATH,StandardCharsets.UTF_8)) {
            String linia = null;
            int numLinia = 0;

            // Llegim el fitxer mentre hi hagi línies
            while((linia = lector.readLine()) != null) {
                String[] dades = linia.split(",",3);

                // Mirem si el document que està en la línia numlinia el seu títol i autor és el que busquem
                if(dades[0].equals(titol) && dades[1].equals(autor)) {
                    return numLinia;
                }
                ++numLinia;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Escriu en el fitxer doc els paràmetres de la funció (titol,autor,ext,contingut) en format CSV
     *
     * @param titol Indica el títol que identifica el document
     * @param autor Indica l'autor que identifica el document
     * @param ext Indica l'extensió del document
     * @param contingut Indica el contingut del document
     * @param path Indica el path del fitxer que volem escriure
     */
    private void guardaDoc(String titol, String autor, String ext, String contingut, String path) {
        String new_linia = titol+","+autor+","+ext+","+contingut;
        Path PATH = Paths.get(path);
        Path TMP = Paths.get("tmp.txt");

        try (BufferedWriter escriptor = Files.newBufferedWriter(PATH,StandardCharsets.UTF_8)) {
            // Mirem si el document ja existeix en el fitxer


            // Afegim el document que no existia previament
            if (l < 0) {
                escriptor.newLine();
                escriptor.append(new_linia);
            }
            // Modifiquem el fitxer existent
            else {

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ^-^
    /**
     * Converteix el String tipus en TipusExtensió
     *
     * @param tipus Indica el tipus d'extensió en String
     * @return Retorna el String tipus en format TipusExtensió
     * @throws TipusExtensioIncorrectaException Si tipus no és una extensió coneguda
     */
    private TipusExtensio toTipus(String tipus) throws TipusExtensioIncorrectaException {
        TipusExtensio ext = null;

        switch(tipus) {
            case "TXT":
                ext = TipusExtensio.TXT;
                break;
            case "XML":
                ext = TipusExtensio.XML;
                break;
            case "BOL":
                ext = TipusExtensio.BOL;
                break;
            default:
                throw new TipusExtensioIncorrectaException(tipus);
        }
        return ext;
    }

    // ^-^
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
            if(!(creada && F.isFile())) {
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

    // ^-^
    /**
     * Crea el directori on s'emmagatzemen les deades del sistema
     *
     * @param path Indica el path de la carpeta on aniran els fitxers .csv
     * @param carpeta Objecte de la carpeta on aniran les dades
     * @throws CarpetaNoCreadaException Si no s'ha pogut crear la carpeta en el path indicat
     */
    private void creaDirectori(String path, File carpeta) throws CarpetaNoCreadaException {
        // Error en crear la carpeta
        if(!carpeta.mkdir()) {
            throw new CarpetaNoCreadaException(path);
        }
    }

    /**
     * Llegeix el contingut del fitxer documents.csv per a obtenir els documents guardats
     * en aquest fitxer
     *
     * @param path Indica el path del fitxer documents.csv
     * @return Retorna un array amb els documents guardats
     * @throws TipusExtensioIncorrectaException Si hi ha algun document amb una extensió no coneguda
     */
    private ArrayList<Document> llegeixDocuments(String path) throws TipusExtensioIncorrectaException {
        File doc = new File(path);
        ArrayList<Document> documents = new ArrayList<Document>();

        // Mirem que el fitxer on guardem els documents existeixi
        if(doc.exists() && doc.isFile()) {
            Path PATH = Paths.get(path);

            // Lector que ens llegirà el fitxer
            try (BufferedReader lector = Files.newBufferedReader(PATH,StandardCharsets.UTF_8)) {
                String linia = null;

                // Llegim el fitxer mentre hi hagi línies
                while((linia = lector.readLine()) != null) {
                    Document D = new Document();

                    // Partim la línia en 4 parts (títol, autor, extensió, contingut)
                    String[] dades = linia.split(",",4);

                    // Convertim la línia llegida en un document
                    D.setTitol(dades[0]);
                    D.setAutor(dades[1]);
                    D.setExtensio(toTipus(dades[2]));
                    D.setContingut(dades[3]);

                    // Afegim el document en el llistat
                    documents.add(D);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Si no existeix, el creem
        else {
            creaFitxer(path);
        }
        return documents;
    }

    // ^-^
    private Document llegeixDocumentTXT(Path PATH) {
        Document D = new Document();

        // Lector que ens llegirà el document
        try (BufferedReader lector = Files.newBufferedReader(PATH,StandardCharsets.UTF_8)) {
            String contingut = null;
            String linia = null;
            int num_linia = 0;

            // Llegim el document mentre hi hagi línies
            while ((linia = lector.readLine()) != null) {
                if (num_linia == 0) {   // En aquesta línia hi ha l'autor
                    D.setAutor(linia);
                }
                else if (num_linia == 1) {   // En aquesta línia hi ha el títol
                    D.setTitol(linia);
                }
                else {  // En aquestes línies hi ha el contingut
                    if (contingut != null) contingut = contingut.concat(linia);
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

    private Document llegeixDocumentXML(Path PATH) {
        ArrayList<

        // Lector que ens llegirà el document
        try (BufferedReader lector = Files.newBufferedReader(PATH, StandardCharsets.UTF_8)) {
            String contingut = null;
            String linia = null;
            int num_linia = 0;

            // Llegim el document mentre hi hagi línies
            while ((linia = lector.readLine()) != null) {

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return D;
    }

    private Document llegeixDocumentBOL(Path PATH) {
        Document D = new Document();

        // Lector que ens llegirà el document
        try (BufferedReader lector = Files.newBufferedReader(PATH, StandardCharsets.UTF_8)) {
            String contingut = null;
            String linia = null;
            int num_linia = 0;

            // Llegim el document mentre hi hagi línies
            while ((linia = lector.readLine()) != null) {

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return D;
    }

    // ^-^
    private Document llegeixDocument(String path) throws TipusExtensioIncorrectaException {
        File doc = new File(path);
        Document D = null;

        // Mirem que el document que volem llegir existeixi
        if(doc.exists() && doc.isFile()) {
            Path PATH = Paths.get(path);

            // Mirem el tipus d'extensió del document
            int l = path.length()-1;
            String ext = path.substring(l-4,l);

            switch(ext) {
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
        return D;
    }

    // ^-^
    private ArrayList<Document> llegeixDocuments(String path, File carpeta) throws TipusExtensioIncorrectaException {
        ArrayList<Document> documents = new ArrayList<Document>();
        String[] docs = carpeta.list(); // Obtenim tots els documents de la carpeta situada en el path

        if(docs != null) {
            // Llegim tots els documents que estan en la carpeta situada en el path
            for (String doc : docs) {
                Document D = llegeixDocument(path+"/"+doc);
                if(D != null) documents.add(D);
            }
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
    private ArrayList<ExpressioBooleana> llegeixExpressions(String path) {
        File exp = new File(path);
        ArrayList<ExpressioBooleana> expressions = new ArrayList<ExpressioBooleana>();

        // Mirem que el fitxer on guardem les expressions existeixi
        if(exp.exists() && exp.isFile()) {
            Path PATH = Paths.get(path);

            // Lector que ens llegirà el fitxer
            try (BufferedReader lector = Files.newBufferedReader(PATH,StandardCharsets.UTF_8)) {
                String linia = null;

                // Llegim el fitxer mentre hi hagi línies
                while((linia = lector.readLine()) != null) {
                    // Llegim la línia del fitxer
                    ExpressioBooleana EB = new ExpressioBooleana(linia);

                    // Afegim l'expressió en el llistat
                    expressions.add(EB);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Si no existeix, el creem
        else {
            creaFitxer(path);
        }
        return expressions;
    }


    ///////////////////////////////////////////////////////////
    ///                 FUNCIONS PÚBLIQUES                  ///
    ///////////////////////////////////////////////////////////

    /**
     * Llegeix el contingut del fitxer documents.csv per a obtenir els documents guardats
     * en aquest fitxer, i si no existeix, crea els fitxers
     *
     * @param path Indica el path de la carpeta on està situat el fitxer
     * @return Retorna un array amb els documents guardats si existeix el fitxer, altrament retorna null
     * @throws CarpetaNoCreadaException Si no s'ha pogut crear la carpeta en el path indicat
     * @throws FitxerNoCreatException Si no s'ha pogut crear el fitxer en la carpeta del path indicat
     * @throws TipusExtensioIncorrectaException Si hi ha algun document amb una extensió no coneguda
     */
    public ArrayList<Document> carregaDocuments(String path) throws CarpetaNoCreadaException,
            FitxerNoCreatException, TipusExtensioIncorrectaException {
        File carpeta = new File(path);
        ArrayList<Document> documents = null;

        // Primer mirem si existeix el directori on guardem els documents i expressions
        if(carpeta.exists() && carpeta.isDirectory()) documents = llegeixDocuments(path,carpeta);
        // Si no existeix el directori, el creem
        else creaDirectori(path,carpeta);

        return documents;
    }

    /**
     * Llegeix el contingut del fitxer expressions.csv per a obtenir les expressions booleanes guardades
     * en aquest fitxer, i si no existeix, crea els fitxers
     *
     * @param path Indica el path de la carpeta on està situat el fitxer
     * @return Retorna un array amb les expressions guardades si existeix el fitxer
     * @throws CarpetaNoCreadaException Si no s'ha pogut crear la carpeta en el path indicat
     * @throws FitxerNoCreatException Si no s'ha pogut crear el fitxer en la carpeta del path indicat
     */
    public ArrayList<ExpressioBooleana> carregaExpressionsBooleanes(String path) throws CarpetaNoCreadaException,
            FitxerNoCreatException {
        File carpeta = new File(path);
        ArrayList<ExpressioBooleana> expressions = null;

        // Primer mirem si existeix el directori on guardem els documents i expressions
        if(carpeta.exists() && carpeta.isDirectory()) expressions = llegeixExpressions(path+"/expressions.csv");
        // Si no existeix el directori el creem
        else creaDirectori(path,carpeta);

        return expressions;
    }

    public void guardaDocument(String titol, String autor, String contingut, String path) {
        File doc = new File(path);

        // Primer mirem si existeix el fitxer on guardem els documents
        if(doc.exists() && doc.isFile()) guardaDoc(titol, autor, ext.toString(), contingut, path);
        // Si no existeix el fitxer el creem
        else creaFitxer(path);
    }

    public void guardaExpressioBool(String expr, String path) {

    }
}
