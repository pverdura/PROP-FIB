package Codi.Presentacio;

import Codi.Util.TipusExtensio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.AbstractMap.SimpleEntry;

/**
 *  Classe que implementa l'editor de documents
 *
 * @author Jordi Palomera
 * @since 13-12-2022
 */

public class ViewEditorDocument {
    private final CtrlPresentacio ctrlPresentacio;
    private JFrame frame;
    private JPanel panellSuperior, panellMig, panellInferior;
    private JLabel labelTitol, labelAutor;
    private JButton btGuardar;
    private JTextField textTitol, textAutor;
    private JTextArea textContingut;
    private JComboBox<String> tipusExtensio;
    private String titol, autor, contingut;
    private TipusExtensio tExtensio;
    private boolean documentNou;
    private final String[] extensions = {"TXT", "XML", "BOL"};
    private JMenuBar barraMenu;
    private JMenu menuFitxer;
    private JMenuItem menuCrear, menuDesar, menuExportar, menuSortir;
    private JScrollPane scrollPane;

    /**
     * Constructor per crear un document nou
     *
     * @param cp Control presentacio
     * @param id Identificador de la vista
     */
    public ViewEditorDocument (CtrlPresentacio cp, int id) {
        this.ctrlPresentacio = cp;
        this.titol = Integer.toString(id);
        this.autor = Integer.toString(id);
        this.contingut = "";
        documentNou = true;

        inicialitzar();
        this.setExtensio(TipusExtensio.BOL);
        this.textTitol.setText("");
        this.textAutor.setText("");
    }

    /**
     * Constructor per modificar un document
     *
     * @param cp Control presentacio
     * @param titol Titol del document
     * @param autor Autor del document
     * @param contingut Contingut del document
     * @param te Tipus d'extensio del document
     */
    public ViewEditorDocument (CtrlPresentacio cp, String titol, String autor, String contingut, TipusExtensio te) {
        this.ctrlPresentacio = cp;
        this.titol = titol;
        this.autor = autor;
        this.contingut = contingut;
        documentNou = false;

        inicialitzar();
        this.setExtensio(te);
    }

    /**
     * Fa la vista visible o invisible
     *
     * @param visible si ha de ser visible o no
     */
    public void ferVisible (boolean visible) {
        if (visible) frame.pack();
        frame.setVisible(visible);
    }

    /**
     * El document que la vista permet modificar ha estat eliminat
     *
     * @param id Nou identificador de la vista
     */
    public void documentEliminat (int id) {
        this.titol = Integer.toString(id);
        this.autor = Integer.toString(id);
        documentNou = true;
    }

    ///////////////////////////////////////////////////////////
    ///                  M??TODES PRIVATS                    ///
    ///////////////////////////////////////////////////////////

    /**
     * Inicialitza i configura la vista
     */
    private void inicialitzar () {
        inicialitzarComponents();
        configurarVista();
        configurarMenu();
        configurarPanellSuperior();
        configurarPanellMig();
        configurarPanellInferior();
        assignarListeners();
    }

    /**
     * Inicialitza els components
     */
    private void inicialitzarComponents () {
        frame = new JFrame("Editor de documents - "+titol);
        barraMenu = new JMenuBar();
        menuFitxer = new JMenu("Fitxer");
        menuCrear = new JMenuItem("Crear");
        menuDesar = new JMenuItem("Desar");
        menuExportar = new JMenuItem("Exportar");
        menuSortir = new JMenuItem("Sortir");
        panellSuperior = new JPanel();
        panellMig = new JPanel();
        panellInferior = new JPanel();
        labelAutor = new JLabel("Autor: ");
        labelTitol = new JLabel("T??tol: ");
        btGuardar = new JButton("Desar");
        textTitol = new JTextField();
        textAutor = new JTextField();
        textContingut = new JTextArea();
        tipusExtensio = new JComboBox<>(extensions);
    }

