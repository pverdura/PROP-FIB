package Codi.Presentacio;

import Codi.Domini.CtrlDomini;
import Codi.Util.TipusCerca;
import Codi.Util.TipusExtensio;
import Codi.Util.TipusOrdenacio;

import java.io.File;
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
    private TipusOrdenacio tipusOrdenacio;

    public CtrlPresentacio () {
        ctrlDomini = new CtrlDomini();

        resultatActual1 = new ArrayList<>();
        ultimaCerca = TipusCerca.TOTS;
        tipusOrdenacio = TipusOrdenacio.ALFABETIC_ASCENDENT;

        viewMenuPrincipal = new ViewMenuPrincipal(this);
    }

    public void init () {
        viewMenuPrincipal.ferVisible();
        prova();    //esborrar
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
        viewModificarDocument.ferVisible(true);
    }

    public boolean importarDocument (File fitxer) {
        return true;
    }

    public void tancarAplicacio () {
        if (viewCercaTitol != null) viewCercaTitol.tancarVista();
        if (viewCercaParaules != null) viewCercaParaules.tancarVista();
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

    public boolean guardarDocument (SimpleEntry<String, String> idVell, SimpleEntry<String, String> idNou, String contingut, TipusExtensio te) {
        //actualitzar id (si cal, canviant idVell i idNou (nou mètode: modificar identificador, que modifica títol i autor alhora?
        //actualitzar classe
        //guardar físicament
        //si hi ha cerca semblant o paraules, actualitzar-ne resultat, si s'ha modifiat el títol/autor, també potser
        return true;
    }

    //cerques
    public boolean cercaBooleana (String expr) {
        try {
            resultatActual1 = ctrlDomini.cercaBooleana(expr, tipusOrdenacio);
            viewMenuPrincipal.actualitzarResultat(resultatActual1);
            ultimaCerca = TipusCerca.BOOLEANA;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void mostrarDocuments () {
        try {
            resultatActual1 = ctrlDomini.cercaAllDocuments(tipusOrdenacio);
            viewMenuPrincipal.actualitzarResultat(resultatActual1);
            ultimaCerca = TipusCerca.TOTS;
        } catch (Exception e) {
            VistaDialeg.errorDialog("Hi ha algun error a l'aplicació");
        }
    }

    public boolean cercaTitol (String titol) {
        ultimaCerca = TipusCerca.TITOL;
        return true;
    }

    public boolean cercaAutor (String autor) {
        ultimaCerca = TipusCerca.AUTOR;
        return true;
    }

    public boolean cercaTitolAutor (String titol, String autor) {
        ultimaCerca = TipusCerca.TITOLAUTOR;
        return true;
    }

    public boolean cercaPrefix (String prefix) {
        ultimaCerca = TipusCerca.PREFIX;
        return true;
    }

    public boolean cercaParaules (String paraules, int k, boolean tots) {
        ultimaCerca = TipusCerca.PARAULES;
        return true;
    }
    public boolean cercaSemblant (String titol, String autor, int k, boolean tots) {
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
    public void modificarDocument (String titol, String autor) {
        if (viewModificarDocument == null)
            viewModificarDocument = new ViewModificarDocument(this);
        viewModificarDocument.setTitol("titol");
        viewModificarDocument.setAutor("autor");
        viewModificarDocument.setContingut("contingut1\ncontingut2\ncontingut3");
        viewModificarDocument.setExtensio(TipusExtensio.XML);
        viewModificarDocument.ferVisible(true);
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
        if (viewCercaTitolAutor == null) viewCercaTitolAutor = new ViewCercaTitolAutor(this);
        viewCercaTitolAutor.ferVisible(true);
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

    //ESBORRAR
    public void prova () {
        ArrayList<SimpleEntry<String, String>> a = new ArrayList<>();
        a.add(new SimpleEntry<>("titol", "autor"));
        viewMenuPrincipal.actualitzarResultat(a);
    }
}

