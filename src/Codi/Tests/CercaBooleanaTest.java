package Codi.Tests;

import Codi.Domini.CercaBooleana;
import Codi.Domini.Document;
import Codi.Stubs.*;
import org.junit.Before;
import org.junit.Test;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class CercaBooleanaTest {

    HashMap<SimpleEntry<String,String>, Document> documents;

    @Before
    public void init() {
        Document doc = new Document("titol", "pau");
        doc.setContingut("Document del pau : Bon dia a tothom");

        Document doc2 = new Document("titol", "jordi");
        doc2.setContingut("Document del jordi : Sóc professional del tetris");

        Document doc3 = new Document("titol", "judit");
        doc3.setContingut("Document de la judit : Ahir vaig anar a fer escalada");

        Document doc4 = new Document("titol", "pol");
        doc4.setContingut("Document del pol : Això és una obra d'art");

        documents = new HashMap<>();
        documents.put(new SimpleEntry<>("titol","jordi"),doc2);
        documents.put(new SimpleEntry<>("titol","judit"),doc3);
        documents.put(new SimpleEntry<>("titol","pol"),doc4);
        documents.put(new SimpleEntry<>("titol","pau"),doc);

    }

    @Test
    public void cercaDocTest1() {
        ArrayList<SimpleEntry<String,String>> expected_documents = new ArrayList<>();
        expected_documents.add(new SimpleEntry<>("titol","judit"));
        expected_documents.add(new SimpleEntry<>("titol","jordi"));
        expected_documents.add(new SimpleEntry<>("titol","pol"));
        expected_documents.add(new SimpleEntry<>("titol","pau"));

        assertEquals(expected_documents, CercaBooleana.cercaDoc(new StubExpressioBooleana1("Document"), documents));
    }

    @Test
    public void cercaDocTest2() {
        ArrayList<SimpleEntry<String,String>> expected_documents = new ArrayList<>();
        expected_documents.add(new SimpleEntry<>("titol","pol"));
        expected_documents.add(new SimpleEntry<>("titol","pau"));

        assertEquals(expected_documents, CercaBooleana.cercaDoc(new StubExpressioBooleana2("Document & (pol | pau)"), documents));
    }

    @Test
    public void cercaDocTest3() {
        ArrayList<SimpleEntry<String,String>> expected_documents = new ArrayList<>();
        expected_documents.add(new SimpleEntry<>("titol","judit"));
        expected_documents.add(new SimpleEntry<>("titol","jordi"));

        assertEquals(expected_documents, CercaBooleana.cercaDoc(new StubExpressioBooleana3("Document & !(pol | pau)"), documents));
    }

    @Test
    public void cercaDocTest4() {
        ArrayList<SimpleEntry<String,String>> expected_documents = new ArrayList<>();
        expected_documents.add(new SimpleEntry<>("titol","judit"));
        expected_documents.add(new SimpleEntry<>("titol","jordi"));
        expected_documents.add(new SimpleEntry<>("titol","pau"));

        assertEquals(expected_documents, CercaBooleana.cercaDoc(new StubExpressioBooleana4("{Document :} & ((escalada | tetris | tothom | obra)) & !pol"), documents));
    }

    @Test
    public void cercaDocTest5() {
        ArrayList<SimpleEntry<String,String>> expected_documents = new ArrayList<>();
        assertEquals(expected_documents, CercaBooleana.cercaDoc(new StubExpressioBooleana5("{Document a} & ((escalada | tetris | tothom | obra) & \"Bona tarda\")"), documents));
    }
}
