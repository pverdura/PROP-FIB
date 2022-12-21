package Codi.Presentacio;

import Codi.Util.TipusExtensio;
import Codi.Util.TipusOrdenacio;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.File;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe que gestiona la vista principal de l'aplicacio
 * @author PauVi
 */
public class ViewMenuPrincipal extends JFrame implements ActionListener, KeyListener {

    /**
     * Instancia que fa referencia a l'element JPanel de la vista
     */
    private JPanel mainPanel;

    /**
     * Instancia que fa referencia a l'element JButton de la vista que al clicar mostrar tots els documents
     */
    private JButton cleanButton;

    /**
     * Instancia que fa referencia a l'element JScrollPane de la vista on dintre seu mostra tots els documents
     */
    private JScrollPane scroll;

    /**
     * Instancia que fa referencia a l'element JLabel de la vista que mostra informacio del que s'esta veient
     */
    private JLabel infoLabel;

    /**
     * Instancies que fan referencia als element JMenuItem del menu de la vista relacionat amb documents i altres
     */
    private JMenuItem miCreaDoc, miImportaDoc, miAjuda, miSortir;

    /**
     * Instancia que fa referencia a l'element JMenuItem del menu de la vista relacionat amb expressio booleana
     */
    private JMenuItem miGestioBool;

    /**
     * Instancies que fan referencia als element JMenuItem del menu de la vista relacionat amb cercar documents
     */
    private JMenuItem miCercaTitol, miCercaAutor, miCercaTitolAutor, miCercaPrefix, miCercaParaules, miCercaSemblant;

    /**
     * Instancies que fan referencia als element JMenuItem del menu de la vista relacionat amb ordenar documents
     */
    private JMenuItem miOrdreAlfAsc, miOrdreAlfDesc, miOrdrePesAsc, miOrdrePesDesc;

    /**
     * Instancia JPopupMenu que serveix per mostrar un menu al clicar boto dret del ratoli sobre els documents mostrats
     */
    private JPopupMenu rightClickMenu;

    /**
     * Instancies que fan referencia als elements JMenuItem del menu de tipus JPopupMenu
     */
    private JMenuItem miExportar, miModificarDoc, miEliminarDoc;

    /**
     * Instancia que fa referencia a la classse CtrlPresentacio
     */
    private final CtrlPresentacio ctrlPresentacio;

    /**
     * Instancia que fa referencia a la classse TipusOrdenacio per indicar com ordenar els documents mostrats
     */
    private TipusOrdenacio tipus_ordenacio;

    /**
     /**
     * Instancia que fa referencia a l'element JTable i que serveix per mostrar els documents separats en camps
     */
    private JTable taula;

    /**
     * Instancia de la classe DefaultTableModel que serveix per mostrar els documents en l'atribut 'taula' de tipus JTable
     */
    private DefaultTableModel dtm;

    /**
     * Variable booleana que indica quan la tecla control esta sent clicada
     */
    private Boolean control_selected;


    /**
     * Constructor
     * @param ctrlPresentacio Control Presentacio de l'aplicacio
     */
    public ViewMenuPrincipal(CtrlPresentacio ctrlPresentacio) {

        //Assignar presentacio i ordenacio per defecte
        this.ctrlPresentacio = ctrlPresentacio;
        this.tipus_ordenacio = TipusOrdenacio.ALFABETIC_ASCENDENT;
        this.control_selected = false;

        //Configurar boto mostrar tot
        this.cleanButton.setFocusable(false);
        this.cleanButton.addActionListener(this);

        //Configurar vista
        configurar_vista();
        configurar_taula_docs();

        //Crear menus vista i inicialitzar popMenu
        crearMenus();
        configurarPopMenu();
    }


