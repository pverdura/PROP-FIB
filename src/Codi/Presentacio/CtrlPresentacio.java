package Codi.Presentacio;

import Codi.Domini.CtrlDomini;

public class CtrlPresentacio {

    private CtrlDomini controlDomini;
    private ViewMenuPrincipal  vistaMenuPrincipal;

    public CtrlPresentacio() {
        controlDomini = new CtrlDomini();
        vistaMenuPrincipal = new ViewMenuPrincipal(this);
    }

    public void init() {
        //Aqui afegir metode per inicialitzar control domini ???
        vistaMenuPrincipal.posar_visible();
    }
}
