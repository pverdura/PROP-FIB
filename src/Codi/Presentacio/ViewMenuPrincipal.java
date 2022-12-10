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
    private JScrollPane scroll;
    private JMenuItem miCreaDoc, miImportaDoc, miAjuda, miSortir;
    private JMenuItem miGestioBool;
    private JMenuItem miCercaTitol, miCercaAutor, miCercaTitolAutor, miCercaPrefix, miCercaParaules, miCercaSemblant;
    private JMenuItem miOrdreAlfAsc, miOrdreAlfDesc, miOrdrePesAsc, miOrdrePesDesc;

    private final CtrlPresentacio ctrlPresentacio;
    private final DefaultListModel<String> dlm;
    private TipusOrdenacio tipus_ordenacio;

    public ViewMenuPrincipal(CtrlPresentacio ctrlPresentacio) {
        //Inicialitzar components principals de la vista
        setContentPane(this.mainPanel);
        setTitle("Menú Principal");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.ctrlPresentacio = ctrlPresentacio;
        this.cleanButton.addActionListener(this);
        this.tipus_ordenacio = TipusOrdenacio.ALFABETIC_ASCENDENT;

        //Iniciar elements per carregar a la vista tots els documents guardats
        this.dlm = new DefaultListModel<>();
        JList<String> llistaCerques = new JList<>(dlm);
        llistaCerques.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        llistaCerques.setSelectedIndex(0);
        this.scroll.setViewportView(llistaCerques);
        //Crear menus vista
        crearMenus();

        //Carregar docs a la vista
        ctrlPresentacio.mostrarDocuments();
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
                File[] files = new File[]{f};
                ctrlPresentacio.importarDocument(files);
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
            dlm.removeAllElements();
        }
    }

    public void actualitzarResultat(ArrayList<SimpleEntry<String,String>> titolsAutors, ArrayList<Integer> pesos, ArrayList<TipusExtensio> extensios) {
        dlm.removeAllElements();
        int size = titolsAutors.size();

        //Afegir a la llista que es mostra per pantalla tots els documents que arriben
        for (int i = 0; i < 5;  i++) {
            dlm.addElement(titolsAutors.get(i).getKey()+"  "+titolsAutors.get(i).getValue()+"  "+
                           pesos.get(i).toString()+"  "+extensios.get(i).toString());
        }
    }

    //Metode per posar visible la vista
    public void ferVisible() {
        setSize(600, 600);
        setVisible(true);
    }

    //Metode que tanca app
    public void tancarVista() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
