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

/**
 * Classe que llegeix i escriu documents, expressions booleanes i stop words en fitxers fisics
 *
 * @author pol
 * @since 15/12/2022
 */
public class GestorDades {

    ///////////////////////////////////////////////////////////
    ///                 FUNCIONS DOCUMENT                   ///
    ///////////////////////////////////////////////////////////

    /**
     * Crea un fitxer en el path indicat (nom inclos)
     *
     * @param path Indica el lloc on es crea el fitxer (nom inclos)
     * @throws FitxerNoCreatException Si no s'ha pogut crear el fiter indicat en el path
     */
    public void creaFitxer(String path) throws FitxerNoCreatException {
        File F = new File(path);
        boolean creada = false;

        try {   // Creem el nou fitxer
            creada = F.createNewFile();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        if(!(creada && F.isFile())) {  // Mirem si s'ha creat el fitxer
            throw new FitxerNoCreatException(path);
        }
    }

    /**
     * Crea el directori amb el path indicat
     *
     * @param path Indica el path del directori que es vol crear
     * @throws CarpetaNoCreadaException Si no s'ha pogut crear la carpeta en el path indicat
     */
    public void creaDirectori(String path) throws CarpetaNoCreadaException {
        File dir = new File(path);

        if(!dir.mkdirs()) { // Mirem si hi ha un error quan es crea el directori
            throw new CarpetaNoCreadaException(path);
        }
    }

    /**
     * Elimina el fitxer amb el path indicat (nom inclos)
     *
     * @param path Indica on esta situat el fitxer que volem eliminar
     * @throws FitxerNoEliminatException Si no s'ha pogut eliminar el fitxer indicat en el path
     */
    private void eliminaFitxer(String path) throws FitxerNoEliminatException {
        File doc = new File(path);

        if(!doc.delete()) { // S'elimina el fitxer
            throw new FitxerNoEliminatException(path);
        }
    }

    /**
     * Elimina tots els fitxers tinguin com a nom el fitxer del path indicat (sense tenir en compte l'extensio)
     *
     * @param path Indica el nom del fitxers que volem eliminar
     * @throws FitxerNoEliminatException Si s'ha intentat eliminar un fitxer del path indicat i no s'ha pogut eliminar
     */
    public void esborraFitxer(String path) throws FitxerNoEliminatException {
        // Traiem l'extensio del document del path
        String path_doc = path.substring(0, path.length()-3);

        // Afegim les extensions que sabem gestionar i eliminem els fitxers del paths modificats (si existeixen)
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
        
        // Mirem si el path que ens passen existeix i que hi ha un fitxer
        return doc.exists() && doc.isFile();
    }

    /**
     * Informa si existeix el directori amb el path indicat
     *
     * @param path Indica el directori que volem saber si existeix
     * @return Retorna true si existeix el directori en el path indicat, false altrament
     */
    public Boolean existeixDirectori(String path) {
        File carpeta = new File(path);

        // Mirem si el path que ens passen existeix i que hi hagi un directori
        return (carpeta.exists() && carpeta.isDirectory());
    }

    /**
     * Llegeix un document en format .txt
     *
     * @param Path Indica el path del fitxer .txt que es llegira
     * @return Retorna un DocumentLlegit que conte l'autor, titol, extensio i contingut del document llegit
     */
    private DocumentLlegit llegeixDocumentTXT(Path Path) {
        DocumentLlegit D = new DocumentLlegit();

        // Lector que utilitzem per a llegir el document
        try (BufferedReader lector = Files.newBufferedReader(Path,StandardCharsets.UTF_8)) {
            String contingut = "";      // Concatenacio de linies per llegir el contingut
            String linia;               // Linia llegida del document
            int num_linia = 0;          // Nombre de linies llegides

            // Llegim el document mentre hi hagi linies
            while ((linia = lector.readLine()) != null) {
                if (num_linia == 0) {   // En aquesta linia hi ha l'autor
                    D.setAutor(linia.trim());
                }
                else if (num_linia == 1) {  // En aquesta linia hi ha el titol
                    D.setTitol(linia.trim());
                }
                else {  // En aquestes linies hi ha el contingut
                    if (num_linia == 3) contingut = linia;
                    else contingut = contingut.concat("\n" + linia);
                }
                ++num_linia;
            }
            D.setContingut(contingut);
            D.setExtensio(TipusExtensio.TXT);
            D.setPath(Path.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return D;
    }

    /**
     * Escriu en el fitxer amb format .txt, situat en el path indicat, els parametres: titol, autor i contingut
     *
     * @param titol Indica el titol que identifica el document
     * @param autor Indica l'autor que identifica el document
     * @param contingut Indica el contingut del document
     * @param path Indica el path del fitxer .txt que volem escriure
     * @throws FitxerNoCreatException Si s'ha intentat crear el fitxer indicat en el path i no s'ha pogut
     */
    private void guardaDocumentTXT(String titol, String autor, String contingut, String path) throws FitxerNoCreatException {
        // Creem el fitxer de nou (buit)
        creaFitxer(path);
        Path PATH = Paths.get(path);

        // Escriptor que utilitzem per a escriure el document
        try (BufferedWriter escriptor = Files.newBufferedWriter(PATH,StandardCharsets.UTF_8)) {
            // Primera linia posem l'autor, en la segona el titol i en la resta el contingut
            escriptor.write(autor + "\n" + titol + "\n" + contingut);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Llegeix un document en format .xml
     *
     * @param Path Indica el path del fitxer .xml que es llegira
     * @return Retorna un DocumentLlegit que conte l'autor, titol, format i contingut del fitxer llegit
     */
    private DocumentLlegit llegeixDocumentXML(Path Path) {
        DocumentLlegit D = new DocumentLlegit();

        // Lector que utilitzem per a llegir el document
        try (BufferedReader lector = Files.newBufferedReader(Path, StandardCharsets.UTF_8)) {
            String contingut = "";      // Concatenacio de linies per llegir el contingut
            String linia;               // Linia llegida del document
            boolean cont = false;       // Ens indica si estem llegint el contingut
            boolean primera = true;     // Ens ajuda a identificar la primera linia del contingut

            // Llegim el document mentre hi hagi linies
            while ((linia = lector.readLine()) != null) {
                if (cont) { // Estem llegit el contingut
                    if(linia.endsWith("</contingut>")) {    // Hem arribat al final del contingut
                       cont = false;
                    }
                    else { // Concatenem les linies per obtenir el contingut
                        linia = linia.substring(2); // Traiem la tabulacio

                        if(primera) {   // Si es la primera linia no posem el salt de linia
                            primera = false;
                            contingut = linia;
                        }
                        else {  // Concatenem el contingut anterior amb un salt de linia
                            contingut = contingut + "\n" + linia;
                        }
                    }
                }
                else {  // Estem llegint l'autor o titol
                    if(linia.contains("<autor>") && linia.contains("</autor>")) {
                        // Agafem el text que esta entre <autor> i </autor>
                        D.setAutor(linia.substring(8,linia.length()-8).trim());
                    }
                    else if(linia.contains("<titol>") && linia.contains("</titol>")) {
                        // Agafem el text que esta entre <titol> i </titol>
                        D.setTitol(linia.substring(8,linia.length()-8).trim());
                    }
                    else if(linia.contains("<contingut>")) {
                        // Comencem a llegir el contingut
                        cont = true;
                    }
                }
            }
            D.setContingut(contingut);
            D.setExtensio(TipusExtensio.XML);
            D.setPath(Path.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return D;
    }

    /**
     * Escriu en el fitxer amb extensio .xml situat en el path, els parametres: titol, autor i contingut
     *
     * @param titol Indica el titol que identifica el document
     * @param autor Indica l'autor que identifica el document
     * @param contingut Indica el contingut del document
     * @param path Indica el path del fitxer .xml que volem escriure
     * @throws FitxerNoCreatException Si s'ha intentat crear el fitxer indicat en el path i no s'ha pogut
     */
    private void guardaDocumentXML(String titol, String autor, String contingut, String path) throws FitxerNoCreatException {
        // Creem el fitxer de nou buit
        creaFitxer(path);
        Path PATH = Paths.get(path);

        // Escriptor que utilitzem per a escriure el document
        try (BufferedWriter escriptor = Files.newBufferedWriter(PATH,StandardCharsets.UTF_8)) {
            // Escrivim les capçaleres amb el contingut corresponent
            escriptor.write("<document>\n");
            escriptor.write("\t<autor>" + autor + "</autor>\n");
            escriptor.write("\t<titol>" + titol + "</titol>\n");
            escriptor.write("\t<contingut>");

            // Partim el contingut en linies per a poder tabular cada linia
            String[] lines_contingut = contingut.split("\n");

            // Escrivim les linies obtingudes
            for(String linia : lines_contingut) {
                escriptor.write("\n\t\t" + linia);
            }

            // Tanquem les capçaleres
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
     * @param Path Indica el path del fitxer .bol que es llegira
     * @return Retorna un DocumentLlegit que conte l'autor, titol, format i contingut del fitxer llegit
     */
    private DocumentLlegit llegeixDocumentBOL(Path Path) {
        DocumentLlegit D = new DocumentLlegit();

        // Lector que utilitzem per a llegir el document
        try (BufferedReader lector = Files.newBufferedReader(Path, StandardCharsets.UTF_8)) {
            String contingut = "";      // Concatenacio de linies per llegir el contingut
            String linia;               // Linia llegida del document
            int espais = 0;             // Ens indica el nombre de "----" que hem llegit

            // Llegim el document mentre hi hagi linies
            while ((linia = lector.readLine()) != null) {
                // Ens indica que ja hem acabat de llegir l'atribut i ja podem començar a llegir el seguent
                if(linia.equals("----")) {
                    ++espais;
                }
                else if(espais == 0) {  // Estem llegint l'autor
                    D.setAutor(linia.trim());
                }
                else if(espais == 1) {  // Estem llegint el titol
                    D.setTitol(linia.trim());
                }
                else if(espais == 2) {  // Estem llegint el contingut
                    if (contingut.equals("")) { // Primera linia del contingut i, per tant, no afegim el salt de linia
                        contingut = linia;
                    }
                    else {  // Concatenem el contingut anterior amb un salt de linia
                        contingut = contingut + "\n" + linia;
                    }
                }
                else break;
            }
            D.setContingut(contingut);
            D.setExtensio(TipusExtensio.BOL);
            D.setPath(Path.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return D;
    }

    /**
     * Escriu en el fitxer amb extensio .bol situat a path, els parametres: titol, autor i contingut
     *
     * @param titol Indica el titol que identifica el document
     * @param autor Indica l'autor que identifica el document
     * @param contingut Indica el contingut del document
     * @param path Indica el path del fitxer .bol que volem escriure
     * @throws FitxerNoCreatException Si s'ha intentat crear el fitxer indicat en el path i no s'ha pogut
     */
    private void guardaDocumentBOL(String titol, String autor, String contingut, String path) throws FitxerNoCreatException {
        // Creem el fitxer de nou (buit)
        creaFitxer(path);
        Path PATH = Paths.get(path);

        // Escriptor que utilitzem per a escriure el document
        try (BufferedWriter escriptor = Files.newBufferedWriter(PATH,StandardCharsets.UTF_8)) {
            escriptor.write(autor); // Escrivim l'autor
            escriptor.write("\n----\n");
            escriptor.write(titol); // Escrivim el titol
            escriptor.write("\n----\n");
            escriptor.write(contingut); // Escrivim el contingut
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Llegeix un document en format .csv
     *
     * @param Path Indica el path del fitxer .csv que es llegira
     * @return Retorna un array de paraules que conte el fitxer
     */
    private ArrayList<String> llegeixDocumentCSV (Path Path) {
        ArrayList<String> paraules = new ArrayList<String>();

        // Lector que ens llegira el document
        try (BufferedReader lector = Files.newBufferedReader(Path, StandardCharsets.UTF_8)) {
            String linia;   // Linia llegida del document

            // Llegim el document mentre hi hagi linies
            while ((linia = lector.readLine()) != null) {
                // Partim el document segons les comes
                String[] linia_i = linia.split(",");

                // Afegim les paraules obtingudes en l'array
                paraules.addAll(Arrays.asList(linia_i));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return paraules;
    }

    /**
     * Llegeix un document en qualsevol extensio (.txt, .xml, .bol)
     *
     * @param path Indica el path del document que volem llegir
     * @return Retorna un DocumentLlegit amb les dades del document
     */
    public DocumentLlegit llegeixDocument(String path) {
        // Mirem que el document que volem llegir existeixi
        if(existeixFitxer(path)) {
            Path PATH = Paths.get(path);

            // Mirem el tipus d'extensio del document
            int l = path.length();

            // Mirem que no siguin backups creats per binaris: emacs, nano o vi
            if(!path.endsWith("~")) {
                // Obtenim l'extensio del document que volem llegir
                String ext = path.substring(l-4,l);

                // Llegim el document segons la seva extensio
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
     * @throws FitxerNoCreatException Si s'ha intentat crear el fitxer i no s'ha pogut
     */
    private void guardaDocumentLlegit(DocumentLlegit D) throws FitxerNoCreatException {
        // Obtenim les dades del document llegit per a escriure-les en el fixer indicat el path que conte
        String autor = D.getAutor();
        String titol = D.getTitol();
        TipusExtensio ext = D.getExtensio();
        String contingut = D.getContingut();
        String path = D.getPath();

        // Escrivim les dades depenent del format del document
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
     * @throws FitxerNoCreatException Si s'ha intentat crear el fitxer i no s'ha pogut
     * @throws FitxerNoEliminatException Si s'ha intentat eliminar el fitxer i no s'ha pogut
     */
    public void guardaDocument(DocumentLlegit D) throws FitxerNoCreatException, FitxerNoEliminatException {
        String path = D.getPath();

        // Si el document existeix, cal eliminar-lo (esborrar-lo i tornar-lo a crear) per actualitzar-ho
        esborraFitxer(path);

        // Guardem el document
        guardaDocumentLlegit(D);
    }

    /**
     * Exporta un document fora del sistema
     *
     * @param D Indica el document que es vol exportar
     * @throws FitxerNoCreatException Si s'ha intentat crear el fitxer i no s'ha pogut
     */
    public void exportarDocument(DocumentLlegit D) throws FitxerNoCreatException {
        // Obtenim el path on es vol exportar el document
        String path = D.getPath();

        // Traiem l'extensio del document per si existeix, poder modificar el seu nom per evitar repetits
        String path_doc = path.substring(0,path.length()-4);

        String ext = D.getExtensio().toString().toLowerCase();  // Obtenim l'extensio que te el document

        int num_copia = 1;  // Ens ajuda a modificar el nom del fitxer per a evitar repetits

        // Mentre el document existeixi, cal modificar el seu path per a no eliminar el document existent
        while(existeixFitxer(path)) {
            // Afegim un nou nom per guardar el document exportat
            path = path_doc + "_" + num_copia + "." + ext;
            ++num_copia;
        }

        // Posem el nou path en el document, que es unic
        D.setPath(path);

        // Guardem el document en el path que te
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

        // Lector que ens llegira les expressions booleanes
        try (BufferedReader lector = Files.newBufferedReader(PATH,StandardCharsets.UTF_8)) {
            String linia;

            // Llegim el fitxer mentre hi hagi linies (cada linia es una expressio booleana)
            while((linia = lector.readLine()) != null) {
                // Afegim l'expressio en el llistat
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
    public void guardaExpressionsBooleanes(ArrayList<String> expressions, String path) throws FitxerNoCreatException,
            FitxerNoEliminatException {
        // Si existeix el document l'eliminem per tornar-lo a crear i actualitzar les expressions
        if(existeixFitxer(path)) {
            eliminaFitxer(path);
        }
        creaFitxer(path);

        Path PATH = Paths.get(path);

        // Escriptor que utilitzem per a escriure les expressions booleanes
        try (BufferedWriter escriptor = Files.newBufferedWriter(PATH, StandardCharsets.UTF_8)) {
            // Escrivim cada expressio de l'array en el document indicat en el path
            for(String expressio : expressions) {
                escriptor.write(expressio+'\n');
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
     * @return Retorna un array de paraules, on cada paraula es una StopWord
     */
    public ArrayList<String> llegeixStopWords(String path) {
        ArrayList<String> stopWords = new ArrayList<String>();

        // Mirem que el fitxer on guardem les stop words existeixi
        if(existeixFitxer(path)) {
            Path PATH = Paths.get(path);

            // Llegim les paraules
            stopWords = llegeixDocumentCSV(PATH);
        }
        return stopWords;
    }
}