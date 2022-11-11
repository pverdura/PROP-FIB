package Codi.Util;

public class DocumentJaExisteixException extends Exception {
    String titol, autor;

    public DocumentJaExisteixException (String titol, String autor) {
        super("Error: el document de nom: " + titol + " creat per l'autor: " + autor + " ja existeix.");
        this.titol = titol;
        this.autor = autor;
    }

    public String toString () {
        return "Error: el document de nom: " + this.titol + " creat per l'autor: " + this.autor + " ja existeix.";
    }
}
