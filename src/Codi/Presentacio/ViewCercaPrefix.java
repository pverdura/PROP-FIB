package Codi.Presentacio;

import Codi.Excepcions.PrefixNoExisteixException;
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
    //private JButton acceptarButton;
    private JButton cercaButton;
    private JButton cancelarButton;
    private JTextArea resultat;
    private JScrollPane scroll;
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


    public void enviarDades(ArrayList<String> res_cerca){

    }

    /////////////////////////// ASSIGNACIÓ DE LISTENERS

    private void assignarListeners(){
        cercaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String prefix = omplirPrefix.getText();
                TipusOrdenacio t;
                if (asc.isSelected()) t = TipusOrdenacio.ALFABETIC_ASCENDENT;
                else t = TipusOrdenacio.ALFABETIC_DESCENDENT;
                ctrlPresentacio.cercaPrefix(prefix, t);
            }
        });
/*
        cercaButton.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getExtendedKeyCode() == KeyEvent.VK_ENTER) cercaButton.doClick();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

 */
        cercaButton.setFocusable(true);
        cercaButton.addKeyListener(new Teclas());

        frameVista.setFocusable(true);
        frameVista.addKeyListener(new Teclas());
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


    private class Teclas extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getExtendedKeyCode() == KeyEvent.VK_ENTER) cercaButton.doClick();
        }
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
        //acceptarButton = new JButton("Acceptar");
        cercaButton = new JButton("Cercar");
        omplirPrefix = new JTextField();
        labelPrefix = new JLabel("Prefix: ");
        resultat = new JTextArea(25,35);
        scroll = new JScrollPane(resultat);
        scroll.setHorizontalScrollBar(null);
        asc = new JRadioButton("Ascendent");
        des = new JRadioButton("Descendent");


        scroll.setFocusable(false);
        asc.setFocusable(false);
        des.setFocusable(false);
        resultat.setFocusable(false);
        cancelarButton.setFocusable(false);
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

        omplirPrefix.setMinimumSize(new Dimension(230,30));
        omplirPrefix.setPreferredSize(omplirPrefix.getMinimumSize());

        prefixPanel.add(labelPrefix);
        prefixPanel.add(omplirPrefix);
        prefixPanel.add(cercaButton);
    }

    private void configResultatPanel(){
        resultatPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        scroll.setPreferredSize(new Dimension(350,325));
        scroll.setViewportView(resultat);

        resultatPanel.add(scroll, BorderLayout.CENTER);
    }

    private void configButtonsPanel(){
        BorderLayout b = new BorderLayout();
        buttonsPanel.setLayout(b);
        JPanel innerPanel = new JPanel(new BorderLayout());
        innerPanel.add(asc, BorderLayout.WEST);
        innerPanel.add(des, BorderLayout.EAST);

        innerPanel.setPreferredSize(new Dimension(150,30));

        asc.setSelected(true);

        buttonsPanel.add(asc, BorderLayout.WEST);
        buttonsPanel.add(des, BorderLayout.CENTER);
        buttonsPanel.add(cancelarButton, BorderLayout.EAST);
        //buttonsPanel.add(innerPanel, BorderLayout.CENTER);
        //buttonsPanel.add(acceptarButton, BorderLayout.EAST);
    }
}
