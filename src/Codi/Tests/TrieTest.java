package Codi.Tests;

import Codi.Util.Trie;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TrieTest {
    Trie trie;
    String autor;
    String autor2;
    String autor3;

    @Before
    public void init(){
        trie = new Trie<>();
    }

    @Test
    public void testConstructora() {
        assertNotNull(trie);
    }

    @Test
    public void testAfegirConteCorrecte() {
        autor = "Brandon Sanderson";
        trie.afegir(autor);
        assertTrue(trie.conteParaula(autor));
    }

    @Test
    public void testAfegirConteCorrecte2() {
        autor = "Brandon Sanderson";
        autor2 = "Brandon Sanderso";
        trie.afegir(autor);
        assertFalse(trie.conteParaula(autor2));
    }

    @Test
    public void testEsborrarCorrecte() {
        autor = "Brandon Sanderson";
        trie.afegir(autor);
        trie.esborrar(autor);
        assertFalse(trie.conteParaula(autor));
    }

    @Test
    public void testEsborrarCorrecte2() {
        autor = "Brandon Sanderson";
        autor2 = "Brandon Sanderso";
        trie.afegir(autor);
        trie.afegir(autor2);
        trie.esborrar(autor);
        assertTrue(trie.conteParaula(autor2) && !trie.conteParaula(autor));
    }

    @Test
    public void testPrefixCorrecte() {
        autor = "Brandon Sanderson";
        autor2 = "Brook Crane";
        autor3 = "Borja Sander";
        trie.afegir(autor);
        trie.afegir(autor2);
        trie.afegir(autor3);
        ArrayList<String> resultat = trie.getParaules("Br");
        ArrayList<String> res_esperat = new ArrayList<>();
        res_esperat.add(autor);
        res_esperat.add(autor2);
        assertEquals(res_esperat, resultat);
    }

    @Test
    public void testPrefixCorrecte2() {
        autor = "Brandon Sanderson";
        autor2 = "Brook Crane";
        autor3 = "Borja Sander";
        trie.afegir(autor);
        trie.afegir(autor2);
        trie.afegir(autor3);
        ArrayList<String> resultat = trie.getParaules("Bon");
        ArrayList<String> res_esperat = new ArrayList<>();
        assertEquals(res_esperat, resultat);
    }

}