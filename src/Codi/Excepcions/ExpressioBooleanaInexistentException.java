package Codi.Excepcions;


/**
 * Excepcio que representa que una expressio booleana no existeix en l'estructura de dades
 * @author PauVi
 */
public class ExpressioBooleanaInexistentException  extends Exception{
    String expressio;

    public ExpressioBooleanaInexistentException (String expressio) {
        super("ERROR: L'expressió: " + expressio + " no existeix.");
        this.expressio = expressio;
    }

    public String toString () {
        return "ERROR: L'expressió: " + this.expressio + " no existeix.";
    }
}
