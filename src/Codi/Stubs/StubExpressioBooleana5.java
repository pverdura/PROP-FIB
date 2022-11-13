package Codi.Stubs;

import Codi.Domini.ExpressioBooleana;
import Codi.Util.BinaryTree;

public class StubExpressioBooleana5 extends ExpressioBooleana {

    public StubExpressioBooleana5(BinaryTree bTree) {
        super(bTree);
    }

    public StubExpressioBooleana5(String expressio) {
        super(expressio);
    }

    @Override
    public boolean compleixCerca(String contingut) {
        return false;
    }
}
