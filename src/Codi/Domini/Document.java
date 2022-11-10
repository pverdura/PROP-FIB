package Codi.Domini;

import Codi.Util.TipusExtensio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Document {    String titol, autor, path, contingut;
    static ArrayList<String> stopWords;
    static String signesDePuntuacio = "-_!\"$%&/()=|@#~€¬?¿¡'[]";
    static String[] extres = {"l'", "d'", "m'", "n'", "t'", "s'", "'l", "'ls", "'m", "'n", "'t", "'s",
                                "-ho","-ne","-me","-te","-se","-los","-les", "-lo","-hi","-li"};
    TipusExtensio tipusExtensio;
    int pes;    //quantitat de caràcters
    HashMap<String, Integer> aparicions;
    public Document () {
        this.aparicions = new HashMap<>();
        this.setContingut("");
        if (Document.stopWords == null) Document.stopWords = new ArrayList<>();
    }

    public Document (String titol, String autor) {
        this();
        this.titol = titol; this.autor = autor;
    }
    public Document (String titol, String autor, String path, String contingut, TipusExtensio tipusExtensio) {
        this(titol, autor);
        this.path = path; this.tipusExtensio = tipusExtensio;

        this.setContingut(contingut);
    }
    String tractar (String s) {
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
    void comptarAparicions () {
        String[] aux = this.contingut.toLowerCase().split("[, \n\t.;:]+");
        ArrayList<String> senseEspais = new ArrayList<>(Arrays.asList(aux));

        for (String paraula : senseEspais) {
            paraula = tractar(paraula);

            if (!Document.stopWords.contains(paraula)) {
                this.aparicions.merge(paraula, 1, Integer::sum);
            }
        }
    }
    public void setContingut (String s) {
        this.contingut = s;
        this.pes = this.contingut.length();
        aparicions.clear();

        this.comptarAparicions();
    }
    public String getContingut () {
        return this.contingut;
    }
    public static void setStopWords (ArrayList<String> s) {
        Document.stopWords = s;
    }

    public static ArrayList<String> getStopWords () {
        return Document.stopWords;
    }
    public void setTitol (String titol) {
        this.titol = titol;
    }
    public String getTitol () {
        return this.titol;
    }
    public void setAutor (String autor) {
        this.autor = autor;
    }
    public String getAutor () {
        return this.autor;
    }
    public void setPath (String path) {
        this.path = path;
    }
    public String getPath () {
        return this.path;
    }
    public void setExtensio (TipusExtensio tipusExtensio) {
        this.tipusExtensio = tipusExtensio;
    }
    public TipusExtensio getExtensio () {
        return this.tipusExtensio;
    }
    public int getPes () {
        return this.pes;
    }
    public HashMap<String, Integer> getAparicions () {
        return this.aparicions;
    }
}
