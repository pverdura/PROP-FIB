package Codi.Presentacio;

import Codi.Domini.CtrlDomini;

public class CtrlPresentacio {
    private CtrlDomini controlDomini;
    private ViewMenuPrincipal viewMenuPrincipal;
    private ViewGestioExprBool viewGestioExprBool;

    public CtrlPresentacio () {
        controlDomini = new CtrlDomini();
        viewMenuPrincipal = new ViewMenuPrincipal(this);
        viewGestioExprBool = new ViewGestioExprBool(this);
    }

    public void init () {
        viewMenuPrincipal.ferVisible();
    }

    public void canviar_menuPrincipal_a_gestioExprBool () {
        viewMenuPrincipal.desactivar();
        viewGestioExprBool.ferVisible();
    }

    public void canviar_gestioExprBool_a_menuPrincipal() {
        viewMenuPrincipal.activar();
        viewGestioExprBool.ferInvisible();
    }
}

