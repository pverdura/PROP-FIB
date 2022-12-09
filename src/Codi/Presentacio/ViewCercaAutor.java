package Codi.Presentacio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class ViewCercaAutor {
    private CtrlPresentacio ctrlPresentacio;
    private JFrame frame;
    private JPanel panellSuperior, panellInferior;
    private JButton btCancelar;
    private JButton btAcceptar;
    private JTextField textTitol;
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
        textTitol = new JTextField();
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

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void configurarPanellSuperior () {
        panellSuperior.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        textTitol.setMinimumSize(new Dimension(250, 30));
        textTitol.setPreferredSize(textTitol.getMinimumSize());
        panellSuperior.add(label);
        panellSuperior.add(textTitol);
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
                String text = textTitol.getText();
                if (ctrlPresentacio.cercaAutor(text)) {
                    //tancar
                    System.out.println("cerca feta correctament");
                } else {
                    //mostrar missatge d'error
                    VistaDialeg.errorDialog("Error a la cerca");
                    System.out.println("error");
                }
            }
        });

        btCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ferVisible(false);
            }
        });

    }

    public void ferVisible (boolean visible) {
        if (visible) frame.pack();
        frame.setVisible(visible);
    }

    public void tancarVista () {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
