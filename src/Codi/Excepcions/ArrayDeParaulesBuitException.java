package Codi.Excepcions;

/**
 * Excepcio que representa que el conjunt de paraules que s'utilitza per fer cerca Semblant o Paraules es buit
 *
 * @author Pol Verdura
 */
public class ArrayDeParaulesBuitException extends Exception {

    public ArrayDeParaulesBuitException() {
        super("Error: No es pot calcular la semblança d'un document buit.");
    }

    public String toString () {
        return "Error: No es pot calcular la semblança d'un document buit.";
    }
}
