package Codi.Excepcions;

/**
 *  Excepcio que representa que no existeix cap document amb un títol en concret
 *
 * @author Jordi Palomera
 * @since 13-12-2022
 */
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
