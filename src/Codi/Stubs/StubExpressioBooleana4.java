package Codi.Stubs;

import Codi.Domini.ExpressioBooleana;
import Codi.Util.BinaryTree;

public class StubExpressioBooleana4 extends ExpressioBooleana {

    int i;

    public StubExpressioBooleana4(BinaryTree bTree) {
        super(bTree);
        this.i = 0;
    }

    public StubExpressioBooleana4(String expressio) {
        super(expressio);
        this.i = 0;
    }

    @Override
    public boolean compleixCerca(String contingut) {
        if (this.i == 2) {
            i++;
            return false;
        }

        i++;
        return true;
    }
}
