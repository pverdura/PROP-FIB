package Codi.Presentacio;

import Codi.Domini.CtrlDomini;
import Codi.Util.TipusCerca;
import Codi.Util.TipusExtensio;
import Codi.Util.TipusOrdenacio;

import java.io.File;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

public class CtrlPresentacio {
    private final CtrlDomini ctrlDomini;
    private ViewMenuPrincipal viewMenuPrincipal;
    private ViewGestioExprBool viewGestioExprBool;
    private ViewModificarDocument viewModificarDocument;
    private ViewCercaTitol viewCercaTitol;
    private ViewCercaAutor viewCercaAutor;
    private ViewCercaTitolAutor viewCercaTitolAutor;
    private ViewCercaPrefix viewCercaPrefix;
    private ViewCercaSemblant viewCercaSemblant;
    private ViewCercaParaules viewCercaParaules;
    private ViewAjuda viewAjuda;

    private ArrayList<SimpleEntry<String, String>> resultatPrincipal;
    private ArrayList<Integer> resultatPrincipalPes;
    private ArrayList<TipusExtensio> resultatPrincipalExtensio;
    private TipusCerca ultimaCerca;
    private TipusOrdenacio tipusOrdenacio;

    public CtrlPresentacio () {
        ctrlDomini = new CtrlDomini();

        resultatPrincipal = new ArrayList<>();
        resultatPrincipalExtensio = new ArrayList<>();
        resultatPrincipalPes = new ArrayList<>();

        ultimaCerca = TipusCerca.TOTS;
        tipusOrdenacio = TipusOrdenacio.ALFABETIC_ASCENDENT;

        viewMenuPrincipal = new ViewMenuPrincipal(this);
    }

    public void init () {
        viewMenuPrincipal.ferVisible();
        mostrarDocuments();
    }

    private void enviarPrincipal () {
        resultatPrincipalPes.clear();
        resultatPrincipalExtensio.clear();

        for (SimpleEntry<String, String> s : resultatPrincipal) {
            String t = s.getKey(); String a = s.getValue();
            resultatPrincipalPes.add(ctrlDomini.getPes(t, a));
            resultatPrincipalExtensio.add(ctrlDomini.getExtensio(t, a));
        }

        resultatPrincipal.add(new SimpleEntry<>("bon dia", "manel vilaro"));
        resultatPrincipalExtensio.add(TipusExtensio.TXT);
        resultatPrincipalPes.add(12);
        viewMenuPrincipal.actualitzarResultat(resultatPrincipal, resultatPrincipalPes, resultatPrincipalExtensio);
    }

