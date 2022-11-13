package Codi.Tests;

import static org.junit.Assert.*;

import Codi.Domini.CercaTitol;
import Codi.Domini.Document;
import Codi.Excepcions.TitolNoExisteixException;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;

public class CercaTitolTest {
    HashMap<AbstractMap.SimpleEntry<String, String>, Document> documents;
    HashMap<String, ArrayList<String>> titolAutors;
    @Before
    public void before () {
        documents = new HashMap<>();
        titolAutors = new HashMap<>();

        ArrayList<String> llibre = new ArrayList<>();
        ArrayList<String> comedia = new ArrayList<>();
        ArrayList<String> genolls = new ArrayList<>();
        ArrayList<String> lavabo = new ArrayList<>();
        ArrayList<String> mecmecmec = new ArrayList<>();

        documents.put(new AbstractMap.SimpleEntry("el llibre", "joanet del cul estret"), new Document("el llibre", "joanet del cul estret"));
        llibre.add("joanet del cul estret");
        documents.put(new AbstractMap.SimpleEntry("comèdia barata", "joanet del cul estret"), new Document("comèdia barata", "joanet del cul estret"));
        comedia.add("joanet del cul estret");
        documents.put(new AbstractMap.SimpleEntry("tipus de genolls", "pere de la cullera"), new Document("tipus de genolls", "pere de la cullera"));
        genolls.add("pere de la cullera");
        documents.put(new AbstractMap.SimpleEntry("aventures al lavabo de l'A6", "joanet del cul estret"), new Document("aventures al lavabo de l'A6", "joanet del cul estret"));
        lavabo.add("joanet del cul estret");
        documents.put(new AbstractMap.SimpleEntry("el llibre", "pere de la cullera"), new Document("el llibre", "pere de la cullera"));
        llibre.add("pere de la cullera");
        documents.put(new AbstractMap.SimpleEntry("el llibre", "manel vilaró"), new Document("el llibre", "manel vilaró"));
        llibre.add("manel vilaró");
        documents.put(new AbstractMap.SimpleEntry("mecmecmec", "manel vilaró fill"), new Document("mecmecmec", "manel vilaró fill"));
        mecmecmec.add("manel vilaró fill");
        documents.put(new AbstractMap.SimpleEntry("mecmecmec", "pere de la cullera"), new Document("La Colla Pessigolla", "pere de la cullera"));
        mecmecmec.add("pere de la cullera");
        documents.put(new AbstractMap.SimpleEntry("mecmecmec", "joanet del cul estret"), new Document("La Penya de L'Espardenya", "joanet del cul estret"));
        mecmecmec.add("joanet del cul estret");

        titolAutors.put("el llibre", llibre);
        titolAutors.put("comèdia barata", comedia);
        titolAutors.put("tipus de genolls", genolls);
        titolAutors.put("aventures al lavabo de l'A6", lavabo);
        titolAutors.put("mecmecmec", mecmecmec);
    }
    @Test
    public void testCercaDoc () {
        String titol = "mecmecmec";
        ArrayList<AbstractMap.SimpleEntry<String, String>> c = new ArrayList<>();
        c.add(new SimpleEntry(titol, "manel vilaró fill"));
        c.add(new SimpleEntry(titol, "pere de la cullera"));
        c.add(new SimpleEntry(titol, "joanet del cul estret"));

        Set<AbstractMap.SimpleEntry<String, String>> correcte = new HashSet<>(c);

        Set<AbstractMap.SimpleEntry<String, String>> resultat = new HashSet<>(CercaTitol.cercaDoc(titol, titolAutors));

        assertEquals(correcte, resultat);
    }
    @Test(expected = TitolNoExisteixException.class)
    public void testCercaDocException () {
        CercaTitol.cercaDoc("no existeix quina pena", titolAutors);
    }
}
