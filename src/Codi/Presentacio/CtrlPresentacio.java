package Codi.Presentacio;

import Codi.Domini.CtrlDomini;
import Codi.Util.TipusCerca;
import Codi.Util.TipusExtensio;
import Codi.Util.TipusOrdenacio;

import java.io.File;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

public class CtrlPresentacio {
    private final CtrlDomini ctrlDomini;
    private final ViewMenuPrincipal viewMenuPrincipal;
    private final HashMap<SimpleEntry<String, String>, ViewEditorDocument> editors;
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
    private int idDocumentEditat;
    private String auxTitol;
    private String auxAutor;
    private String auxPrefix;
    private String auxExpr;
    private String auxParaules;
    private int k;
    private TipusOrdenacio tipusOrdenacioPrefix;
    String informacio;

    /**
     * Constructor
     */
    public CtrlPresentacio () {
        ctrlDomini = new CtrlDomini();

        resultatPrincipal = new ArrayList<>();
        resultatPrincipalExtensio = new ArrayList<>();
        resultatPrincipalPes = new ArrayList<>();
        resultatPrefix = new ArrayList<>();
        editors = new HashMap<>();

        ultimaCerca = TipusCerca.TOTS;
        tipusOrdenacio = TipusOrdenacio.ALFABETIC_ASCENDENT;

        viewMenuPrincipal = new ViewMenuPrincipal(this);
    }

    /**
     * Inicialitza la capa de domini i les estructures de dades, i carrega la vista del menú principal
     */
    public void init () {
        try {
            ctrlDomini.inicialitza();
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }

        viewMenuPrincipal.ferVisible();
        mostrarDocuments();
        idDocumentEditat = 0;
    }

    /**
     * Tanca l'aplicació
     */
    public void tancarAplicacio () {
        viewMenuPrincipal.tancarVista();
    }

    ///////////////////////////////////////////////////////////
    ///          MÈTODES D'EXPRESSIONS BOOLEANES            ///
    ///////////////////////////////////////////////////////////

