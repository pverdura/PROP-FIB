package Codi.Util;

class Node {
    String value;
    Node left;
    Node right;

    Node() {
        this.value = null;
        this.left = null;
        this.right = null;
    }
}

public class BinaryTree {

    private Node root;

    public BinaryTree(String expressio) {
        this.root = new Node();
        insertar(this.root, expressio);
    }

    private void insertar(Node node, String expressio) {

        boolean op_asignado = insertarOP_i_dividir('|', node, expressio);

        if (!op_asignado) {
            op_asignado = insertarOP_i_dividir('&', node, expressio);

            if (!op_asignado){
                op_asignado =  insertarOP_i_dividir('!', node, expressio);

                if (!op_asignado) {
                    if (expressio.charAt(0) == '(') {
                        //Eliminar () más externo y volver a empezar
                        expressio = expressio.substring(1, expressio.length()-1);
                        insertar(node, expressio);

                    } else {
                        node.value = expressio;
                    }
                }
            }
        }
    }
    private boolean insertarOP_i_dividir(char op, Node node, String expressio) {
        int l = expressio.length();
        int i = l-1;

        int suma_substring = op == '!' ? 1 : 2;

        while (i >= 0) {
            char c = expressio.charAt(i);

            if (c == ')' || c == '"' || c ==  '}') {
               switch (c) {
                   case ')':
                       i = ignorar_seq(')', '(', i, expressio);
                       break;
                   case '"':
                       i = ignorar_seq('"', '"', i, expressio);
                       break;
                   case '}':
                       i = ignorar_seq('}', '{', i, expressio);
                       break;
               }
            }

            if (i >= 0) c = expressio.charAt(i);

            if (c == op) {
                node.value = String.valueOf(op);

                if (i + suma_substring < l) {
                    node.right = new Node();
                    insertar(node.right, expressio.substring(i + suma_substring, l));
                }

                if (i -1 > 0) {
                    node.left = new Node();
                    insertar(node.left, expressio.substring(0, i - 1));
                }
                return true;
            }
            i--;
        }
        return false;
    }

    private int ignorar_seq(char cAbrir, char cCerrar, int i, String expressio) {
        int suma = 1;
        i--;
        while (suma != 0) {
            if (expressio.charAt(i) == cCerrar) suma--;
            else if (expressio.charAt(i) == cAbrir) suma++;
            i--;
        }
        return i;
    }


}
