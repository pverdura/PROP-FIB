package Codi.Domini;

import Codi.Util.BinaryTree;

/**
 * Classe que representa una Expressio Booleana
 * @author PauVi
 */
public class ExpressioBooleana {

    /**
     * Instancia de la classe BinaryTree que s'utilitza com estructura de dades per guardar una expressio booleana
     */
    private BinaryTree treeExpressio;

    /**
     * Constructor
     * @param bTree Estructura de dades que s'assigna a la expressio booleana
     */
    public ExpressioBooleana(BinaryTree bTree) {
        this.treeExpressio = bTree;
    }

    /**
     * Constructor
     * @param expressio String que serveix per crear l'estructura de dades de l'atribut 'treeExpressio' de tipus BinaryTree
     */
    public ExpressioBooleana(String expressio) {
        this.treeExpressio = new BinaryTree(expressio);
    }

    /**
     * Constructor
     */
    public ExpressioBooleana() {

    }

    /**
     * Funcio que comprova si el contingut d'un document compleix la expressio booleana
     * @param contingut Contingut d'un document
     * @return Retorna el resultat sobre si el contingut del document compleix la expressio booleana
     */
    public boolean compleixCerca(String contingut) {
        return this.treeExpressio.cerca(contingut);
    }

}
