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
    private ViewModificarDocument viewModificarDocument;
    private ViewCercaTitol viewCercaTitol;
    private ViewCercaAutor viewCercaAutor;
    private ViewCercaTitolAutor viewCercaTitolAutor;
    private ViewCercaPrefix viewCercaPrefix;
    private ViewCercaSemblant viewCercaSemblant;
    private ViewCercaParaules viewCercaParaules;

    private ArrayList<SimpleEntry<String, String>> resultatActual1;
    private TipusCerca ultimaCerca;

    public CtrlPresentacio () {
        ctrlDomini = new CtrlDomini();

        resultatActual1 = new ArrayList<>();

        viewMenuPrincipal = new ViewMenuPrincipal(this);
    }

    public void init () {
        viewMenuPrincipal.ferVisible();
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
        viewModificarDocument.ferVisible();
    }

    public void importarDocument () {

    }

    public void tancarAplicacio () {
        if (viewCercaTitol != null) viewCercaTitol.tancarVista();

    }

    public void modificarDocument (SimpleEntry<String, String> id) {
        //obrir vista modificar document amb les dades del document id
    }

    public void exportarDocument (SimpleEntry<String, String> id, String path) {
        //exportar document id a path
    }

    public void esborrarDocument (SimpleEntry<String, String> id) {
        //esborrar document id
        //si hi és a la cerca, esborrar de la cerca, tornar a ordenar i actualitzar vista principal
        //si és cerca semblant/paraules, tornar-la a fer
    }

    public void ordenar (TipusOrdenacio tipusOrdenacio) {
        //ordenar i actualitzar vista principal
    }

    public void guardarDocument (SimpleEntry<String, String> idVell, SimpleEntry<String, String> idNou) {
        //actualitzar id (si cal, canviant idVell i idNou (nou mètode: modificar identificador, que modifica títol i autor alhora?
        //actualitzar classe
        //guardar físicament
        //si hi ha cerca semblant o paraules, actualitzar-ne resultat, si s'ha modifiat el títol/autor, també potser
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

    public boolean cercaTitol (String text) {
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

    //obrir vistes
    public void obrirAjuda () {
        //obrir vista ajuda
    }
    public void obrirGestioExprBool () {
        if (viewGestioExprBool == null)
            viewGestioExprBool = new ViewGestioExprBool(this);
        viewGestioExprBool.ferVisible();
    }
    public void obrirViewModificarDocument () {
        if (viewModificarDocument == null)
            viewModificarDocument = new ViewModificarDocument(this);
        viewModificarDocument.ferVisible();
    }
    public void obrirCercaTitol () {
        if (viewCercaTitol == null) viewCercaTitol = new ViewCercaTitol(this);
        viewCercaTitol.ferVisible(true);
    }
    public void obrirCercaAutor () {
        if (viewCercaAutor == null) viewCercaAutor = new ViewCercaAutor(this);
        viewCercaAutor.ferVisible(true);
    }
    public void obrirCercaTitolAutor () {
        //if (viewCercaTitolAutor == null) viewCercaTitolAutor = new ViewCercaTitolAutor(this);
        //viewCercaTitolAutor.ferVisible(true);
    }
    public void obrirCercaPrefix () {
        //if (viewCercaPrefix == null) viewCercaPrefix = new ViewCercaPrefix(this);
        //viewCercaPrefix.ferVisible(true);
    }
    public void obrirCercaSemblant () {
        //if (viewCercaSemblant == null) viewCercaSemblant = new ViewCercaSemblant(this);
        //viewCercaSemblant.ferVisible(true);
    }
    public void obrirCercaParaules () {
        if (viewCercaParaules == null) viewCercaParaules = new ViewCercaParaules(this);
        viewCercaParaules.ferVisible(true);
    }
}

