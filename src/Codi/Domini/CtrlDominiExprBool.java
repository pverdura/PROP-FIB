package Codi.Domini;
import java.util.ArrayList;
import java.util.HashMap;

public class CtrlDominiExprBool {

    public CtrlDominiExprBool() {}

    public void creaExpressioBool (String expressio, HashMap<String,ExpressioBooleana> expressions) {
        expressions.put(expressio, new ExpressioBooleana(expressio));
    }

    public void eliminaExpressioBool (String valor, HashMap<String,ExpressioBooleana> expressions) {
        expressions.remove(valor);
    }

    void modificaExpressioBool(String anticValor, String nouValor,
                               HashMap<String,ExpressioBooleana> expressions) {

            expressions.remove(anticValor);
            expressions.put(nouValor, new ExpressioBooleana(nouValor));
    }

    int getNombreExpressionsBooleanes(HashMap<String, ExpressioBooleana> expressions) {
        return expressions.size();
    }
}
