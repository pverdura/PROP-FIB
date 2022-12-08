package Codi.Excepcions;

public class FitxerNoCreatException extends NullPointerException {
    String path;

    public FitxerNoCreatException(String path) {
        super("Error: No es pot crear el fitxer " + path);
        this.path = path;
    }

    public String toString() {
        return "Error: No es pot crear el fitxer " + this.path;
    }
}
