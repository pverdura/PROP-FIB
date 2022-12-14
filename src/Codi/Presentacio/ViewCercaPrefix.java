package Codi.Presentacio;

import Codi.Util.TipusOrdenacio;

import javax.swing.*;
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
    private JButton esborrarButton;
    private JButton cercaButton;
    private JButton cancelarButton;
    private JButton mostraButton;
    private JList resCerca;
    private JScrollPane scroll;
    private JRadioButton asc;
    private JRadioButton des;
    private JCheckBox totsAutors;
    private boolean esborrar;
    private boolean tots;
    private int index;

    private final CtrlPresentacio ctrlPresentacio;

    /////////////////////////////// CONSTRUCTOR I MÈTODES PÚBLICS
    public ViewCercaPrefix (CtrlPresentacio ctrlPresentacio){
        this.ctrlPresentacio = ctrlPresentacio;
        inicialitza();
    }

    public void ferVisible(boolean visible){
        omplirPrefix.setText("");
        if (visible) frameVista.pack();
        frameVista.setVisible(visible);
        esborraCerca();
        asc.setSelected(true);
    }

    public Boolean esVisible(){
        return frameVista.isVisible();
    }


    public void enviarDades(ArrayList<String> resultatCerca){
        DefaultListModel listModel = new DefaultListModel<>();

        for(String s : resultatCerca){
            listModel.addElement(s);
        }
        resCerca = new JList<>(listModel);
        resCerca.addKeyListener(new Tecles());

        scroll.setViewportView(resCerca);
    }

    /////////////////////////// ASSIGNACIÓ DE LISTENERS

    private void assignarListeners(){
        cercaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarCerca();
            }
        });

        frameVista.addKeyListener(new Tecles());

        omplirPrefix.addKeyListener(new Tecles());

        scroll.addKeyListener(new Tecles());

        esborrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                esborraCerca();
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
                des.setSelected(!asc.isSelected());
                mostrarCerca();
            }
        });

        des.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                asc.setSelected(!des.isSelected());
                mostrarCerca();
            }
        });

        totsAutors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (totsAutors.isSelected()) {
                    tots = true;
                    mostrarCerca();
                }
                else {
                    tots = false;
                    esborraCerca();
                }
            }
        });


        mostraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                index = resCerca.getSelectedIndex();
                ctrlPresentacio.cercaAutor(resCerca.getModel().getElementAt(index).toString());
            }
        });
    }


    private class Tecles extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getExtendedKeyCode() == KeyEvent.VK_ENTER) mostrarCerca();
            else if (e.getExtendedKeyCode() == KeyEvent.VK_ESCAPE) ferVisible(false);
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
        esborrarButton = new JButton("Esborrar");
        cercaButton = new JButton("Cercar");
        mostraButton = new JButton("Mostra els documents de l'autor");
        omplirPrefix = new JTextField();
        labelPrefix = new JLabel("Prefix: ");
        resCerca = new JList<>();
        resCerca.setSelectedIndex(0);
        resCerca.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scroll = new JScrollPane();
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        asc = new JRadioButton("Ascendent");
        des = new JRadioButton("Descendent");
        totsAutors = new JCheckBox("Mostra tots els autors");
        esborrar = false;

        scroll.setFocusable(false);
        asc.setFocusable(false);
        des.setFocusable(false);
        cancelarButton.setFocusable(false);
        esborrarButton.setFocusable(false);
        totsAutors.setFocusable(false);
        mostraButton.setFocusable(false);
    }

    private void configurarVista(){
        frameVista.setLayout(new BorderLayout());

        JPanel auxPanel = new JPanel(new BorderLayout());
        auxPanel.add(totsAutors, BorderLayout.WEST);
        auxPanel.add(mostraButton, BorderLayout.EAST);

        JPanel auxPanel2 = new JPanel(new BorderLayout());
        auxPanel2.add(resultatPanel, BorderLayout.NORTH);
        auxPanel2.add(auxPanel, BorderLayout.SOUTH);

        frameVista.add(prefixPanel, BorderLayout.NORTH);
        frameVista.add(auxPanel2, BorderLayout.CENTER);
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

        resultatPanel.add(scroll, BorderLayout.CENTER);
        scroll.setPreferredSize(new Dimension(350,300));
    }

    private void configButtonsPanel(){
        BorderLayout b = new BorderLayout();
        buttonsPanel.setLayout(b);
        JPanel innerPanel = new JPanel(new BorderLayout());
        innerPanel.add(asc, BorderLayout.WEST);
        innerPanel.add(des, BorderLayout.EAST);

        innerPanel.setPreferredSize(new Dimension(150, 30));

        buttonsPanel.add(cancelarButton, BorderLayout.EAST);
        buttonsPanel.add(innerPanel, BorderLayout.CENTER);
        buttonsPanel.add(esborrarButton, BorderLayout.WEST);
    }

    private void mostrarCerca(){
        String prefix = omplirPrefix.getText();
        if (!prefix.isEmpty()) {
            totsAutors.setSelected(false);
            totsAutors.setEnabled(false);
            mostraCerca(prefix);
            tots = false;
            mostraButton.setEnabled(true);
        }
        else if (tots) {
            mostraCerca(prefix);
            mostraButton.setEnabled(true);
        }
        else esborraCerca();
    }

    private void mostraCerca(String prefix){
        TipusOrdenacio t;
        if (asc.isSelected()) t = TipusOrdenacio.ALFABETIC_ASCENDENT;
        else t = TipusOrdenacio.ALFABETIC_DESCENDENT;
        ctrlPresentacio.cercaPrefix(prefix, t, esborrar);
        esborrar = false;
    }

    private void esborraCerca(){
        enviarDades(new ArrayList<>());
        esborrar = true;
        totsAutors.setEnabled(true);
        totsAutors.setSelected(false);
        omplirPrefix.setText("");
        mostraButton.setEnabled(false);
    }

}
