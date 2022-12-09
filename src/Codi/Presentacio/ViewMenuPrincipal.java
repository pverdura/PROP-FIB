package Codi.Presentacio;

import Codi.Util.TipusExtensio;
import Codi.Util.TipusOrdenacio;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

public class ViewMenuPrincipal extends JFrame implements ActionListener {

    private JPanel mainPanel;
    private JButton cleanButton;
    private JTextArea textAreaCerques;
    private JScrollPane scroll;
    private JPanel cerquesPanel;
    private JMenuItem miCreaDoc, miImportaDoc, miAjuda, miSortir;
    private JMenuItem miGestioBool;
    private JMenuItem miCercaTitol, miCercaAutor, miCercaTitolAutor, miCercaPrefix, miCercaParaules, miCercaSemblant;
    private JMenuItem miOrdreAlfAsc, miOrdreAlfDesc, miOrdrePesAsc, miOrdrePesDesc;

    private final CtrlPresentacio ctrlPresentacio;

    private TipusOrdenacio tipus_ordenacio;

    public ViewMenuPrincipal(CtrlPresentacio ctrlPresentacio) {
        this.ctrlPresentacio = ctrlPresentacio;
        this.cleanButton.addActionListener(this);
        this.tipus_ordenacio = TipusOrdenacio.ALFABETIC_ASCENDENT;

        //Inicialitzar el scrollPanel i el text area associat
        this.textAreaCerques = new JTextArea(100,1);
        this.textAreaCerques.setVisible(true);
        this.textAreaCerques.setEditable(false);
        this.scroll.setViewportView(textAreaCerques);

        //Inicialitzar components principals de la vista
        setContentPane(this.mainPanel);
        setTitle("Menú Principal");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        crearMenus();
        //TODO: afegir ctrlPresentacio.mostrarDocuments();
        //ArrayList<SimpleEntry<String,String>> a = new ArrayList<>();
        //actualitzarResultat(a);
    }

