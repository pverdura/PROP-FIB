package Codi.Excepcions;

import java.io.IOException;

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