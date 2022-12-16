package Codi.Presentacio;

import javax.swing.*;
import java.awt.event.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;


/**
 * Classe que gestiona la vista que organitza les expressions booleanes de l'aplicacio
 * @author PauVi
 */
public class ViewGestioExprBool extends JFrame implements ActionListener, KeyListener {

    /**
     * Instancia que fa referencia a l'element JPanel de la vista
     */
    private JPanel boolPanel;

    /**
     * Instancia que fa referencia a l'element JButton de la vista que al clicar crea una expressio
     */
    private JButton createButton;

    /**
     * Instancia que fa referencia a l'element JScrollPane de la vista on dintre seu mostra totes les expressions
     */
    private JScrollPane scroll;

    /**
     * Instancia JPopupMenu que serveix per mostrar un menu al clicar boto dret del ratoli sobre les expressions mostrades
     */
    private JPopupMenu rightClickMenu;

    /**
     * Instancies que fan referencia als elements JMenuItem del menu de tipus JPopupMenu
     */
    private JMenuItem miEliminar, miModificar, miCercar;

    /**
     /**
     * Instancia que fa referencia a l'element JList i que serveix per mostrar les expressions
     */
    private JList<String> llistaBool;

    /**
     * Instancia de la classe DefaultListModel que serveix per mostrar les expressions en l'atribut 'llistaBool' de tipus JList
     */
    private DefaultListModel<String> dlm;

    /**
     * Instancia que fa referencia a la classse CtrlPresentacio
     */
    private final CtrlPresentacio ctrlPresentacio;

    /**
     * Variable booleana que indica quan la tecla control esta sent clicada
     */
    private boolean control_selected;


    /**
     * Constructor
     * @param ctrlPresentacio Control Presentacio de l'aplicacio
     */
    public ViewGestioExprBool(CtrlPresentacio ctrlPresentacio) {
        this.ctrlPresentacio = ctrlPresentacio;
        this.control_selected = false;

        //Afegir listeners per detectar quan es clica cada boto
        createButton.addActionListener(this);

        //Configurar inicialment la vista
        configurar_vista();

        //Afegir totes les expressions booleanes al text area
        configurar_llista_expressions();

        //Configurar menu del click dret del ratoli i mostrar expressions per pantalla
        configure_pop_menu();
        mostrarAllExpressions();
    }

    /**
     * Metode que associa cada element interactiu amb una funcionalitat
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        //Realitzar funcionalitat del boto que s ha clicat
        if (source == createButton) {
            crearExpressio();

        }else if (source == miEliminar) {
            eliminarExpressio();

        } else if (source == miModificar) {
            modificarExpressio();
        }
        else if (source == miCercar) {
            if (!llistaBool.isSelectionEmpty()) {
                ctrlPresentacio.cercaBooleana(llistaBool.getSelectedValue());
            }
        }
    }

    /**
     * Metode per posar visible la vista
     */
    public void ferVisible() {
        setSize(500, 500);
        setVisible(true);
    }

    /**
     * Metode per mostrar totes les expressions booleanes de l'aplicacio
     */
    private void mostrarAllExpressions() {
        dlm.removeAllElements();

        ArrayList<String> expressions = ctrlPresentacio.getExpressionsBooleanes();
        for (String e: expressions) dlm.addElement(e);
    }


    /**
     * Metode per configurar la vista
     */
    private void configurar_vista() {
        setContentPane(this.boolPanel);
        setTitle("Gestió d'Expressions Booleanes");
        addKeyListener(this);
        setSize(500, 500);
        setResizable(false);
    }


    /**
     * Metode per configurar la llista que mostra les expressions per pantalla
     */
    private void configurar_llista_expressions() {
        //Inicialitzar llista de expressions booleanes
        this.dlm = new DefaultListModel<>();
        this.llistaBool = new JList<>(this.dlm);
        this.llistaBool.addKeyListener(this);
        this.llistaBool.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.llistaBool.setSelectedIndex(0);
        this.scroll.setViewportView(this.llistaBool);

        this.llistaBool.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getSource() == llistaBool && !llistaBool.isSelectionEmpty() && e.getClickCount()==2 ) {
                    modificarExpressio();
                }
            }
        });
    }

    /**
     * Metode que configurar l'atribut 'rightClickMenu' de la classe JPopupMenu i elements relacionats
     */
    private void configure_pop_menu() {
        this.rightClickMenu = new JPopupMenu();

        //Crear menu al clicar boto dret del ratoli
        this.miEliminar = new JMenuItem("Eliminar");
        this.miModificar = new JMenuItem("Modificar");
        this.miCercar = new JMenuItem("Cercar");

        this.rightClickMenu.add(this.miEliminar);
        this.rightClickMenu.add(this.miModificar);
        this.rightClickMenu.add(this.miCercar);

        //Activar listeners als menuItems
        this.miEliminar.addActionListener(this);
        this.miModificar.addActionListener(this);
        this.miCercar.addActionListener(this);

        //Afegir listener a la llista per mostrar popup menu
        this.llistaBool.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if(e.isPopupTrigger()) {
                    llistaBool.setSelectedIndex(llistaBool.locationToIndex(e.getPoint()));
                    rightClickMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    /**
     * Metode que crea una expressio booleana i l'afegeix a la llista 'llistaBool' de tipus JList
     */
    private void crearExpressio() {
        Object[] options = {"Crear", "Sortir"};
        String input = VistaDialeg.inputDialog("Crear Expressió Booleana", "Valor", options);

        //Si input != null significa que s ha clicat boto crear en el dialeg
        if (input != null) {
            if (VistaDialeg.confirmDialog("Segur que vols crear la expressió?")) {
                ctrlPresentacio.crearExprBool(input);
                mostrarAllExpressions();
            }
        }
    }

    /**
     * Metode que elimina la expressio booleana seleccionada de l'atribut 'llistaBool' de tipus JList
     */
    private void eliminarExpressio() {
        if (!llistaBool.isSelectionEmpty()) {
            if (VistaDialeg.confirmDialog("Segur que vols eliminar la expressió")) {
                //Eliminar expressio seleccionada
                ctrlPresentacio.eliminarExprBool(llistaBool.getSelectedValue());
                mostrarAllExpressions();
            }
        }
    }

    /**
     * Metode que modifica l'expressio booleana seleccionada de l'atribut 'llistaBool' de tipus JList
     */
    private void modificarExpressio() {
        if (!llistaBool.isSelectionEmpty()) {
            Object[] options = {"Modifica", "Sortir"};
            SimpleEntry<String, String> inputs = VistaDialeg.twoInputDialog("Modificar Expressió Booleana", "Antic Valor",
                    "Nou Valor", llistaBool.getSelectedValue(), options);

            //Si input != null significa que s ha clicat boto modificar en el dialeg
            if (inputs != null) {
                if (VistaDialeg.confirmDialog("Segur que vols modificar la expressió")) {
                    ctrlPresentacio.modificarExprBool(inputs.getKey(), inputs.getValue());
                    mostrarAllExpressions();
                }
            }
        }
    }

    /**
     * Metode que assigna funcionalitats de l'aplicacio de forma alternativa (combinacio de tecles)
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_CONTROL) {
            this.control_selected = true;

        } else if (control_selected && key == KeyEvent.VK_N) {
            crearExpressio();

        } else if (key == KeyEvent.VK_DELETE && !llistaBool.isSelectionEmpty()) {
            eliminarExpressio();
        }
    }

    /**
     * Metode que serveix per posar a 'false' l'atribut 'control_selected'
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            this.control_selected = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}


