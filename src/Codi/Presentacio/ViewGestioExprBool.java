package Codi.Presentacio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

public class ViewGestioExprBool extends JFrame implements ActionListener{

    private JPanel boolPanel;
    private JButton createButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextArea textArea1;
    private JButton searchButton;
    private JScrollPane scroll;


    private final CtrlPresentacio ctrlPresentacio;

    public ViewGestioExprBool(CtrlPresentacio ctrlPresentacio) {
        this.ctrlPresentacio = ctrlPresentacio;

        //Inicialitzar el scrollPanel i el text area associat
        this.textArea1 = new JTextArea(100,1);
        this.textArea1.setVisible(true);
        this.textArea1.setEditable(false);
        this.scroll.setViewportView(textArea1);


        //Inicialitzar components principals de la vista
        setContentPane(this.boolPanel);
        setTitle("Gestió d'Expressions Booleanes");
        setSize(500, 500);

        //Afegir listeners per detectar quan es clica cada boto
        createButton.addActionListener(this);
        deleteButton.addActionListener(this);
        updateButton.addActionListener(this);
        searchButton.addActionListener(this);

        //Afegir totes les expressions booleanes al text area
        mostrarAllExpressions();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        //Realitzar funcionalitat del boto que s ha clicat
        if (source == createButton) {

            Object[] options = {"Crear", "Sortir"};
            String input = VistaDialeg.inputDialog("Crear Expressió Booleana", "Valor", options);

            //Si input != null significa que s ha clicat boto crear en el dialeg
            if (input != null) ctrlPresentacio.crearExprBool(input);


        } else if (source == deleteButton) {
            Object[] options = {"Elimina", "Sortir"};
            String input = VistaDialeg.inputDialog("Eliminar Expressió Booleana", "Valor", options);

            //Si input != null significa que s ha clicat boto crear en el dialeg
            if (input != null) ctrlPresentacio.eliminarExprBool(input);

        } else if (source == updateButton) {

            Object[] options = {"Modifica", "Sortir"};
            SimpleEntry<String, String> inputs = VistaDialeg.twoInputDialog("Modificar Expressió Booleana","Antic Valor", "Nou Valor", options);

            //Si input != null significa que s ha clicat boto modificar en el dialeg
            if (inputs != null) ctrlPresentacio.modificarExprBool(inputs.getKey(), inputs.getValue());

        } else if (source == searchButton) {

            Object[] options = {"Cerca", "Sortir"};
            String input = VistaDialeg.inputDialog("Cercar Expressió Booleana", "Valor", options);

            //Si sha clicat sortir en el dialeg -> no realitzara la cerca, en altre cas si
            if (input != null) ctrlPresentacio.cercaBooleana(input);
        }
    }

    //Metode per afegir les expressions booleanes a la vista
    private void mostrarAllExpressions() {
        ArrayList<String> expressions = ctrlPresentacio.getExpressionsBooleanes();
        for (String e: expressions) textArea1.append(e+"\n");
    }

    //Metode per visualitzar vista
    public void ferVisible() {
        setSize(500, 500);
        setVisible(true);
    }
}


