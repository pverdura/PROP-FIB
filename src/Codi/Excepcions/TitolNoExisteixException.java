package Codi.Excepcions;

public class TitolNoExisteixException extends NullPointerException {
    String titol;

    public TitolNoExisteixException (String titol) {
        super("Error: no existeix cap document de nom: " + titol);
        this.titol = titol;
    }

    public String toString () {
        return "Error: no existeix cap document de nom: " + this.titol;
    }
}
