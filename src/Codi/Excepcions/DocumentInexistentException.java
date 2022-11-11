package Codi.Excepcions;

public class DocumentInexistentException extends NullPointerException {
    String titol, autor;

    public DocumentInexistentException (String titol, String autor) {
        super("Error: el document de nom: " + titol + " creat per l'autor: " + autor + " no existeix.");
        this.titol = titol;
        this.autor = autor;
    }

    public String toString () {
        return "Error: el document de nom: " + this.titol + " creat per l'autor: " + this.autor + " no existeix.";
    }
}
