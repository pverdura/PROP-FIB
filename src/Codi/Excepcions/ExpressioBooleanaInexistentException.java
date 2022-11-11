package Codi.Excepcions;

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
