package Codi;
import Codi.Domini.CercaBooleana;
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

        Pair<Integer, Integer> p = new Pair<>(1, 2);

        System.out.println(p.getFirst()+" "+p.getSecond());

        p.setFirst(3);
        p.setSecond(4);

        System.out.println(p.getFirst()+" "+p.getSecond());

        CercaBooleana c = new CercaBooleana();
        System.out.println(c.cercaDoc(2));

        BinaryTree parseTree = new BinaryTree("\"!holak\" & (p1 | p2)");
        System.out.println("fin");
    }
}
