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
    private JScrollPane panellInferior;
    private JLabel labelTitol;
    private JLabel labelAutor;
    private JLabel labelContingut;
    private JButton btGuardar;
    private JTextField textTitol;
    private JTextField textAutor;
    private JTextArea textContingut;
    private JComboBox<String> tipusExtensio;

    private CtrlPresentacio ctrlPresentacio;
    private String titol;
    private String autor;
    private String[] extensions = {"TXT", "XML", "BOL"};
    public ViewModificarDocument (CtrlPresentacio cp) {
        this.ctrlPresentacio = cp;
        inicialitzar();
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
        panellInferior = new JScrollPane();
        labelAutor = new JLabel("Autor: ");
        labelContingut = new JLabel("Contingut: ");
        labelTitol = new JLabel("Títol: ");
        btGuardar = new JButton("Desar");
        textTitol = new JTextField();
        textAutor = new JTextField();
        textContingut = new JTextArea();
        tipusExtensio = new JComboBox<>(extensions);
    }

    private void configurarVista () {
        //frame.setLayout(new BorderLayout());
        frame.add(panellSuperior);
        frame.add(panellMig);
        frame.add(panellInferior);

        frame.setMinimumSize(new Dimension(500, 500));
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

        panellSuperior.add(labelTitol);
        panellSuperior.add(textTitol);
        panellSuperior.add(tipusExtensio);
    }

    public void configurarPanellMig () {


        panellMig.add(labelAutor);
        panellMig.add(textAutor);
        panellMig.add(btGuardar);
    }

    public void configurarPanellInferior () {


        panellInferior.add(labelContingut);
        panellInferior.add(textContingut);
    }

    public void assignarListeners () {
        btGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleEntry<String, String> idVell = new SimpleEntry<>(titol, autor);
                SimpleEntry<String, String> idNou = new SimpleEntry<>(textTitol.getText(), textAutor.getText());

                if (ctrlPresentacio.guardarDocument(idVell, idNou, textContingut.getText(), getTipusExtensio())) {
                    VistaDialeg.messageDialog("Error", "El document s'ha desat correctament.");
                } else {
                    VistaDialeg.errorDialog("El document no s'ha pogut desar correctament.");
                }
            }
        });
    }

    public TipusExtensio getTipusExtensio () {
        return TipusExtensio.XML;
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
}
