package Codi.Presentacio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ViewCercaAutor {
    private final CtrlPresentacio ctrlPresentacio;
    private JFrame frame;
    private JPanel panellSuperior, panellInferior;
    private JButton btCancelar;
    private JButton btAcceptar;
    private JTextField textAutor;
    private JLabel label;

    /**
     * Constructor
     *
     * @param cp Control presentació
     */
    public ViewCercaAutor (CtrlPresentacio cp) {
        this.ctrlPresentacio = cp;
        inicialitzar();
    }

    /**
     * Fa la vista visible o invisible
     *
     * @param visible si ha de ser visible o no
     */

    public void ferVisible (boolean visible) {
        textAutor.setText("");
        if (visible) frame.pack();
        frame.setVisible(visible);
    }

    ///////////////////////////////////////////////////////////
    ///                  MÈTODES PRIVATS                    ///
    ///////////////////////////////////////////////////////////

    /**
     * Inicialitza i configura la vista
     */
    private void inicialitzar () {
        inicialitzarComponents();
        configurarVista();
        configurarPanellSuperior();
        configurarPanellInferior();
        assignarListeners();
    }

    /**
     * Inicialitza els components
     */
    private void inicialitzarComponents () {
        frame = new JFrame("Cerca per Autor");
        panellSuperior = new JPanel();
        panellInferior = new JPanel();
        btCancelar = new JButton("Cancel·lar");
        btAcceptar = new JButton("Acceptar");
        textAutor = new JTextField();
        label = new JLabel("Autor: ");
    }

    /**
     * Configura la vista
     */
    private void configurarVista () {
        frame.setLayout(new BorderLayout());
        frame.add(panellSuperior, BorderLayout.CENTER);
        frame.add(panellInferior, BorderLayout.SOUTH);


        frame.setMinimumSize(new Dimension(400, 125));
        frame.setPreferredSize(frame.getMinimumSize());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Configura el panell superior
     */
    private void configurarPanellSuperior () {
        panellSuperior.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        textAutor.setMinimumSize(new Dimension(250, 30));
        textAutor.setPreferredSize(textAutor.getMinimumSize());
        panellSuperior.add(label);
        panellSuperior.add(textAutor);
    }

    /**
     * Configura el panell inferior
     */
    private void configurarPanellInferior () {
        BorderLayout bl = new BorderLayout();
        panellInferior.setLayout(bl);
        panellInferior.add(btCancelar, BorderLayout.WEST);
        panellInferior.add(btAcceptar, BorderLayout.EAST);
    }

    /**
     * Assigna els listeners als components de la vista
     */
    private void assignarListeners () {
        btAcceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ctrlPresentacio.cercaAutor(textAutor.getText());
            }
        });

        btCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ferVisible(false);
            }
        });
        frame.addKeyListener(new Tecles());
        textAutor.addKeyListener(new Tecles());
    }

    /**
     * Classe que implementa les dreceres de teclat per la vista
     */
    private class Tecles extends KeyAdapter {
        private boolean control = false;

        /**
         * Sobreescriptura del mètode de tecla premuda
         * Enter fa la cerca
         * Esc tanca la vista
         *
         * @param e l'event a ser processat
         */
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
                ctrlPresentacio.cercaAutor(textAutor.getText());
            } else if (e.getExtendedKeyCode() == KeyEvent.VK_ESCAPE) {
                ferVisible(false);
            }
        }
    }
}
