package Codi.Stubs;

import Codi.Domini.*;

import java.util.*;

public class StubDocument extends Document {

    @Override
    public HashMap<String, Integer> getAparicions() {
        System.out.println("Crida al metode getAparicions() sense paràmetres");

        HashMap<String, Integer> aux = new HashMap<String, Integer>();
        aux.put("hola",1);
        aux.put("adeu",4);
        aux.put("macarrons",3);
        aux.put("verdura",3);

        return aux;
    }
}
