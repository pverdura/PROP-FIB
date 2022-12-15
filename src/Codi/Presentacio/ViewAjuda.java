package Codi.Presentacio;

import javax.swing.*;
import javax.swing.text.TextAction;
import java.awt.*;
import java.awt.event.*;

public class ViewAjuda {
    private JPanel panellSuperior;
    private JPanel panellInferior;
    private JButton tancar_button;
    private JLabel text_ajuda;
    private JFrame frame;

    public ViewAjuda() {
        inicialitzarComponents();
        configurarVista();
        assignarListeners();
    }

    private String setText() {
        return "AJUDA";
    }

    private void inicialitzarComponents() {
        frame = new JFrame("Ajuda");
        panellSuperior = new JPanel();
        panellInferior = new JPanel();
        tancar_button = new JButton("Tancar");
        text_ajuda = new JLabel(setText());
    }

    private void configurarVista() {
        frame.setLayout(new BorderLayout());
        frame.add(panellSuperior,BorderLayout.NORTH);
        frame.add(panellInferior,BorderLayout.SOUTH);

        panellSuperior.add(text_ajuda,BorderLayout.CENTER);
        panellInferior.add(tancar_button,BorderLayout.SOUTH);

        frame.setMinimumSize(new Dimension(400,400));
        frame.setPreferredSize(frame.getMinimumSize());
        frame.setLocationRelativeTo(null);
    }

    private void assignarListeners() {
        tancar_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });
        frame.addKeyListener(new TeclaAjuda());
        tancar_button.addKeyListener(new TeclaAjuda());
        text_ajuda.addKeyListener(new TeclaAjuda());
    }

    public void ferVisible() {
        frame.setVisible(true);
    }

    private class TeclaAjuda extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
                frame.setVisible(false);
            }
        }
    }
}
