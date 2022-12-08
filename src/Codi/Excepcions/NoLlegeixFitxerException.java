package Codi.Excepcions;

public class NoLlegeixFitxerException extends NullPointerException {
    String path;

    public NoLlegeixFitxerException(String path) {
        super("Error: No s'ha pogut llegir el document " + path);
        this.path = path;
    }

    public String toString() {
        return "Error: No s'ha pogut llegir el document " + this.path;
    }
}
