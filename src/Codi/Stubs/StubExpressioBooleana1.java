package Codi.Stubs;

import Codi.Domini.ExpressioBooleana;
import Codi.Util.BinaryTree;

public class StubExpressioBooleana1 extends ExpressioBooleana {

    public StubExpressioBooleana1(BinaryTree bTree) {
        super(bTree);
    }

    public StubExpressioBooleana1(String expressio) {
        super(expressio);
    }

    @Override
    public boolean compleixCerca(String contingut) {
        return true;
    }
}
