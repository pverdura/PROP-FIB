package Codi.Stubs;

import Codi.Util.Trie;

import java.util.ArrayList;

public class StubTrie2 extends Trie {

    @Override
    public ArrayList<String> getParaules(String prefix) {
        System.out.println("Crida al mètode getParaules() amb paràmetre" + prefix);

        ArrayList<String> aux = new ArrayList<>();

        return aux;
    }
}