package Codi.Excepcions;

/**
 * Excepcio que representa que una expressio booleana ja existeix en l'estructura de dades
 * @author PauVi
 */
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
