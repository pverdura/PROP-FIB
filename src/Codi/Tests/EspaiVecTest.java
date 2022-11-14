package Codi.Tests;

import Codi.Domini.*;
import Codi.Stubs.*;
import Codi.Excepcions.*;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

public class EspaiVecTest {

    @Test
    public void calculaTF_IDF() throws ArrayDeParaulesBuitException {
        ArrayList<SimpleEntry<String,Double>> paraulesIDF = new ArrayList<SimpleEntry<String,Double>>();
        SimpleEntry<String,Double> p1 = new SimpleEntry<>("gat",1.0);
        SimpleEntry<String,Double> p2 = new SimpleEntry<>("cranc",2.0);
        SimpleEntry<String,Double> p3 = new SimpleEntry<>("elefant",0.0);
        SimpleEntry<String,Double> p4 = new SimpleEntry<>("pikachu",2.0);

        paraulesIDF.add(p1);
        paraulesIDF.add(p2);
        paraulesIDF.add(p3);
        paraulesIDF.add(p4);

        Document D1 = new StubDocument();

        double result = EspaiVec.calculaTF_IDF(paraulesIDF,D1);
        assertEquals(0.2, result,0); // Resultat teòric, resultat real, marge error
    }

    @Test(expected = ArrayDeParaulesBuitException.class)
    public void calculaTF_IDFerror() throws ArrayDeParaulesBuitException {
        ArrayList<SimpleEntry<String,Double>> paraulesIDF = new ArrayList<>();

        Document D1 = new StubDocument();
        EspaiVec.calculaTF_IDF(paraulesIDF,D1);
    }
}
