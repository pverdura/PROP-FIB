package Codi.Tests;

import Codi.Domini.CercaAllDocuments;
import Codi.Domini.Document;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;

import static org.junit.Assert.assertEquals;

public class CercaAllDocumentsTest {
    HashMap<AbstractMap.SimpleEntry<String, String>, Document> documents;
    @Before
    public void before () {
        documents = new HashMap<>();
    }
    @Test
    public void testCercaDoc () {
        documents.put(new AbstractMap.SimpleEntry("el llibre", "joanet del cul estret"), new Document("el llibre", "joanet del cul estret"));
        documents.put(new AbstractMap.SimpleEntry("comèdia barata", "joanet del cul estret"), new Document("comèdia barata", "joanet del cul estret"));
        documents.put(new AbstractMap.SimpleEntry("tipus de genolls", "pere de la cullera"), new Document("tipus de genolls", "pere de la cullera"));
        documents.put(new AbstractMap.SimpleEntry("aventures al lavabo de l'A6", "joanet del cul estret"), new Document("aventures al lavabo de l'A6", "joanet del cul estret"));
        documents.put(new AbstractMap.SimpleEntry("el llibre", "pere de la cullera"), new Document("el llibre", "pere de la cullera"));
        documents.put(new AbstractMap.SimpleEntry("el llibre", "manel vilaró"), new Document("el llibre", "manel vilaró"));
        documents.put(new AbstractMap.SimpleEntry("mecmecmec", "manel vilaró fill"), new Document("mecmecmec", "manel vilaró fill"));
        documents.put(new AbstractMap.SimpleEntry("La Colla Pessigolla", "pere de la cullera"), new Document("La Colla Pessigolla", "pere de la cullera"));
        documents.put(new AbstractMap.SimpleEntry("La Penya de L'Espardenya", "joanet del cul estret"), new Document("La Penya de L'Espardenya", "joanet del cul estret"));

        ArrayList<SimpleEntry<String, String>> c = new ArrayList<>();
        c.add(new SimpleEntry<>("el llibre", "joanet del cul estret"));
        c.add(new SimpleEntry<>("comèdia barata", "joanet del cul estret"));
        c.add(new SimpleEntry<>("tipus de genolls", "pere de la cullera"));
        c.add(new SimpleEntry<>("aventures al lavabo de l'A6", "joanet del cul estret"));
        c.add(new SimpleEntry<>("el llibre", "pere de la cullera"));
        c.add(new SimpleEntry<>("el llibre", "manel vilaró"));
        c.add(new SimpleEntry<>("mecmecmec", "manel vilaró fill"));
        c.add(new SimpleEntry<>("La Colla Pessigolla", "pere de la cullera"));
        c.add(new SimpleEntry<>("La Penya de L'Espardenya", "joanet del cul estret"));

        Set<SimpleEntry<String, String>> correcte = new HashSet<>(c);
        assertEquals(correcte, new HashSet<>(CercaAllDocuments.cercaDoc(documents)));
    }
}