    /**
     * Configura la vista
     */
    private void configurarVista () {
        frame.add(panellSuperior, BorderLayout.NORTH);
        frame.add(panellMig, BorderLayout.CENTER);

        frame.setMinimumSize(new Dimension(800, 600));
        frame.setPreferredSize(frame.getMinimumSize());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Configura la barra de menu
     */
    private void configurarMenu () {
        frame.setJMenuBar(barraMenu);
        barraMenu.add(menuFitxer);
        menuFitxer.add(menuCrear);
        menuFitxer.add(menuDesar);
        menuFitxer.add(menuExportar);
        menuFitxer.add(menuSortir);
    }

    /**
     * Configura el panell superior
     */
    private void configurarPanellSuperior () {
        tipusExtensio.setSelectedIndex(2);
        textTitol.setMinimumSize(new Dimension(400, 30));
        textTitol.setPreferredSize(textTitol.getMinimumSize());
        textTitol.setText(titol);

        panellSuperior.add(labelTitol);
        panellSuperior.add(textTitol);
        panellSuperior.add(tipusExtensio);
    }

    /**
     * Configura el panell del mig
     */
    private void configurarPanellMig () {
        textAutor.setMinimumSize(new Dimension(400, 30));
        textAutor.setPreferredSize(textAutor.getMinimumSize());
        textAutor.setText(autor);

        panellMig.add(labelAutor);
        panellMig.add(textAutor);
        panellMig.add(btGuardar);
    }

    /**
     * Configura el panell inferior
     */
    private void configurarPanellInferior () {
        textContingut.setText(contingut);
        textContingut.setMinimumSize(new Dimension(750, 450));

        scrollPane = new JScrollPane(textContingut);
        scrollPane.setPreferredSize(textContingut.getMinimumSize());
        scrollPane.setViewportView(textContingut);
        scrollPane.getViewport().setViewPosition(new Point(0, scrollPane.getViewport().getHeight()));

        panellInferior.add(scrollPane, BorderLayout.CENTER);
        panellInferior.setMaximumSize(new Dimension(800, 450));

        frame.add(panellInferior, BorderLayout.SOUTH);
    }

    /**
     * Assigna els listeners als components de la vista
     */
    private void assignarListeners () {
        btGuardar.addActionListener(e -> desarDocument(false));

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                tancarVista();
            }
        });

        menuCrear.addActionListener(e -> crearDocument());
        menuDesar.addActionListener(e -> desarDocument(false));
        menuExportar.addActionListener(e -> exportarDocument());
        menuSortir.addActionListener(e -> tancarVista());

        frame.addKeyListener(new Tecles());
        textContingut.addKeyListener(new Tecles());
        textTitol.addKeyListener(new Tecles());
        textAutor.addKeyListener(new Tecles());
        tipusExtensio.addKeyListener(new Tecles());
    }

    /**
     * Retorna el tipus d'extensio seleccionada
     *
     * @return El tipus d'extensio seleccionada
     */
    private TipusExtensio getTipusExtensio () {
        String ext = (String) tipusExtensio.getSelectedItem();
        if (ext.equals("TXT")) return TipusExtensio.TXT;
        else if (ext.equals("XML")) return TipusExtensio.XML;
        else return TipusExtensio.BOL;
    }

    /**
     * Modifica l'extensio del component visual
     *
     * @param te El nou tipus d'extensio
     */
    private void setExtensio (TipusExtensio te) {
        this.tExtensio = te;
        if (te.equals(TipusExtensio.TXT)) tipusExtensio.setSelectedIndex(0);
        else if (te.equals(TipusExtensio.XML)) tipusExtensio.setSelectedIndex(1);
        else tipusExtensio.setSelectedIndex(2);
    }

    /**
     *  Crea un nou document
     */
    private void crearDocument () {
        ctrlPresentacio.obrirEditorDocuments(null, null, true);
    }

    /**
     *  Desa el document
     *
     * @param segur {@code true} si s'ha de desar obligatoriament, sense generar el dialeg
     */
    private void desarDocument (boolean segur) {
        if (segur || ViewDialeg.confirmDialog("Segur que vols desar el document?")) {
            SimpleEntry<String, String> idVell = new SimpleEntry<>(titol, autor);
            boolean m = modificat();

            titol = textTitol.getText().trim();
            autor = textAutor.getText().trim();
            contingut = textContingut.getText();
            setExtensio(getTipusExtensio());
            SimpleEntry<String, String> idNou = new SimpleEntry<>(titol, autor);

            ctrlPresentacio.guardarDocument(documentNou, m, idVell, idNou, contingut, tExtensio);
            documentNou = false;
            frame.setTitle("Editor de documents - "+titol);
        }
    }

    /**
     *  Exporta el document
     */
    private void exportarDocument () {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);

        int res = fc.showOpenDialog(frame);
        if (res == JFileChooser.APPROVE_OPTION) {
            desarDocument(true);
            ctrlPresentacio.exportarDocument(this.titol, this.autor, fc.getSelectedFile());
        }
    }

    /**
     *  Tanca la vista
     */
    public void tancarVista () {
        if (modificat() && ViewDialeg.confirmDialog("Hi ha canvis no guardats. Vols desar el document abans de tancar l'aplicaci???")) {
            desarDocument(true);
        }
        ctrlPresentacio.tancarDocument(titol, autor);
        frame.dispose();
    }

    /**
     * Comprova si s'ha modificat el document
     *
     * @return {@code true} si s'ha modificat
     */
    private boolean modificat () {
        return (!titol.equals(textTitol.getText())) || (!autor.equals(textAutor.getText())) ||
                (!contingut.equals(textContingut.getText())) || tExtensio != getTipusExtensio();
    }

    /**
     * Classe que implementa les dreceres de teclat per la vista
     */
    private class Tecles extends KeyAdapter {
        private boolean control = false;

        /**
         * Sobreescriptura del metode de tecla premuda
         * Ctrl + S per desar el document
         * Ctrl + N per crear un nou document
         * Ctrl + E per exportar el document
         * Ctrl + Q per sortir de l'editor
         *
         * @param e l'esdeveniment a ser processat
         */
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getExtendedKeyCode() == KeyEvent.VK_CONTROL) {
                control = true;
            } else if (e.getExtendedKeyCode() == KeyEvent.VK_S && control) {
                desarDocument(false);
                control = false;
            } else if (e.getExtendedKeyCode() == KeyEvent.VK_N && control) {
                crearDocument();
                control = false;
            } else if (e.getExtendedKeyCode() == KeyEvent.VK_E && control) {
                exportarDocument();
                control = false;
            } else if (e.getExtendedKeyCode() == KeyEvent.VK_Q && control) {
                tancarVista();
                control = false;
            }
        }

        /**
         * Sobreescriptura del metode de tecla alliberada
         *
         * @param e l'esdeveniment a ser processat
         */
        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getExtendedKeyCode() == KeyEvent.VK_CONTROL) {
                control = false;
            }
        }
    }
}
