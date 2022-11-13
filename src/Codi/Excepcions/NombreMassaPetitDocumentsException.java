package Codi.Excepcions;

public class NombreMassaPetitDocumentsException extends Exception {

    public NombreMassaPetitDocumentsException() {
        super("Error: No es pot obtenir un nombre tant petit de documents.");
    }

    public String toString() {
        return "Error: No es pot obtenir un nombre tant petit de documents.";
    }
}