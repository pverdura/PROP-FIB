package Codi.Presentacio;

import javax.swing.*;
import java.util.AbstractMap.SimpleEntry;

public class VistaDialeg {

    public static String inputDialog(String titol, String label, Object[] options) {

        JPanel panel = new JPanel();
        panel.add(new JLabel(label));
        JTextField tf = new JTextField(10);
        panel.add(tf);

        int res = JOptionPane.showOptionDialog(null, panel, titol, JOptionPane.YES_NO_OPTION,
                                                    JOptionPane.PLAIN_MESSAGE,null, options, null);

        if (res == JOptionPane.YES_OPTION) return tf.getText();

        return null;
    }

    public  static SimpleEntry<String,String> twoInputDialog(String titol, String label1, String label2, Object[] options) {

        JPanel panel = new JPanel();

        panel.add(new JLabel(label1));
        JTextField tf1 = new JTextField(10);
        panel.add(tf1);

        JTextField tf2 = new JTextField(10);
        panel.add(new JLabel(label2));
        panel.add(tf2);

        int res = JOptionPane.showOptionDialog(null, panel, titol, JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,null, options, null);

        if (res == JOptionPane.YES_OPTION) return  new SimpleEntry<>(tf1.getText(), tf2.getText());

        return null;
    }

    public static void errorDialog(String error) {
        JOptionPane.showMessageDialog(null, error,
                "Error detectat", JOptionPane.ERROR_MESSAGE);
    }

    public static void messageDialog(String titol, String missatge) {
        JOptionPane.showMessageDialog(null, missatge, titol,  JOptionPane.INFORMATION_MESSAGE);
    }
}
