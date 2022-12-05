package Codi.Presentacio;

public class Main {

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        CtrlPresentacio ctrlPresentacio = new CtrlPresentacio();
                        ctrlPresentacio.init();
                    }
                }
        );
    }
}


