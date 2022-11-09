package Codi.Util;


import java.util.HashMap;
import java.util.Map;

public class Trie<T> {

    private class TrieNode {
        private Map<Character, TrieNode> fills = new HashMap<>();
        private boolean fi_paraula;
    }

    private TrieNode arrel;


    public Trie() {
        arrel = new TrieNode();
        arrel.fi_paraula = false;
    }

    public void afegir(String paraula) {
        TrieNode actual = arrel;
        for (int i = 0; i < paraula.length(); ++i){
            char ch = paraula.charAt(i);
            actual = actual.fills.computeIfAbsent(ch, c -> new TrieNode());
        }
        actual.fi_paraula = true;
    }

    public boolean esborra(String paraula) {
        return esborra(arrel, paraula, 0);
    }

    public boolean conteParaula(String paraula) {
        TrieNode actual = arrel;

        for (int i = 0; i < paraula.length(); ++i) {
            char ch = paraula.charAt(i);
            TrieNode node = actual.fills.get(ch);
            if (node == null) {
                return false;
            }
            actual = node;
        }
        return actual.fi_paraula;
    }

    boolean isEmpty() {
        return arrel == null;
    }

    private boolean esborra(TrieNode actual, String paraula, int index) {
        if (index == paraula.length()) {
            if (!actual.fi_paraula) {
                return false;
            }
            actual.fi_paraula = false;
            return actual.fills.isEmpty();
        }
        char ch = paraula.charAt(index);
        TrieNode node = actual.fills.get(ch);
        if (node == null) {
            return false;
        }
        boolean es_pot_borrar = esborra(node, paraula, index + 1) && !node.fi_paraula;

        if (es_pot_borrar) {
            actual.fills.remove(ch);
            return actual.fills.isEmpty();
        }
        return false;
    }
}
