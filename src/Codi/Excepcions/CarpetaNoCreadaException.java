package Codi.Excepcions;

import java.io.IOException;

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
