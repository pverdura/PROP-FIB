package Codi.Domini;

import Codi.Util.BinaryTree;

public class ExpressioBooleana {

    private BinaryTree treeExpressio;

    public ExpressioBooleana(String expressio) {
        this.treeExpressio = new BinaryTree(expressio);
    }

    public boolean compleixCerca(String contingut) {
        return this.treeExpressio.cerca(contingut);
    }

}
