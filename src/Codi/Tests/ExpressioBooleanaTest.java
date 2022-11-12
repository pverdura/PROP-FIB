package Codi.Tests;

import static org.junit.Assert.*;

import Codi.Domini.ExpressioBooleana;
import org.junit.Before;
import org.junit.Test;
import Codi.Util.BinaryTree;

public class ExpressioBooleanaTest {

    ExpressioBooleana e;
    String expressio, contingut;

    @Test
    public void testConstructora() {
        expressio = "!Buenas & (!\"Hola que tal\")";
        e = new ExpressioBooleana(expressio);
        assertNotNull(e);
    }

    @Test
    public void testCompleixCerca() {
        expressio = "!Buenas & (!\"Hola que tal\")";
        e = new ExpressioBooleana(expressio);

        contingut = "bon dia joan , com estàs?";
        assertTrue(e.compleixCerca(contingut));
    }

    @Test
    public void testNoCompleixCerca() {
        expressio = "!Buenas & (!\"Hola que tal\")";
        e = new ExpressioBooleana(expressio);

        contingut = "Hola que tal joan , com estàs?";
        assertFalse(e.compleixCerca(contingut));
    }

    @Test
    public void testCompleixCerca2() {
        expressio = "!{estudiants FIB} | (\"els professors\" | !\"els alumnes\")";
        e = new ExpressioBooleana(expressio);

        contingut = "Els estudiants de la FIB son els millors";
        assertTrue(e.compleixCerca(contingut));
    }

    @Test
    public void testNoCompleixCerca2() {
        expressio = "!{estudiants FIB} & !(\"els professors\" | \"els alumnes\")";
        e = new ExpressioBooleana(expressio);

        contingut = "Els estudiants de la FIB son els millors";
        assertFalse(e.compleixCerca(contingut));
    }

}
