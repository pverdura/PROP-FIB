package Codi;
import Codi.Domini.CercaBooleana;
import Codi.Domini.ExpressioBooleana;
import Codi.Util.BinaryTree;
import Codi.Util.Pair;

import java.util.Scanner;

public class Main {

    private Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Main main = new Main();
        main.init();
    }

    private void init() {

        ExpressioBooleana e = new ExpressioBooleana("(\"els professors\" | \"els alumnes\")");
        System.out.println(e.compleixCerca("Els estudiants de la FIB son els millors"));
        System.out.println("fin");
    }
}
