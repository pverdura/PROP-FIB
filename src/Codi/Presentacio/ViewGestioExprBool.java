package Codi.Presentacio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewGestioExprBool extends JFrame{

    private JPanel boolPanel;
    private JButton createButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextArea textArea1;


    private CtrlPresentacio ctrlPresentacio;

    public ViewGestioExprBool(CtrlPresentacio ctrlPresentacio) {

        this.ctrlPresentacio = ctrlPresentacio;
    }

    public void posar_visible() {
        setContentPane(this.boolPanel);
        setTitle("Gestió d'Expressions Booleanes");
        setSize(350, 350);
        setVisible(true);
    }
}


