package Codi.Presentacio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ViewCercaTitolAutor {

    private JFrame frameVista;
    private JPanel titolPanel, autorPanel;
    private JPanel buttonsPanel;
    private JButton acceptarButton;
    private JButton cancelarButton;
    private JTextField omplirTitol;
    private JTextField omplirAutor;
    private JLabel labelTitol;
    private JLabel labelAutor;

    private final CtrlPresentacio ctrlPresentacio;

    /////////////////////////////// CONSTRUCTOR I MÈTODES PÚBLICS
    public ViewCercaTitolAutor (CtrlPresentacio ctrlPresentacio){
        this.ctrlPresentacio = ctrlPresentacio;
        inicialitza();
    }

    public void ferVisible(boolean visible){
        omplirTitol.setText("");
        omplirAutor.setText("");
        if (visible) frameVista.pack();
        frameVista.setVisible(visible);
    }


    /////////////////////////// ASSIGNACIÓ DE LISTENERS

    private void assignarListeners(){
        acceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { ferCercaTitolAutor(); }
        });

        omplirAutor.addKeyListener(new Tecles());
        omplirTitol.addKeyListener(new Tecles());
        frameVista.addKeyListener(new Tecles());
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ferVisible(false);
            }
        });
    }

    private class Tecles extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getExtendedKeyCode() == KeyEvent.VK_ENTER) acceptarButton.doClick();
            else if (e.getExtendedKeyCode() == KeyEvent.VK_ESCAPE) cancelarButton.doClick();
        }
    }


    ////////////////////////// RESTA DE MÈTODES PRIVATS
    private void inicialitza() {
        inicialitzarComponents();
        configurarVista();
        configTitolPanel();
        configAutorPanel();
        configButtonsPanel();
        assignarListeners();
    }

    private void inicialitzarComponents(){
        frameVista = new JFrame("Cerca per Títol i Autor");
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

    private void configurarVista(){
        frameVista.setLayout(new BorderLayout());
        frameVista.add(titolPanel, BorderLayout.NORTH);
        frameVista.add(autorPanel, BorderLayout.CENTER);
        frameVista.add(buttonsPanel, BorderLayout.SOUTH);

        frameVista.setMinimumSize(new Dimension(400, 175));
        frameVista.setPreferredSize(frameVista.getMinimumSize());
        frameVista.setResizable(false);
    }

    private void configTitolPanel(){
        titolPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        omplirTitol.setMinimumSize(new Dimension(250,30));
        omplirTitol.setPreferredSize(omplirTitol.getMinimumSize());

        titolPanel.add(labelTitol);
        titolPanel.add(omplirTitol);
    }

    private void configAutorPanel(){
        autorPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        omplirAutor.setMinimumSize(new Dimension(250, 30));
        omplirAutor.setPreferredSize(omplirTitol.getMinimumSize());

        autorPanel.add(labelAutor);
        autorPanel.add(omplirAutor);
    }

    private void configButtonsPanel(){
        BorderLayout b = new BorderLayout();
        buttonsPanel.setLayout(b);
        buttonsPanel.add(cancelarButton, BorderLayout.WEST);
        buttonsPanel.add(acceptarButton, BorderLayout.EAST);
    }

    private void ferCercaTitolAutor(){
        String titol = omplirTitol.getText();
        String autor = omplirAutor.getText();
        ctrlPresentacio.cercaTitolAutor(titol, autor);
    }
}

