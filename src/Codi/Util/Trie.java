package Codi.Util;


public class Trie<T> {

    //CONSTRUCTORA


    public Trie() {
        arrel = null;

    }


    private static class node_trie{
        String info;

        node_trie left;
        node_trie right;
        node_trie center;

        Boolean end_word;
    }


    node_trie arrel;

    private void afegirParaula(node_trie n, String info, int i){
        String s = Character.toString(info.charAt(i));

        if (n == null){
            node_trie aux = new node_trie();

            aux.info = s;
            aux.right = null;
            aux.left = null;
            aux.center = null;
            aux.end_word = false;

            n = aux;
            ++i;

            if (i < info.length()) afegirParaula(n.center, info, i);
            else n.end_word = true;
        }
        else if (n.info.compareTo(s) == 0){
            ++i;
            if (i < info.length()) afegirParaula(n.center, info, i);
            else n.end_word = true;
        }
        else if (n.info.compareTo(s) > 0) afegirParaula(n.right, info, i);
        else if (n.info.compareTo(s) < 0) afegirParaula(n.left, info, i);
    }

    private void simplificaTrie(node_trie n){
        if (n != null) {
            node_trie seg = new node_trie();
            seg.center = n.center;

            if (n.right == null && n.left == null && seg.right == null && seg.left == null && !(n.end_word)){
                n.info += seg.info;
                n.end_word = seg.end_word;
                simplificaTrie(n);
            }
            simplificaTrie(n.right);
            simplificaTrie(n.center);
            simplificaTrie(n.left);
        }
    }

    private Boolean inclouParaula(String a, String b, int i, int  k){
        Boolean resultat = true;
        
        if ((a.length() - i) <= b.length()){
            k = 0;
            int m = a.length() - i;
            while(k < m){
                if (Character.toString(a.charAt(i)) != Character.toString(b.charAt(k))) resultat = false;
                ++i;
                ++k;
            }
            k = 0;
        }
        else {
            k = 0;
            while(k < b.length()){
                if (Character.toString(a.charAt(i)) != Character.toString(b.charAt(k))) resultat = false;
                ++i;
                ++k;
            }
            k = 1;
        }
        return resultat;
    }

    private void existeixParaula(String paraula, node_trie n, int i, Boolean e){

        int j = 0;
        if (n == null){
            e = false;
            return;
        } else if (i == paraula.length()) {
            e = true;
            return;
        } else if (n.info.charAt(0) == paraula.charAt(i) && inclouParaula(paraula, n.info, i, j)) {
            if (j == 0){
                i += n.info.length();
                e = true;
                return;
            }
            else {
                int salt = n.info.length();
                existeixParaula(paraula, n.center, i + salt, e);
            }
        }
        else if (n.info.charAt(0) < paraula.charAt(i)) existeixParaula(paraula, n.left, i, e);
        else if (n.info.charAt(0) > paraula.charAt(i)) existeixParaula(paraula, n.right, i, e);
    }

    //MODIFICADORES
    /*  Afegeix l'string c al trie
        Pre: cert
        Post: s'ha afegit el string c al trie
     */
    public void afegir(String paraula){
        afegirParaula(arrel, paraula, 0);
    }

    public void simplifica(){
        simplificaTrie(arrel);
    }

    public Boolean comprovar(String c){
        Boolean e = false;
        existeixParaula(c, arrel, 0, e);
        return e;
    }

    private void escriureNodes(node_trie n){
        if (n != null){
            System.out.println(n.info);
            escriureNodes(n.right);
            escriureNodes(n.center);
            escriureNodes(n.left);
        }
    }

    public void escriure(){
        escriureNodes(arrel);
    }
}
