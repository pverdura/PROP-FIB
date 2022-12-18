package Codi.Presentacio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Vista que ajuda a l'usuari a com usar l'aplicacio
 *
 * @author Pol Verdura
 */
public class ViewAjuda {

    ///////////////////////////////////////////////////////////
    ///                     ESTRUCTURES                     ///
    ///////////////////////////////////////////////////////////

    private JPanel panellSuperior;
    private JPanel panellInferior;
    private JButton tancar_button;
    private JTextArea text_ajuda;
    private JFrame frame;


    ///////////////////////////////////////////////////////////
    ///                      FUNCIONS                       ///
    ///////////////////////////////////////////////////////////

    /**
     * Creadora que inicialitza la vista
     */
    public ViewAjuda() {
        inicialitzarComponents();
        configurarVista();
        assignarListeners();
    }

    /**
     * Inicialitza les estructures de la vista
     */
    private void inicialitzarComponents() {
        frame = new JFrame("Ajuda");
        panellSuperior = new JPanel();
        panellInferior = new JPanel();
        tancar_button = new JButton("Tancar");
        text_ajuda = new JTextArea("AJUDA");
    }

    /**
     * Configura les estructures de la vista
     */
    private void configurarVista() {
        frame.setLayout(new BorderLayout());
        frame.add(panellSuperior,BorderLayout.NORTH);
        frame.add(panellInferior,BorderLayout.SOUTH);

        panellSuperior.add(text_ajuda,BorderLayout.CENTER);
        panellInferior.add(tancar_button,BorderLayout.SOUTH);

        frame.setMinimumSize(new Dimension(400,400));
        frame.setPreferredSize(frame.getMinimumSize());
        frame.setLocationRelativeTo(null);

        text_ajuda.setEditable(false);
    }

    /**
     * Assigna a les estructures de la vista uns listeners
     */
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

    /**
     * Fa que la vista sigui visible
     */
    public void ferVisible() {
        frame.setVisible(true);
    }

    /**
     * Configura l'us de les tecles
     */
    private class TeclaAjuda extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
                frame.setVisible(false);
            }
        }
    }
}
