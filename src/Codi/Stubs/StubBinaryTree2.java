package Codi.Stubs;

import Codi.Util.BinaryTree;

public class StubBinaryTree2 extends BinaryTree {

    public StubBinaryTree2(String expressio) {
        super(expressio);
    }

    @Override
    public boolean cerca(String contingut) {
        //Sabem que el contingut no compleix la cerca
        System.out.println("Crida al mètode cerca() de la classe BinaryTree amb paràmetre " + contingut);
        return false;
    }
}
