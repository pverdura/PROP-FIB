package Codi.Persistencia;

import Codi.Excepcions.CarpetaBuidaException;
import Codi.Excepcions.CarpetaNoCreadaException;
import Codi.Excepcions.FitxerNoCreatException;
import Codi.Excepcions.TipusExtensioIncorrectaException;
import Codi.Util.DocumentLlegit;
import Codi.Util.TipusExtensio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class GestorDades {

    ///////////////////////////////////////////////////////////
    ///                 FUNCIONS PRIVADES                   ///
    ///////////////////////////////////////////////////////////

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


            /* Afegim el document que no existia previament
            if (l < 0) {
                escriptor.newLine();
                escriptor.append(new_linia);
            }
            // Modifiquem el fitxer existent
            else {

            }*/
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    // ^-^
    private DocumentLlegit llegeixDocument(String path) throws TipusExtensioIncorrectaException {
        File doc = new File(path);
        DocumentLlegit D = null;

        // Mirem que el document que volem llegir existeixi
        if(doc.exists() && doc.isFile()) {
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

    // ^-^
    private ArrayList<DocumentLlegit> llegeixDocuments(String path, File carpeta) throws TipusExtensioIncorrectaException {
        ArrayList<DocumentLlegit> documents = new ArrayList<DocumentLlegit>();
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
        File exp = new File(path);
        ArrayList<String> expressions = new ArrayList<String>();

        // Mirem que el fitxer on guardem les expressions existeixi
        if(exp.exists() && exp.isFile()) {
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
    public ArrayList<DocumentLlegit> carregaDocuments(String path) throws CarpetaNoCreadaException,
            FitxerNoCreatException, TipusExtensioIncorrectaException {
        File carpeta = new File(path);
        ArrayList<DocumentLlegit> documents = null;

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
    public ArrayList<String> carregaExpressionsBooleanes(String path) throws CarpetaNoCreadaException,
            FitxerNoCreatException {
        File carpeta = new File(path);
        ArrayList<String> expressions = null;

        // Primer mirem si existeix el directori on guardem els documents i expressions
        if(carpeta.exists() && carpeta.isDirectory()) expressions = llegeixExpressions(path+"/expressions.csv");
        // Si no existeix el directori el creem
        else creaDirectori(path,carpeta);

        return expressions;
    }

    public void guardaDocument(String titol, String autor, String contingut, String path) {
        File doc = new File(path);

        // Primer mirem si existeix el fitxer on guardem els documents
        if(doc.exists() && doc.isFile()) guardaDoc(titol, autor, path, contingut, path);
        // Si no existeix el fitxer el creem
        else creaFitxer(path);
    }

    public void guardaExpressioBool(String expr, String path) {

    }
}
