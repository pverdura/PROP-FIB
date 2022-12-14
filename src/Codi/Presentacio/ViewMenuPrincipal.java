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

public class ViewMenuPrincipal extends JFrame implements ActionListener, KeyListener {

    private JPanel mainPanel;
    private JButton cleanButton;
    private JScrollPane scroll;
    private JLabel infoLabel;
    private JMenuItem miCreaDoc, miImportaDoc, miAjuda, miSortir;
    private JMenuItem miGestioBool;
    private JMenuItem miCercaTitol, miCercaAutor, miCercaTitolAutor, miCercaPrefix, miCercaParaules, miCercaSemblant;
    private JMenuItem miOrdreAlfAsc, miOrdreAlfDesc, miOrdrePesAsc, miOrdrePesDesc;
    private JPopupMenu rightClickMenu;
    private JMenuItem miExportar, miModificarDoc, miEliminarDoc;

    private final CtrlPresentacio ctrlPresentacio;
    private TipusOrdenacio tipus_ordenacio;

    private JTable taula;
    private DefaultTableModel dtm;
    private int fila_seleccionada;
    private Boolean control_selected;


    public ViewMenuPrincipal(CtrlPresentacio ctrlPresentacio) {

        //Assignar presentacio i ordenacio per defecte
        this.ctrlPresentacio = ctrlPresentacio;
        this.tipus_ordenacio = TipusOrdenacio.ALFABETIC_ASCENDENT;
        this.control_selected = false;

        //Activar listener boto mostrar tot
        this.cleanButton.addActionListener(this);

        //Configurar vista
        configurar_vista();
        configurar_taula_docs();

        //Configurar listener ratoli double click
        configurar_double_click_selection();

        //Crear menus vista i inicialitzar popMenu
        crearMenus();
        configurarPopMenu();
    }

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
        miCercaPrefix = new JMenuItem("Cerca Prefix");
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

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        //Aplicar funcionalitats associades als items del Menu principal
        if (source == miCreaDoc) {
            ctrlPresentacio.crearDocument();

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


    //Metode per actualitzar els elements que es mostren per pantalla
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

    //Metode per posar visible la vista
    public void ferVisible() {
        setSize(600, 600);
        setVisible(true);
    }

    //Metode que tanca app
    public void tancarVista() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

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

        //Comprobar si s han seleccionat els fitxers
        if (seleccionat == JFileChooser.APPROVE_OPTION) {

            //Obtenir fitxers seleccionats
            File[] files = fc.getSelectedFiles();

            ArrayList<File> files_par = new ArrayList<>(Arrays.asList(files));

            //Importar fitxers seleccionats
            ctrlPresentacio.importarDocuments(files_par);
        }
    }

    private void seleccionarDirNav() {
        //Crear File Chooser per seleccionar carpeta a exportar el doc seleccionat
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);

        //Obrir jFileChooser
        int res = fc.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            ctrlPresentacio.exportarDocument(taula.getModel().getValueAt(fila_seleccionada,0).toString(),
                    taula.getModel().getValueAt(fila_seleccionada,1).toString(), fc.getSelectedFile());
        }
    }

    //Metode per configurar la vista
    private void configurar_vista() {
        setContentPane(this.mainPanel);
        addKeyListener(this);
        setTitle("Menú Principal");
        setSize(600, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

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
    }

    private void configurar_double_click_selection() {
        taula.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getSource() == taula && !taula.getSelectionModel().isSelectionEmpty() && e.getClickCount()==2 ) {
                    modificarDoc();
                }
            }
        });
    }

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
                    fila_seleccionada = taula.getSelectedRow();
                    rightClickMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    private void eliminarDoc() {
        //Menu confirmacio per eliminar doc
        if (VistaDialeg.confirmDialog("Segur que vols eliminar el document?")) {
            ctrlPresentacio.esborrarDocument(taula.getModel().getValueAt(fila_seleccionada, 0).toString(),
                    taula.getModel().getValueAt(fila_seleccionada, 1).toString());
        }
    }

    private void modificarDoc() {
        ctrlPresentacio.modificarDocument(taula.getModel().getValueAt(fila_seleccionada, 0).toString(),
                taula.getModel().getValueAt(fila_seleccionada, 1).toString());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_CONTROL) {
            this.control_selected = true;

        } else if (control_selected && key == KeyEvent.VK_N) {
            ctrlPresentacio.crearDocument();

        } else if (key == KeyEvent.VK_DELETE && !taula.getSelectionModel().isSelectionEmpty()) {
            eliminarDoc();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            this.control_selected = false;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
}
