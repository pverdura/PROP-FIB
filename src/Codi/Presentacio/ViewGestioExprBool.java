package Codi.Presentacio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;

public class ViewGestioExprBool extends JFrame implements ActionListener{

    private JPanel boolPanel;
    private JButton createButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextArea textArea1;
    private JButton searchButton;
    private JButton backButton;
    private JScrollPane scroll;


    private CtrlPresentacio ctrlPresentacio;

    public ViewGestioExprBool(CtrlPresentacio ctrlPresentacio) {
        this.ctrlPresentacio = ctrlPresentacio;
        this.textArea1 = new JTextArea(30,1);
        this.textArea1.setEditable(false);
        this.scroll.setViewportView(textArea1);


        //Inicialitzar components de la vista
        setContentPane(this.boolPanel);
        setTitle("Gestió d'Expressions Booleanes");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Afegir listeners per detectar quan es clica cada boto
        createButton.addActionListener(this);
        deleteButton.addActionListener(this);
        updateButton.addActionListener(this);
        searchButton.addActionListener(this);
        backButton.addActionListener(this);

        //Carregar expressions a la vista
        carregaExpressions();
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

        } else if (source == searchButton) {
            //TODO: fer dialog i realitzar cerca booleana

        } else if (source == backButton) {
            ctrlPresentacio.tancarGestioExprBool();
        }
    }

    //Metode per carregar les expressions per pantalla
    private void carregaExpressions() {

        try {

            FileReader fr = new FileReader("src/Codi/Persistencia/expressions.csv");
            BufferedReader br = new BufferedReader(fr);
            textArea1.setVisible(true);
            String expressio;
            while ((expressio = br.readLine()) != null) {
                textArea1.append(expressio + "\n");
            }
            br.close();

        } catch (Exception e){
            e.printStackTrace();
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

    //Metode per visualitzar vista
    public void ferVisible() {
        setSize(500, 500);
        setVisible(true);
    }

    //Metode per deixar de visualitzar vista
    public void ferInvisible() {
        setVisible(false);
    }
}


