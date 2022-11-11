package Codi.Excepcions;

public class NombreMassaPetitDocuments extends Exception {

    public NombreMassaPetitDocuments() {
        super("Error: No es pot obtenir un nombre tant petit de documents.");
    }

    public String toString() {
        return "Error: No es pot obtenir un nombre tant petit de documents.";
    }
}
