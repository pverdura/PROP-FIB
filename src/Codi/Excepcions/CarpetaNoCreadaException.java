package Codi.Excepcions;

import java.io.IOException;

/**
 * Excepcio que representa que el no s'ha pogut crear una carpeta en el path indicat
 *
 * @author Pol Verdura
 */
public class CarpetaNoCreadaException extends IOException {
    String path;

    public CarpetaNoCreadaException(String path) {
        super("Error: No es pot crear el directori " + path);
        this.path = path;
    }

    public String toString() {
        return "Error: No es pot crear el directori " + this.path;
    }
}
