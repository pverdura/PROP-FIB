package Codi.Presentacio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewMenuPrincipal extends JFrame implements ActionListener {

    private JPanel mainPanel;
    private JButton cleanButton;
    private JTextArea textAreaCerques;
    private JMenuBar barraMenu;
    private JMenu menuDoc, menuBool, menuCerca, menuOrdre;
    private JMenuItem miCreaDoc, miImportaDoc, miExportaDoc;
    private JMenuItem miGestioBool;
    private JMenuItem miCercaTitol, miCercaAutor, miCercaTitolAutor, miCercaPrefix, miCercaBool, miCercaParaules, miCercaDoc;
    private JMenuItem miOrdreAlfAsc, miOrdreAlfDesc, miOrdrePesAsc, miOrdrePesDesc;

    private CtrlPresentacio ctrlPresentacio;
    private  ViewGestioExprBool viewGestioExprBool;

    public ViewMenuPrincipal(CtrlPresentacio ctrlPresentacio1) {
        this.ctrlPresentacio = ctrlPresentacio1;
        crearMenus();
    }

    private void crearMenus() {
        barraMenu = new JMenuBar();
        setJMenuBar(barraMenu);

        //Afegir menu document
        menuDoc = new JMenu("Document");
        barraMenu.add(menuDoc);

        miCreaDoc = new JMenuItem("Crea / Modifica");
        miCreaDoc.addActionListener(this);
        miImportaDoc = new JMenuItem("Importa");
        miImportaDoc.addActionListener(this);
        miExportaDoc = new JMenuItem("Exporta");
        miExportaDoc.addActionListener(this);

        menuDoc.add(miCreaDoc);
        menuDoc.add(miImportaDoc);
        menuDoc.add(miExportaDoc);

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
        miCercaBool = new JMenuItem("Cerca Booleana");
        miCercaBool.addActionListener(this);
        miCercaParaules = new JMenuItem("Cerca Paraules");
        miCercaParaules.addActionListener(this);
        miCercaDoc = new JMenuItem("Cerca per Document");
        miCercaDoc.addActionListener(this);

        menuCerca.add(miCercaTitol);
        menuCerca.add(miCercaAutor);
        menuCerca.add(miCercaTitolAutor);
        menuCerca.add(miCercaPrefix);
        menuCerca.add(miCercaBool);
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

        if (source == miCreaDoc) {

        } else if (source == miImportaDoc) {

        } else if (source == miExportaDoc) {

        } else if (miGestioBool.equals(source)) {
            viewGestioExprBool = new ViewGestioExprBool(ctrlPresentacio);
            viewGestioExprBool.posar_visible();

        } else if (source == miCercaTitol) {

        } else if (source == miCercaAutor) {

        } else if (source == miCercaTitolAutor) {

        } else if (source == miCercaPrefix) {

        } else if (source == miCercaBool) {

        } else if (source == miCercaParaules) {

        } else if (source == miCercaDoc) {

        }
    }

    public void posar_visible() {
        setContentPane(this.mainPanel);
        setTitle("Menú Principal");
        setSize(600, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
