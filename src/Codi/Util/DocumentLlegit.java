package Codi.Util;

public class DocumentLlegit {
    private String titol;
    private String autor;
    private String path;
    private String contingut;


    public String getTitol(){
        return titol;
    }

    public String getAutor(){
        return autor;
    }

    public String getPath(){
        return path;
    }

    public String getContingut(){
        return contingut;
    }

    public void setTitol(String s){
        titol = s;
    }

    public void setAutor(String s){
        autor = s;
    }

    public void setPath(String s){
        path = s;
    }

    public void setContingut(String s){
        contingut = s;
    }
}