package Codi.Stubs;

import Codi.Domini.Document;

import java.util.HashMap;

public class StubDocumentCP4 extends Document {
    @Override
    public HashMap<String, Integer> getAparicions() {
        System.out.println("Crida al metode getAparicions() sense paràmetres");

        HashMap<String, Integer> aux = new HashMap<String, Integer>();
        aux.put("gat",1);
        aux.put("pikachu",1);

        return aux;
    }
}