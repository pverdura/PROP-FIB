package Codi.Presentacio;

import Codi.Util.TipusExtensio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractMap.SimpleEntry;

public class ViewModificarDocument {
    private JFrame frame;
    private JPanel panellSuperior;
    private JPanel panellMig;
    private JPanel panellInferior;
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
    private final String[] extensions = {"TXT", "XML", "BOL"};
    private boolean documentNou;
    public ViewModificarDocument (CtrlPresentacio cp) {
        this.ctrlPresentacio = cp;
        inicialitzar();
        documentNou = true;
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
        frame = new JFrame("Editor de documents");
        panellSuperior = new JPanel();
        panellMig = new JPanel();
        panellInferior = new JPanel();
        labelAutor = new JLabel("Autor: ");
        labelTitol = new JLabel("Títol: ");
        btGuardar = new JButton("Desar");
        textTitol = new JTextField();
        textAutor = new JTextField();
        textContingut = new JTextArea();
        tipusExtensio = new JComboBox<>(extensions);
    }

    private void configurarVista () {
        //frame.setLayout(new BorderLayout());
        frame.add(panellSuperior, BorderLayout.NORTH);
        frame.add(panellMig, BorderLayout.CENTER);
        frame.add(panellInferior, BorderLayout.SOUTH);

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

        panellSuperior.add(labelTitol);
        panellSuperior.add(textTitol);
        panellSuperior.add(tipusExtensio);
    }

    public void configurarPanellMig () {
        textAutor.setMinimumSize(new Dimension(400, 30));
        textAutor.setPreferredSize(textAutor.getMinimumSize());

        panellMig.add(labelAutor);
        panellMig.add(textAutor);
        panellMig.add(btGuardar);
    }

    public void configurarPanellInferior () {
        textContingut.setMinimumSize(new Dimension(700, 450));
        textContingut.setPreferredSize(textContingut.getMinimumSize());

        JScrollPane panellContingut = new JScrollPane(textContingut);
        panellContingut.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panellInferior.add(panellContingut, BorderLayout.CENTER);
    }

    public void assignarListeners () {
        btGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (VistaDialeg.confirmDialog("Segur que vols desar el document?")) {
                    SimpleEntry<String, String> idVell;
                    if (documentNou) idVell = null;
                    else idVell = new SimpleEntry<>(titol, autor);
                    SimpleEntry<String, String> idNou = new SimpleEntry<>(textTitol.getText(), textAutor.getText());

                    ctrlPresentacio.guardarDocument(idVell, idNou, textContingut.getText(), getTipusExtensio());
                }
            }
        });
    }

    public TipusExtensio getTipusExtensio () {
        String ext = (String) tipusExtensio.getSelectedItem();
        if (ext == "TXT") return TipusExtensio.TXT;
        else if (ext == "XML") return TipusExtensio.XML;
        else return TipusExtensio.BOL;
    }

    public void setTitol (String text) {
        titol = text;
        textTitol.setText(text);
    }
    public void setAutor (String text) {
        autor = text;
        textAutor.setText(text);
    }
    public void setContingut (String text) {
        textContingut.setText(text);
    }
    public void setExtensio (TipusExtensio te) {
        if (te == TipusExtensio.TXT) tipusExtensio.setSelectedIndex(0);
        else if (te == TipusExtensio.XML) tipusExtensio.setSelectedIndex(1);
        else tipusExtensio.setSelectedIndex(2);
    }
    public void setDocumentNou (boolean esNou) {
        documentNou = esNou;
    }
}
