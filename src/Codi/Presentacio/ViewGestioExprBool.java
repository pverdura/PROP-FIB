package Codi.Presentacio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewGestioExprBool extends JFrame implements ActionListener{

    private JPanel boolPanel;
    private JButton createButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextArea textArea1;


    private CtrlPresentacio ctrlPresentacio;

    public ViewGestioExprBool(CtrlPresentacio ctrlPresentacio) {
        this.ctrlPresentacio = ctrlPresentacio;

        //Afegir listeners per detectar quan es clica cada boto
        createButton.addActionListener(this);
        deleteButton.addActionListener(this);
        updateButton.addActionListener(this);

        //TODO: CARGAR AL TEXT AREA TOTES LES EXPRESSIONS
    }

    //Metode per visualitzar vista
    public void posar_visible() {
        setContentPane(this.boolPanel);
        setTitle("Gestió d'Expressions Booleanes");
        setSize(350, 350);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        //Realitzar funcionalitat del boto que s ha clicat
        if (source == createButton) {

            Object[] options = {"Crear", "Sortir"};
            String[] input = customDialog(options, "Crear Expressió Booleana", "Valor", 1);

            if (input[0] != null)  {
                //TODO: crear expressio booleana amb valor inputs[0]
            }

        } else if (source == deleteButton) {
            Object[] options = {"Elimina", "Sortir"};
            String[] input = customDialog(options, "Eliminar Expressió Booleana", "Valor", 1);

            if (input[0] != null)  {
                //TODO: eliminar expressio booleana amb valor inputs[0]
            }

        } else if (source == updateButton) {

            Object[] options = {"Modifica", "Sortir"};
            String[] input = customDialog(options, "Modificar Expressió Booleana", "Antic Valor", 2);

            if (input[0] != null)  {
                //TODO: modifica expressio booleana amb valor inputs[0] pel valor inputs[1]
            }
        }
    }

    private String[] customDialog(Object[] options, String title, String label1, int inputs) {

        //Crear panel del dialog
        JPanel panel = new JPanel();

        JTextField inputExpressio  = new JTextField(10);
        JTextField inputExpressioNova = new JTextField(10);

        //Primer input de crear, eliminar o modificar una expressio
        panel.add(new JLabel(label1));
        panel.add(inputExpressio);

        //Cas modificar expressio que necessita input del nou valor
        if (inputs == 2) {
            panel.add(new JLabel("Nou valor"));
            panel.add(inputExpressioNova);
        }

        //Crear dialog
        int result = JOptionPane.showOptionDialog(null, panel, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,null,options,null);

        //Comprobar si s'ha acceptat l operacio
        if (result == JOptionPane.YES_OPTION) {
            if (inputs == 2) return new String[]{inputExpressio.getText(), inputExpressioNova.getText()};

            return new String[]{inputExpressio.getText()};
        }

        return new String[]{null};
    }
}


