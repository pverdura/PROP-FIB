package Codi.Tests;

import Codi.Domini.CercaParaules;
import Codi.Domini.Document;
import Codi.Excepcions.ArrayDeParaulesBuitException;
import Codi.Excepcions.NombreMassaPetitDocumentsException;
import Codi.Stubs.StubDocumentCP1;
import Codi.Stubs.StubDocumentCP2;
import Codi.Stubs.StubDocumentCP3;
import Codi.Stubs.StubDocumentCP4;
import org.junit.Test;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class CercaParaulesTest {

    @Test
    public void cercaDoc() throws ArrayDeParaulesBuitException, NombreMassaPetitDocumentsException {
        ArrayList<String> paraules = new ArrayList<String>();
        paraules.add("pikachu");
        paraules.add("gos");

        HashMap<String,ArrayList<SimpleEntry<String,String>>> DocumentsParaules = new  HashMap<String,ArrayList<SimpleEntry<String,String>>>();
        HashMap<SimpleEntry<String,String>,Document> Documents = new HashMap<SimpleEntry<String,String>,Document>();

        SimpleEntry<String,String> id1 = new SimpleEntry<String,String>("D1","Pol");
        SimpleEntry<String,String> id2 = new SimpleEntry<String,String>("D2","Pau");
        SimpleEntry<String,String> id3 = new SimpleEntry<String,String>("D3","Jordi");
        SimpleEntry<String,String> id4 = new SimpleEntry<String,String>("D4","Judit");

        Document D1 = new StubDocumentCP1();
        Document D2 = new StubDocumentCP2();
        Document D3 = new StubDocumentCP3();
        Document D4 = new StubDocumentCP4();

        Documents.put(id1,D1);
        Documents.put(id2,D2);
        Documents.put(id3,D3);
        Documents.put(id4,D4);

        ArrayList<SimpleEntry<String,String>> elefant= new ArrayList<SimpleEntry<String,String>>();
        ArrayList<SimpleEntry<String,String>> pikachu= new ArrayList<SimpleEntry<String,String>>();
        ArrayList<SimpleEntry<String,String>> gos= new ArrayList<SimpleEntry<String,String>>();
        ArrayList<SimpleEntry<String,String>> gat= new ArrayList<SimpleEntry<String,String>>();

        elefant.add(id1);
        pikachu.add(id1);
        pikachu.add(id2);
        pikachu.add(id4);
        gos.add(id2);
        gos.add(id3);
        gat.add(id2);
        gat.add(id4);

        DocumentsParaules.put("elefant",elefant);
        DocumentsParaules.put("pikachu",pikachu);
        DocumentsParaules.put("gos",gos);
        DocumentsParaules.put("gat",gat);

        ArrayList<SimpleEntry<String,String>> resposta = CercaParaules.cercaDoc(paraules,2,DocumentsParaules,Documents);

        ArrayList<SimpleEntry<String,String>> teoric = new ArrayList<SimpleEntry<String,String>>();
        teoric.add(id3);
        teoric.add(id4);

        assertEquals(teoric,resposta);
    }

    @Test(expected = ArrayDeParaulesBuitException.class)
    public void cercaDocBuit() throws ArrayDeParaulesBuitException, NombreMassaPetitDocumentsException {
        ArrayList<String> paraules = new ArrayList<String>();

        HashMap<String,ArrayList<SimpleEntry<String,String>>> DocumentsParaules = new  HashMap<String,ArrayList<SimpleEntry<String,String>>>();
        HashMap<SimpleEntry<String,String>,Document> Documents = new HashMap<SimpleEntry<String,String>,Document>();

        SimpleEntry<String,String> id1 = new SimpleEntry<String,String>("D1","Pol");    // elefant pikachu
        SimpleEntry<String,String> id2 = new SimpleEntry<String,String>("D2","Pau");    // pikachu gos gat
        SimpleEntry<String,String> id3 = new SimpleEntry<String,String>("D3","Jordi");  // gos
        SimpleEntry<String,String> id4 = new SimpleEntry<String,String>("D4","Judit");  // gat pikachu

        Document D1 = new StubDocumentCP1();
        Document D2 = new StubDocumentCP2();
        Document D3 = new StubDocumentCP3();
        Document D4 = new StubDocumentCP4();

        Documents.put(id1,D1);
        Documents.put(id2,D2);
        Documents.put(id3,D3);
        Documents.put(id4,D4);

        ArrayList<SimpleEntry<String,String>> p1Docs= new ArrayList<SimpleEntry<String,String>>();
        ArrayList<SimpleEntry<String,String>> p2Docs= new ArrayList<SimpleEntry<String,String>>();
        ArrayList<SimpleEntry<String,String>> p3Docs= new ArrayList<SimpleEntry<String,String>>();
        ArrayList<SimpleEntry<String,String>> p4Docs= new ArrayList<SimpleEntry<String,String>>();

        p1Docs.add(id1);
        p2Docs.add(id1);
        p2Docs.add(id2);
        p2Docs.add(id4);
        p3Docs.add(id2);
        p3Docs.add(id3);
        p4Docs.add(id2);
        p4Docs.add(id4);

        DocumentsParaules.put("elefant",p1Docs);
        DocumentsParaules.put("pikachu",p2Docs);
        DocumentsParaules.put("gos",p3Docs);
        DocumentsParaules.put("gat",p4Docs);

        CercaParaules.cercaDoc(paraules,2,DocumentsParaules,Documents);
    }

    @Test(expected = NombreMassaPetitDocumentsException.class)
    public void cercaDocNegatiu() throws ArrayDeParaulesBuitException, NombreMassaPetitDocumentsException {
        ArrayList<String> paraules = new ArrayList<String>();
        paraules.add("pikachu");
        paraules.add("gos");

        HashMap<String,ArrayList<SimpleEntry<String,String>>> DocumentsParaules = new  HashMap<String,ArrayList<SimpleEntry<String,String>>>();
        HashMap<SimpleEntry<String,String>,Document> Documents = new HashMap<SimpleEntry<String,String>,Document>();

        SimpleEntry<String,String> id1 = new SimpleEntry<String,String>("D1","Pol");    // elefant pikachu
        SimpleEntry<String,String> id2 = new SimpleEntry<String,String>("D2","Pau");    // pikachu gos gat
        SimpleEntry<String,String> id3 = new SimpleEntry<String,String>("D3","Jordi");  // gos
        SimpleEntry<String,String> id4 = new SimpleEntry<String,String>("D4","Judit");  // gat pikachu

        Document D1 = new StubDocumentCP1();
        Document D2 = new StubDocumentCP2();
        Document D3 = new StubDocumentCP3();
        Document D4 = new StubDocumentCP4();

        Documents.put(id1,D1);
        Documents.put(id2,D2);
        Documents.put(id3,D3);
        Documents.put(id4,D4);

        ArrayList<SimpleEntry<String,String>> p1Docs= new ArrayList<SimpleEntry<String,String>>();
        ArrayList<SimpleEntry<String,String>> p2Docs= new ArrayList<SimpleEntry<String,String>>();
        ArrayList<SimpleEntry<String,String>> p3Docs= new ArrayList<SimpleEntry<String,String>>();
        ArrayList<SimpleEntry<String,String>> p4Docs= new ArrayList<SimpleEntry<String,String>>();

        p1Docs.add(id1);
        p2Docs.add(id1);
        p2Docs.add(id2);
        p2Docs.add(id4);
        p3Docs.add(id2);
        p3Docs.add(id3);
        p4Docs.add(id2);
        p4Docs.add(id4);

        DocumentsParaules.put("elefant",p1Docs);
        DocumentsParaules.put("pikachu",p2Docs);
        DocumentsParaules.put("gos",p3Docs);
        DocumentsParaules.put("gat",p4Docs);

        CercaParaules.cercaDoc(paraules,-1,DocumentsParaules,Documents);
    }
}