    /**
     * Crea una expressió booleana
     *
     * @param expr Expressió booleana a crear
     */
    public void crearExprBool (String expr) {
        try {
            ctrlDomini.creaExpressioBool(expr);
        }  catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Modifica una expressió booleana
     *
     * @param exprAntiga Expressió booleana original
     * @param exprNova Expressió booleana modificada
     */
    public void modificarExprBool (String exprAntiga, String exprNova) {
        try {
            ctrlDomini.modificaExpressioBool(exprAntiga, exprNova);
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Elimina una expressió booleana
     *
     * @param expr Expressió booleana a eliminar
     */
    public void eliminarExprBool (String expr) {
        try {
            ctrlDomini.eliminaExpressioBool(expr);
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Obté totes les expressions booleanes existents en forma d'{@code String}
     *
     * @return {@code ArrayList<String>} de totes les expressions booleanes
     */
    public ArrayList<String> getExpressionsBooleanes () {
        return ctrlDomini.cercaAllExpressionsBool(TipusOrdenacio.ALFABETIC_ASCENDENT);
    }

    ///////////////////////////////////////////////////////////
    ///                MÈTODES DE DOCUMENTS                 ///
    ///////////////////////////////////////////////////////////

    /**
     * Obre la vista editor de documents per editar un document nou
     */
    public void crearDocument () {
        if (obrirDocument()) {
            ViewEditorDocument v = new ViewEditorDocument(this, idDocumentEditat);
            v.ferVisible(true);

            editors.put(new SimpleEntry<>(Integer.toString(idDocumentEditat), Integer.toString(idDocumentEditat)), v);
            ++idDocumentEditat;
        }
    }

    /**
     * Importa una sèrie de documents
     *
     * @param fitxers {@code ArrayList<File>} dels fitxers que s'han d'importar
     */
    public void importarDocuments (ArrayList<File> fitxers) {
        try {
            ctrlDomini.importarDocuments(fitxers);
            this.actualitzarCerca();
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Exporta un document
     *
     * @param titol Títol del document
     * @param autor Autor del document
     * @param path Destí del document
     */
    public void exportarDocument (String titol, String autor, File path) {
        try {
            ctrlDomini.exportarDocument(titol, autor, path);
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Esborra un document
     *
     * @param titol Títol del document
     * @param autor Autor del document
     */
    public void esborrarDocument (String titol, String autor) {
        try {
            SimpleEntry<String, String> id = new SimpleEntry<>(titol, autor);
            ctrlDomini.eliminaDocument(titol, autor);
            if (editors.containsKey(id)) {
                ViewEditorDocument aux = editors.get(id);
                aux.documentEliminat(idDocumentEditat);
                editors.remove(id);
                editors.put(new SimpleEntry<>(Integer.toString(idDocumentEditat), Integer.toString(idDocumentEditat)), aux);
                ++idDocumentEditat;
            }
            this.actualitzarCerca();
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Guarda un document
     *
     * @param nou {@code true} si el document és nou
     * @param idVell Identificador antic del document
     * @param idNou Iidentificador nou del document
     * @param contingut Contingut nou del document
     * @param te Tipus d'extensió nova del document
     */
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
            this.actualitzarCerca();

            if (editors.containsKey(idVell))    {
                ViewEditorDocument aux = editors.get(idVell);
                editors.remove(idVell);
                editors.put(idNou, aux);
            }
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    ///////////////////////////////////////////////////////////
    ///                 MÈTODES DE CERCA                    ///
    ///////////////////////////////////////////////////////////

    /**
     * Mostra tots els documents existents al menú principal
     */
    public void mostrarDocuments () {
        try {
            resultatPrincipal = ctrlDomini.cercaAllDocuments(tipusOrdenacio);
            enviarPrincipal();
            ultimaCerca = TipusCerca.TOTS;
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Mostra el resultat de la cerca per títol al menú principal
     *
     * @param titol Títol a cercar
     */
    public void cercaTitol (String titol) {
        try {
            resultatPrincipal = ctrlDomini.cercaTitol(titol, tipusOrdenacio);
            enviarPrincipal();
            ultimaCerca = TipusCerca.TITOL;
            this.auxTitol = titol;
            informacio = "Documents de títol: "+this.auxTitol;
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Mostra el resultat de la cerca per autor al menú principal
     *
     * @param autor Autor a cercar
     */
    public void cercaAutor (String autor) {
        try {
            resultatPrincipal = ctrlDomini.cercaAutor(autor, tipusOrdenacio);
            enviarPrincipal();
            ultimaCerca = TipusCerca.AUTOR;
            this.auxAutor = autor;
            informacio = "Documents creats per l'autor: "+this.auxAutor;
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Mostra el resultat de la cerca per títol i autor al menú principal
     *
     * @param titol Títol del document a cercar
     * @param autor Autor del document a cercar
     */
    public void cercaTitolAutor (String titol, String autor) {
        try {
            resultatPrincipal.clear();
            resultatPrincipal.add(new SimpleEntry<>(titol, autor));
            enviarPrincipal();
            ultimaCerca = TipusCerca.TITOLAUTOR;
            this.auxTitol = titol;
            this.auxAutor = autor;
            informacio = "Document de títol: "+this.auxTitol+" i autor: "+this.auxAutor;
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Mostra el resultat de la cerca per prefix a la vista de cerca prefix
     *
     * @param prefix Prefix a cercar
     * @param to Tipus d'ordenació del resultat
     * @param documentsModificats {@code true} si s'han modificat els documents de l'aplicació
     */
    public void cercaPrefix (String prefix, TipusOrdenacio to, boolean documentsModificats) {
        try {
            if (documentsModificats || auxPrefix == null || !this.auxPrefix.equals(prefix)) {
                resultatPrefix = ctrlDomini.cercaPrefix(prefix, to);
                viewCercaPrefix.enviarDades(resultatPrefix);
                this.auxPrefix = prefix;
                this.tipusOrdenacioPrefix = to;
            } else if (this.tipusOrdenacioPrefix != to) {
                viewCercaPrefix.enviarDades(ctrlDomini.ordenarCercaSimple(resultatPrefix, to));
                this.tipusOrdenacioPrefix = to;
            }
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Mostra el resultat de la cerca per paraules rellevants del contingut dels documents pel menú principal
     *
     * @param paraules Paraules a cercar
     * @param k Nombre de documents a mostrar
     * @param tots {@code true} si s'han de mostrar tots els documents
     */
    public void cercaParaules (String paraules, int k, boolean tots) {
        try {
            if (tots) k = ctrlDomini.getNombreDocuments();
            resultatPrincipal = ctrlDomini.cercaParaules(paraules, k);
            enviarPrincipal();
            ultimaCerca = TipusCerca.PARAULES;
            this.auxParaules = paraules;
            this.k = k;
            informacio = "Els "+k+" documents més rellevants per les paraules "+this.auxParaules;
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Mostra el resultat de la cerca per documents semblants pel menú principal
     *
     * @param titol Títol del document a comparar
     * @param autor Autor del document a comparar
     * @param k Nombre de documents a mostrar
     * @param tots {@code true} si s'han de mostrar tots els documents
     */
    public void cercaSemblant (String titol, String autor, int k, boolean tots) {
        try {
            if (tots) k = ctrlDomini.getNombreDocuments();
            resultatPrincipal = ctrlDomini.cercaSemblant(titol, autor, k);
            enviarPrincipal();
            ultimaCerca = TipusCerca.SEMBLANT;
            this.auxTitol = titol;
            this.auxAutor = autor;
            this.k = k;
            informacio = "Els "+k+" documents més semblants al document de títol: "+this.auxTitol+" i autor: "+this.auxAutor;
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Mostra el resultat de la cerca booleana pel menú principal
     *
     * @param expr Expressió booleana
     */
    public void cercaBooleana (String expr) {
        try {
            resultatPrincipal = ctrlDomini.cercaBooleana(expr, tipusOrdenacio);
            enviarPrincipal();
            ultimaCerca = TipusCerca.BOOLEANA;
            this.auxExpr = expr;
            informacio = "Els documents que compleixen l'expressió "+this.auxExpr;
        } catch (Exception e) {
            VistaDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Ordena el resultat que es mostra pel menú principal
     *
     * @param to Tipus d'ordenació
     */
    public void ordenar (TipusOrdenacio to) {
        if (to != tipusOrdenacio) {
            tipusOrdenacio = to;
            resultatPrincipal = ctrlDomini.ordenarCerca(resultatPrincipal, tipusOrdenacio);
            enviarPrincipal();
        }
    }

    ///////////////////////////////////////////////////////////
    ///             MÈTODES PER OBRIR VISTES                ///
    ///////////////////////////////////////////////////////////

    /**
     * Obre la vista de modificar un document
     *
     * @param titol Títol del document que es modifica
     * @param autor Autor del document que es modifica
     */
    public void modificarDocument (String titol, String autor) {
        if (obrirDocument()) {
            ViewEditorDocument v = new ViewEditorDocument(this, titol, autor, ctrlDomini.getContingut(titol, autor), ctrlDomini.getExtensio(titol, autor));
            v.ferVisible(true);
        }
    }

    /**
     * Obre la vista de cerca per títol
     */
    public void obrirCercaTitol () {
        if (viewCercaTitol == null) viewCercaTitol = new ViewCercaTitol(this);
        viewCercaTitol.ferVisible(true);
    }

    /**
     * Obre la vista de cerca per autor
     */
    public void obrirCercaAutor () {
        if (viewCercaAutor == null) viewCercaAutor = new ViewCercaAutor(this);
        viewCercaAutor.ferVisible(true);
    }

    /**
     * Obre la vista de cerca per títol i autor
     */
    public void obrirCercaTitolAutor () {
        if (viewCercaTitolAutor == null) viewCercaTitolAutor = new ViewCercaTitolAutor(this);
        viewCercaTitolAutor.ferVisible(true);
    }

    /**
     * Obre la vista de cerca per prefix
     */
    public void obrirCercaPrefix () {
        if (viewCercaPrefix == null) viewCercaPrefix = new ViewCercaPrefix(this);
        viewCercaPrefix.ferVisible(true);
    }

    /**
     * Obre la vista de cerca per document semblant
     */
    public void obrirCercaSemblant () {
        if (viewCercaSemblant == null) viewCercaSemblant = new ViewCercaSemblant(this);
        viewCercaSemblant.ferVisible(true);
    }

    /**
     * Obre la vista de cerca per paraules rellevants
     */
    public void obrirCercaParaules () {
        if (viewCercaParaules == null) viewCercaParaules = new ViewCercaParaules(this);
        viewCercaParaules.ferVisible(true);
    }

    /**
     * Obre la vista de gestió d'expressions booleanes
     */
    public void obrirGestioExprBool () {
        if (viewGestioExprBool == null)
            viewGestioExprBool = new ViewGestioExprBool(this);
        viewGestioExprBool.ferVisible();
    }

    /**
     * Obre la vista d'ajuda
     */
    public void obrirAjuda () {
        if (viewAjuda == null) viewAjuda = new ViewAjuda();
        viewAjuda.ferVisible();
    }

    /**
     * Una vista d'editor documents ha estat tancada
     */
    public void tancarDocument (String titol, String autor) {
        editors.remove(new SimpleEntry<>(titol, autor));

    }

    ///////////////////////////////////////////////////////////
    ///                  MÈTODES PRIVATS                    ///
    ///////////////////////////////////////////////////////////

    /**
     * Envia els resultats al menú principal
     */
    private void enviarPrincipal () {
        resultatPrincipalPes.clear();
        resultatPrincipalExtensio.clear();

        for (SimpleEntry<String, String> s : resultatPrincipal) {
            String t = s.getKey(); String a = s.getValue();
            resultatPrincipalPes.add(ctrlDomini.getPes(t, a));
            resultatPrincipalExtensio.add(ctrlDomini.getExtensio(t, a));
        }

        viewMenuPrincipal.actualitzarResultat(resultatPrincipal, resultatPrincipalPes, resultatPrincipalExtensio, informacio);
    }

    /**
     * Obre un document
     *
     * @return {@code true} si hi ha menys de 20 documents oberts
     */
    private boolean obrirDocument () {
        if (editors.size() < 20) return true;
        else {
            VistaDialeg.messageDialog("Massa documents oberts", "Tens massa documents oberts. Tanca'n algun abans de continuar");
            return false;
        }
    }

    /**
     * Actualitza el resultat del menú principal després de crear, modificar, importar o eliminar un document
     */
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
        if (viewCercaPrefix != null && viewCercaPrefix.esVisible() && auxPrefix == null) {
            this.cercaPrefix(auxPrefix, tipusOrdenacioPrefix, true);
        }
    }

    //ESBORRAR
    private void print (String s) {System.out.println(s);}
}

