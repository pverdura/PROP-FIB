package Codi.Excepcions;

import Codi.Util.TipusExtensio;

public class TipusExtensioIncorrectaException extends NullPointerException {
    String extensio;

    public TipusExtensioIncorrectaException(String extensio) {
        super("Error: " + extensio + " no és un format correcte.");
        this.extensio = extensio;
    }

    public String toString() {
        return "Error: " + extensio + " no és un format correcte.";
    }
}