    /**
     * Metode que associa cada element interactiu amb una funcionalitat
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        //Aplicar funcionalitats associades als items del Menu principal
        if (source == miCreaDoc) {
            ctrlPresentacio.obrirEditorDocuments(null,null, true);

        } else if (source == miImportaDoc) {
            seleccionarFitxersNav();

        } else if (source == miAjuda) {
            ctrlPresentacio.obrirAjuda();

        } else if (source == miSortir) {
            ctrlPresentacio.tancarAplicacio();

        } else if (miGestioBool.equals(source)) {
            ctrlPresentacio.obrirGestioExprBool();

        } else if (source == miCercaTitol) {
            ctrlPresentacio.obrirCercaTitol();

        } else if (source == miCercaAutor) {
            ctrlPresentacio.obrirCercaAutor();

        } else if (source == miCercaTitolAutor) {
            ctrlPresentacio.obrirCercaTitolAutor();

        } else if (source == miCercaPrefix) {
            ctrlPresentacio.obrirCercaPrefix();

        } else if (source == miCercaParaules) {
            ctrlPresentacio.obrirCercaParaules();

        } else if (source == miCercaSemblant) {
            ctrlPresentacio.obrirCercaSemblant();

        } else if (source == miOrdreAlfAsc) {
            tipus_ordenacio = TipusOrdenacio.ALFABETIC_ASCENDENT;
            ctrlPresentacio.ordenar(tipus_ordenacio);

        } else if (source == miOrdreAlfDesc) {
            tipus_ordenacio = TipusOrdenacio.ALFABETIC_DESCENDENT;
            ctrlPresentacio.ordenar(tipus_ordenacio);

        } else if (source == miOrdrePesAsc) {
            tipus_ordenacio = TipusOrdenacio.PES_ASCENDENT;
            ctrlPresentacio.ordenar(tipus_ordenacio);

        } else if (source == miOrdrePesDesc) {
            tipus_ordenacio = TipusOrdenacio.PES_DESCENDENT;
            ctrlPresentacio.ordenar(tipus_ordenacio);

        }

        //Aplicar funcionalitats associades als items del PopUp Menu
        else if (source == miExportar) {
            seleccionarDirNav();

        } else if (source == miModificarDoc) {
            modificarDoc();

        } else if (source == miEliminarDoc) {
            eliminarDoc();
        }

        //Aplicar funcionalitat associades al Mostra Tot
        else if (source == cleanButton) {
            ctrlPresentacio.mostrarDocuments();
        }
    }


    /**
     * Metode per actualitzar els documents a visualitzar i la informacio del atribut 'infoLabel'
     * @param titolsAutors Llista dels titols i autors dels documents a mostrar
     * @param pesos LLista dels pesos dels documents a mostrar
     * @param extensios LLista de les extensions dels documents a mostrar
     * @param info Text a mostrar per informar sobre que s'esta visualitzant dels documents
     */
    public void actualitzarResultat(ArrayList<SimpleEntry<String,String>> titolsAutors, ArrayList<Integer> pesos, ArrayList<TipusExtensio> extensios, String info) {

        this.infoLabel.setText(info);

        //Eliminar els antics elements visibles
        int size_row = dtm.getRowCount();
        if (size_row > 0) {
            for (int i = size_row-1; i > -1; i--) dtm.removeRow(i);
        }

        //Afegir els nous elements a visualitzar
        int size = titolsAutors.size();
        for (int i = 0; i < size; i++) {
            dtm.addRow(new Object[]{ titolsAutors.get(i).getKey(),
                    titolsAutors.get(i).getValue(),
                    String.valueOf(pesos.get(i)),
                    String.valueOf(extensios.get(i))});
        }
    }

    /**
     * Metode per posar visible la vista
     */
    public void ferVisible() {
        setSize(600, 600);
        setVisible(true);
    }

