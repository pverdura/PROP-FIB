package Codi.Excepcions;

public class FitxerNoEliminatExeption extends NullPointerException {
    String path;

    public FitxerNoEliminatExeption(String path) {
        super("Error: No es pot eliminar el fitxer " + path);
        this.path = path;
    }

    public String toString() {
        return "Error: No es pot eliminar el fitxer " + this.path;
    }
}
