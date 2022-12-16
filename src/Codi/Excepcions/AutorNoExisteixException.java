package Codi.Excepcions;

/**
 * Excepcio que representa que no existeix cap document amb un autor en concret
 *
 * @author Judit Serna
 */
public class AutorNoExisteixException extends NullPointerException {
    String autor;

    public AutorNoExisteixException (String autor) {
        super("Error: no existeix cap document amb autor " + autor);
        this.autor = autor;
    }

    public String toString () {
        return "Error: no existeix cap document amb autor: " + this.autor;
    }
}
