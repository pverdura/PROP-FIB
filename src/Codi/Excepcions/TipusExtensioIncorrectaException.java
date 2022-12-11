package Codi.Excepcions;

import java.io.IOException;

public class TipusExtensioIncorrectaException extends IOException {
    String extensio;

    public TipusExtensioIncorrectaException(String extensio) {
        super("Error: " + extensio + " no és un format correcte.");
        this.extensio = extensio;
    }

    public String toString() {
        return "Error: " + extensio + " no és un format correcte.";
    }
}
