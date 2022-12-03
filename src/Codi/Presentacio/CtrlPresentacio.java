package Codi.Presentacio;

import Codi.Domini.CtrlDomini;
import Codi.Domini.CtrlDominiExprBool;

public class CtrlPresentacio {

    private CtrlDominiExprBool controlDominiExprBool;
    private ViewGestioExprBool vistaGestioBool;

    public CtrlPresentacio() {
        controlDominiExprBool = new CtrlDominiExprBool();
        vistaGestioBool = new ViewGestioExprBool(this);
    }

    public void init() {

        //Aqui afegir metode per inicialitzar control domini
        vistaGestioBool.posar_visible();    //Aixo hauria de ser la vista principal de l'aplicacio
    }
}
