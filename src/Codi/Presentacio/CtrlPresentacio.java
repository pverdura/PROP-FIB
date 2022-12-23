package Codi.Presentacio;

import Codi.Domini.CtrlDomini;
import Codi.Util.TipusCerca;
import Codi.Util.TipusExtensio;
import Codi.Util.TipusOrdenacio;

import java.io.File;
import java.security.KeyStore;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *  Classe que controla la capa de presentacio
 *
 *  @author Jordi Palomera
 *  @since 13-12-2022
 */
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
    private TipusOrdenacio tipusOrdenacioPrefix;
    private int idDocumentEditat;
    private String auxTitol;
    private String auxAutor;
    private String auxPrefix;
    private String auxExpr;
    private String auxParaules;
    private int k;
    private String informacio;

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
     * Inicialitza la capa de domini i les estructures de dades, i carrega la vista del menu principal
     */
    public void init () {
        try {
            ctrlDomini.inicialitza();
        } catch (Exception e) {
            ViewDialeg.errorDialog(e.toString());
        }

        viewMenuPrincipal.ferVisible();
        mostrarDocuments();
        idDocumentEditat = 0;
    }

    /**
     * Tanca l'aplicacio
     */
    public void tancarAplicacio () {
        viewMenuPrincipal.tancarVista();
        System.exit(0);
    }

    ///////////////////////////////////////////////////////////
    ///          MÈTODES D'EXPRESSIONS BOOLEANES            ///
    ///////////////////////////////////////////////////////////

    /**
     * Crea una expressio booleana
     *
     * @param expr Expressio booleana a crear
     */
    public void crearExprBool (String expr) {
        try {
            ctrlDomini.creaExpressioBool(expr);
        }  catch (Exception e) {
            ViewDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Modifica una expressio booleana
     *
     * @param exprAntiga Expressio booleana original
     * @param exprNova Expressio booleana modificada
     */
    public void modificarExprBool (String exprAntiga, String exprNova) {
        try {
            ctrlDomini.modificaExpressioBool(exprAntiga, exprNova);
        } catch (Exception e) {
            ViewDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Elimina una expressio booleana
     *
     * @param expr Expressio booleana a eliminar
     */
    public void eliminarExprBool (String expr) {
        try {
            ctrlDomini.eliminaExpressioBool(expr);
        } catch (Exception e) {
            ViewDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Obte totes les expressions booleanes existents en forma d'{@code String}
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
     * Obre l'editor de documents, sigui per un document nou o per modificar-ne un
     *
     * @param titol Titol del document. {@code null} si es un document nou
     * @param autor Autor del document. {@code null} si es un document nou
     * @param crear {@code true} si el document es nou
     */
    public void obrirEditorDocuments (String titol, String autor, boolean crear) {
        if (crear) {
            titol = Integer.toString(idDocumentEditat);
            autor = titol;
        }
        if (obrirDocument(titol, autor)) {
            ViewEditorDocument v;

            if (crear) {
                v = new ViewEditorDocument(this, idDocumentEditat);
                ++idDocumentEditat;
            }
            else v = new ViewEditorDocument(this, titol, autor, ctrlDomini.getContingut(titol, autor), ctrlDomini.getExtensio(titol, autor));
            v.ferVisible(true);

            editors.put(new SimpleEntry<>(titol, autor), v);
        }
    }

    /**
     * Importa una serie de documents
     *
     * @param fitxers {@code ArrayList<File>} dels fitxers que s'han d'importar
     */
    public void importarDocuments (ArrayList<File> fitxers) {
        try {
            ctrlDomini.importarDocuments(fitxers);
            this.actualitzarCerca();
        } catch (Exception e) {
            ViewDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Exporta un document
     *
     * @param titol Titol del document
     * @param autor Autor del document
     * @param path Desti del document
     */
    public void exportarDocument (String titol, String autor, File path) {
        try {
            ctrlDomini.exportarDocument(titol, autor, path);
        } catch (Exception e) {
            ViewDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Esborra un document
     *
     * @param titol Titol del document
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
            ViewDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Guarda un document
     *
     * @param nou {@code true} si el document es nou
     * @param modificat {@code true} si el document s'ha modificat
     * @param idVell Identificador antic del document
     * @param idNou Identificador nou del document
     * @param contingut Contingut nou del document
     * @param te Tipus d'extensio nova del document
     */
    public void guardarDocument (boolean nou, boolean modificat, SimpleEntry<String, String> idVell, SimpleEntry<String, String> idNou, String contingut, TipusExtensio te) {
        try {
            if (nou) {
                ctrlDomini.creaDocument(idNou.getKey(), idNou.getValue());
            } else {
                if (!idVell.equals(idNou))
                    ctrlDomini.modificarIdentificador(idVell, idNou);
            }
            ctrlDomini.setContingut(idNou.getKey(), idNou.getValue(), contingut);
            ctrlDomini.setExtensio(idNou.getKey(), idNou.getValue(), te);
            if (modificat) {
                ctrlDomini.guardaDocument(idNou.getKey(), idNou.getValue());
                this.actualitzarCerca();
            }

            if (editors.containsKey(idVell))    {
                ViewEditorDocument aux = editors.get(idVell);
                editors.remove(idVell);
                editors.put(idNou, aux);
            }
        } catch (Exception e) {
            ViewDialeg.errorDialog(e.toString());
        }
    }

    ///////////////////////////////////////////////////////////
    ///                 MÈTODES DE CERCA                    ///
    ///////////////////////////////////////////////////////////

    /**
     * Mostra tots els documents existents al menu principal
     */
    public void mostrarDocuments () {
        try {
            this.resultatPrincipal = ctrlDomini.cercaAllDocuments(tipusOrdenacio);
            this.informacio = "Tots els documents";
            this.ultimaCerca = TipusCerca.TOTS;
            enviarPrincipal();
        } catch (Exception e) {
            ViewDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Mostra el resultat de la cerca per titol al menu principal
     *
     * @param titol Titol a cercar
     */
    public void cercaTitol (String titol) {
        try {
            this.resultatPrincipal = ctrlDomini.cercaTitol(titol, tipusOrdenacio);
            this.auxTitol = titol;
            this.informacio = "Documents de títol: "+this.auxTitol;
            this.ultimaCerca = TipusCerca.TITOL;
            enviarPrincipal();
        } catch (Exception e) {
            ViewDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Mostra el resultat de la cerca per autor al menu principal
     *
     * @param autor Autor a cercar
     */
    public void cercaAutor (String autor) {
        try {
            this.resultatPrincipal = ctrlDomini.cercaAutor(autor, tipusOrdenacio);
            this.auxAutor = autor;
            this.informacio = "Documents creats per l'autor: "+this.auxAutor;
            this.ultimaCerca = TipusCerca.AUTOR;
            enviarPrincipal();
        } catch (Exception e) {
            ViewDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Mostra el resultat de la cerca per titol i autor al menu principal
     *
     * @param titol Titol del document a cercar
     * @param autor Autor del document a cercar
     */
    public void cercaTitolAutor (String titol, String autor) {
        try {
            resultatPrincipal.clear();
            resultatPrincipal.add(new SimpleEntry<>(titol, autor));
            ultimaCerca = TipusCerca.TITOLAUTOR;
            this.auxTitol = titol;
            this.auxAutor = autor;
            this.informacio = "Document de títol: "+this.auxTitol+" i autor: "+this.auxAutor;
            enviarPrincipal();
        } catch (Exception e) {
            ViewDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Mostra el resultat de la cerca per prefix a la vista de cerca prefix
     *
     * @param prefix Prefix a cercar
     * @param to Tipus d'ordenacio del resultat
     * @param documentsModificats {@code true} si s'han modificat els documents de l'aplicacio
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
            ViewDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Mostra el resultat de la cerca per paraules rellevants del contingut dels documents pel meno principal
     *
     * @param paraules Paraules a cercar
     * @param k Nombre de documents a mostrar
     * @param tots {@code true} si s'han de mostrar tots els documents
     */
    public void cercaParaules (String paraules, int k, boolean tots) {
        try {
            if (tots) k = ctrlDomini.getNombreDocuments();
            resultatPrincipal = ctrlDomini.cercaParaules(paraules.toLowerCase(), k);
            ultimaCerca = TipusCerca.PARAULES;
            this.auxParaules = paraules;
            this.k = k;
            this.informacio = "Els "+k+" documents més rellevants per les paraules "+this.auxParaules;
            enviarPrincipal();
        } catch (Exception e) {
            ViewDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Mostra el resultat de la cerca per documents semblants pel menu principal
     *
     * @param titol Titol del document a comparar
     * @param autor Autor del document a comparar
     * @param k Nombre de documents a mostrar
     * @param tots {@code true} si s'han de mostrar tots els documents
     */
    public void cercaSemblant (String titol, String autor, int k, boolean tots) {
        try {
            if (tots) k = ctrlDomini.getNombreDocuments();
            resultatPrincipal = ctrlDomini.cercaSemblant(titol, autor, k);
            ultimaCerca = TipusCerca.SEMBLANT;
            this.auxTitol = titol;
            this.auxAutor = autor;
            this.k = k;
            this.informacio = "Els "+(k-1)+" documents més semblants al document de títol: "+this.auxTitol+" i autor: "+this.auxAutor;
            enviarPrincipal();
        } catch (Exception e) {
            ViewDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Mostra el resultat de la cerca booleana pel menu principal
     *
     * @param expr Expressio booleana
     */
    public void cercaBooleana (String expr) {
        try {
            resultatPrincipal = ctrlDomini.cercaBooleana(expr, tipusOrdenacio);
            this.informacio = "Els documents que compleixen l'expressió "+this.auxExpr;
            ultimaCerca = TipusCerca.BOOLEANA;
            this.auxExpr = expr;
            enviarPrincipal();
        } catch (Exception e) {
            ViewDialeg.errorDialog(e.toString());
        }
    }

    /**
     * Ordena el resultat que es mostra pel menu principal
     *
     * @param to Tipus d'ordenacio
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
     * Obre la vista de cerca per titol
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
     * Obre la vista de cerca per titol i autor
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
     * Obre la vista de gestio d'expressions booleanes
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
     * La vista del document especificat ha estat tancada
     *
     * @param titol Titol del document tancat
     * @param autor Autor del document tancat
     */
    public void tancarDocument (String titol, String autor) {
        editors.remove(new SimpleEntry<>(titol, autor));

    }

    ///////////////////////////////////////////////////////////
    ///                  MÈTODES PRIVATS                    ///
    ///////////////////////////////////////////////////////////

    /**
     * Envia els resultats al menu principal
     */
    private void enviarPrincipal () {
        resultatPrincipalPes.clear();
        resultatPrincipalExtensio.clear();

        for (SimpleEntry<String, String> s : resultatPrincipal) {
            String t = s.getKey(); String a = s.getValue();
            resultatPrincipalPes.add(ctrlDomini.getPes(t, a));
            resultatPrincipalExtensio.add(ctrlDomini.getExtensio(t, a));
        }

        viewMenuPrincipal.actualitzarResultat(resultatPrincipal, resultatPrincipalPes, resultatPrincipalExtensio, this.informacio);
    }

    /**
     * Obre un document
     *
     * @return {@code true} si hi ha menys de 20 documents oberts i el document no es obert
     */
    private boolean obrirDocument (String titol, String autor) {
        if (editors.size() < 20) {
            if (editors.containsKey(new SimpleEntry<>(titol, autor))) {
                ViewDialeg.messageDialog("Ja és obert", "El document de títol: "+titol+" i autor: "+autor+
                        " ja és obert en un editor");
                return false;
            } else return true;
        }
        else {
            ViewDialeg.messageDialog("Massa documents oberts", "Tens massa documents oberts. Tanca'n algun abans de continuar");
            return false;
        }
    }

    /**
     * Actualitza el resultat del menu principal despres de crear, modificar, importar o eliminar un document
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
        if (viewCercaPrefix != null && viewCercaPrefix.esVisible() && auxPrefix != null) {
            this.cercaPrefix(auxPrefix, tipusOrdenacioPrefix, true);
        }
    }
}