    /**
     * Metode per tancar la vista
     */
    public void tancarVista() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    /**
     * Metode que crea els menus principals de l'aplicacio
     */
    private void crearMenus() {
        JMenuBar barraMenu = new JMenuBar();
        setJMenuBar(barraMenu);

        //Afegir menu document
        JMenu menuFitxer = new JMenu("Fitxer");
        barraMenu.add(menuFitxer);

        miCreaDoc = new JMenuItem("Crea");
        miCreaDoc.addActionListener(this);
        miImportaDoc = new JMenuItem("Importa");
        miImportaDoc.addActionListener(this);
        miAjuda = new JMenuItem("Ajuda");
        miAjuda.addActionListener(this);
        miSortir = new JMenuItem("Sortir");
        miSortir.addActionListener(this);

        menuFitxer.add(miCreaDoc);
        menuFitxer.addSeparator();
        menuFitxer.add(miImportaDoc);
        menuFitxer.addSeparator();
        menuFitxer.add(miAjuda);
        menuFitxer.addSeparator();
        menuFitxer.add(miSortir);

        //Afegir menu expressio bool
        JMenu menuBool = new JMenu("Expressió Bool");
        barraMenu.add(menuBool);

        miGestioBool = new JMenuItem("Gestionar expressions");
        miGestioBool.addActionListener(this);
        menuBool.add(miGestioBool);

        //Afegir menu cerca
        JMenu menuCerca = new JMenu("Cerca");
        barraMenu.add(menuCerca);

        miCercaAutor = new JMenuItem("Cerca Autor");
        miCercaAutor.addActionListener(this);
        miCercaTitol = new JMenuItem("Cerca Títol");
        miCercaTitol.addActionListener(this);
        miCercaTitolAutor = new JMenuItem("Cerca Títol i Autor");
        miCercaTitolAutor.addActionListener(this);
        miCercaParaules = new JMenuItem("Cerca Paraules");
        miCercaParaules.addActionListener(this);
        miCercaPrefix = new JMenuItem("Cerca Autor per Prefix");
        miCercaPrefix.addActionListener(this);
        miCercaSemblant = new JMenuItem("Cerca per Documents");
        miCercaSemblant.addActionListener(this);

        menuCerca.add(miCercaTitol);
        menuCerca.addSeparator();
        menuCerca.add(miCercaAutor);
        menuCerca.addSeparator();
        menuCerca.add(miCercaTitolAutor);
        menuCerca.addSeparator();
        menuCerca.add(miCercaPrefix);
        menuCerca.addSeparator();
        menuCerca.add(miCercaParaules);
        menuCerca.addSeparator();
        menuCerca.add(miCercaSemblant);

        //Afegir menu ordre
        JMenu menuOrdre = new JMenu("Ordre");
        barraMenu.add(menuOrdre);

        miOrdreAlfAsc = new JMenuItem("Alfabètic Ascendent");
        miOrdreAlfAsc.addActionListener(this);
        miOrdreAlfDesc = new JMenuItem("Alfabètic Descendent");
        miOrdreAlfDesc.addActionListener(this);
        miOrdrePesAsc = new JMenuItem("Pes Ascendent");
        miOrdrePesAsc.addActionListener(this);
        miOrdrePesDesc = new JMenuItem("Pes Descendent");
        miOrdrePesDesc.addActionListener(this);

        menuOrdre.add(miOrdreAlfAsc);
        menuOrdre.addSeparator();
        menuOrdre.add(miOrdreAlfDesc);
        menuOrdre.addSeparator();
        menuOrdre.add(miOrdrePesAsc);
        menuOrdre.addSeparator();
        menuOrdre.add(miOrdrePesDesc);
    }


    /**
     * Metode que selecciona els fitxers que es volen importar a l'aplicacio
     */
    private void seleccionarFitxersNav() {
        JFileChooser fc = new JFileChooser();

        //Crear filtre d extensions al importar fitxer
        FileNameExtensionFilter filtre = new FileNameExtensionFilter("Fitxers app",
                TipusExtensio.BOL.toString(),
                TipusExtensio.TXT.toString(),
                TipusExtensio.XML.toString());
        fc.setFileFilter(filtre);

        //Aplicar multiples seleccions de fitxers
        fc.setMultiSelectionEnabled(true);

        int seleccionat = fc.showOpenDialog(this);

        //Comprovar si s han seleccionat els fitxers
        if (seleccionat == JFileChooser.APPROVE_OPTION) {

            //Obtenir fitxers seleccionats
            File[] files = fc.getSelectedFiles();

            ArrayList<File> files_par = new ArrayList<>(Arrays.asList(files));

            //Importar fitxers seleccionats
            ctrlPresentacio.importarDocuments(files_par);
        }
    }

