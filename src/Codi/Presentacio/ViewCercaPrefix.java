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
    private JButton esborrarButton;
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
        omplirPrefix.setText("");
        if (visible) frameVista.pack();
        frameVista.setVisible(visible);
        esborraCerca();
    }

    public Boolean esVisible(){
        return frameVista.isVisible();
    }


    public void tancarVista(){
        frameVista.dispatchEvent(new WindowEvent(frameVista, WindowEvent.WINDOW_CLOSING));
    }


    public void enviarDades(ArrayList<String> res_cerca){
        DefaultListModel<String> listModel = new DefaultListModel<>();

        for(String s : res_cerca){
            listModel.addElement(s);
            //listModel.addElement("\n");
        }
        final JList<String> resultat = new JList<>(listModel);

        scroll.setViewportView(resultat);
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
                ctrlPresentacio.cercaPrefix(prefix, t, false);
            }
        });

        cercaButton.addKeyListener(new TeclaEnter());

        frameVista.addKeyListener(new TeclaEnter());

        omplirPrefix.addKeyListener(new TeclaEnter());


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
                if (asc.isSelected()) des.setSelected(false);
                else des.setSelected(true);
                cercaButton.doClick();
            }
        });

        des.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (des.isSelected()) asc.setSelected(false);
                else asc.setSelected(true);
                cercaButton.doClick();
            }
        });
    }


    private class TeclaEnter extends KeyAdapter{
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
        esborrarButton = new JButton("Esborrar");
        cercaButton = new JButton("Cercar");
        omplirPrefix = new JTextField();
        labelPrefix = new JLabel("Prefix: ");
        resultat = new JTextArea(25,35);
        scroll = new JScrollPane();
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBar(null);
        asc = new JRadioButton("Ascendent");
        des = new JRadioButton("Descendent");


        scroll.setFocusable(false);
        asc.setFocusable(false);
        des.setFocusable(false);
        resultat.setFocusable(false);
        cancelarButton.setFocusable(false);
        esborrarButton.setFocusable(false);
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

        //omplirPrefix.setEditable(true);
    }

    private void configResultatPanel(){
        resultatPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        resultatPanel.add(resultat);
        resultatPanel.add(scroll, BorderLayout.CENTER);
        scroll.setPreferredSize(new Dimension(350,325));
        scroll.setViewportView(resultat);
    }

    private void configButtonsPanel(){
        BorderLayout b = new BorderLayout();
        buttonsPanel.setLayout(b);
        JPanel innerPanel = new JPanel(new BorderLayout());
        innerPanel.add(asc, BorderLayout.WEST);
        innerPanel.add(des, BorderLayout.EAST);

        innerPanel.setPreferredSize(new Dimension(150,30));

        asc.setSelected(true);

        buttonsPanel.add(cancelarButton, BorderLayout.EAST);
        buttonsPanel.add(innerPanel, BorderLayout.CENTER);
        buttonsPanel.add(esborrarButton, BorderLayout.WEST);
    }

    private void esborraCerca(){
        enviarDades(new ArrayList<>());
    }
}
