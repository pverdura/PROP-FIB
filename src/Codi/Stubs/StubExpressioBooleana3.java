package Codi.Stubs;

import Codi.Domini.ExpressioBooleana;
import Codi.Util.BinaryTree;

public class StubExpressioBooleana3 extends ExpressioBooleana {

    int i;

    public StubExpressioBooleana3(BinaryTree bTree) {
        super(bTree);
        this.i = 0;
    }

    public StubExpressioBooleana3(String expressio) {
        super(expressio);
        this.i = 0;
    }

    @Override
    public boolean compleixCerca(String contingut) {
        if (this.i == 0 | this.i == 1) {
            i++;
            return true;
        }
        i++;
        return false;
    }
}
