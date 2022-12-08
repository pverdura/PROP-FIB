package Codi.Persistencia;

import Codi.Domini.*;
import Codi.Excepcions.CarpetaNoCreadaException;
import Codi.Excepcions.FitxerNoCreatException;
import Codi.Excepcions.NoLlegeixFitxerException;
import Codi.Excepcions.TipusExtensioIncorrectaException;
import Codi.Util.TipusExtensio;

import java.awt.image.MultiPixelPackedSampleModel;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class GestorDades {

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
     * Crea el directori on s'emmagatzemen les dades del sistema
     *
     * @param path Indica el path de la carpeta on aniran els fitxers .csv
     * @param carpeta Objecte de la carpeta on aniran les dades
     */
    private void creaDirectori(String path, File carpeta) throws FitxerNoCreatException {
        // La carpeta s'ha creat correctament
        if(carpeta.mkdir()) {
            // Creem els fitxers on guardarem el contingut
            File doc = new File(path+"/documents.csv");

            try {
                boolean creada = doc.createNewFile();
                // Mirem si s'ha creat el fitxer
                if(!(creada && doc.isFile())) {
                    throw new FitxerNoCreatException(path+"/documents.csv");
                }
            }
            catch (IOException ex) {
                System.out.println("[Insert text here]");
            }

            File exp = new File(path+"/expressions.csv");

            try {
                boolean creada = exp.createNewFile();
                // Mirem si s'ha creat el fitxer
                if(!(creada && doc.isFile())) {
                    throw new FitxerNoCreatException((path+"/expressions.csv"));
                }
            }
            catch (IOException ex) {
                System.out.println("[Insert text here]");
            }
        }
        // Error al crear la carpeta
        else {
            throw new CarpetaNoCreadaException(path);
        }
    }

    private ArrayList<Document> llegeixDocuments(String path) throws NoLlegeixFitxerException,
            FitxerNoCreatException, TipusExtensioIncorrectaException {
        File doc = new File(path+"documents.csv");
        ArrayList<Document> documents = new ArrayList<Document>();

        // Mirem que el fitxer on guardem els documents es pugui llegir
        if(doc.canRead()) {
            try {
                Scanner lector = new Scanner(doc);

                // Llegim el fitxer mentre hi hagi línies
                while(lector.hasNextLine()) {
                    // Llegim la línia del fitxer
                    String dades = lector.nextLine();
                    Document D = new Document();

                    // Partim la línia en 4 parts (títol, autor, extensió, contingut)
                    String[] parts = dades.split(",",4);

                    // Convertim la línia llegida en un document
                    D.setTitol(parts[0]);
                    D.setAutor(parts[1]);
                    D.setExtensio(toTipus(parts[2]));
                    D.setContingut(parts[3]);

                    // Afegim el document en el llistat
                    documents.add(D);
                }
            }
            catch (IOException ex) {
                System.out.println("[Insert text here]");
            }
        }
        else {
            throw new NoLlegeixFitxerException(path);
        }
        return documents;
    }

    public ArrayList<Document> carregaDocuments(String path) throws NoLlegeixFitxerException,
            FitxerNoCreatException, TipusExtensioIncorrectaException {
        File carpeta = new File(path);
        ArrayList<Document> documents = null;

        // Primer mirem si existeix el directori on guardem els documents i expressions
        if (carpeta.exists() && carpeta.isDirectory()) documents = llegeixDocuments(path);
        // Si no existeix el directori el creem
        else creaDirectori(path,carpeta);

        return documents;
    }

    public void guardaDocument(String titol, String autor, String contingut, String path) {
        try {
            Path pth = Paths.get(path); // Passem el string path en format Path
            Files.writeString(pth,titol,StandardCharsets.UTF_8);
        }
        catch (IOException ex) {
            System.out.println("[Insert text here]");
        }
    }

    public void guardaExpressioBool(String expr, String path) {

    }

    public ExpressioBooleana carregaExpressioBooleana(String path) {
        return null;
    }
}
