package Codi.Presentacio;

import Codi.Domini.CtrlDomini;
import Codi.Excepcions.ExpressioBooleanaInexistentException;
import Codi.Excepcions.ExpressioBooleanaJaExistentException;

public class CtrlPresentacio {
    private CtrlDomini ctrlDomini;
    private ViewMenuPrincipal viewMenuPrincipal;
    private ViewGestioExprBool viewGestioExprBool;

    public CtrlPresentacio () {
        ctrlDomini = new CtrlDomini();
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

    public boolean crearExprBool (String expr) {
        try {
            ctrlDomini.creaExpressioBool(expr);
            return true;
        } catch (ExpressioBooleanaJaExistentException e) {
            return false;
        } catch (Exception e) {
            viewGestioExprBool.error(e);
            return false;
        }
    }

    public boolean eliminarExprBool (String expr) {
        try {
            ctrlDomini.eliminaExpressioBool(expr);
            return true;
        } catch (ExpressioBooleanaInexistentException e) {
            return false;
        } catch (Exception e) {
            viewGestioExprBool.error(e);
            return false;
        }
    }

    public boolean modificarExprBool (String exprAntiga, String exprNova) {
        try {
            ctrlDomini.modificaExpressioBool(exprAntiga, exprNova);
            return true;
        } catch (ExpressioBooleanaInexistentException e) {
            return false;
        } catch (Exception e) {
            viewGestioExprBool.error(e);
            return false;
        }
    }

    public boolean cercaBooleana () {
        return true;
    }
}

