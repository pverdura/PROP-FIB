package Codi.Util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

public class Trie<T> {

    private static class TrieNode {
        private final Map<Character, TrieNode> fills = new HashMap<>();
        private boolean fi_paraula;
    }

    private final TrieNode arrel;


    public Trie() {
        arrel = new TrieNode();
        arrel.fi_paraula = false;
    }

    public void afegir(String paraula) {
        TrieNode actual = arrel;
        for (int i = 0; i < paraula.length(); ++i){
            char ch = paraula.charAt(i);
            if (!actual.fills.containsKey(ch)) {
                actual.fills.put(ch, new TrieNode());
            }
            actual = actual.fills.get(ch);
        }
        actual.fi_paraula = true;
    }

    public void esborrar(String paraula) {
        esborrar(arrel, paraula, 0);
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


    private   SimpleEntry<Boolean, TrieNode> contePrefix(String prefix){
        TrieNode actual = arrel;
        for (int i = 0; i < prefix.length(); ++i) {
            char ch = prefix.charAt(i);
            TrieNode node = actual.fills.get(ch);
            if (node == null) {
                return new SimpleEntry<>(false, null);
            }
            actual = node;
        }
        return new SimpleEntry<>(true, actual);
    }


    /* obte les paraules que comencen pel prefix*/
    public ArrayList<String> getParaules(String prefix) {
        ArrayList<String> paraules = new ArrayList<>();
          SimpleEntry<Boolean, TrieNode> existeixPrefix = contePrefix(prefix);
        if (existeixPrefix.getKey()){
            getParaulesNode(prefix, existeixPrefix.getValue(), paraules);
        }
        return paraules;
    }

    private void getParaulesNode(String prefix, TrieNode n, ArrayList<String> paraules) {
        if(n.fi_paraula) paraules.add(prefix);
        if(n.fills.isEmpty()) {
            return;
        }
        for(Character c: n.fills.keySet()) {
            getParaulesNode(prefix + c, n.fills.get(c), paraules);
        }
    }

    private boolean esborrar(TrieNode actual, String paraula, int index) {
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
        boolean es_pot_borrar = esborrar(node, paraula, index + 1) && !node.fi_paraula;

        if (es_pot_borrar) {
            actual.fills.remove(ch);
            return actual.fills.isEmpty();
        }
        return false;
    }
}
