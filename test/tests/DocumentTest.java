package tests;


import Codi.Domini.CercaTitol;
import Codi.Domini.CercaTitolAutor;
import Codi.Domini.Document;

import java.util.AbstractMap.SimpleEntry;

import java.util.ArrayList;
import java.util.HashMap;

public class DocumentTest {
    public void testSetContingut () {

    }
    public static void main (String[] args) {
        Document d1 = new Document("mec", "jordi");
        SimpleEntry<String, String> id1 = new SimpleEntry<>("mec", "jordi");
        Document d2 = new Document("joventut", "jordi");
        SimpleEntry<String, String> id2 = new SimpleEntry<>("joventut", "jordi");
        Document d3 = new Document("joventut", "pol");
        SimpleEntry<String, String> id3 = new SimpleEntry<>("joventut", "pol");
        Document d4 = new Document("aire", "jordi");
        SimpleEntry<String, String> id4 = new SimpleEntry<>("aire", "jordi");
        Document d5 = new Document("sirena", "judit");
        SimpleEntry<String, String> id5 = new SimpleEntry<>("sirena", "judit");
        Document d6 = new Document("serenor", "judit");
        SimpleEntry<String, String> id6 = new SimpleEntry<>("serenor", "judit");

        HashMap<SimpleEntry<String, String>, Document> documents = new HashMap<>();
        documents.put(id1, d1);
        documents.put(id2, d2);
        documents.put(id3, d3);
        documents.put(id4, d4);
        documents.put(id5, d5);
        documents.put(id6, d6);

        ArrayList<String> s = new ArrayList<>();
        //Document.setStopWords(s);

        //cerca títol autor
        Document res = CercaTitolAutor.cercaDoc("mec", "jordi", documents);
        System.out.println(res.getTitol()+" "+res.getAutor());

        System.out.println(documents.get(id6).getTitol());
        System.out.println(documents.get(new SimpleEntry<>("serenor", "judit")).getTitol());

        Document res2 = CercaTitolAutor.cercaDoc("sirena", "judit", documents);
        System.out.println(res2.getPes());


        /*Document d7 = new Document("1", "2");
        SimpleEntry<String, String> id7 = new SimpleEntry<>("1", "2");

        HashMap<SimpleEntry<String, String>, Document> documents2 = new HashMap<SimpleEntry<String, String>, Document>();

        documents2.put(id7, d7);

        System.out.println(documents2.get(id7).getTitol());
        System.out.println(documents2.get(new SimpleEntry<>("1", "2")).getTitol());*/

        //cerca títol


        SimpleEntry<String, Integer> s2 = new SimpleEntry<>("2", 2);
        int b = s2.getValue();
    }
}
