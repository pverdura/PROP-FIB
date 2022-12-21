package Codi.Presentacio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Classe que implementa la vista de cerca titol i autor
 *
 * @author Judit Serna
 */
public class ViewCercaTitolAutor {

    ///////////////////////////////////////////////////////////
    ///                     VARIABLES                       ///
    ///////////////////////////////////////////////////////////

    /**
     * Vista on s’inclouen tots els elements de la finestra
     */
    private JFrame frameVista;

    /**
     * Panell on hi ha l’etiqueta de Titol: i el quadre de text omplirTitol
     */
    private JPanel titolPanel;

    /**
     * Panell on hi ha l’etiqueta de Autor: i el quadre de text omplirAutor
     */
    private JPanel autorPanel;

    /**
     * Panell on hi ha els dos botons cancelarButton i acceptarButton
     */
    private JPanel buttonsPanel;

    /**
     * Boto per realitzar la cerca
     */
    private JButton acceptarButton;

    /**
     * Boto per sortir de la vista
     */
    private JButton cancelarButton;

    /**
     * Quadre de text per escriure un titol
     */
    private JTextField omplirTitol;

    /**
     * Quadre de text per escriure un autor
     */
    private JTextField omplirAutor;

    /**
     * Etiqueta del titol
     */
    private JLabel labelTitol;

    /**
     * Etiqueta de l'autor
     */
    private JLabel labelAutor;

    /**
     * Instancia de la classe Control Presentacio
     * Instancia de la classe Control Presentacio
     */
    private final CtrlPresentacio ctrlPresentacio;

    ///////////////////////////////////////////////////////////
    ///                      CONSTRUCTORA                   ///
    ///////////////////////////////////////////////////////////

    /**
     * Creadora per defecte
     * @param ctrlPresentacio Control presentacio
     */
    public ViewCercaTitolAutor (CtrlPresentacio ctrlPresentacio){
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
        if (visible) frameVista.pack();
        frameVista.setVisible(visible);
    }

    ///////////////////////////////////////////////////////////
    ///                  MÈTODES PRIVATS                    ///
    ///////////////////////////////////////////////////////////

    /////////////////////////// ASSIGNACIÓ DE LISTENERS

    /**
     * Assigna els listeners als components de la vista
     */
    private void assignarListeners(){
        acceptarButton.addActionListener(e -> ferCercaTitolAutor());

        omplirAutor.addKeyListener(new Tecles());
        omplirTitol.addKeyListener(new Tecles());
        frameVista.addKeyListener(new Tecles());
        cancelarButton.addActionListener(e -> ferVisible(false));
    }

    /**
     * Classe que implementa les dreceres de teclat per la vista
     */
    private class Tecles extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getExtendedKeyCode() == KeyEvent.VK_ENTER) acceptarButton.doClick();
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
        configTitolPanel();
        configAutorPanel();
        configButtonsPanel();
        assignarListeners();
    }

    /**
     * Inicialitza els components
     */
    private void inicialitzarComponents(){
        frameVista = new JFrame("Cerca per per Títol i Autor");
        titolPanel = new JPanel();
        autorPanel = new JPanel();
        buttonsPanel = new JPanel();
        cancelarButton = new JButton("Cancel·lar");
        acceptarButton = new JButton("Acceptar");
        omplirTitol = new JTextField();
        omplirAutor = new JTextField();
        labelTitol = new JLabel("Títol: ");
        labelAutor = new JLabel("Autor: ");

        acceptarButton.setFocusable(false);
        cancelarButton.setFocusable(false);
    }

    /**
     * Configura la vista
     */
    private void configurarVista(){
        frameVista.setLayout(new BorderLayout());
        frameVista.add(titolPanel, BorderLayout.NORTH);
        frameVista.add(autorPanel, BorderLayout.CENTER);
        frameVista.add(buttonsPanel, BorderLayout.SOUTH);

        frameVista.setMinimumSize(new Dimension(400, 175));
        frameVista.setPreferredSize(frameVista.getMinimumSize());
        frameVista.setResizable(false);
        frameVista.setLocationRelativeTo(null);
    }

    /**
     * Configura el panell per escriure el titol
     */
    private void configTitolPanel(){
        titolPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

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
     * Configura el panell amb els botons
     */
    private void configButtonsPanel(){
        BorderLayout b = new BorderLayout();
        buttonsPanel.setLayout(b);
        buttonsPanel.add(cancelarButton, BorderLayout.WEST);
        buttonsPanel.add(acceptarButton, BorderLayout.EAST);
    }

    /**
     * Fa la cerca
     */
    private void ferCercaTitolAutor(){
        String titol = omplirTitol.getText();
        String autor = omplirAutor.getText();
        ctrlPresentacio.cercaTitolAutor(titol, autor);
    }
}

