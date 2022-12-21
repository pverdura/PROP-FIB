package Codi.Excepcions;

import java.io.IOException;

/**
 * Excepcio que representa que no s'ha pogut crear el fitxer en el path indicat
 */
public class FitxerNoCreatException extends IOException {
    String path;

    public FitxerNoCreatException(String path) {
        super("Error: No es pot crear el fitxer " + path);
        this.path = path;
    }

    public String toString() {
        return "Error: No es pot crear el fitxer " + this.path;
    }
}
