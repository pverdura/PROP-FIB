package Codi.Tests;

import Codi.Domini.CercaAutor;
import Codi.Excepcions.AutorNoExisteixException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class CercaAutorTest {
    ArrayList<SimpleEntry<String ,String>> res_esperat;
    HashMap<String, ArrayList<String>> autorTitols;
    String autor;
    String autor1;
    String autor2;
    String autor3;

    ArrayList<String> titols;

    @Before
    public void init(){
        res_esperat = new ArrayList<>();
        autorTitols = new HashMap<>();
        titols = new ArrayList<>();
        titols.add("Computació quàntica");
        titols.add("Projectes de programació");
        titols.add("Assumptes externs");
        autor = "Romul Vazquez";
        autor1 = "Josep Salazar";
        autor2 = "Montserrat Cavaller";
        autor3 = "Romul Vazquez";
        autorTitols.put(autor1, titols);
        autorTitols.put(autor2, titols);
        autorTitols.put(autor3, titols);
    }

    @Test
    public void testCerca(){
        res_esperat.add(new SimpleEntry<>(titols.get(0), autor));
        res_esperat.add(new SimpleEntry<>(titols.get(1), autor));
        res_esperat.add(new SimpleEntry<>(titols.get(2), autor));

        assertEquals(res_esperat,CercaAutor.cercaDoc(autor,autorTitols));
    }



    @Test(expected = AutorNoExisteixException.class)
    public void testAutorsExcepcio() {
        CercaAutor.cercaDoc("George Martin", autorTitols);
    }
}
