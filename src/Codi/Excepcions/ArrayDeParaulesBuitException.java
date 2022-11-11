package Codi.Excepcions;

public class ArrayDeParaulesBuitException extends Exception {

    public ArrayDeParaulesBuitException() {
        super("Error: No es pot calcular la semblança d'un conjunt buit.");
    }

    public String toString () {
        return "Error: No es pot calcular la semblança d'un conjunt buit.";
    }
}
