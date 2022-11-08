package Codi.Domini;

import Codi.Util.Pair;
import Codi.Util.Trie;

import java.util.ArrayList;
import java.util.HashMap;

public class CtrlDominiCerca {

    public CtrlDominiCerca() {
    }

    //Retorna
    ArrayList<Pair<String , String >> cercaAutor(String autor,  HashMap<String, ArrayList<String>> autorsTitols){
        return CercaAutor.cercaDoc(autor, autorsTitols);
    }

    ArrayList<Pair<String , String >> cercaTitol(String titol,  HashMap<String, ArrayList<String>> titolsAutors){
        return CercaTitol.cercaDoc(titol, titolsAutors);
    }

    Document cercaTitolAutor(String titol, String autor, HashMap<Pair<String, String>, Document> documents){
        return CercaTitolAutor.cercaDoc(titol, autor, documents);
    }

    ArrayList<String> cercaPrefix(String prefix, Trie<String> autors){
        return CercaPrefix.cercaDoc(prefix, autors);
    }

    ArrayList<Pair<String , String >> cercaSemblant(Document document, int k, HashMap<Pair<String,String>, Document> documents, Boolean tipus){
        return CercaSemblant.cercaDoc(document, k, documents, tipus);
    }

    ArrayList<Pair<String , String >> cercaParaules(ArrayList<String> paraules, int k, HashMap<String,ArrayList<String>> paraulesDocuments, Boolean tipus){
        return CercaParaules.cercaDoc(paraules, k, paraulesDocuments, tipus);
    }

    int cercaBooleana(int a){
        return CercaBooleana.cercaDoc(a);
    }
}
