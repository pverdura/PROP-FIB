package Codi.Tests;

import static org.junit.Assert.*;

import Codi.Domini.Document;
import Codi.Excepcions.DocumentInexistentException;
import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;

public class CercaTitolAutorTest {
    HashMap<SimpleEntry<String, String>, Document> documents;
    @Before
    public void before () {
        documents = new HashMap<>();
        documents.put(new SimpleEntry("el llibre", "joanet del cul estret"), new Document("el llibre", "joanet del cul estret"));
        documents.put(new SimpleEntry("comèdia barata", "joanet del cul estret"), new Document("comèdia barata", "joanet del cul estret"));
        documents.put(new SimpleEntry("tipus de genolls", "pere de la cullera"), new Document("tipus de genolls", "pere de la cullera"));
        documents.put(new SimpleEntry("aventures al lavabo de l'A6", "joanet del cul estret"), new Document("aventures al lavabo de l'A6", "joanet del cul estret"));
        documents.put(new SimpleEntry("el llibre", "pere de la cullera"), new Document("el llibre", "pere de la cullera"));
        documents.put(new SimpleEntry("el llibre", "manel vilaró"), new Document("el llibre", "manel vilaró"));
        documents.put(new SimpleEntry("mecmecmec", "manel vilaró fill"), new Document("mecmecmec", "manel vilaró fill"));
        documents.put(new SimpleEntry("La Colla Pessigolla", "pere de la cullera"), new Document("La Colla Pessigolla", "pere de la cullera"));
        documents.put(new SimpleEntry("La Penya de L'Espardenya", "joanet del cul estret"), new Document("La Penya de L'Espardenya", "joanet del cul estret"));
    }
    @Test
    public void testCercaDocStrings () {
        Document prova = new Document("titol", "autor");
        documents.put(new SimpleEntry("titol", "autor"), prova);

        Document resultat = CercaTitolAutor.cercaDoc("titol", "autor", documents);

        assertSame(prova, resultat);
    }
    @Test(expected = DocumentInexistentException.class)
    public void testCercaDocStringsException () {
        Document resultat = CercaTitolAutor.cercaDoc("titol", "autor", documents);
    }
    @Test
    public void testCercaDocSimpleEntry () {
        Document prova = new Document("titol", "autor");
        documents.put(new SimpleEntry("titol", "autor"), prova);

        Document resultat = CercaTitolAutor.cercaDoc(new SimpleEntry("titol", "autor"), documents);

        assertSame(prova, resultat);
    }
    @Test(expected = DocumentInexistentException.class)
    public void testCercaDocSimpleEntryException () {
        Document resultat = CercaTitolAutor.cercaDoc(new SimpleEntry("titol", "autor"), documents);
    }
}
