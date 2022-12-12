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
    private final ViewMenuPrincipal viewMenuPrincipal;
    private ViewGestioExprBool viewGestioExprBool;
    private ViewCercaTitol viewCercaTitol;
    private ViewCercaAutor viewCercaAutor;
    private ViewCercaTitolAutor viewCercaTitolAutor;
    private ViewCercaPrefix viewCercaPrefix;
    private ViewCercaSemblant viewCercaSemblant;
    private ViewCercaParaules viewCercaParaules;
    private ViewAjuda viewAjuda;

    private ArrayList<SimpleEntry<String, String>> resultatPrincipal;
    private final ArrayList<Integer> resultatPrincipalPes;
    private final ArrayList<TipusExtensio> resultatPrincipalExtensio;
    private ArrayList<String> resultatPrefix;
    private TipusCerca ultimaCerca;
    private TipusOrdenacio tipusOrdenacio;
    private int nDocumentsOberts;
    private String auxTitol;
    private String auxAutor;
    private String auxPrefix;
    private String auxExpr;
    private String auxParaules;
    private int k;
    private TipusOrdenacio tipusOrdenacioPrefix;

    public CtrlPresentacio () {
        ctrlDomini = new CtrlDomini();

        resultatPrincipal = new ArrayList<>();
        resultatPrincipalExtensio = new ArrayList<>();
        resultatPrincipalPes = new ArrayList<>();
        resultatPrefix = new ArrayList<>();

        ultimaCerca = TipusCerca.TOTS;
        tipusOrdenacio = TipusOrdenacio.ALFABETIC_ASCENDENT;

        viewMenuPrincipal = new ViewMenuPrincipal(this);
    }

    public void init () {
        try {
            ctrlDomini.inicialitza();
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
            e.printStackTrace();
        }

        viewMenuPrincipal.ferVisible();
        mostrarDocuments();
        nDocumentsOberts = 0;
    }

    private void enviarPrincipal () {
        resultatPrincipalPes.clear();
        resultatPrincipalExtensio.clear();

        for (SimpleEntry<String, String> s : resultatPrincipal) {
            String t = s.getKey(); String a = s.getValue();
            resultatPrincipalPes.add(ctrlDomini.getPes(t, a));
            resultatPrincipalExtensio.add(ctrlDomini.getExtensio(t, a));
        }

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
        if (obrirDocument()) {
            ViewModificarDocument v = new ViewModificarDocument(this);
            v.ferVisible(true);
        }
    }

    public void importarDocuments (ArrayList<File> fitxers) {
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

    public void exportarDocument (String titol, String autor, File path) {
        //exportar document id a path
        try {
            ctrlDomini.exportarDocument(titol, autor, path);
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

    public void guardarDocument (boolean nou, SimpleEntry<String, String> idVell, SimpleEntry<String, String> idNou, String contingut, TipusExtensio te) {
        try {
            if (nou) {
                ctrlDomini.creaDocument(idNou.getKey(), idNou.getValue());
            } else {
                if (!idVell.equals(idNou))
                    ctrlDomini.modificarIdentificador(idVell, idNou);
            }
            ctrlDomini.setContingut(idNou.getKey(), idNou.getValue(), contingut);
            ctrlDomini.setExtensio(idNou.getKey(), idNou.getValue(), te);
            ctrlDomini.guardaDocument(idNou.getKey(), idNou.getValue());
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
            e.printStackTrace();
        }
    }

    //cerques
    public void cercaBooleana (String expr) {
        try {
            resultatPrincipal = ctrlDomini.cercaBooleana(expr, tipusOrdenacio);
            enviarPrincipal();
            ultimaCerca = TipusCerca.BOOLEANA;
            this.auxExpr = expr;
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
            this.auxTitol = titol;
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    public void cercaAutor (String autor) {
        try {
            resultatPrincipal = ctrlDomini.cercaAutor(autor, tipusOrdenacio);
            enviarPrincipal();
            ultimaCerca = TipusCerca.AUTOR;
            this.auxAutor = autor;
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
            this.auxTitol = titol;
            this.auxAutor = autor;
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    public void cercaPrefix (String prefix, TipusOrdenacio to, boolean documentsModificats) {
        try {
            if (documentsModificats || !this.auxPrefix.equals(prefix)) {
                resultatPrefix = ctrlDomini.cercaPrefix(prefix, to);
                viewCercaPrefix.enviarDades(resultatPrefix);
                this.auxPrefix = prefix;
                this.tipusOrdenacioPrefix = to;
            } else if (this.tipusOrdenacioPrefix != to) {
                this.tipusOrdenacioPrefix = to;
                viewCercaPrefix.enviarDades(ctrlDomini.ordenaCercaPrefix(resultatPrefix, to));
            }
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
            this.auxParaules = paraules;.
            this.k = k;
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
            this.k = k;
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
        if (obrirDocument()) {
            ViewModificarDocument v = new ViewModificarDocument(this, titol, autor, ctrlDomini.getContingut(titol, autor), ctrlDomini.getExtensio(titol, autor));
            v.ferVisible(true);
        }
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
    private boolean obrirDocument () {
        if (nDocumentsOberts < 20) {
            nDocumentsOberts += 1;
            return true;
        } else {
            VistaDialeg.messageDialog("Massa documents oberts", "Tens massa documents oberts. Tanca'n algun abans de continuar");
            return false;
        }
    }
    public void tancarDocument () {
        nDocumentsOberts -= 1;
    }
    private void actualitzarCerca () {
        switch (ultimaCerca) {
            case TOTS:
                this.mostrarDocuments();
                break;
            case TITOL:
                this.cercaTitol(auxTitol);
                break;
            case AUTOR:
                this.cercaAutor(auxAutor);
                break;
            case TITOLAUTOR:
                this.cercaTitolAutor(auxTitol, auxAutor);
                break;
            case PARAULES:
                this.cercaParaules(auxParaules, k, false);
                break;
            case SEMBLANT:
                this.cercaSemblant(auxTitol, auxAutor, k, false);
                break;
            case BOOLEANA:
                this.cercaBooleana(auxExpr);
                break;
        }

        //actualitzar prefix si la pantalla és oberta
        if (viewCercaPrefix != null && viewCercaPrefix.esVisible()) {
            this.cercaPrefix(auxPrefix, tipusOrdenacioPrefix, true);
        }
    }
}

