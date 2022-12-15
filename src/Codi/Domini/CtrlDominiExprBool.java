package Codi.Domini;
import Codi.Excepcions.ExpressioBooleanaInexistentException;
import Codi.Excepcions.ExpressioBooleanaJaExistentException;

import java.util.HashMap;

/**
 * Classe que gestiona la creacio, eliminacio i modificacio d'una expressio booleana
 * @author PauVi
 */
public class CtrlDominiExprBool {

    /**
     * Constructor
     */
    public CtrlDominiExprBool() {}


    /**
     * Metode que gestiona la creacio d'una expressio booleana en l'estructura de dades
     * @param expressio Expressio booleana a crear en l'estructura de dades
     * @param expressions Estructura de dades de les expressions booleanes
     * @throws ExpressioBooleanaJaExistentException Si l'expressio a crear ja existeix
     */
    public void creaExpressioBool (String expressio, HashMap<String,ExpressioBooleana> expressions) throws ExpressioBooleanaJaExistentException {

        if (expressions.containsKey(expressio)) throw new ExpressioBooleanaJaExistentException(expressio);

        expressions.put(expressio, new ExpressioBooleana(expressio));
    }

    /**
     * Metode que gestiona la eliminacio d'una expressio booleana en l'estructura de dades
     * @param expressio Expressio booleana a eliminar en l'estructura de dades
     * @param expressions Estructura de dades de les expressions booleanes
     * @throws ExpressioBooleanaInexistentException Si l'expressio a eliminar no existeix
     */
    public void eliminaExpressioBool (String expressio, HashMap<String,ExpressioBooleana> expressions) throws ExpressioBooleanaInexistentException {

        if (!expressions.containsKey(expressio)) throw new ExpressioBooleanaInexistentException(expressio);
         expressions.remove(expressio);
    }

    /**
     * Metode que gestiona la modificacio d'una expressio booleana en l'estructura de dades
     * @param anticValor Valor antic expressio booleana
     * @param nouValor Valor nou expressio booleana
     * @param expressions Estructura de dades de les expressions booleanes
     * @throws ExpressioBooleanaInexistentException Si l'expressio booleana a modificar no existeix
     * @throws ExpressioBooleanaJaExistentException  Si el nou valor de l'expressio booleana a modificar ja existeix
     */
    public void modificaExpressioBool(String anticValor, String nouValor,
                               HashMap<String,ExpressioBooleana> expressions) throws ExpressioBooleanaInexistentException, ExpressioBooleanaJaExistentException {

        if (!expressions.containsKey(anticValor)) throw new ExpressioBooleanaInexistentException(anticValor);
        if (expressions.containsKey(nouValor)) throw new ExpressioBooleanaJaExistentException(nouValor);

        expressions.remove(anticValor);
        expressions.put(nouValor, new ExpressioBooleana(nouValor));
    }

    /**
     * Funcio per obtenir el nombre total d'expressions de l'estructura de dades
     * @param expressions Estructura de dades de les expressions booleanes
     * @return Retorna el nombre total d'expressions booleanes de l'estructura de dades
     */
    public int getNombreExpressionsBooleanes(HashMap<String, ExpressioBooleana> expressions) {
        return expressions.size();
    }
}
