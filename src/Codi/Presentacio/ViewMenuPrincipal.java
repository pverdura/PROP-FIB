package Codi.Presentacio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewMenuPrincipal extends JFrame implements ActionListener {

    private JPanel mainPanel;
    private JButton cleanButton;
    private JTextArea textAreaCerques;
    private JMenuBar barraMenu;
    private JMenu menuFitxer, menuBool, menuCerca, menuOrdre;
    private JMenuItem miCreaDoc, miImportaDoc, miAjuda, miSortir;
    private JMenuItem miGestioBool;
    private JMenuItem miCercaTitol, miCercaAutor, miCercaTitolAutor, miCercaPrefix, miCercaParaules, miCercaDoc;
    private JMenuItem miOrdreAlfAsc, miOrdreAlfDesc, miOrdrePesAsc, miOrdrePesDesc;

    private CtrlPresentacio ctrlPresentacio;

    public ViewMenuPrincipal(CtrlPresentacio ctrlPresentacio1) {
        this.ctrlPresentacio = ctrlPresentacio1;

        setContentPane(this.mainPanel);
        setTitle("Menú Principal");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cleanButton.addActionListener(this);
        crearMenus();
    }

    private void crearMenus() {
        barraMenu = new JMenuBar();
        setJMenuBar(barraMenu);

        //Afegir menu document
        menuFitxer = new JMenu("Fitxer");
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
        menuBool = new JMenu("Expressió Bool");
        barraMenu.add(menuBool);

        miGestioBool = new JMenuItem("Gestió");
        miGestioBool.addActionListener(this);
        menuBool.add(miGestioBool);

        //Afegir menu cerca
        menuCerca = new JMenu("Cerca");
        barraMenu.add(menuCerca);

        miCercaTitol = new JMenuItem("Cerca Títol");
        miCercaTitol.addActionListener(this);
        miCercaAutor = new JMenuItem("Cerca Autor");
        miCercaAutor.addActionListener(this);
        miCercaTitolAutor = new JMenuItem("Cerca Títol i Autor");
        miCercaTitolAutor.addActionListener(this);
        miCercaPrefix = new JMenuItem("Cerca Prefix");
        miCercaPrefix.addActionListener(this);
        miCercaParaules = new JMenuItem("Cerca Paraules");
        miCercaParaules.addActionListener(this);
        miCercaDoc = new JMenuItem("Cerca per Document");
        miCercaDoc.addActionListener(this);

        menuCerca.add(miCercaTitol);
        menuCerca.add(miCercaAutor);
        menuCerca.add(miCercaTitolAutor);
        menuCerca.add(miCercaPrefix);
        menuCerca.add(miCercaParaules);
        menuCerca.add(miCercaDoc);

        //Afegir menu ordre
        menuOrdre = new JMenu("Ordre");
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

        //Afegir la vista del menuItem clicat
        if (source == miCreaDoc) {

        } else if (source == miImportaDoc) {

        } else if (source == miAjuda) {

        } else if (source == miSortir) {
            System.exit(0);

        } else if (miGestioBool.equals(source)) {
            ctrlPresentacio.canviar_menuPrincipal_a_gestioExprBool();

        } else if (source == miCercaTitol) {

        } else if (source == miCercaAutor) {

        } else if (source == miCercaTitolAutor) {

        } else if (source == miCercaPrefix) {

        } else if (source == miCercaParaules) {

        } else if (source == miCercaDoc) {

        } else if (source == miOrdreAlfAsc) {

        } else if (source == miOrdreAlfDesc) {

        } else if (source == miOrdrePesAsc) {

        } else if (source == miOrdrePesDesc) {

        } else if (source == cleanButton) {
            textAreaCerques.setText("");
        }
    }

    //Metode per posar visible la vista
    public void ferVisible() {
        setSize(600, 600);
        setVisible(true);
    }
    
    public void activar() {
        setEnabled(true);
    }
    public  void desactivar() {
        setEnabled(false);
    }
}
