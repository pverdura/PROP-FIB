package Codi.Domini;
import Codi.Excepcions.ExpressioBooleanaInexistentException;
import Codi.Excepcions.ExpressioBooleanaJaExistentException;

import java.util.HashMap;

public class CtrlDominiExprBool {

    public CtrlDominiExprBool() {}

    public void creaExpressioBool (String expressio, HashMap<String,ExpressioBooleana> expressions) throws ExpressioBooleanaJaExistentException {

        if (expressions.containsKey(expressio)) throw new ExpressioBooleanaJaExistentException(expressio);

        expressions.put(expressio, new ExpressioBooleana(expressio));
    }

    public void eliminaExpressioBool (String expressio, HashMap<String,ExpressioBooleana> expressions) throws ExpressioBooleanaInexistentException {

        if (!expressions.containsKey(expressio)) throw new ExpressioBooleanaInexistentException(expressio);
         expressions.remove(expressio);
    }

    void modificaExpressioBool(String anticValor, String nouValor,
                               HashMap<String,ExpressioBooleana> expressions) throws ExpressioBooleanaInexistentException, ExpressioBooleanaJaExistentException {

        if (!expressions.containsKey(anticValor)) throw new ExpressioBooleanaInexistentException(anticValor);
        if (expressions.containsKey(nouValor)) throw new ExpressioBooleanaJaExistentException(nouValor);

        expressions.remove(anticValor);
        expressions.put(nouValor, new ExpressioBooleana(nouValor));
    }

    int getNombreExpressionsBooleanes(HashMap<String, ExpressioBooleana> expressions) {
        return expressions.size();
    }
}
