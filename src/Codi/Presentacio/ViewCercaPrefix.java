package Codi.Presentacio;

import Codi.Util.TipusOrdenacio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ViewCercaPrefix {

    ///////////////////////////////////////////////////////////
    ///                     VARIABLES                       ///
    ///////////////////////////////////////////////////////////
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
    private JList<Object> resCerca;
    private JScrollPane scroll;
    private JRadioButton asc;
    private JRadioButton des;
    private JCheckBox totsAutors;
    private boolean esborrar;       //indica si s'ha esborrat la cerca
    private boolean tots;           //indica si s'estan ensenyant tots els autors
    private int index;              //indica l'autor seleccionat

    private final CtrlPresentacio ctrlPresentacio;

    ///////////////////////////////////////////////////////////
    ///                      CONSTRUCTORA                   ///
    ///////////////////////////////////////////////////////////

    /**
     * Creadora per defecte
     * @param ctrlPresentacio Control presentacio
     */
    public ViewCercaPrefix (CtrlPresentacio ctrlPresentacio){
        this.ctrlPresentacio = ctrlPresentacio;
        inicialitza();
    }

    ///////////////////////////////////////////////////////////
    ///                  MÈTODES PÚBLICS                    ///
    ///////////////////////////////////////////////////////////

    /**
     * Fa visible o invisible la vista
     *
     * @param visible Indica si ha de ser o no visible
     */
    public void ferVisible(boolean visible){
        omplirPrefix.setText("");
        if (visible) frameVista.pack();
        frameVista.setVisible(visible);
        esborraCerca();
        asc.setSelected(true);
    }


    /**
     * Indica si la vista és visible
     *
     * @return {@code Boolean} Retorna true si és visible, false altrament
     */
    public Boolean esVisible(){
        return frameVista.isVisible();
    }


    /**
     * Mètode per rebre les dades de la Cerca Prefix
     *
     * @param resultatCerca {@code ArrayList<String>} Resultat de la Cerca Prefix
     */
    public void enviarDades(ArrayList<String> resultatCerca){
        DefaultListModel<Object> listModel = new DefaultListModel<>();

        for(String s : resultatCerca){
            listModel.addElement(s);
        }
        resCerca = new JList<>(listModel);
        resCerca.addKeyListener(new Tecles());

        resCerca.addListSelectionListener(e -> {
            if (!resCerca.getSelectionModel().isSelectionEmpty() && e.getSource()==resCerca && e.getValueIsAdjusting()){
                mostraButton.setEnabled(true);
            }
        });

        scroll.setViewportView(resCerca);
    }

    ///////////////////////////////////////////////////////////
    ///                  MÈTODES PRIVATS                    ///
    ///////////////////////////////////////////////////////////

    /////////////////////////// ASSIGNACIO DE LISTENERS

    /**
     * Assigna els listeners als components de la vista
     */
    private void assignarListeners(){
        cercaButton.addActionListener(e -> mostrarCerca());

        frameVista.addKeyListener(new Tecles());

        omplirPrefix.addKeyListener(new Tecles());

        scroll.addKeyListener(new Tecles());

        esborrarButton.addActionListener(e -> esborraCerca());

        cancelarButton.addActionListener(e -> ferVisible(false));

        asc.addItemListener(e -> {
            des.setSelected(!asc.isSelected());
            mostrarCerca();
        });

        des.addItemListener(e -> {
            asc.setSelected(!des.isSelected());
            mostrarCerca();
        });

        totsAutors.addActionListener(e -> {
            if (totsAutors.isSelected()) {
                tots = true;
                mostrarCerca();
            }
            else {
                tots = false;
                esborraCerca();
            }
        });

        mostraButton.addActionListener(e -> {
            if (!resCerca.getSelectionModel().isSelectionEmpty()){
                index = resCerca.getSelectedIndex();
                ctrlPresentacio.cercaAutor(resCerca.getModel().getElementAt(index).toString());
            }
        });

    }


    /**
     * Classe que implementa les dreceres de teclat per la vista
     */
    private class Tecles extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getExtendedKeyCode() == KeyEvent.VK_ENTER) cercaButton.doClick();
            else if (e.getExtendedKeyCode() == KeyEvent.VK_ESCAPE) cancelarButton.doClick();
        }
    }


    ////////////////////////// RESTA DE MÈTODES PRIVATS

    /**
     * Inicialitza i configura la vista
     */
    private void inicialitza() {
        inicialitzarComponents();
        configurarVista();
        configPrefixPanel();
        configResultatPanel();
        configButtonsPanel();
        assignarListeners();
    }

    /**
     * Inicialitza els components
     */
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
        cercaButton.setFocusable(false);
    }

    /**
     * Configura la vista
     */
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

    /**
     * Configura el panell per fer la cerca
     */
    private void configPrefixPanel(){
        prefixPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        omplirPrefix.setMinimumSize(new Dimension(230,30));
        omplirPrefix.setPreferredSize(omplirPrefix.getMinimumSize());

        prefixPanel.add(labelPrefix);
        prefixPanel.add(omplirPrefix);
        prefixPanel.add(cercaButton);
    }

    /**
     * Configura el panell on es mostra el resultat de la cerca
     */
    private void configResultatPanel(){
        resultatPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        scroll.setPreferredSize(new Dimension(350,300));
        resultatPanel.add(scroll, BorderLayout.CENTER);
    }

    /**
     * Configura el panell amb els botons
     */
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

    /**
     * Mostra la cerca
     */
    private void mostrarCerca(){
        String prefix = omplirPrefix.getText();
        if (!prefix.isEmpty() || tots) {
            if(!prefix.isEmpty()) {
                totsAutors.setSelected(false);
                totsAutors.setEnabled(false);
                tots = false;
            }
            mostraCerca(prefix);
        }
        else esborraCerca();
    }

    /**
     * Mostra la Cerca Prefix donat un prefix
     *
     * @param prefix Indica el prefix amb el qual es vol fer la cerca
     */
    private void mostraCerca(String prefix){
        TipusOrdenacio t;
        if (asc.isSelected()) t = TipusOrdenacio.ALFABETIC_ASCENDENT;
        else t = TipusOrdenacio.ALFABETIC_DESCENDENT;
        ctrlPresentacio.cercaPrefix(prefix, t, esborrar);
        esborrar = false;
        mostraButton.setEnabled(false);
    }


    /**
     * Esborra el resultat de la cerca
     */
    private void esborraCerca(){
        enviarDades(new ArrayList<>());
        esborrar = true;
        totsAutors.setEnabled(true);
        totsAutors.setSelected(false);
        omplirPrefix.setText("");
        mostraButton.setEnabled(false);
    }

}
