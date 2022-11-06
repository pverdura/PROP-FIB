package Codi.Domini;
import java.util.ArrayList;

public class CtrlDominiExprBool {

    public CtrlDominiExprBool() {}

    public void creaExpressioBool (String valor, ArrayList<ExpressioBooleana> expressions) {
        expressions.add(new ExpressioBooleana(valor));
    }

    public void eliminaExpressioBool (String valor, ArrayList<ExpressioBooleana> expressions) {
        expressions.remove(new ExpressioBooleana(valor));
    }

    void modificaExpressioBool(String anticValor, String nouValor,
                               ArrayList<ExpressioBooleana> expressions) {

        //Si no troba l'expressio retorna -1
        int index = expressions.indexOf(new ExpressioBooleana(anticValor));

        if (index != -1) {
            expressions.get(index).setExpressio(nouValor);
        }
    }
}
