package Codi.Presentacio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ViewCercaSemblant {
    private JFrame frameVista;
    private JPanel titolPanel, autorPanel, numPanel;
    private JPanel buttonsPanel;
    private JButton acceptarButton;
    private JButton cancelarButton;
    private JTextField omplirTitol;
    private JTextField omplirAutor;
    private JLabel labelTitol;
    private JLabel labelAutor;
    private JLabel labelNumDocs;
    private SpinnerNumberModel model;
    private JSpinner numDocs;
    private JCheckBox totsDocs;

    private final CtrlPresentacio ctrlPresentacio;

    ////////////////////////////// CONSTRUCTOR I MÈTODES PÚBLICS
    public ViewCercaSemblant(CtrlPresentacio ctrlPresentacio){
        this.ctrlPresentacio = ctrlPresentacio;
        inicialitza();
    }

    public void ferVisible(boolean visible){
        omplirTitol.setText("");
        omplirAutor.setText("");
        numDocs.setValue(0);
        if (visible) frameVista.pack();
        frameVista.setVisible(visible);
    }


    public void tancarVista(){
        frameVista.dispatchEvent(new WindowEvent(frameVista, WindowEvent.WINDOW_CLOSING));
    }

    /////////////////////////// ASSIGNACIÓ DE LISTENERS


    private void assignarListeners(){
        acceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titol = omplirTitol.getText();
                String autor = omplirAutor.getText();
                int k = (int)numDocs.getValue();
                boolean tots = totsDocs.isSelected();
                ctrlPresentacio.cercaSemblant(titol, autor, k, tots);
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ferVisible(false);
            }
        });

        totsDocs.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                numDocs.setEnabled(!totsDocs.isSelected());
            }
        });
    }



    ////////////////////////// RESTA DE MÈTODES PRIVATS

    private void inicialitza(){
        inicialitzarComponents();
        configurarVista();
        configTitolPanel();
        configAutorPanel();
        configNumDocsPanel();
        configButtonsPanel();
        assignarListeners();
    }

    private void inicialitzarComponents(){
        frameVista = new JFrame("Cerca per Document");
        titolPanel = new JPanel();
        autorPanel = new JPanel();
        numPanel = new JPanel();
        buttonsPanel = new JPanel();
        cancelarButton = new JButton("Cancel·lar");
        acceptarButton = new JButton("Acceptar");
        omplirTitol = new JTextField();
        omplirAutor = new JTextField();
        model = new SpinnerNumberModel();
        model.setValue(0);
        model.setMinimum(0);
        model.setStepSize(1);
        numDocs = new JSpinner(model);
        labelTitol = new JLabel("Títol: ");
        labelAutor = new JLabel("Autor: ");
        labelNumDocs = new JLabel("Nº documents: ");
        totsDocs = new JCheckBox("Tots els documents");
    }

    private void configurarVista(){
        frameVista.setLayout(new BorderLayout());

        JPanel innerPanel = new JPanel(new BorderLayout());
        innerPanel.add(titolPanel, BorderLayout.NORTH);
        innerPanel.add(autorPanel, BorderLayout.CENTER);
        innerPanel.add(numPanel, BorderLayout.SOUTH);

        frameVista.add(innerPanel, BorderLayout.NORTH);
        frameVista.add(buttonsPanel, BorderLayout.SOUTH);

        frameVista.setMinimumSize(new Dimension(500, 225));
        frameVista.setPreferredSize(frameVista.getMinimumSize());
        frameVista.setResizable(false);
    }

    private void configTitolPanel(){
        titolPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10, 19));

        omplirTitol.setMinimumSize(new Dimension(250,30));
        omplirTitol.setPreferredSize(omplirTitol.getMinimumSize());

        titolPanel.add(labelTitol);
        titolPanel.add(omplirTitol);
    }

    private void configAutorPanel(){
        autorPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        omplirAutor.setMinimumSize(new Dimension(250, 30));
        omplirAutor.setPreferredSize(omplirTitol.getMinimumSize());

        autorPanel.add(labelAutor);
        autorPanel.add(omplirAutor);
    }

    private void configNumDocsPanel(){
        numPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        numDocs.setMinimumSize(new Dimension(50,25));
        numDocs.setPreferredSize(numDocs.getMinimumSize());
        numPanel.add(labelNumDocs);
        numPanel.add(numDocs);
    }

    private void configButtonsPanel(){
        BorderLayout b = new BorderLayout();
        buttonsPanel.setLayout(b);
        buttonsPanel.add(cancelarButton, BorderLayout.WEST);
        buttonsPanel.add(totsDocs, BorderLayout.CENTER);
        buttonsPanel.add(acceptarButton, BorderLayout.EAST);
    }
}
