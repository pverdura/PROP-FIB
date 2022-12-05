package Codi.Presentacio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewGestioExprBool extends JFrame{

    private JPanel boolPanel;
    private JPanel updateExprPanel;
    private JPanel currentExprPanel;
    private JButton createButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JPanel buttonPanel;
    private JTextField textFieldExpr;
    private JTextField textFieldNovaExpr;

    private CtrlPresentacio ctrlPresentacio;
    private boolean updatePanelVisible;

    public ViewGestioExprBool(CtrlPresentacio ctrlPresentacio) {

        this.ctrlPresentacio = ctrlPresentacio;
        updatePanelVisible = false;

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expressio_text = textFieldExpr.getText();
                JOptionPane.showMessageDialog(createButton, expressio_text);
                textFieldExpr.setText("");
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expressio_text = textFieldExpr.getText();
                JOptionPane.showMessageDialog(deleteButton, expressio_text);
                textFieldExpr.setText("");
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Si no es visible
                if (!updatePanelVisible) {

                    String expressio_text = textFieldExpr.getText();
                    //TODO: Comprobar si existeix expressio amb try_catch

                    updateExprPanel.setVisible(true);
                    textFieldExpr.setEditable(false);

                    createButton.setEnabled(false);
                    deleteButton.setEnabled(false);

                } else {
                    String expressio_text = textFieldExpr.getText();
                    String expressio_nova_text = textFieldNovaExpr.getText();

                    //TODO: Crida funcio d'actualitzar
                    updateExprPanel.setVisible(false);
                    textFieldExpr.setEditable(true);

                    createButton.setEnabled(true);
                    deleteButton.setEnabled(true);

                    textFieldExpr.setText("");
                    textFieldNovaExpr.setText("");
                }

                updatePanelVisible = !updatePanelVisible;
            }
        });
    }

    public void posar_visible() {
        setContentPane(this.boolPanel);
        setTitle("Gestió d'Expressions Booleanes");
        setSize(350, 350);
        setVisible(true);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}


