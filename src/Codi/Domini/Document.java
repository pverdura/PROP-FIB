package Codi.Domini;

import Codi.Util.TipusExtensio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

/**
 * Classe que representa un document
 *
 * @author Jordi Palomera
 * @since 13-12-22
 */

public class Document {
    private String titol, autor, path, contingut;
    private int pes;
    private TipusExtensio tipusExtensio;
    private static ArrayList<String> stopWords;
    private final HashMap<String, Integer> aparicions;
    private final static String signesDePuntuacio = "-_!\"$%&/()=|@#~€¬?¿¡'[]";
    private final static String[] extres = {"l'", "d'", "m'", "n'", "t'", "s'", "'l", "'ls", "'m", "'n", "'t", "'s",
            "-ho","-ne","-me","-te","-se","-los","-les", "-lo","-hi","-li"};

    /**
     * Constructor d'un document buit
     */
    public Document () {
        this.aparicions = new HashMap<>();
        if (Objects.isNull(Document.stopWords)) Document.stopWords = new ArrayList<>();
        this.setContingut("");
    }

    /**
     * Constructor d'un document a partir del titol i l'autor
     *
     * @param titol Titol del document
     * @param autor Autor del document
     */
    public Document (String titol, String autor) {
        this();
        this.titol = titol; this.autor = autor;
    }

    /**
     * Constructor d'un document a partir dels seus atributs basics
     *
     * @param titol Titol del document
     * @param autor Autor del document
     * @param path  Path del document
     * @param contingut Contingut del document
     * @param tipusExtensio Tipus d'extensio del document
     */
    public Document (String titol, String autor, String path, String contingut, TipusExtensio tipusExtensio) {
        this(titol, autor);
        this.path = path; this.tipusExtensio = tipusExtensio;

        this.setContingut(contingut);
    }

    /**
     * Modifica el titol del document
     *
     * @param titol Nou titol el document
     */
    public void setTitol (String titol) {
        this.titol = titol;
    }

    /**
     * Obte el titol del document
     *
     * @return Titol del document
     */
    public String getTitol () {
        return this.titol;
    }

    /**
     * Modifica l'autor del document
     *
     * @param autor Nou autor del document
     */
    public void setAutor (String autor) {
        this.autor = autor;
    }

    /**
     * Obte l'autor del document
     *
     * @return Autor del document
     */
    public String getAutor () {
        return this.autor;
    }

    /**
     * Modifica el contingut del document
     *
     * @param contingut Nou contingut del document
     */
    public void setContingut (String contingut) {
        this.contingut = contingut;
        this.pes = this.contingut.length();
        aparicions.clear();

        this.comptarAparicions();
    }

    /**
     * Obte el contingut del document
     *
     * @return El contingut del document
     */
    public String getContingut () {
        return this.contingut;
    }

    /**
     * Modifica la path del document
     *
     * @param path Nova path del document
     */
    public void setPath (String path) {
        this.path = path;
    }

    /**
     * Obte la path del document
     *
     * @return Path del document
     */
    public String getPath () {
        return this.path;
    }

    /**
     * Modifica l'extensio i la path del document
     *
     * @param tipusExtensio Nova extensio del document
     */
    public void setExtensio (TipusExtensio tipusExtensio) {
        this.tipusExtensio = tipusExtensio;
        String p = getPath().substring(0, getPath().length()-3);

        if (tipusExtensio.equals(TipusExtensio.TXT)) p = p + "txt";
        else if (tipusExtensio.equals(TipusExtensio.XML)) p = p + "xml";
        else p = p + "bol";

        setPath(p);
    }

    /**
     * Obte l'extensio del document
     *
     * @return L'extensio del document
     */
    public TipusExtensio getExtensio () {
        return this.tipusExtensio;
    }

    /**
     * Obte el pes (la quantitat de caracters del contingut) del document
     *
     * @return El pes del document
     */
    public int getPes () {
        return this.pes;
    }

    /**
     * Modifica les stop words de la classe Document
     *
     * @param s {@code ArrayList<String>} de les noves stop words
     */
    public static void setStopWords (ArrayList<String> s) {
        Document.stopWords = s;
    }

    /**
     *  Obte les stop words
     *
     * @return {@code ArrayList<String>} de les stop words
     */
    public static ArrayList<String> getStopWords () {
        return Document.stopWords;
    }

    /**
     * Obte la quantitat de cops que apareix cada paraula al contingut del document
     *
     * @return {@code HashMap<String, Integer} on la clau és la paraula i el valor la quantitat d'aparicions de la paraula
     */
    public HashMap<String, Integer> getAparicions () {
        return this.aparicions;
    }

    /**
     * Obte la llista de paraules del contingut del document
     *
     * @return {@code ArrayList<String>} de paraules sense repeticions del document
     */
    public ArrayList<String> getParaules () {
        return new ArrayList<>(this.aparicions.keySet());
    }

    ///////////////////////////////////////////////////////////
    ///                  MÈTODES PRIVATS                    ///
    ///////////////////////////////////////////////////////////

    /**
     * Esborra els pronoms febles, els articles i els signes de puntuacio d'una paraula
     *
     * @param s Cadena de text a tractar
     * @return {@code String} Cadena de text tractada
     */
    private String tractar (String s) {
        int n = extres.length;

        for (int i = 0; i < n; ++i) {
            s = s.replace(extres[i], "");
        }

        n = Document.signesDePuntuacio.length();
        //eliminar signes de puntuacio
        for (int i = 0; i < n; ++i) {
            s = s.replace(Character.toString(Document.signesDePuntuacio.charAt(i)), "");
        }
        return s;
    }

    /**
     * Compta les aparicions de cada paraula al contingut del document
     * Cada paraula és tractada amb el metode {@link #tractar(String) Tractar}, i no té en compte les stop words
     */
    private void comptarAparicions () {
        String[] aux = this.contingut.toLowerCase().split("[, \n\t.;:]+");
        ArrayList<String> senseEspais = new ArrayList<>(Arrays.asList(aux));

        for (String paraula : senseEspais) {
            paraula = tractar(paraula);

            if (!Document.stopWords.contains(paraula) && !paraula.equals("")) {
                this.aparicions.merge(paraula, 1, Integer::sum);
            }
        }
    }
}
