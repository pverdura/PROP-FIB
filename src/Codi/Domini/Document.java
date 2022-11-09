package Codi.Domini;

import Codi.Util.TipusExtensio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Document {
    String titol, autor, path, contingut;

    static ArrayList<String> stopWords;
    static String signesDePuntuacio = ",.;:-_!\"$%&/()=|@#~€¬?¿¡'";

    TipusExtensio tipusExtensio;

    int pes;    //quantitat de caràcters

    HashMap<String, Integer> aparicions;
    public Document () {
        aparicions = new HashMap<String, Integer>();
        if (Document.stopWords == null) Document.stopWords = new ArrayList<String>();
    }

    public Document (String titol, String autor) {
        this.titol = titol; this.autor = autor;
        aparicions = new HashMap<String, Integer>();

        setContingut("");
    }
    public Document (String titol, String autor, String path, String contingut, TipusExtensio tipusExtensio) {
        this.titol = titol; this.autor = autor; this.path = path;
        this.tipusExtensio = tipusExtensio;
        aparicions = new HashMap<String, Integer>();

        this.setContingut(contingut);
    }

    void esborrarSignesPuntuacio (String s) {
        int n = Document.signesDePuntuacio.length();
        for (int i = 0; i < n; ++i) {
            s = s.replace(Character.toString(Document.signesDePuntuacio.charAt(i)), "");
        }
    }
    void comptarAparicions () {
        //comptar aparicions de les paraules
        //filtrar stopword
        //esborrar signes de puntuació
        String[] aux = this.contingut.toLowerCase().split(" ");
        ArrayList<String> senseEspais = new ArrayList<String>(Arrays.asList(aux));

        for (int i = 0; i < senseEspais.size(); ++i) {
            String paraula = senseEspais.get(i);
            System.out.println(paraula);
            //esborrar signes de puntuació
            if (!Document.stopWords.contains(paraula)) {
                aparicions.merge(paraula, 1, Integer::sum);
            }
        }
    }
    public void setContingut (String s) {
        this.contingut = s;
        this.pes = this.contingut.length();
        aparicions.clear();

        this.comptarAparicions();
    }

    public static void setStopWords (ArrayList<String> s) {
        Document.stopWords = s;
    }

    public static ArrayList<String> getStopWords () {
        return Document.stopWords;
    }
    public String getContingut () {
        return this.contingut;
    }
    public String getTitol() {
        return this.titol;
    }
    public String getAutor() {
        return this.autor;
    }
    public String getPath() {
        return this.path;
    }
    public TipusExtensio getExtensio() {
        return this.tipusExtensio;
    }
    public int getPes() {
        return this.pes;
    }
    public void setTitol(String titol) {
        this.titol = titol;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public void setExtensio(TipusExtensio tipusExtensio) {
        this.tipusExtensio = tipusExtensio;
    }
    public HashMap<String, Integer> getAparicions() {
        return this.aparicions;
    }
}
