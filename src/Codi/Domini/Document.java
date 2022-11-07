package Codi.Domini;

import Codi.Util.TipusExtensio;
import java.util.HashMap;

public class Document {
    String titol, autor, path, contingut;

    TipusExtensio tipusExtensio;

    int pes;    //quantitat de caràcters

    HashMap<String, Integer> aparicions;
    public Document () {}

    public Document (String titol, String autor) {
        this.titol = titol; this.autor = autor;
    }

    public Document (String titol, String autor, String path, String contingut, TipusExtensio tipusExtensio) {
        this.titol = titol; this.autor = autor; this.path = path;
        this.tipusExtensio = tipusExtensio;

        this.setContingut(contingut);
    }
    void comptarAparicions () {
        //comptar aparicions
        //filtrar stopwords
    }
    void setContingut (String s) {
        this.contingut = s;
        this.pes = this.contingut.length();
        comptarAparicions();
    }
    String getContingut () {return this.contingut;}

    public String getTitol() {
        return titol;
    }
    public String getAutor() {
        return autor;
    }
    public String getPath() {
        return path;
    }
    public TipusExtensio getTipusExtensio() {
        return tipusExtensio;
    }
    public int getPes() {
        return pes;
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
    public void setTipusExtensio(TipusExtensio tipusExtensio) {
        this.tipusExtensio = tipusExtensio;
    }
    public HashMap<String, Integer> getAparicions() {
        return this.aparicions;
    }
}
