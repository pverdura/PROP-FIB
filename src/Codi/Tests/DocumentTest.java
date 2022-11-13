package Codi.Tests;

import static org.junit.Assert.*;

import Codi.Domini.Document;
import Codi.Util.TipusExtensio;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
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
    public void testSetStopWords () throws NoSuchFieldException, IllegalAccessException {
        d = new Document();

        ArrayList<String> stopWords = new ArrayList<>();
        stopWords.add("stop1");
        stopWords.add("stop2");
        stopWords.add("aigua");
        stopWords.add("farina");

        d.setStopWords(stopWords);

        final Field field = d.getClass().getDeclaredField("stopWords");
        field.setAccessible(true);

        assertEquals(stopWords, field.get(d));
    }
    @Test
    public void testGetStopWords () throws NoSuchFieldException, IllegalAccessException {
        d = new Document();

        ArrayList<String> stopWords = new ArrayList<>();
        stopWords.add("miau");
        stopWords.add("gat");


        final Field field = d.getClass().getDeclaredField("stopWords");
        field.setAccessible(true);
        field.set(d, stopWords);

        final ArrayList<String> result = d.getStopWords();

        assertEquals(result, stopWords);
    }
    @Test
    public void testSetAutor () throws NoSuchFieldException, IllegalAccessException {
        d = new Document();

        d.setAutor("Johan Johan Johan Haaaans");

        final Field field = d.getClass().getDeclaredField("autor");
        field.setAccessible(true);

        assertEquals("Johan Johan Johan Haaaans", field.get(d));
    }
    @Test
    public void testGetAutor () throws NoSuchFieldException, IllegalAccessException {
        d = new Document();

        final Field field = d.getClass().getDeclaredField("autor");
        field.setAccessible(true);
        field.set(d, "Carles de la Carlada");

        final String result = d.getAutor();

        assertEquals(result, "Carles de la Carlada");
    }
    @Test
    public void testSetTitol () throws NoSuchFieldException, IllegalAccessException {
        d = new Document();

        d.setTitol("el millor títol");

        final Field field = d.getClass().getDeclaredField("titol");
        field.setAccessible(true);

        assertEquals("el millor títol", field.get(d));
    }
    @Test
    public void testGetTitol () throws NoSuchFieldException, IllegalAccessException {
        d = new Document();

        final Field field = d.getClass().getDeclaredField("titol");
        field.setAccessible(true);
        field.set(d, "Per què PROP és una assignatura amb massa feina: VOLUM IX");

        final String result = d.getTitol();

        assertEquals(result, "Per què PROP és una assignatura amb massa feina: VOLUM IX");
    }
    @Test
    public void testSetPath () throws NoSuchFieldException, IllegalAccessException {
        d = new Document();

        d.setPath("./path/fitxer.txt");

        final Field field = d.getClass().getDeclaredField("path");
        field.setAccessible(true);

        assertEquals("./path/fitxer.txt", field.get(d));
    }
    @Test
    public void testGetPath () throws NoSuchFieldException, IllegalAccessException {
        d = new Document();

        final Field field = d.getClass().getDeclaredField("path");
        field.setAccessible(true);
        field.set(d, "./path/fitxer/carpeta/ola/prop/subgrup/fitxer.txt");

        final String result = d.getPath();

        assertEquals(result, "./path/fitxer/carpeta/ola/prop/subgrup/fitxer.txt");
    }
    @Test
    public void testSetExtensio () throws NoSuchFieldException, IllegalAccessException {
        d = new Document();

        d.setExtensio(TipusExtensio.XML);

        final Field field = d.getClass().getDeclaredField("tipusExtensio");
        field.setAccessible(true);

        assertEquals(TipusExtensio.XML, field.get(d));
    }
    @Test
    public void testGetExtensio () throws NoSuchFieldException, IllegalAccessException {
        d = new Document();

        final Field field = d.getClass().getDeclaredField("tipusExtensio");
        field.setAccessible(true);
        field.set(d, TipusExtensio.TXT);

        final TipusExtensio result = d.getExtensio();

        assertEquals(result, TipusExtensio.TXT);
    }
    @Test
    public void testGetPes () throws NoSuchFieldException, IllegalAccessException {
        d = new Document();

        final Field field = d.getClass().getDeclaredField("contingut");
        field.setAccessible(true);
        field.set(d, "quin pes deu tenir? qui sap, vés a saber");

        final int result = d.getPes();

        assertEquals(result, "quin pes deu tenir? qui sap, vés a saber".length());
    }
    @Test
    public void testGetAparicions () throws NoSuchFieldException, IllegalAccessException {
        d = new Document();
        d.setContingut("taula cadira d'era sostre cadira d'era cadira sostre la meva tota perfecte digues digues-ho");

        final HashMap<String, Integer> correcte = new HashMap<>();
        correcte.put("taula", 1);
        correcte.put("cadira", 3);
        correcte.put("era", 2);
        correcte.put("sostre", 2);
        correcte.put("la", 1);
        correcte.put("meva", 1);
        correcte.put("tota", 1);
        correcte.put("perfecte", 1);
        correcte.put("digues", 2);
        final HashMap<String, Integer> result = d.getAparicions();

        assertEquals(result, correcte);
    }
    @Test
    public void testAparicionsStopWords () throws NoSuchFieldException, IllegalAccessException {
        d = new Document();
        d.setContingut("stop no bon dia una casa stop2 stop3 bon dolent bon stop una era seré stop");

        ArrayList<String> stopWords = new ArrayList<>();
        stopWords.add("stop");
        stopWords.add("stop2");
        stopWords.add("stop3");

        d.setStopWords(stopWords);

        final HashMap<String, Integer> correcte = new HashMap<>();
        correcte.put("no", 1);
        correcte.put("bon", 3);
        correcte.put("dia", 2);
        correcte.put("una", 2);
        correcte.put("casa", 1);
        correcte.put("dolent", 1);
        correcte.put("seré", 1);
        final HashMap<String, Integer> result = d.getAparicions();

        assertEquals(result, correcte);
    }
    @Test
    public void testGetParaules () throws NoSuchFieldException, IllegalAccessException {
        d = new Document();
        d.setContingut("llista de paraules diferents això és una llista de paraules diferents");

        ArrayList<String> stopWords = new ArrayList<>();
        stopWords.add("això");

        d.setStopWords(stopWords);

        final ArrayList<String> correcte = new ArrayList<>();
        correcte.add("llista");
        correcte.add("de");
        correcte.add("paraules");
        correcte.add("diferents");
        correcte.add("una");

        final ArrayList<String> result = d.getParaules();

        assertEquals(result, correcte);
    }
}
