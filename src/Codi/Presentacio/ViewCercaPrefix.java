package Codi.Presentacio;

import Codi.Util.TipusOrdenacio;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ViewCercaPrefix {
    private JFrame frameVista;
    private JPanel prefixPanel;
    private JPanel resultatPanel;
    private JPanel buttonsPanel;
    private JLabel labelPrefix;
    private JTextField omplirPrefix;
    private JButton acceptarButton;
    private JButton cancelarButton;
    private JTextArea resultat;
    private JScrollPane scroll;
    private JScrollBar bar;
    private JRadioButton asc;
    private JRadioButton des;

    private final CtrlPresentacio ctrlPresentacio;

    /////////////////////////////// CONSTRUCTOR I MÈTODES PÚBLICS
    public ViewCercaPrefix (CtrlPresentacio ctrlPresentacio){
        this.ctrlPresentacio = ctrlPresentacio;
        inicialitza();
    }

    public void ferVisible(boolean visible){
        if (visible) frameVista.pack();
        frameVista.setVisible(true);
    }


    public void tancarVista(){
        frameVista.dispatchEvent(new WindowEvent(frameVista, WindowEvent.WINDOW_CLOSING));
    }


    public void enviarDades(ArrayList<String> l){

    }

    /////////////////////////// ASSIGNACIÓ DE LISTENERS

    private void assignarListeners(){
        acceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String prefix = omplirPrefix.getText();
                TipusOrdenacio t;
                if (asc.isSelected()) t = TipusOrdenacio.ALFABETIC_ASCENDENT;
                else t = TipusOrdenacio.ALFABETIC_DESCENDENT;
                ctrlPresentacio.cercaPrefix(prefix, t);
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ferVisible(false);
            }
        });

        asc.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (asc.isSelected()) des.setSelected(false);
                else des.setSelected(true);
            }
        });

        des.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (des.isSelected()) asc.setSelected(false);
                else asc.setSelected(true);
            }
        });
    }



    ////////////////////////// RESTA DE MÈTODES PRIVATS
    private void inicialitza() {
        inicialitzarComponents();
        configurarVista();
        configPrefixPanel();
        configResultatPanel();
        configButtonsPanel();
        assignarListeners();
    }

    private void inicialitzarComponents(){
        frameVista = new JFrame("Cerca per Prefix");
        prefixPanel = new JPanel();
        resultatPanel = new JPanel();
        buttonsPanel = new JPanel();
        cancelarButton = new JButton("Cancel·lar");
        acceptarButton = new JButton("Acceptar");
        omplirPrefix = new JTextField();
        labelPrefix = new JLabel("Prefix: ");
        resultat = new JTextArea(20,35);
        scroll = new JScrollPane();
        bar = new JScrollBar();
        asc = new JRadioButton("Ascendent");
        des = new JRadioButton("Descendent");
    }

    private void configurarVista(){
        frameVista.setLayout(new BorderLayout());
        frameVista.add(prefixPanel, BorderLayout.NORTH);
        frameVista.add(resultatPanel, BorderLayout.CENTER);
        frameVista.add(buttonsPanel, BorderLayout.SOUTH);

        frameVista.setMinimumSize(new Dimension(400, 475));
        frameVista.setPreferredSize(frameVista.getMinimumSize());
        frameVista.setResizable(false);
    }

    private void configPrefixPanel(){
        prefixPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        omplirPrefix.setMinimumSize(new Dimension(250,30));
        omplirPrefix.setPreferredSize(omplirPrefix.getMinimumSize());

        prefixPanel.add(labelPrefix);
        prefixPanel.add(omplirPrefix);
    }

    private void configResultatPanel(){
        resultatPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        resultat.setVisible(true);
        resultat.setEditable(false);
        scroll.setViewportView(resultat);

        resultatPanel.add(resultat, BorderLayout.CENTER);
        resultat.add(bar, BorderLayout.AFTER_LINE_ENDS);
    }

    private void configButtonsPanel(){
        BorderLayout b = new BorderLayout();
        buttonsPanel.setLayout(b);
        JPanel innerPanel = new JPanel(new BorderLayout());
        innerPanel.add(asc, BorderLayout.WEST);
        innerPanel.add(des, BorderLayout.EAST);

        asc.setSelected(true);

        buttonsPanel.add(cancelarButton, BorderLayout.WEST);
        buttonsPanel.add(innerPanel, BorderLayout.CENTER);
        buttonsPanel.add(acceptarButton, BorderLayout.EAST);
    }
}
