package Codi.Util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

/**
 * Classe que implementa un Trie
 *
 * @author Judit Serna
 */
public class Trie {

    /**
     * Classe per crear un node del Trie
     */
    private static class TrieNode {
        private final Map<Character, TrieNode> fills = new HashMap<>();
        private boolean fi_paraula;
    }

    /**
     * Node arrel a partir del qual es comença a crear el Trie
     */
    private final TrieNode arrel;


    /**
     * Creadora per defecte
     */
    public Trie() {
        arrel = new TrieNode();
        arrel.fi_paraula = false;
    }

    /**
     * Afegeix una paraula al Trie
     *
     * @param paraula Indica la paraula a afegir al Trie
     */
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

    /**
     * Esborra una paraula del Trie
     *
     * @param paraula Indica la paraula que es vol esborrar
     */
    public void esborrar(String paraula) {
        esborrar(arrel, paraula, 0);
    }

    /**
     * Comprova si el Trie conte una paraula
     *
     * @param paraula Indica la paraula que es vol trobar
     * @return  {@code boolean} Retorna true si el Trie conte la paraula, false altrament
     */
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

    /**
     * Comprova si el Trie conte un prefix
     *
     * @param prefix Indica el prefix que es vol trobar
     * @return  {@code boolean} Retorna true si el Trie conte el prefix, false altrament
     */
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

    /**
     * Obte les paraules que comencen pel prefix
     *
     * @param prefix Indica el prefix pel qual han de començar les paraules
     * @return {@code ArrayList<String>} Retorna les paraules que comencen pel prefix
     */
    public ArrayList<String> getParaules(String prefix) {
        ArrayList<String> paraules = new ArrayList<>();
          SimpleEntry<Boolean, TrieNode> existeixPrefix = contePrefix(prefix);
        if (existeixPrefix.getKey()){
            getParaulesNode(prefix, existeixPrefix.getValue(), paraules);
        }
        return paraules;
    }

    /**
     * Obte les paraules a partir d'un node i el prefix
     *
     * @param prefix Indica el prefix pel qual han de començar les paraules
     * @param n Indica el node a partir del qual s'han d'obtenir les paraules
     * @param paraules {@code ArrayList<String>} Estructura on es guarden les paraules trobades
     */
    private void getParaulesNode(String prefix, TrieNode n, ArrayList<String> paraules) {
        if(n.fi_paraula) paraules.add(prefix);
        if(n.fills.isEmpty()) {
            return;
        }
        for(Character c: n.fills.keySet()) {
            getParaulesNode(prefix + c, n.fills.get(c), paraules);
        }
    }

    /**
     * Esborra una paraula del Trie
     *
     * @param actual Indica el node actual
     * @param paraula Indica la paraula a esborrar
     * @param index Indica la posicio de la lletra de la paraula a esborrar
     * @return {@code boolean} Retorna true si s'ha esborrat la paraula completa, false altrament
     */
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
