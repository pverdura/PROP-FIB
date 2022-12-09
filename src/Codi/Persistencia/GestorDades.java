package Codi.Persistencia;

import Codi.Domini.*;
import Codi.Excepcions.CarpetaNoCreadaException;
import Codi.Excepcions.FitxerNoCreatException;
import Codi.Excepcions.TipusExtensioIncorrectaException;
import Codi.Util.TipusExtensio;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class GestorDades {

    /**
     * Busca la línia on el primer atribut és títol, i el segon autor del fitxer doc
     *
     * @param titol Indica el títol del document que volem
     * @param autor Indica l'autor del document que volem
     * @param doc Indica el document que se li aplicarà la cerca
     * @return Retorna el número de línia on està primer el string títol i llavors el string autor,
     *         si no hi ha cap línia amb les propietats anteriors, retorna -1
     */
    private int buscaLinia(String titol, String autor, File doc) {
        try {
            Scanner lector = new Scanner(doc);
            int numLinia = 0;

            // Llegim el fitxer de documents
            while(lector.hasNextLine()) {
                String linia = lector.nextLine();
                String[] dades = linia.split(",",3);

                // Mirem si el document que està en la línia numlinia el seu títol i autor és el que busquem
                if(dades[0].equals(titol) && dades[1].equals(autor)) {
                    return numLinia;
                }
                ++numLinia;
            }
            lector.close();
        }
        catch (IOException ex) {
            System.out.println("[Insert text here]");
        }
        return -1;
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
    private void creaFile(String path) throws FitxerNoCreatException {
        File F = new File(path);

        try {
            boolean creada = F.createNewFile();

            // Mirem si s'ha creat el fitxer
            if(!(creada && F.isFile())) {
                System.out.println("S'ha creat el fitxer "+path +" correctament");
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
     * Crea el directori on s'emmagatzemen les dades del sistema
     *
     * @param path Indica el path de la carpeta on aniran els fitxers .csv
     * @param carpeta Objecte de la carpeta on aniran les dades
     * @throws CarpetaNoCreadaException Si no s'ha pogut crear la carpeta en el path indicat
     * @throws FitxerNoCreatException Si no s'ha pogut crear el fitxer en la carpeta del path indicat
     */
    private void creaDirectori(String path, File carpeta) throws CarpetaNoCreadaException, FitxerNoCreatException {
        // La carpeta s'ha creat correctament
        if(carpeta.mkdir()) {
            // Creem els fitxers on guardarem les dades
            creaFile(path+"/documents.csv");
            creaFile(path+"/expressions.csv");
        }
        // Error al crear la carpeta
        else {
            throw new CarpetaNoCreadaException(path);
        }
    }

    /**
     * Llegeix el contingut del fitxer documents.csv per a obtenir els documents guardats
     * en aquest fitxer
     *
     * @param path Indica el path del fitxer documents.csv
     * @return Retorna un array amb els documents guardats
     * @throws TipusExtensioIncorrectaException Si hi ha algun fitxer amb una extensió no coneguda
     */
    private ArrayList<Document> llegeixDocuments(String path) throws TipusExtensioIncorrectaException {
        File doc = new File(path);
        ArrayList<Document> documents = new ArrayList<Document>();

        // Mirem que el fitxer on guardem els documents existeixi
        if(doc.exists() && doc.isFile()) {
            try {
                Scanner lector = new Scanner(doc);

                // Llegim el fitxer mentre hi hagi línies
                while(lector.hasNextLine()) {
                    // Llegim la línia del fitxer
                    String linia = lector.nextLine();
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
                lector.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Si no existeix, el creem
        else {
            creaFile(path);
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
            try {
                Scanner lector = new Scanner(exp);

                // Llegim el fitxer mentre hi hagi línies
                while(lector.hasNextLine()) {
                    // Llegim la línia del fitxer
                    String linia = lector.nextLine();

                    ExpressioBooleana EB = new ExpressioBooleana(linia);

                    // Afegim l'expressió en el llistat
                    expressions.add(EB);
                }
                lector.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Si no existeix, el creem
        else {
            creaFile(path);
        }
        return expressions;
    }

    /**
     * Llegeix el contingut del fitxer documents.csv per a obtenir els documents guardats
     * en aquest fitxer, i si no existeix, crea els fitxers
     *
     * @param path Indica el path de la carpeta on està situat el fitxer
     * @return Retorna un array amb els documents guardats si existeix el fitxer
     * @throws CarpetaNoCreadaException Si no s'ha pogut crear la carpeta en el path indicat
     * @throws FitxerNoCreatException Si no s'ha pogut crear el fitxer en la carpeta del path indicat
     * @throws TipusExtensioIncorrectaException Si hi ha algun fitxer amb una extensió no coneguda
     */
    public ArrayList<Document> carregaDocuments(String path) throws CarpetaNoCreadaException,
            FitxerNoCreatException, TipusExtensioIncorrectaException {
        File carpeta = new File(path);
        ArrayList<Document> documents = null;

        // Primer mirem si existeix el directori on guardem els documents i expressions
        if(carpeta.exists() && carpeta.isDirectory()) documents = llegeixDocuments(path+"/documents.csv");
        // Si no existeix el directori el creem
        else creaDirectori(path,carpeta);

        return documents;
    }

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
        try {
            Path pth = Paths.get(path); // Passem el string path en format Path
            Files.writeString(pth,titol,StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void guardaExpressioBool(String expr, String path) {

    }
}
