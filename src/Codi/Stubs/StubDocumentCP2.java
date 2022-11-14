package Codi.Stubs;

import Codi.Domini.Document;

import java.util.HashMap;

public class StubDocumentCP2 extends Document {
    @Override
    public HashMap<String, Integer> getAparicions() {
        System.out.println("Crida al metode getAparicions() sense paràmetres");

        HashMap<String, Integer> aux = new HashMap<String, Integer>();
        aux.put("pikachu",1);
        aux.put("gos",1);
        aux.put("gat",1);

        return aux;
    }
}