    private void crearMenus() {
        JMenuBar barraMenu = new JMenuBar();
        setJMenuBar(barraMenu);

        //Afegir menu document
        JMenu menuFitxer = new JMenu("Fitxer");
        barraMenu.add(menuFitxer);

        miCreaDoc = new JMenuItem("Crea / Modifica");
        miCreaDoc.addActionListener(this);
        miImportaDoc = new JMenuItem("Importa");
        miImportaDoc.addActionListener(this);
        miAjuda = new JMenuItem("Ajuda");
        miAjuda.addActionListener(this);
        miSortir = new JMenuItem("Sortir");
        miSortir.addActionListener(this);

        menuFitxer.add(miCreaDoc);
        menuFitxer.add(miImportaDoc);
        menuFitxer.add(miAjuda);
        menuFitxer.add(miSortir);

        //Afegir menu expressio bool
        JMenu menuBool = new JMenu("Expressió Bool");
        barraMenu.add(menuBool);

        miGestioBool = new JMenuItem("Gestió");
        miGestioBool.addActionListener(this);
        menuBool.add(miGestioBool);

        //Afegir menu cerca
        JMenu menuCerca = new JMenu("Cerca");
        barraMenu.add(menuCerca);

        miCercaAutor = new JMenuItem("Cerca Autor");
        miCercaAutor.addActionListener(this);
        miCercaTitol = new JMenuItem("Cerca Títol");
        miCercaTitol.addActionListener(this);
        miCercaTitolAutor = new JMenuItem("Cerca Títol i Autor");
        miCercaTitolAutor.addActionListener(this);
        miCercaParaules = new JMenuItem("Cerca Paraules");
        miCercaParaules.addActionListener(this);
        miCercaPrefix = new JMenuItem("Cerca Prefix");
        miCercaPrefix.addActionListener(this);
        miCercaSemblant = new JMenuItem("Cerca per Documents");
        miCercaSemblant.addActionListener(this);

        menuCerca.add(miCercaTitol);
        menuCerca.add(miCercaAutor);
        menuCerca.add(miCercaTitolAutor);
        menuCerca.add(miCercaPrefix);
        menuCerca.add(miCercaParaules);
        menuCerca.add(miCercaSemblant);

        //Afegir menu ordre
        JMenu menuOrdre = new JMenu("Ordre");
        barraMenu.add(menuOrdre);

        miOrdreAlfAsc = new JMenuItem("Alfabètic Ascendent");
        miOrdreAlfAsc.addActionListener(this);
        miOrdreAlfDesc = new JMenuItem("Alfabètic Ascendent");
        miOrdreAlfDesc.addActionListener(this);
        miOrdrePesAsc = new JMenuItem("Pes Ascendent");
        miOrdrePesAsc.addActionListener(this);
        miOrdrePesDesc = new JMenuItem("Pes Descendent");
        miOrdrePesDesc.addActionListener(this);

        menuOrdre.add(miOrdreAlfAsc);
        menuOrdre.add(miOrdreAlfDesc);
        menuOrdre.add(miOrdrePesAsc);
        menuOrdre.add(miOrdrePesDesc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        //Aplicar funcionalitats associades al menu + buidar cerques de la vista
        if (source == miCreaDoc) {
            ctrlPresentacio.crearDocument();

        } else if (source == miImportaDoc) {

            FileNameExtensionFilter filtre = new FileNameExtensionFilter("Fitxers app",

                    TipusExtensio.BOL.toString(),
                    TipusExtensio.TXT.toString(),
                    TipusExtensio.XML.toString());

            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(filtre);

            int seleccionat = fc.showOpenDialog(this);

            if (seleccionat == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                boolean res = ctrlPresentacio.importarDocument(f);

                if (res)
                    VistaDialeg.messageDialog("Import", "S'ha importat el fitxer correctament");
                else
                    VistaDialeg.errorDialog("ERROR: El fitxer no sha importat.\nÉs possible que ja existeixi un fitxer igual!");
            }

        } else if (source == miAjuda) {
            ctrlPresentacio.obrirAjuda();

        } else if (source == miSortir) {
            ctrlPresentacio.tancarAplicacio();

        } else if (miGestioBool.equals(source)) {
            ctrlPresentacio.obrirGestioExprBool();

        } else if (source == miCercaTitol) {
            ctrlPresentacio.obrirCercaTitol();

        } else if (source == miCercaAutor) {
            ctrlPresentacio.obrirCercaAutor();

        } else if (source == miCercaTitolAutor) {
            ctrlPresentacio.obrirCercaTitolAutor();

        } else if (source == miCercaPrefix) {
            ctrlPresentacio.obrirCercaPrefix();

        } else if (source == miCercaParaules) {
            ctrlPresentacio.obrirCercaParaules();

        } else if (source == miCercaSemblant) {
            ctrlPresentacio.obrirCercaSemblant();

        } else if (source == miOrdreAlfAsc) {
            tipus_ordenacio = TipusOrdenacio.ALFABETIC_ASCENDENT;
            ctrlPresentacio.ordenar(tipus_ordenacio);

        } else if (source == miOrdreAlfDesc) {
            tipus_ordenacio = TipusOrdenacio.ALFABETIC_DESCENDENT;
            ctrlPresentacio.ordenar(tipus_ordenacio);

        } else if (source == miOrdrePesAsc) {
            tipus_ordenacio = TipusOrdenacio.PES_ASCENDENT;
            ctrlPresentacio.ordenar(tipus_ordenacio);

        } else if (source == miOrdrePesDesc) {
            tipus_ordenacio = TipusOrdenacio.PES_DESCENDENT;
            ctrlPresentacio.ordenar(tipus_ordenacio);

        } else if (source == cleanButton) {
            textAreaCerques.setText("");
        }
    }

    public void actualitzarResultat(ArrayList<SimpleEntry<String,String>> documents) {
        //TODO: ACTUALITZAR EL SCROLL PANEL AMB EL VALORS QUE ARRIBEN PER PARÀMETRE I FER DELETE/UPDATE/EXPORT AL CLICAR
        //TODO: SEPARAR ELS VALORS DEL TEXT AREA PER "-" AIXI AMB FUNC SPLIT PODEM SEPARAR ELS VALORS

        /*
        cerquesPanel = new JPanel();
        for (int i = 0; i <= 10; i++) {
            JLabel a = new JLabel("AJAJAJAJJAJJAJA");
            cerquesPanel.add(a);
        }
        scroll.add(cerquesPanel);

         */
    }

    //Metode per posar visible la vista
    public void ferVisible() {
        setSize(600, 600);
        setVisible(true);
    }
}
