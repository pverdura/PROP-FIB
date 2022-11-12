package Codi.Tests;

import Codi.Util.Trie;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TrieTest {
    Trie trie;
    String autor1;
    String autor2;
    String autor3;

    @Before
    public void init(){ trie = new Trie<>();  }

    @Test
    public void testConstructora() {
        assertNotNull(trie);
    }

    @Test
    public void testAfegirConteCorrecte() {
        autor1 = "Brandon Sanderson";
        trie.afegir(autor1);
        assertTrue(trie.conteParaula(autor1));
    }

    @Test
    public void testAfegirConteCorrecte2() {
        autor1 = "Brandon Sanderson";
        autor2 = "Brandon Sanderso";
        trie.afegir(autor1);
        assertFalse(trie.conteParaula(autor2));
    }

    @Test
    public void testEsborrarCorrecte() {
        autor1 = "Brandon Sanderson";
        trie.afegir(autor1);
        trie.esborrar(autor1);
        assertFalse(trie.conteParaula(autor1));
    }

    @Test
    public void testEsborrarCorrecte2() {
        autor1 = "Brandon Sanderson";
        autor2 = "Brandon Sanderso";
        trie.afegir(autor1);
        trie.afegir(autor2);
        trie.esborrar(autor1);
        assertTrue(trie.conteParaula(autor2) && !trie.conteParaula(autor1));
    }

    @Test
    public void testAfegirEsborrarCorrecte2() {
        autor1 = "Brandon Sanderson";
        trie.afegir(autor1);
        trie.afegir(autor1);
        trie.esborrar(autor1);
        assertFalse(trie.conteParaula(autor1));
    }

    @Test
    public void testPrefixCorrecte() {
        autor1 = "Brandon Sanderson";
        autor2 = "Brook Crane";
        autor3 = "Borja Sander";
        trie.afegir(autor1);
        trie.afegir(autor2);
        trie.afegir(autor3);
        ArrayList<String> resultat = trie.getParaules("Br");
        ArrayList<String> res_esperat = new ArrayList<>();
        res_esperat.add(autor1);
        res_esperat.add(autor2);
        assertEquals(res_esperat, resultat);
    }

    @Test
    public void testPrefixCorrecte2() {
        autor1 = "Brandon Sanderson";
        autor2 = "Brook Crane";
        autor3 = "Borja Sander";
        trie.afegir(autor1);
        trie.afegir(autor2);
        trie.afegir(autor3);
        ArrayList<String> resultat = trie.getParaules("B");
        ArrayList<String> res_esperat = new ArrayList<>();
        res_esperat.add(autor1);
        res_esperat.add(autor2);
        res_esperat.add(autor3);
        assertEquals(res_esperat, resultat);
    }

    @Test
    public void testPrefixCorrecte3() {
        autor1 = "Brandon Sanderson";
        autor2 = "Brook Crane";
        autor3 = "Borja Sander";
        trie.afegir(autor1);
        trie.afegir(autor2);
        trie.afegir(autor3);
        ArrayList<String> resultat = trie.getParaules("Bon");
        ArrayList<String> res_esperat = new ArrayList<>();
        assertEquals(res_esperat, resultat);
    }

}