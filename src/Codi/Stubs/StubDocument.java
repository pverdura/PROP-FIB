package Codi.Stubs;

import Codi.Domini.*;

import java.util.*;

public class StubDocument extends Document {

    @Override
    public HashMap<String, Integer> getAparicions() {
        System.out.println("Crida al metode getAparicions() sense paràmetres");

        HashMap<String, Integer> aux = new HashMap<String, Integer>();
        aux.put("gat",1);
        aux.put("elefant",4);

        return aux;
    }
}
