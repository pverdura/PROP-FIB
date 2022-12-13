package Codi.Presentacio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ViewCercaParaules {
    private final CtrlPresentacio ctrlPresentacio;

    private JFrame frame;
    private JPanel panellSuperior, panellMig, panellInferior;
    private JButton btCancelar;
    private JButton btAcceptar;
    private JTextField textParaules;
    private JSpinner textNombreDocuments;
    private JLabel label1, label2;
    private JCheckBox totsDocuments;

    public ViewCercaParaules (CtrlPresentacio cp) {
        this.ctrlPresentacio = cp;
        inicialitzar();
    }

    private void inicialitzar () {
        inicialitzarComponents();
        configurarVista();
        configurarPanellSuperior();
        configurarPanellMig();
        configurarPanellInferior();
        assignarListeners();
    }

    private void inicialitzarComponents () {
        frame = new JFrame("Cerca per Paraules");
        panellSuperior = new JPanel();
        panellMig = new JPanel();
        panellInferior = new JPanel();
        btCancelar = new JButton("Cancel·lar");
        btAcceptar = new JButton("Acceptar");
        textParaules = new JTextField();
        SpinnerNumberModel model = new SpinnerNumberModel();
        model.setValue(0);
        model.setMinimum(0);
        model.setStepSize(1);
        textNombreDocuments = new JSpinner(model);
        label1 = new JLabel("Paraules: ");
        label2 = new JLabel("Nª documents: ");
        totsDocuments = new JCheckBox("Tots els documents", false);
    }

    private void configurarVista () {
        frame.setLayout(new BorderLayout());
        frame.add(panellSuperior, BorderLayout.NORTH);
        frame.add(panellMig, BorderLayout.CENTER);
        frame.add(panellInferior, BorderLayout.SOUTH);

        frame.setMinimumSize(new Dimension(500, 175));
        frame.setPreferredSize(frame.getMinimumSize());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    private void configurarPanellSuperior () {
        panellSuperior.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        textParaules.setMinimumSize(new Dimension(250, 30));
        textParaules.setPreferredSize(textParaules.getMinimumSize());
        panellSuperior.add(label1);
        panellSuperior.add(textParaules);
    }

    private void configurarPanellMig () {
        panellMig.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        textNombreDocuments.setMinimumSize(new Dimension(50, 25));
        textNombreDocuments.setPreferredSize(textNombreDocuments.getMinimumSize());
        panellMig.add(label2);
        panellMig.add(textNombreDocuments);
    }

    private void configurarPanellInferior () {
        BorderLayout bl = new BorderLayout();
        panellInferior.setLayout(bl);
        panellInferior.add(btCancelar, BorderLayout.WEST);
        panellInferior.add(totsDocuments, BorderLayout.CENTER);
        panellInferior.add(btAcceptar, BorderLayout.EAST);
    }

    private void assignarListeners () {
        btAcceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ferCerca();
            }
        });

        btCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ferVisible(false);
            }
        });

        totsDocuments.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged (ItemEvent e) {
                textNombreDocuments.setEnabled(!totsDocuments.isSelected());
            }
        });
        frame.addKeyListener(new Tecles());
        textParaules.addKeyListener(new Tecles());
        textNombreDocuments.addKeyListener(new Tecles());
    }

    public void ferVisible (boolean visible) {
        textParaules.setText("");
        textNombreDocuments.setValue(0);
        if (visible) frame.pack();
        frame.setVisible(visible);
    }

    private void ferCerca () {
        String text = textParaules.getText();
        int k = (int)textNombreDocuments.getValue();
        boolean tots = totsDocuments.isSelected();
        ctrlPresentacio.cercaParaules(text, k, tots);
    }

    private int max (int a, int b) {if (a > b) return a; return b;}

    private class Tecles extends KeyAdapter {
        private boolean control = false;
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
                ferCerca();
            } else if (e.getExtendedKeyCode() == KeyEvent.VK_ESCAPE) {
                ferVisible(false);
            } else if (e.getExtendedKeyCode() == KeyEvent.VK_UP) {
                textNombreDocuments.setValue((int)textNombreDocuments.getValue()+1);
            } else if (e.getExtendedKeyCode() == KeyEvent.VK_DOWN) {
                textNombreDocuments.setValue(max((int)textNombreDocuments.getValue()-1, 0));
            }
        }
    }
}