    public void crearExprBool (String expr) {
        try {
            ctrlDomini.creaExpressioBool(expr);
        }  catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    public void eliminarExprBool (String expr) {
        try {
            ctrlDomini.eliminaExpressioBool(expr);
        } catch (Exception e) {

            VistaDialeg.errorDialog(e.toString());
        }
    }

    public void modificarExprBool (String exprAntiga, String exprNova) {
        try {
            ctrlDomini.modificaExpressioBool(exprAntiga, exprNova);
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    public ArrayList<String> getExpressionsBooleanes () {
        return ctrlDomini.cercaAllExpressionsBool(TipusOrdenacio.ALFABETIC_ASCENDENT);
    }

    //documents
    public void crearDocument () {
        if (viewModificarDocument == null) viewModificarDocument = new ViewModificarDocument(this);
        viewModificarDocument.setTitol("");
        viewModificarDocument.setAutor("");
        viewModificarDocument.setContingut("");
        viewModificarDocument.setExtensio(TipusExtensio.BOL);
        viewModificarDocument.ferVisible(true);
    }

    public void importarDocument (File[] fitxers) {
        try {
            ctrlDomini.importarDocuments(fitxers);
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    public void tancarAplicacio () {
        //if (viewCercaTitol != null) viewCercaTitol.tancarVista();
        //if (viewCercaParaules != null) viewCercaParaules.tancarVista();
        viewMenuPrincipal.tancarVista();
    }

    public void exportarDocuments (ArrayList<String> titols, ArrayList<String> autors, File path) {
        //exportar document id a path
        try {
            ctrlDomini.exportarDocuments(titols, autors, path);
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    public void esborrarDocument (String titol, String autor) {
        //esborrar document id
        try {
            ctrlDomini.eliminaDocument(titol, autor);
            //si hi és a la cerca, esborrar de la cerca, tornar a ordenar i actualitzar vista principal

            //si és cerca semblant/paraules, tornar-la a fer
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    public void ordenar (TipusOrdenacio to) {
        //ordenar i actualitzar vista principal
        if (to != tipusOrdenacio) {
            tipusOrdenacio = to;
            resultatPrincipal = ctrlDomini.ordenarCerca(resultatPrincipal, tipusOrdenacio);
            enviarPrincipal();
        }
    }

    public boolean guardarDocument (SimpleEntry<String, String> idVell, SimpleEntry<String, String> idNou, String contingut, TipusExtensio te) {
        //actualitzar id (si cal, canviant idVell i idNou (nou mètode: modificar identificador, que modifica títol i autor alhora?
        //actualitzar classe
        //guardar físicament
        //si hi ha cerca semblant o paraules, actualitzar-ne resultat, si s'ha modifiat el títol/autor, també potser
        return true;
    }

    //cerques
    public void cercaBooleana (String expr) {
        try {
            resultatPrincipal = ctrlDomini.cercaBooleana(expr, tipusOrdenacio);
            enviarPrincipal();
            ultimaCerca = TipusCerca.BOOLEANA;
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    public void mostrarDocuments () {
        try {
            resultatPrincipal = ctrlDomini.cercaAllDocuments(tipusOrdenacio);
            enviarPrincipal();
            ultimaCerca = TipusCerca.TOTS;
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
            e.printStackTrace();
        }
    }

    public void cercaTitol (String titol) {
        try {
            resultatPrincipal = ctrlDomini.cercaTitol(titol, tipusOrdenacio);
            enviarPrincipal();
            ultimaCerca = TipusCerca.TITOL;
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    public void cercaAutor (String autor) {
        try {
            resultatPrincipal = ctrlDomini.cercaAutor(autor, tipusOrdenacio);
            enviarPrincipal();
            ultimaCerca = TipusCerca.AUTOR;
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    public void cercaTitolAutor (String titol, String autor) {
        try {
            resultatPrincipal.clear();
            resultatPrincipal.add(new SimpleEntry<>(titol, autor));
            enviarPrincipal();
            ultimaCerca = TipusCerca.TITOLAUTOR;
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    public void cercaPrefix (String prefix, TipusOrdenacio to) {
        try {
            viewCercaPrefix.enviarDades(ctrlDomini.cercaPrefix(prefix, to));
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    public void cercaParaules (String paraules, int k, boolean tots) {
        try {
            if (tots) k = ctrlDomini.getNombreDocuments();
            resultatPrincipal = ctrlDomini.cercaParaules(paraules, k);
            enviarPrincipal();
            ultimaCerca = TipusCerca.PARAULES;
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }
    public void cercaSemblant (String titol, String autor, int k, boolean tots) {
        try {
            if (tots) k = ctrlDomini.getNombreDocuments();
            resultatPrincipal = ctrlDomini.cercaSemblant(titol, autor, k);
            enviarPrincipal();
            ultimaCerca = TipusCerca.SEMBLANT;
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    //obrir vistes
    public void obrirAjuda () {
        if (viewAjuda == null) viewAjuda = new ViewAjuda();
        viewAjuda.ferVisible();
    }
    public void obrirGestioExprBool () {
        if (viewGestioExprBool == null)
            viewGestioExprBool = new ViewGestioExprBool(this);
        viewGestioExprBool.ferVisible();
    }
    public void modificarDocument (String titol, String autor) {
        if (viewModificarDocument == null)
            viewModificarDocument = new ViewModificarDocument(this);
        viewModificarDocument.setTitol(titol);
        viewModificarDocument.setAutor(autor);
        viewModificarDocument.setContingut(ctrlDomini.getContingut(titol, autor));
        viewModificarDocument.setExtensio(ctrlDomini.getExtensio(titol, autor));
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
        if (viewCercaPrefix == null) viewCercaPrefix = new ViewCercaPrefix(this);
        viewCercaPrefix.ferVisible(true);
    }
    public void obrirCercaSemblant () {
        if (viewCercaSemblant == null) viewCercaSemblant = new ViewCercaSemblant(this);
        viewCercaSemblant.ferVisible(true);
    }
    public void obrirCercaParaules () {
        if (viewCercaParaules == null) viewCercaParaules = new ViewCercaParaules(this);
        viewCercaParaules.ferVisible(true);
    }
}