    /**
     * Metode que selecciona a quin directori es vol exportar el document seleccionat
     */
    private void seleccionarDirNav() {
        //Crear File Chooser per seleccionar carpeta a exportar el doc seleccionat
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);

        //Obrir jFileChooser
        int res = fc.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            int fila_seleccionada = taula.getSelectedRow();
            ctrlPresentacio.exportarDocument(taula.getModel().getValueAt(fila_seleccionada,0).toString(),
                    taula.getModel().getValueAt(fila_seleccionada,1).toString(), fc.getSelectedFile());
        }
    }

    /**
     * Metode per configurar la vista
     */
    private void configurar_vista() {
        setContentPane(this.mainPanel);
        setTitle("Menú Principal");
        addKeyListener(this);
        setSize(600, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Metode per configurar l'atribut 'taula' que mostra en una JTable els documents
     */
    private void configurar_taula_docs() {
        //Iniciar elements per carregar a la vista tots els documents guardats
        String[] header = new String[]{"Títol", "Autor", "Pes", "Extensió"};
        this.dtm = new DefaultTableModel(null, header)  {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        this.taula = new JTable(this.dtm);
        this.taula.addKeyListener(this);
        this.taula.setShowHorizontalLines(true);
        this.taula.setRowSelectionAllowed(true);
        this.taula.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.scroll.setViewportView(this.taula);

        this.taula.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getSource() == taula && !taula.getSelectionModel().isSelectionEmpty() && e.getClickCount()==2 ) {
                    modificarDoc();
                }
            }
        });
    }

    /**
     * Metode que configurar l'atribut 'rightClickMenu' de la classe JPopupMenu i elements relacionats
     */
    private void configurarPopMenu() {
        this.rightClickMenu = new JPopupMenu();

        //Crear items del PopUp Menu
        miExportar = new JMenuItem("Exportar");
        miModificarDoc = new JMenuItem("Modificar");
        miEliminarDoc = new JMenuItem("Eliminar");

        //Afegir opcions menu boto dret ratoli
        this.rightClickMenu.add(miExportar);
        this.rightClickMenu.add(miModificarDoc);
        this.rightClickMenu.add(miEliminarDoc);

        //Activar listeners als menuItems
        miExportar.addActionListener(this);
        miModificarDoc.addActionListener(this);
        miEliminarDoc.addActionListener(this);

        //Afegir listener a la taula per mostrar popup menu
        this.taula.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if(e.isPopupTrigger() && !taula.getSelectionModel().isSelectionEmpty()) {
                    rightClickMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    /**
     * Metode que elimina el document seleccionat de l'atribut 'taula' de tipus JTable
     */
    private void eliminarDoc() {
        //Menu confirmacio per eliminar doc
        if (ViewDialeg.confirmDialog("Segur que vols eliminar el document?")) {

            int fila_seleccionada = taula.getSelectedRow();
            ctrlPresentacio.esborrarDocument(taula.getModel().getValueAt(fila_seleccionada, 0).toString(),
                    taula.getModel().getValueAt(fila_seleccionada, 1).toString());
        }
    }

    /**
     * Metode que modifica el document seleccionat de l'atribut 'taula' de tipus JTable
     */
    private void modificarDoc() {
        int fila_seleccionada = taula.getSelectedRow();
        ctrlPresentacio.obrirEditorDocuments(taula.getModel().getValueAt(fila_seleccionada, 0).toString(),
                taula.getModel().getValueAt(fila_seleccionada, 1).toString(), false);
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
            ctrlPresentacio.obrirEditorDocuments(null,null,true);

        } else if (key == KeyEvent.VK_DELETE && !taula.getSelectionModel().isSelectionEmpty()) {
            eliminarDoc();
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
