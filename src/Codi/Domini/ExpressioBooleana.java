package Codi.Domini;

import Codi.Util.BinaryTree;

import java.util.Objects;

public class ExpressioBooleana {

    //private String expressio;
    private BinaryTree treeExpressio;

    public ExpressioBooleana(String expressio) {
        this.treeExpressio = new BinaryTree(expressio);
    }

    public boolean compleixCerca(String contingut) {
        return this.treeExpressio.cerca(contingut);
    }

}
