package Codi.Presentacio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewAjuda {
    private JPanel panell;
    private JButton tancar_button;
    private JLabel text_ajuda;
    private JFrame frame;

    public ViewAjuda() {
        inicialitzarComponents();
        configurarVista();
        assignarListeners();
    }

    private String setText() {
        return "Text d'ajuda";
    }

    private void inicialitzarComponents() {
        frame = new JFrame("Ajuda");
        panell = new JPanel();
        tancar_button = new JButton("Tancar");
        text_ajuda = new JLabel(setText());
    }

    private void configurarVista() {
        frame.setLayout(new BorderLayout());
        frame.add(panell,BorderLayout.CENTER);

        BorderLayout border = new BorderLayout();
        panell.setLayout(border);
        panell.add(tancar_button,BorderLayout.EAST);
        panell.add(text_ajuda,BorderLayout.CENTER);

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
    }

    public void ferVisible() {
        frame.setVisible(true);
    }
}
