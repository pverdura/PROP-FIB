package Codi.Presentacio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ViewCercaSemblant {

    ///////////////////////////////////////////////////////////
    ///                     VARIABLES                       ///
    ///////////////////////////////////////////////////////////
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
    private JSpinner numDocs;
    private JCheckBox totsDocs;
    private final CtrlPresentacio ctrlPresentacio;

    ///////////////////////////////////////////////////////////
    ///                      CONSTRUCTORA                   ///
    ///////////////////////////////////////////////////////////

    /**
     * Creadora per defecte
     * @param ctrlPresentacio Control presentacio
     */
    public ViewCercaSemblant(CtrlPresentacio ctrlPresentacio){
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
        omplirTitol.setText("");
        omplirAutor.setText("");
        numDocs.setValue(1);
        if (visible) frameVista.pack();
        frameVista.setVisible(visible);
    }

    ///////////////////////////////////////////////////////////
    ///                  MÈTODES PRIVATS                    ///
    ///////////////////////////////////////////////////////////

    /////////////////////////// ASSIGNACIO DE LISTENERS

    /**
     * Assigna els listeners als components de la vista
     */
    private void assignarListeners(){
        acceptarButton.addActionListener(e -> ferCercaSemblant());

        omplirAutor.addKeyListener(new Tecles());
        omplirTitol.addKeyListener(new Tecles());
        frameVista.addKeyListener(new Tecles());

        cancelarButton.addActionListener(e -> ferVisible(false));

        totsDocs.addItemListener(e -> numDocs.setEnabled(!totsDocs.isSelected()));
    }

    /**
     * Classe que implementa les dreceres de teclat per la vista
     */
    private class Tecles extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getExtendedKeyCode() == KeyEvent.VK_ENTER) acceptarButton.doClick();
            else if (e.getExtendedKeyCode() == KeyEvent.VK_ESCAPE) cancelarButton.doClick();
            else if (e.getExtendedKeyCode() == KeyEvent.VK_UP) numDocs.setValue((int)numDocs.getValue()+1);
            else if (e.getExtendedKeyCode() == KeyEvent.VK_DOWN) numDocs.setValue(Math.max((int)numDocs.getValue()-1, 1));
        }
    }

    ////////////////////////// RESTA DE MÈTODES PRIVATS

    /**
     * Inicialitza i configura la vista
     */
    private void inicialitza(){
        inicialitzarComponents();
        configurarVista();
        configTitolPanel();
        configAutorPanel();
        configNumDocsPanel();
        configButtonsPanel();
        assignarListeners();
    }

    /**
     * Inicialitza els components
     */
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

        SpinnerNumberModel model = new SpinnerNumberModel();
        model.setValue(0);
        model.setMinimum(1);
        model.setStepSize(1);
        numDocs = new JSpinner(model);

        labelTitol = new JLabel("Títol: ");
        labelAutor = new JLabel("Autor: ");
        labelNumDocs = new JLabel("Nº documents: ");
        totsDocs = new JCheckBox("Tots els documents");

        numDocs.setFocusable(false);
        totsDocs.setFocusable(false);
        cancelarButton.setFocusable(false);
        acceptarButton.setFocusable(false);
    }

    /**
     * Configura la vista
     */
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
        frameVista.setLocationRelativeTo(null);
    }

    /**
     * Configura el panell per escriure el titol
     */
    private void configTitolPanel(){
        titolPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10, 19));

        omplirTitol.setMinimumSize(new Dimension(250,30));
        omplirTitol.setPreferredSize(omplirTitol.getMinimumSize());

        titolPanel.add(labelTitol);
        titolPanel.add(omplirTitol);
    }

    /**
     * Configura el panell per escriure l'autor
     */
    private void configAutorPanel(){
        autorPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        omplirAutor.setMinimumSize(new Dimension(250, 30));
        omplirAutor.setPreferredSize(omplirTitol.getMinimumSize());

        autorPanel.add(labelAutor);
        autorPanel.add(omplirAutor);
    }

    /**
     * Configura el panell per indicar el nombre de documents
     */
    private void configNumDocsPanel(){
        numPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        numDocs.setMinimumSize(new Dimension(50,25));
        numDocs.setPreferredSize(numDocs.getMinimumSize());

        numPanel.add(labelNumDocs);
        numPanel.add(numDocs);
    }

    /**
     * Configura el panell amb els botons
     */
    private void configButtonsPanel(){
        BorderLayout b = new BorderLayout();
        buttonsPanel.setLayout(b);
        buttonsPanel.add(cancelarButton, BorderLayout.WEST);
        buttonsPanel.add(totsDocs, BorderLayout.CENTER);
        buttonsPanel.add(acceptarButton, BorderLayout.EAST);
    }

    /**
     * Fa la cerca
     */
    private void ferCercaSemblant(){
        String titol = omplirTitol.getText();
        String autor = omplirAutor.getText();

        int k = (int)numDocs.getValue();
        boolean tots = totsDocs.isSelected();

        ctrlPresentacio.cercaSemblant(titol, autor, k, tots);
    }
}
