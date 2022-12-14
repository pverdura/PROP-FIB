package Codi.Presentacio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

public class ViewGestioExprBool extends JFrame implements ActionListener{

    private JPanel boolPanel;
    private JButton createButton;
    private JScrollPane scroll;


    private JMenuItem miEliminar, miModificar, miCercar;
    private JPopupMenu rightClickMenu;
    private JList<String> llistaBool;
    private DefaultListModel<String> dlm;

    private final CtrlPresentacio ctrlPresentacio;

    public ViewGestioExprBool(CtrlPresentacio ctrlPresentacio) {
        this.ctrlPresentacio = ctrlPresentacio;

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

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        //Realitzar funcionalitat del boto que s ha clicat
        if (source == createButton) {

            Object[] options = {"Crear", "Sortir"};
            String input = VistaDialeg.inputDialog("Crear Expressió Booleana", "Valor", options);

            //Si input != null significa que s ha clicat boto crear en el dialeg
            if (input != null) {
                if (VistaDialeg.confirmDialog("Segur que vols crear la expressió?")) {
                    ctrlPresentacio.crearExprBool(input);
                    mostrarAllExpressions();
                }
            }

        }else if (source == miEliminar) {

            if (!llistaBool.isSelectionEmpty()) {
                if (VistaDialeg.confirmDialog("Segur que vols eliminar la expressió")) {
                    //Eliminar expressio seleccionada
                    ctrlPresentacio.eliminarExprBool(llistaBool.getSelectedValue());
                    mostrarAllExpressions();
                }
            }

        } else if (source == miModificar) {

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
            //TODO: Missatge modificacio

        }
        else if (source == miCercar) {

            if (!llistaBool.isSelectionEmpty()) {
                ctrlPresentacio.cercaBooleana(llistaBool.getSelectedValue());
                //TODO: Actualitzar pantalla
            }
        }
    }

    //Metode per afegir les expressions booleanes a la vista
    private void mostrarAllExpressions() {
        dlm.removeAllElements();

        ArrayList<String> expressions = ctrlPresentacio.getExpressionsBooleanes();
        for (String e: expressions) dlm.addElement(e);
    }

    //Metode per visualitzar vista
    public void ferVisible() {
        setSize(500, 500);
        setVisible(true);
    }

    //Metode per configurar la llista que mostrara les expressions per pantalla
    private void configurar_llista_expressions() {
        //Inicialitzar llista de expressions booleanes
        this.dlm = new DefaultListModel<>();
        this.llistaBool = new JList<>(this.dlm);
        this.llistaBool.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.llistaBool.setSelectedIndex(0);
        this.scroll.setViewportView(this.llistaBool);
    }

    //Metode per configurar la vista
    private void configurar_vista() {
        setContentPane(this.boolPanel);
        setTitle("Gestió d'Expressions Booleanes");
        setSize(500, 500);
        setResizable(false);
    }

    //Metode per configurar el menu del boto dret del ratoli
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
}


