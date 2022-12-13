package Codi.Presentacio;

import Codi.Util.TipusExtensio;
import Codi.Util.TipusOrdenacio;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;

public class ViewMenuPrincipal extends JFrame implements ActionListener {

    private JPanel mainPanel;
    private JButton cleanButton;
    private JScrollPane scroll;
    private JMenuItem miCreaDoc, miImportaDoc, miAjuda, miSortir;
    private JMenuItem miGestioBool;
    private JMenuItem miCercaTitol, miCercaAutor, miCercaTitolAutor, miCercaPrefix, miCercaParaules, miCercaSemblant;
    private JMenuItem miOrdreAlfAsc, miOrdreAlfDesc, miOrdrePesAsc, miOrdrePesDesc;
    private JPopupMenu rightClickMenu;
    private JMenuItem miExportar, miModificarDoc, miEliminarDoc;

    private final CtrlPresentacio ctrlPresentacio;
    private final DefaultListModel<String> dlm;
    private final JList<String> llistaCerques;
    private TipusOrdenacio tipus_ordenacio;

    public ViewMenuPrincipal(CtrlPresentacio ctrlPresentacio) {

        //Assignar presentacio i ordenacio per defecte
        this.ctrlPresentacio = ctrlPresentacio;
        this.tipus_ordenacio = TipusOrdenacio.ALFABETIC_ASCENDENT;

        //Inicialitzar components principals de la vista
        setContentPane(this.mainPanel);
        setTitle("Menú Principal");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Iniciar elements per carregar a la vista tots els documents guardats
        this.dlm = new DefaultListModel<>();
        this.llistaCerques = new JList<>(dlm);
        this.llistaCerques.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        this.llistaCerques.setSelectedIndex(0);
        this.scroll.setViewportView(this.llistaCerques);

        //Activar listener boto neteja
        this.cleanButton.addActionListener(this);

        //Crear menus vista i inicialitzar popMenu
        crearMenus();
        initPopMenu();
    }

    private void crearMenus() {
        JMenuBar barraMenu = new JMenuBar();
        setJMenuBar(barraMenu);

        //Afegir menu document
        JMenu menuFitxer = new JMenu("Fitxer");
        barraMenu.add(menuFitxer);

        miCreaDoc = new JMenuItem("Crea / Modifica");
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

        miGestioBool = new JMenuItem("Gestió");
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
        miOrdreAlfDesc = new JMenuItem("Alfabètic Ascendent");
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

        //TODO: Borrar tot lo d'abaix a partir d'aqui (proves)
        /*
            ArrayList<SimpleEntry<String,String>> at = new ArrayList<>();
            at.add(new SimpleEntry<String,String>("hayday","pau"));
            at.add(new SimpleEntry<String,String>("tetris","jordi"));
            at.add(new SimpleEntry<String,String>("crossfit","judit"));
            at.add(new SimpleEntry<String,String>("croissants","pol"));

            ArrayList<Integer> p = new ArrayList<>();
            p.add(3); p.add(2); p.add(1); p.add(5);

            ArrayList<TipusExtensio> e = new ArrayList<>();
            e.add(TipusExtensio.TXT); e.add(TipusExtensio.BOL);
            e.add(TipusExtensio.XML); e.add(TipusExtensio.TXT);

            actualitzarResultat(at, p, e);
         */

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
            String[] fila = llistaCerques.getSelectedValue().split("  ");
            ctrlPresentacio.modificarDocument(fila[0], fila[1]);

        } else if (source == miEliminarDoc) {
            String[] fila = llistaCerques.getSelectedValue().split("  ");
            ctrlPresentacio.esborrarDocument(fila[0], fila[1]);
        }

        //Aplicar funcionalitat associades al Mostra Tot
        else if (source == cleanButton) {
            ctrlPresentacio.mostrarDocuments();
        }
    }

    public void actualitzarResultat(ArrayList<SimpleEntry<String,String>> titolsAutors, ArrayList<Integer> pesos, ArrayList<TipusExtensio> extensios) {
        dlm.removeAllElements();
        int size = titolsAutors.size();

        //Afegir a la llista que es mostra per pantalla tots els documents que arriben
        for (int i = 0; i < size;  i++) {
            dlm.addElement(titolsAutors.get(i).getKey()+"  "+titolsAutors.get(i).getValue()+"  "+
                           pesos.get(i).toString()+"  "+extensios.get(i).toString());
        }

        dlm.trimToSize();
    }

    //Metode per posar visible la vista
    public void ferVisible() {
        setSize(600, 600);
        setVisible(true);
    }

    //Metode que tanca app
    public void tancarVista() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
            //Obtenir valors del fitxer seleccionat i exportar-lo
            String[] fila = llistaCerques.getSelectedValue().split("  ");
            ctrlPresentacio.exportarDocument(fila[0], fila[1], fc.getSelectedFile());
        }
    }

    private void initPopMenu() {
        this.rightClickMenu = new JPopupMenu();

        //Crear items del PopUp Menu
        miExportar = new JMenuItem("Exportar");
        miModificarDoc = new JMenuItem("Modificar");
        miEliminarDoc = new JMenuItem("Eliminar");

        //Afegir opcions menu boto dret
        this.rightClickMenu.add(miExportar);
        this.rightClickMenu.add(miModificarDoc);
        this.rightClickMenu.add(miEliminarDoc);

        //Activar listeners als menuItems
        miExportar.addActionListener(this);
        miModificarDoc.addActionListener(this);
        miEliminarDoc.addActionListener(this);

        //Afegir listener a la llista per mostrar popup menu
        this.llistaCerques.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if(e.isPopupTrigger()) {
                    llistaCerques.setSelectedIndex(llistaCerques.locationToIndex(e.getPoint()));
                    rightClickMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }
}
