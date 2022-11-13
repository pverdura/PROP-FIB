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
import java.util.HashSet;
import java.util.Set;

public class DocumentTest {
    HashMap<String, Integer> aparicions;

    @Before
    public void before () {
        aparicions = new HashMap<>();
    }

    @Test
    public void testConstructorBuit () {
        Document d = new Document();
        assertNotNull(d);
        assertNull(d.getTitol());
        assertNull(d.getAutor());
        assertEquals("", d.getContingut());
        assertEquals(0, d.getPes());
        assertNull(d.getExtensio());
        assertNull(d.getPath());

        assertEquals(new HashMap<String, Integer>(), d.getAparicions());
    }
    @Test
    public void testConstructor2 () {
        Document d = new Document("titol", "autor");
        assertNotNull(d);
        assertEquals("titol", d.getTitol());
        assertEquals("autor", d.getAutor());
        assertEquals("", d.getContingut());
        assertEquals(0, d.getPes());
        assertNull(d.getExtensio());
        assertNull(d.getPath());

        assertEquals(new HashMap<String, Integer>(), d.getAparicions());
    }
    @Test
    public void testConstructorPle () {
        Document d = new Document("titol", "autor", "./path/fitxer.txt", "contingut", TipusExtensio.BOL);
        assertNotNull(d);
        assertEquals("titol", d.getTitol());
        assertEquals("autor", d.getAutor());
        assertEquals("contingut", d.getContingut());
        assertEquals("contingut".length(), d.getPes());
        assertEquals(TipusExtensio.BOL, d.getExtensio());
        assertEquals("./path/fitxer.txt", d.getPath());

        HashMap<String, Integer> resultat = new HashMap<>();
        resultat.put("contingut", 1);
        assertEquals(resultat, d.getAparicions());
    }
    @Test
    public void testSetContingut () throws NoSuchFieldException, IllegalAccessException {
        Document d = new Document();

        d.setContingut("hola bon dia");

        final Field field = d.getClass().getDeclaredField("contingut");
        field.setAccessible(true);

        assertEquals("hola bon dia", field.get(d));
    }
    @Test
    public void testGetContingut () throws NoSuchFieldException, IllegalAccessException {
        Document d = new Document();

        final Field field = d.getClass().getDeclaredField("contingut");
        field.setAccessible(true);
        field.set(d, "prova de getter de contingut");

        final String result = d.getContingut();

        assertEquals("prova de getter de contingut", result);
    }
    @Test
    public void testSetStopWords () throws NoSuchFieldException, IllegalAccessException {
        Document d = new Document();

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
        Document d = new Document();

        ArrayList<String> stopWords = new ArrayList<>();
        stopWords.add("miau");
        stopWords.add("gat");


        final Field field = d.getClass().getDeclaredField("stopWords");
        field.setAccessible(true);
        field.set(d, stopWords);

        final ArrayList<String> result = d.getStopWords();

        assertEquals(stopWords, result);
    }
    @Test
    public void testSetAutor () throws NoSuchFieldException, IllegalAccessException {
        Document d = new Document();

        d.setAutor("Johan Johan Johan Haaaans");

        final Field field = d.getClass().getDeclaredField("autor");
        field.setAccessible(true);

        assertEquals("Johan Johan Johan Haaaans", field.get(d));
    }
    @Test
    public void testGetAutor () throws NoSuchFieldException, IllegalAccessException {
        Document d = new Document();

        final Field field = d.getClass().getDeclaredField("autor");
        field.setAccessible(true);
        field.set(d, "Carles de la Carlada");

        final String result = d.getAutor();

        assertEquals("Carles de la Carlada", result);
    }
    @Test
    public void testSetTitol () throws NoSuchFieldException, IllegalAccessException {
        Document d = new Document();

        d.setTitol("el millor títol");

        final Field field = d.getClass().getDeclaredField("titol");
        field.setAccessible(true);

        assertEquals("el millor títol", field.get(d));
    }
    @Test
    public void testGetTitol () throws NoSuchFieldException, IllegalAccessException {
        Document d = new Document();

        final Field field = d.getClass().getDeclaredField("titol");
        field.setAccessible(true);
        field.set(d, "Per què PROP és una assignatura amb massa feina: VOLUM IX");

        final String result = d.getTitol();

        assertEquals("Per què PROP és una assignatura amb massa feina: VOLUM IX", result);
    }
    @Test
    public void testSetPath () throws NoSuchFieldException, IllegalAccessException {
        Document d = new Document();

        d.setPath("./path/fitxer.txt");

        final Field field = d.getClass().getDeclaredField("path");
        field.setAccessible(true);

        assertEquals("./path/fitxer.txt", field.get(d));
    }
    @Test
    public void testGetPath () throws NoSuchFieldException, IllegalAccessException {
        Document d = new Document();

        final Field field = d.getClass().getDeclaredField("path");
        field.setAccessible(true);
        field.set(d, "./path/fitxer/carpeta/ola/prop/subgrup/fitxer.txt");

        final String result = d.getPath();

        assertEquals("./path/fitxer/carpeta/ola/prop/subgrup/fitxer.txt", result);
    }
    @Test
    public void testSetExtensio () throws NoSuchFieldException, IllegalAccessException {
        Document d = new Document();

        d.setExtensio(TipusExtensio.XML);

        final Field field = d.getClass().getDeclaredField("tipusExtensio");
        field.setAccessible(true);

        assertEquals(TipusExtensio.XML, field.get(d));
    }
    @Test
    public void testGetExtensio () throws NoSuchFieldException, IllegalAccessException {
        Document d = new Document();

        final Field field = d.getClass().getDeclaredField("tipusExtensio");
        field.setAccessible(true);
        field.set(d, TipusExtensio.TXT);

        final TipusExtensio result = d.getExtensio();

        assertEquals(TipusExtensio.TXT, result);
    }
    @Test
    public void testGetPes () throws NoSuchFieldException, IllegalAccessException {
        Document d = new Document();

        d.setContingut("quin pes deu tenir? qui sap, vés a saber");

        final int result = d.getPes();

        assertEquals("quin pes deu tenir? qui sap, vés a saber".length(), result);
    }
    @Test
    public void testGetAparicions () throws NoSuchFieldException, IllegalAccessException {
        Document d = new Document();
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

        assertEquals(correcte, result);
    }
    @Test
    public void testAparicionsStopWords () throws NoSuchFieldException, IllegalAccessException {
        Document d = new Document();

        ArrayList<String> stopWords = new ArrayList<>();
        stopWords.add("stop");
        stopWords.add("stop2");
        stopWords.add("stop3");

        d.setStopWords(stopWords);


        d.setContingut("stop no bon dia una casa stop2 stop3 bon dolent bon stop una era seré stop");

        final HashMap<String, Integer> correcte = new HashMap<>();
        correcte.put("no", 1);
        correcte.put("bon", 3);
        correcte.put("dia", 1);
        correcte.put("una", 2);
        correcte.put("casa", 1);
        correcte.put("dolent", 1);
        correcte.put("seré", 1);
        correcte.put("era", 1);
        final HashMap<String, Integer> result = d.getAparicions();

        assertEquals(correcte, result);
    }
    @Test
    public void testGetParaules () throws NoSuchFieldException, IllegalAccessException {
        Document d = new Document();

        ArrayList<String> stopWords = new ArrayList<>();
        stopWords.add("això");
        d.setStopWords(stopWords);

        d.setContingut("llista de paraules diferents això és una llista de paraules diferents");


        final ArrayList<String> c = new ArrayList<>();
        c.add("llista");
        c.add("de");
        c.add("paraules");
        c.add("diferents");
        c.add("una");
        c.add("és");

        Set<String> correcte = new HashSet(c);

        final Set<String> result = new HashSet(d.getParaules());

        assertEquals(correcte, result);
    }
}
