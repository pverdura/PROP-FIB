package Codi.Presentacio;

import Codi.Domini.CtrlDomini;

public class CtrlPresentacio {

    private CtrlDomini controlDomini;
    private ViewMenuPrincipal  vistaMenuPrincipal;
    private ViewGestioExprBool vistaGestioExprBool;

    public CtrlPresentacio() {
        controlDomini = new CtrlDomini();
        vistaMenuPrincipal = new ViewMenuPrincipal(this);
        vistaGestioExprBool = new ViewGestioExprBool(this);
    }

    public void init() {
        //Aqui afegir metode per inicialitzar control domini ???
        vistaMenuPrincipal.ferVisible();
    }

    public void canviar_menuPrincipal_a_gestioExprBool() {
        vistaMenuPrincipal.desactivar();
        vistaGestioExprBool.ferVisible();
    }

    public void canviar_gestioExprBool_a_menuPrincipal() {
        vistaMenuPrincipal.activar();
        vistaGestioExprBool.ferInvisible();
    }
}
