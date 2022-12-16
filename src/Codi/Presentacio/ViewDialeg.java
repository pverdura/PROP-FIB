package Codi.Presentacio;

import javax.swing.*;
import java.util.AbstractMap.SimpleEntry;

/**
 * Classe que gestiona diferents dialegs necessaris dins les vistes
 * @author PauVi
 */
public class ViewDialeg {


    /**
     * Funcio estatica que crea un dialeg amb un camp a omplir
     * @param titol Titol del dialeg
     * @param label Nom del camp que voldrem omplir
     * @param options Opcions resultants a mostrar
     * @return Retorna els resultats dels camps a omplir
     */
    public static String inputDialog(String titol, String label, Object[] options) {

        //Crear panel input
        JPanel panel = new JPanel();
        panel.add(new JLabel(label));
        JTextField tf = new JTextField(10);
        panel.add(tf);

        //Crear dialogo con panel creado anteriormente
        int res = JOptionPane.showOptionDialog(null, panel, titol, JOptionPane.YES_NO_OPTION,
                                                    JOptionPane.PLAIN_MESSAGE,null, options, null);

        if (res == JOptionPane.YES_OPTION) return tf.getText();

        return null;
    }

    /**
     * Funcio estatica que crea un dialeg amb dos camps a omplir
     * @param titol Titol del dialeg
     * @param label1 Nom del primer camp per omplir
     * @param label2 Nom del segon camp per omplir
     * @param textInp1 Text que inicialment omple el primer camp
     * @param options Opcions resultants a mostrar
     * @return Retorna els resultats dels camps a omplir
     */
    public  static SimpleEntry<String,String> twoInputDialog(String titol, String label1, String label2, String textInp1, Object[] options) {

        //Crear panel inputs
        JPanel panel = new JPanel();

        panel.add(new JLabel(label1));
        JTextField tf1 = new JTextField(10);
        tf1.setText(textInp1);
        tf1.setEditable(false);
        panel.add(tf1);

        JTextField tf2 = new JTextField(10);
        panel.add(new JLabel(label2));
        panel.add(tf2);

        //Crear dialogo con panel creado anteriormente
        int res = JOptionPane.showOptionDialog(null, panel, titol, JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,null, options, null);

        if (res == JOptionPane.YES_OPTION) return  new SimpleEntry<>(tf1.getText(), tf2.getText());

        return null;
    }

    /**
     * Metode que crea un dialeg per mostrar un error
     * @param error Text que mostra el dialeg
     */
    public static void errorDialog(String error) {
        //Crear dialog error
        JOptionPane.showMessageDialog(null, error,
                "Error detectat", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Metode que crea un dialeg per mostrar un missatge
     * @param titol Titol del dialeg
     * @param missatge Text del missatge a mostrar
     */
    public static void messageDialog(String titol, String missatge) {
        //Crear messageDialog
        JOptionPane.showMessageDialog(null, missatge, titol,  JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Funcio que crea un dialeg per confirmar si una accio es vol confirmar
     * @param missatge Text del missatge que indicara quina accio es vol confirmar
     * @return Retorna un boolea que indica si es confirma l'accio o no
     */
    public static Boolean confirmDialog(String missatge){
        //Crear confirm Dialog
        int res = JOptionPane.showConfirmDialog(null, missatge, "Confirmació",
                                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

        //Si res == 0 significa que se ha aceptado la confirmacion
        return res == 0;
    }
}
