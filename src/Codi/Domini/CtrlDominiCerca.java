package Codi.Domini;

import Codi.Util.Pair;
import Codi.Util.Trie;

import java.util.ArrayList;
import java.util.HashMap;

public class CtrlDominiCerca {

    public CtrlDominiCerca() {
    }

    ArrayList<Pair<String , String >> cercaAutor(String autor,  HashMap<String, ArrayList<String>> autorsTitols){
        CercaAutor llistaAutors = new CercaAutor();
        return llistaAutors.cercaDoc(autor, autorsTitols);
    }

    ArrayList<Pair<String , String >> cercaTitol(String titol,  HashMap<String, ArrayList<String>> titolsAutors){
        CercaTitol llistaTitols = new CercaTitol();
        return llistaTitols.cercaDoc(titol, titolsAutors);
    }

    Document cercaTitolAutor(String titol, String autor, HashMap<Pair<String, String>, Document> documents){
        CercaTitolAutor llistaTitolsAutor = new CercaTitolAutor();
        return llistaTitolsAutor.cercaDoc(titol, autor, documents);
    }

    ArrayList<String> cercaPrefix(String prefix, Trie<String> autors){
        CercaPrefix llistaAutors = new CercaPrefix();
        return llistaAutors.cercaDoc(prefix, autors);
    }

    ArrayList<Pair<String , String >> cercaSemblant(Document document, int k, HashMap<Pair<String,String>, Document> documents, Boolean tipus){
        CercaSemblant llistaDocuments = new CercaSemblant();
        return llistaDocuments.cercaDoc(document, k, documents, tipus);
    }

    ArrayList<Pair<String , String >> cercaParaules(ArrayList<String> paraules, int k, HashMap<String,ArrayList<String>> paraulesDocuments, Boolean tipus){
        CercaParaules llistaDocuments = new CercaParaules();
        return llistaDocuments.cercaDoc(paraules, k, paraulesDocuments, tipus);
    }

    int cercaBooleana(int a){
        CercaBooleana llistaDocuments = new CercaBooleana();
        return llistaDocuments.cercaDoc(a);
    }
}
