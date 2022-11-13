package Codi.Tests;

import Codi.Domini.CercaPrefix;
import Codi.Excepcions.PrefixNoExisteixException;
import Codi.Stubs.StubTrie1;
import Codi.Stubs.StubTrie2;
import Codi.Util.Trie;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CercaPrefixTest {
    ArrayList<String> resultat;
    ArrayList<String> res_esperat;
    Trie autors;
    String autor1;
    String autor2;
    String autor3;
    String prefix;

    @Before
    public void init() {
        resultat = new ArrayList<>();
        res_esperat = new ArrayList<>();
        autor1 = "Brandon Sanderso";
        autor2 = "Brandon Sanderson";
        autor3 = "Brook Crane";

    }


    @Test
    public void testCercaCorrecte(){
        autors = new StubTrie1();
        prefix = "Br";
        resultat = CercaPrefix.cercaDoc(prefix, autors);

        res_esperat.add(autor1);
        res_esperat.add(autor2);
        res_esperat.add(autor3);

        assertEquals(res_esperat, resultat);
    }



    @Test(expected = PrefixNoExisteixException.class)
    public void testPrefixExcepcio() {
        autors = new StubTrie2();
        prefix = "Bon";
        resultat = CercaPrefix.cercaDoc(prefix, autors);
    }

}