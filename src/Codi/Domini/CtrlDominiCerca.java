package Codi.Domini;

import Codi.Util.Pair;
import Codi.Util.Trie;

import java.util.ArrayList;
import java.util.HashMap;

public class CtrlDominiCerca {

    public CtrlDominiCerca() {
    }

    //Retorna
    public ArrayList<Pair<String , String >> cercaAutor(String autor,  HashMap<String, ArrayList<String>> autorsTitols){
        return CercaAutor.cercaDoc(autor, autorsTitols);
    }

    public ArrayList<Pair<String , String >> cercaTitol(String titol,  HashMap<String, ArrayList<String>> titolsAutors){
        return CercaTitol.cercaDoc(titol, titolsAutors);
    }

    public Document cercaTitolAutor(String titol, String autor, HashMap<Pair<String, String>, Document> documents){
        return CercaTitolAutor.cercaDoc(titol, autor, documents);
    }

    public ArrayList<String> cercaPrefix(String prefix, Trie<String> autors){
        return CercaPrefix.cercaDoc(prefix, autors);
    }

    public ArrayList<Pair<String , String >> cercaSemblant(Document document, int k){
        return CercaSemblant.cercaDoc(document, k);
    }

    public ArrayList<Pair<String , String >> cercaParaules(ArrayList<String> paraules, int k){
        return CercaParaules.cercaDoc(paraules, k);
    }

    public int cercaBooleana(int a){
        return CercaBooleana.cercaDoc(a);
    }
}
