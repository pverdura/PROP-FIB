package Codi.Tests;

import static org.junit.Assert.*;
import Codi.Domini.ExpressioBooleana;
import Codi.Stubs.StubBinaryTree1;
import Codi.Stubs.StubBinaryTree2;
import Codi.Util.BinaryTree;
import org.junit.Test;

public class ExpressioBooleanaTest {

    ExpressioBooleana e;
    String expressio, contingut;

    @Test
    public void testConstructora() {
        expressio = "!Buenas & (!\"Hola que tal\")";
        e = new ExpressioBooleana(new StubBinaryTree1(expressio));
        assertNotNull(e);
    }

    @Test
    public void testConstructora2() {
        expressio = "!Buenas & (!\"Hola que tal\")";
        e = new ExpressioBooleana(expressio);
        assertNotNull(e);
    }

    @Test
    public void testCompleixCerca() {
        expressio = "!Buenas & (!\"Hola que tal\")";

        BinaryTree bTree = new StubBinaryTree1(expressio);
        e = new ExpressioBooleana(bTree);

        contingut = "bon dia joan , com estàs?";
        assertTrue(e.compleixCerca(contingut));
    }

    @Test
    public void testNoCompleixCerca() {
        expressio = "!Buenas & (!\"Hola que tal\")";
        BinaryTree bTree = new StubBinaryTree2(expressio);

        e = new ExpressioBooleana(bTree);

        contingut = "Hola que tal joan , com estàs?";
        assertFalse(e.compleixCerca(contingut));
    }

    @Test
    public void testCompleixCerca2() {
        expressio = "!{estudiants FIB} | (\"els professors\" | !\"els alumnes\")";
        BinaryTree bTree = new StubBinaryTree1(expressio);

        e = new ExpressioBooleana(bTree);

        contingut = "Els estudiants de la FIB son els millors";
        assertTrue(e.compleixCerca(contingut));
    }

    @Test
    public void testNoCompleixCerca2() {
        expressio = "!{estudiants FIB} & !(\"els professors\" | \"els alumnes\")";
        BinaryTree bTree = new StubBinaryTree2(expressio);

        e = new ExpressioBooleana(bTree);

        contingut = "Els estudiants de la FIB son els millors";
        assertFalse(e.compleixCerca(contingut));
    }

}
