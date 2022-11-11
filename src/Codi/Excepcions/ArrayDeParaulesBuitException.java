package Codi.Excepcions;

public class ArrayDeParaulesBuitException extends Exception {

    public ArrayDeParaulesBuitException() {
        super ("Error en calcular la semblança d'un conjunt buit.");
    }

    public void String toString () {
        return "Error: en calcular la semblança d'un conjunt buit.";
    }
}
