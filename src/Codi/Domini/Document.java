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
    private static ArrayList<String> stopWords;
    private final static String signesDePuntuacio = "-_!\"$%&/()=|@#~€¬?¿¡'[]";
    private final static String[] extres = {"l'", "d'", "m'", "n'", "t'", "s'", "'l", "'ls", "'m", "'n", "'t", "'s",
                                "-ho","-ne","-me","-te","-se","-los","-les", "-lo","-hi","-li"};
    private TipusExtensio tipusExtensio;
    private int pes;    //quantitat de caràcters
    private final HashMap<String, Integer> aparicions;

    /**
     * Constructor d'un document buit
     */
    public Document () {
        this.aparicions = new HashMap<>();
        if (Objects.isNull(Document.stopWords)) Document.stopWords = new ArrayList<>();
        this.setContingut("");
    }

    /**
     * Constructor d'un document a partir del títol i l'autor
     *
     * @param titol Títol del document
     * @param autor Autor del document
     */
    public Document (String titol, String autor) {
        this();
        this.titol = titol; this.autor = autor;
    }

    /**
     * Constructor d'un document a partir dels seus atributs bàsics
     *
     * @param titol Títol del document
     * @param autor Autor del document
     * @param path  Path del document
     * @param contingut Contingut del document
     * @param tipusExtensio Tipus d'extensió del document
     */
    public Document (String titol, String autor, String path, String contingut, TipusExtensio tipusExtensio) {
        this(titol, autor);
        this.path = path; this.tipusExtensio = tipusExtensio;

        this.setContingut(contingut);
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
     * Obté el contingut del document
     *
     * @return El contingut del document
     */
    public String getContingut () {
        return this.contingut;
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
     *  Obté les stop words
     *
     * @return {@code ArrayList<String>} de les stop words
     */
    public static ArrayList<String> getStopWords () {
        return Document.stopWords;
    }

    /**
     * Modifica el títol del document
     *
     * @param titol Nou títol el document
     */
    public void setTitol (String titol) {
        this.titol = titol;
    }

    /**
     * Obté el títol del document
     *
     * @return Títol del document
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
     * Obté l'autor del document
     *
     * @return Autor del document
     */
    public String getAutor () {
        return this.autor;
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
     * Obté la path del document
     *
     * @return Path del document
     */
    public String getPath () {
        return this.path;
    }

    /**
     * Modifica l'extensió i la path del document
     *
     * @param tipusExtensio Nova extensió del document
     */
    public void setExtensio (TipusExtensio tipusExtensio) {
        this.tipusExtensio = tipusExtensio;
        String p = getPath();

        if (tipusExtensio.equals(TipusExtensio.TXT)) p = p.substring(0, p.length()-3)+"txt";
        else if (tipusExtensio.equals(TipusExtensio.XML)) p = p.substring(0, p.length()-3)+"xml";
        else p = p.substring(0, p.length()-3)+"bol";

        setPath(p);
    }

    /**
     * Obté l'extensió del document
     *
     * @return L'extensió del document
     */
    public TipusExtensio getExtensio () {
        return this.tipusExtensio;
    }

    /**
     * Obté el pes (la quantitat de caràcters del contingut) del document
     *
     * @return El pes del document
     */
    public int getPes () {
        return this.pes;
    }

    /**
     * Obté la quantitat de cops que apareix cada paraula al contingut del document
     *
     * @return {@code HashMap<String, Integer} on la clau és la paraula i el valor la quantitat d'aparicions de la paraula
     */
    public HashMap<String, Integer> getAparicions () {
        return this.aparicions;
    }

    /**
     * Obté la llista de paraules del contingut del document
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
     * Esborra els pronoms febles, els articles i els signes de puntuació d'una paraula
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
        //eliminar signes de puntuació
        for (int i = 0; i < n; ++i) {
            s = s.replace(Character.toString(Document.signesDePuntuacio.charAt(i)), "");
        }
        return s;
    }

    /**
     * Compta les aparicions de cada paraula al contingut del document     *
     * Cada paraula és tractada amb el mètode {@link #tractar(String) Tractar}, i no té en compte les stop words
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
