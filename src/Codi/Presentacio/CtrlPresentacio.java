package Codi.Presentacio;

import Codi.Domini.CtrlDomini;
import Codi.Util.TipusCerca;
import Codi.Util.TipusOrdenacio;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

public class CtrlPresentacio {
    private CtrlDomini ctrlDomini;
    private ViewMenuPrincipal viewMenuPrincipal;
    private ViewGestioExprBool viewGestioExprBool;

    private ArrayList<SimpleEntry<String, String>> resultatActual1;
    private TipusCerca ultimaCerca;

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

    //documents
    public void crearDocument () {

    }

    public void importarDocument () {

    }

    public void obrirAjuda () {

    }

    public void tancarAplicacio () {

    }

    public void modificarDocument () {

    }

    public void exportarDocument () {

    }

    public void esborrarDocument () {

    }

    public void ordenar (TipusOrdenacio tipusOrdenacio) {

    }

    public void guardarDocument () {

    }

    //cerques
    public boolean cercaBooleana (String expr) {
        try {
            resultatActual1 = ctrlDomini.cercaBooleana(expr, viewMenuPrincipal.getTipusOrdenacio());
            viewMenuPrincipal.actualitzarResultat(resultatActual1);
            ultimaCerca = TipusCerca.BOOLEANA;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean esborrarCerca () {
        ultimaCerca = TipusCerca.TOTS;
        return true;
    }

    public boolean cercaTitol () {
        ultimaCerca = TipusCerca.TITOL;
        return true;
    }

    public boolean cercaAutor () {
        ultimaCerca = TipusCerca.AUTOR;
        return true;
    }

    public boolean cercaTitolAutor () {
        ultimaCerca = TipusCerca.TITOLAUTOR;
        return true;
    }

    public boolean cercaPrefix () {
        ultimaCerca = TipusCerca.PREFIX;
        return true;
    }

    public boolean cercaParaules () {
        ultimaCerca = TipusCerca.PARAULES;
        return true;
    }
    public boolean cercaSemblant () {
        ultimaCerca = TipusCerca.SEMBLANT;
        return true;
    }
}

