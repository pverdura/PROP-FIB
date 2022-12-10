package Codi.Excepcions;

public class CarpetaBuidaException extends NullPointerException {

    public CarpetaBuidaException() {
        super("Error: no hi ha cap document guardat en el sistema");
    }

    String toSting() {
        return "Error: no hi ha cap document guardat en el sistema";
    }
}
