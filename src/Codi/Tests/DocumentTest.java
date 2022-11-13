package Codi.Tests;

import static org.junit.Assert.*;

import Codi.Domini.Document;
import Codi.Util.TipusExtensio;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class DocumentTest {
    Document d;
    HashMap<String, Integer> aparicions;

    @Before
    public void before () {
        aparicions = new HashMap<>();
    }

    @Test
    public void testConstructorBuit () {
        d = new Document();
        assertNotNull(d);
        assertNull(d.getTitol());
        assertNull(d.getAutor());
        assertEquals("", d.getContingut());
        assertEquals(0, d.getPes());
        assertNull(d.getExtensio());
        assertNull(d.getPath());

        assertEquals(new HashMap<String, Integer>(), d.getAparicions());
        assertEquals(new ArrayList<String>(), d.getStopWords());
    }
    @Test
    public void testConstructor2 () {
        d = new Document("titol", "autor");
        assertNotNull(d);
        assertEquals("titol", d.getTitol());
        assertEquals("autor", d.getAutor());
        assertEquals("", d.getContingut());
        assertEquals(0, d.getPes());
        assertNull(d.getExtensio());
        assertNull(d.getPath());

        assertEquals(new HashMap<String, Integer>(), d.getAparicions());
        assertEquals(new ArrayList<String>(), d.getStopWords());
    }
    @Test
    public void testConstructorPle () {
        d = new Document("titol", "autor", "./path/fitxer.txt", "contingut", TipusExtensio.BOL);
        assertNotNull(d);
        assertEquals("titol", d.getTitol());
        assertEquals("autor", d.getAutor());
        assertEquals("contingut", d.getContingut());
        assertEquals("contingut".length(), d.getPes());
        assertEquals(TipusExtensio.BOL, d.getExtensio());
        assertEquals("./path/fitxer.txt", d.getPath());

        assertEquals(new HashMap<String, Integer>(), d.getAparicions());
        assertEquals(new ArrayList<String>(), d.getStopWords());
    }
    @Test
    public void testSetContingut () throws NoSuchFieldException, IllegalAccessException {
        d = new Document();

        d.setContingut("hola bon dia");

        final Field field = d.getClass().getDeclaredField("contingut");
        field.setAccessible(true);

        assertEquals("hola bon dia", field.get(d));
    }
    @Test
    public void testGetContingut () throws NoSuchFieldException, IllegalAccessException {
        d = new Document();

        final Field field = d.getClass().getDeclaredField("contingut");
        field.setAccessible(true);
        field.set(d, "prova de getter de contingut");

        final String result = d.getContingut();

        assertEquals(result, "prova de getter de contingut");
    }
    @Test
    public void testSetStopWords () {

    }
    @Test
    public void testGetStopWords () {

    }
    @Test
    public void testSetTitol () {

    }
    @Test
    public void testGetTitol () {

    }
    @Test
    public void testSetPath () {

    }
    @Test
    public void testGetPath () {

    }
    @Test
    public void testSetExtensio () {

    }
    @Test
    public void testGetExtensio () {

    }
    @Test
    public void testGetPes () {

    }
    @Test
    public void testGetAparicions () {

    }
    @Test
    public void testGetParaules () {

    }
}
