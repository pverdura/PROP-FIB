package Codi.Excepcions;

public class ArrayDeParaulesBuitException extends Exception {

    public ArrayDeParaulesBuitException() {
        super ("Error en intentar calcular la semblança d'un array buit.");
    }

    public String toString () {
        return ("Error: en intentar calcular la semblança d'un array buit.");
    }
}
