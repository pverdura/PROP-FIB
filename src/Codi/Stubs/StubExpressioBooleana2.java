package Codi.Stubs;

import Codi.Domini.ExpressioBooleana;
import Codi.Util.BinaryTree;

public class StubExpressioBooleana2 extends ExpressioBooleana {

    int i = 0;

    public StubExpressioBooleana2(BinaryTree bTree) {
        super(bTree);
        this.i = 0;
    }

    public StubExpressioBooleana2(String expressio) {
        super(expressio);
        this.i = 0;
    }

    @Override
    public boolean compleixCerca(String contingut) {

        if (this.i == 0 | this.i == 1) {
            i++;
            return false;
        }
        i++;
        return true;
    }
}
