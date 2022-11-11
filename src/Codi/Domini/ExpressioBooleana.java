package Codi.Domini;

import Codi.Util.BinaryTree;

import java.util.Objects;

public class ExpressioBooleana {

    //private String expressio;
    private BinaryTree treeExpressio;

    public ExpressioBooleana(String expressio) {
        //this.expressio = expressio;
        this.treeExpressio = new BinaryTree(expressio);
    }

    public boolean cumpleixCerca(String contingut) {
        return treeExpressio.cerca(contingut);
    }

}
