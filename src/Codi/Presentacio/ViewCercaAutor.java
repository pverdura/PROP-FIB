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

    public ViewCercaAutor (CtrlPresentacio cp) {
        this.ctrlPresentacio = cp;
        inicialitzar();
    }

    private void inicialitzar () {
        inicialitzarComponents();
        configurarVista();
        configurarPanellSuperior();
        configurarPanellInferior();
        assignarListeners();
    }

    private void inicialitzarComponents () {
        frame = new JFrame("Cerca per Autor");
        panellSuperior = new JPanel();
        panellInferior = new JPanel();
        btCancelar = new JButton("Cancel·lar");
        btAcceptar = new JButton("Acceptar");
        textAutor = new JTextField();
        label = new JLabel("Autor: ");
    }

    private void configurarVista () {
        frame.setLayout(new BorderLayout());
        frame.add(panellSuperior, BorderLayout.CENTER);
        frame.add(panellInferior, BorderLayout.SOUTH);


        frame.setMinimumSize(new Dimension(400, 125));
        frame.setPreferredSize(frame.getMinimumSize());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    private void configurarPanellSuperior () {
        panellSuperior.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        textAutor.setMinimumSize(new Dimension(250, 30));
        textAutor.setPreferredSize(textAutor.getMinimumSize());
        panellSuperior.add(label);
        panellSuperior.add(textAutor);
    }

    private void configurarPanellInferior () {
        BorderLayout bl = new BorderLayout();
        panellInferior.setLayout(bl);
        panellInferior.add(btCancelar, BorderLayout.WEST);
        panellInferior.add(btAcceptar, BorderLayout.EAST);
    }

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

    public void ferVisible (boolean visible) {
        textAutor.setText("");
        if (visible) frame.pack();
        frame.setVisible(visible);
    }

    private class Tecles extends KeyAdapter {
        private boolean control = false;
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
