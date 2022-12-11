package Codi.Excepcions;

import java.io.IOException;

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
