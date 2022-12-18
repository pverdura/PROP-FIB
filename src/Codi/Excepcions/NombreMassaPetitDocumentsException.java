package Codi.Excepcions;

/**
 * Excepcio que representa que el nombre de documents que es vol cercar es massa petit (-INF, 0]
 */
public class NombreMassaPetitDocumentsException extends Exception {

    public NombreMassaPetitDocumentsException() {
        super("Error: No es pot obtenir un nombre tant petit de documents.");
    }

    public String toString() {
        return "Error: No es pot obtenir un nombre tant petit de documents.";
    }
}
