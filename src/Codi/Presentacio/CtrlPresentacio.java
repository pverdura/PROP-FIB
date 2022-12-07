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

    public void obrirGestioExprBool () {
        viewGestioExprBool.ferVisible();
    }
    public void tancarGestioExprBool () {
        viewGestioExprBool.ferInvisible();
    }

    public void afegirExprBool () {

    }

    public void eliminarExprBool () {

    }

    public void modificarExprBool () {

    }

    public void cercaBooleana () {

    }
}

