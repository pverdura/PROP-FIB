package Codi.Excepcions;

public class ExpressioBooleanaJaExistentException extends Exception {
    String expressio;

    public ExpressioBooleanaJaExistentException (String expressio) {
        super("ERROR: L'expressió: " + expressio + " ja existeix.");
        this.expressio = expressio;
    }

    public String toString () {
        return "ERROR: L'expressió: " + this.expressio + " ja existeix.";
    }
}
