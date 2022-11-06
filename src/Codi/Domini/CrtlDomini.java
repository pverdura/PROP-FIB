package Codi.Domini;

import Codi.Util.Pair;
import java.util.ArrayList;
import java.util.HashMap;

public class CrtlDomini {
    private HashMap<Pair<String,String>,Document>Documents; // Estructura on es guarden els documents
    private Trie<String> Autors; // Estructura on es guarden els autors (serveix per trobar el prefix)
    private HashMap<String,ArrayList<String>> DocumentsAutor; // Estructura on es guarden els títols dels documents creats per un autor
    private HashMap<String,ArrayList<String>> TitolAutors; // Estructura on es guarden els autors que han creat un document amb un títol
    private HashMap<String,ArrayList<Pair<String,String>>> Paraules; // Estructura on es guarden els documents que contenen la paraula
    private ArrayList<ExpressioBooleana> ExpressionsBooleanes; // Estructura on es guarden totes les expression booleanes
}
