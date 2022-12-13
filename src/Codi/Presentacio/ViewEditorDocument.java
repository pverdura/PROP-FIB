package Codi.Presentacio;

import Codi.Util.TipusExtensio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.AbstractMap.SimpleEntry;

public class ViewEditorDocument {
    private JFrame frame;
    private JPanel panellSuperior;
    private JPanel panellMig;
    private JScrollPane panellInferior;
    private JLabel labelTitol;
    private JLabel labelAutor;
    private JButton btGuardar;
    private JTextField textTitol;
    private JTextField textAutor;
    private JTextArea textContingut;
    private JComboBox<String> tipusExtensio;

    private final CtrlPresentacio ctrlPresentacio;
    private String titol;
    private String autor;
    private String contingut;
    private TipusExtensio tExtensio;
    private final String[] extensions = {"TXT", "XML", "BOL"};
    private boolean documentNou;
    public ViewEditorDocument(CtrlPresentacio cp) {
        //constructor per document nou
        this.ctrlPresentacio = cp;
        this.titol = "";
        this.autor = "";
        this.contingut = "";
        documentNou = true;

        inicialitzar();
        this.setExtensio(TipusExtensio.BOL);
    }

    public ViewEditorDocument(CtrlPresentacio cp, String titol, String autor, String contingut, TipusExtensio te) {
        //constructor per modificar un document
        this.ctrlPresentacio = cp;
        this.titol = titol;
        this.autor = autor;
        this.contingut = contingut;
        documentNou = false;

        inicialitzar();
        this.setExtensio(te);
    }

    private void inicialitzar () {
        inicializarComponents();
        configurarVista();
        configurarPanellSuperior();
        configurarPanellMig();
        configurarPanellInferior();
        assignarListeners();
    }

    private void inicializarComponents () {
        frame = new JFrame("Editor de documents -"+titol);
        panellSuperior = new JPanel();
        panellMig = new JPanel();
        labelAutor = new JLabel("Autor: ");
        labelTitol = new JLabel("Títol: ");
        btGuardar = new JButton("Desar");
        textTitol = new JTextField();
        textAutor = new JTextField();
        textContingut = new JTextArea();
        tipusExtensio = new JComboBox<>(extensions);
    }

    private void configurarVista () {
        frame.add(panellSuperior, BorderLayout.NORTH);
        frame.add(panellMig, BorderLayout.CENTER);

        frame.setMinimumSize(new Dimension(800, 600));
        frame.setPreferredSize(frame.getMinimumSize());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    public void ferVisible (boolean visible) {
        if (visible) frame.pack();
        frame.setVisible(visible);
    }

    public void configurarPanellSuperior () {
        tipusExtensio.setSelectedIndex(2);
        textTitol.setMinimumSize(new Dimension(400, 30));
        textTitol.setPreferredSize(textTitol.getMinimumSize());
        textTitol.setText(titol);

        panellSuperior.add(labelTitol);
        panellSuperior.add(textTitol);
        panellSuperior.add(tipusExtensio);
    }

    public void configurarPanellMig () {
        textAutor.setMinimumSize(new Dimension(400, 30));
        textAutor.setPreferredSize(textAutor.getMinimumSize());
        textAutor.setText(autor);

        panellMig.add(labelAutor);
        panellMig.add(textAutor);
        panellMig.add(btGuardar);
    }

    public void configurarPanellInferior () {
        textContingut.setMinimumSize(new Dimension(800, 450));
        textContingut.setPreferredSize(textContingut.getMinimumSize());
        textContingut.setText(contingut);

        panellInferior = new JScrollPane(textContingut);
        panellInferior.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        panellInferior.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panellInferior.setPreferredSize(new Dimension(800, 450));

        frame.add(panellInferior, BorderLayout.SOUTH);
    }

    public void assignarListeners () {
        btGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (VistaDialeg.confirmDialog("Segur que vols desar el document?")) {
                    guardarDocument();
                }
            }
        });

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (modificat() && VistaDialeg.confirmDialog("Hi ha canvis no guardats. Vols desar el document abans de tancar l'aplicació?")) {
                    guardarDocument();
                }
                ctrlPresentacio.tancarDocument();
            }
        });

        frame.addKeyListener(new Tecles());
        textContingut.addKeyListener(new Tecles());
        textTitol.addKeyListener(new Tecles());
        textAutor.addKeyListener(new Tecles());
        tipusExtensio.addKeyListener(new Tecles());
    }
    private TipusExtensio getTipusExtensio () {
        String ext = (String) tipusExtensio.getSelectedItem();
        if (ext == "TXT") return TipusExtensio.TXT;
        else if (ext == "XML") return TipusExtensio.XML;
        else return TipusExtensio.BOL;
    }
    private void setExtensio (TipusExtensio te) {
        this.tExtensio = te;
        if (te.equals(TipusExtensio.TXT)) tipusExtensio.setSelectedIndex(0);
        else if (te.equals(TipusExtensio.XML)) tipusExtensio.setSelectedIndex(1);
        else tipusExtensio.setSelectedIndex(2);
    }
    private void guardarDocument () {
        SimpleEntry<String, String> idVell;
        if (documentNou) idVell = null;
        else idVell = new SimpleEntry<>(titol, autor);

        titol = textTitol.getText().trim();
        autor = textAutor.getText().trim();
        contingut = textContingut.getText();
        setExtensio(getTipusExtensio());
        SimpleEntry<String, String> idNou = new SimpleEntry<>(titol, autor);

        ctrlPresentacio.guardarDocument(documentNou, idVell, idNou, contingut, tExtensio);
        documentNou = false;
    }
    private boolean modificat () {
        return (!titol.equals(textTitol.getText())) || (!autor.equals(textAutor.getText())) ||
                (!contingut.equals(textContingut.getText())) || tExtensio != getTipusExtensio();
    }

    private class Tecles extends KeyAdapter {
        private boolean control = false;
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getExtendedKeyCode() == KeyEvent.VK_CONTROL) {
                control = true;
            } else if (e.getExtendedKeyCode() == KeyEvent.VK_S && control) {
                guardarDocument();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getExtendedKeyCode() == KeyEvent.VK_CONTROL) {
                control = false;
            }
        }
    }
}
