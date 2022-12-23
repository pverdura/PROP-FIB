package Codi.Presentacio;

/**
 * Classe que executa l'aplicacio
 *
 * @author PauVi
 */
public class Main {

    /**
     * Obre l'aplicacio creant un control de presentacio que sera inicialitzat
     * @param args Arguments
     */
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


