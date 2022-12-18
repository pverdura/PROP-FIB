package Codi.Excepcions;

import java.io.IOException;

/**
 * Excepcio que representa que no s'ha pogut eliminar el fitxer del path indicat
 */
public class FitxerNoEliminatException extends IOException {
    String path;

    public FitxerNoEliminatException(String path) {
        super("Error: No es pot eliminar el fitxer " + path);
        this.path = path;
    }

    public String toString() {
        return "Error: No es pot eliminar el fitxer " + this.path;
    }
}