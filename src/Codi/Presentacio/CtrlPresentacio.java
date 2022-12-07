package Codi.Presentacio;

import Codi.Domini.CtrlDomini;
import Codi.Excepcions.ExpressioBooleanaInexistentException;
import Codi.Excepcions.ExpressioBooleanaJaExistentException;
import Codi.Util.TipusOrdenacio;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

public class CtrlPresentacio {
    private CtrlDomini ctrlDomini;
    private ViewMenuPrincipal viewMenuPrincipal;
    private ViewGestioExprBool viewGestioExprBool;

    private ArrayList<SimpleEntry<String, String>> resultatActual1;

    public CtrlPresentacio () {
        ctrlDomini = new CtrlDomini();

        resultatActual1 = new ArrayList<>();

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
        }  catch (Exception e) {
            return false;
        }
    }

    public boolean eliminarExprBool (String expr) {
        try {
            ctrlDomini.eliminaExpressioBool(expr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificarExprBool (String exprAntiga, String exprNova) {
        try {
            ctrlDomini.modificaExpressioBool(exprAntiga, exprNova);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<String> getExpressionsBooleanes () {
        return ctrlDomini.cercaAllExpressionsBool(TipusOrdenacio.ALFABETIC_ASCENDENT);
    }

    public boolean cercaBooleana (String expr) {
        try {
            resultatActual1 = ctrlDomini.cercaBooleana(expr, viewMenuPrincipal.getTipusOrdenacio());
            viewMenuPrincipal.actualitzarResultat(resultatActual1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean cercaTitol () {
        return true;
    }

    public boolean cercaAutor () {
        return true;
    }

    public boolean cercaTitolAutor () {
        return true;
    }

    public boolean cercaPrefix () {
        return true;
    }

    public boolean cercaParaules () {
        return true;
    }
    public boolean cercaSemblant () {
        return true;
    }
}

