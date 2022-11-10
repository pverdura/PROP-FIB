package Codi.Domini;

import Codi.Util.TipusExtensio;
import Codi.Util.TipusOrdenacio;
import Codi.Util.Trie;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class CtrlDominiCerca {

    public CtrlDominiCerca() {
    }

    //Retorna
    public ArrayList<SimpleEntry<String,String>> cercaAutor(String autor, HashMap<String, ArrayList<String>> autorsTitols){
        return CercaAutor.cercaDoc(autor, autorsTitols);
    }

    public ArrayList<SimpleEntry<String, String>> cercaTitol(String titol,  HashMap<String, ArrayList<String>> titolsAutors){
        return CercaTitol.cercaDoc(titol, titolsAutors);
    }

    public Document cercaTitolAutor(String titol, String autor, HashMap<SimpleEntry<String,String>, Document> documents){
        return CercaTitolAutor.cercaDoc(titol, autor, documents);
    }

    public ArrayList<String> cercaPrefix(String prefix, Trie<String> autors){
        return CercaPrefix.cercaDoc(prefix, autors);
    }

    public ArrayList<SimpleEntry<String, String>> cercaSemblant(Document document, int k, HashMap<String,ArrayList<SimpleEntry<String,String>>> paraulesDocuments,
                                                           HashMap<SimpleEntry<String, String>, Document> documents){
        return CercaSemblant.cercaDoc(document, k, paraulesDocuments, documents);
    }

    public ArrayList<SimpleEntry<String, String>> cercaParaules(ArrayList<String> paraules, int k, HashMap<String,ArrayList<SimpleEntry<String,String>>> paraulesDocuments,
                                                           HashMap<SimpleEntry<String, String>,Document> documents){
        return CercaParaules.cercaDoc(paraules, k, paraulesDocuments, documents);
    }

    public int cercaBooleana(int a){
        return CercaBooleana.cercaDoc(a);
    }



    public void ordenarCerca(ArrayList<SimpleEntry<String, String>> cerca, TipusOrdenacio tipus){
        if (tipus == TipusOrdenacio.ALFABETIC_ASCENDENT){ ordreAlfAscendent(cerca);}
        else if (tipus == TipusOrdenacio.ALFABETIC_DESCENDENT) { ordreAlfDescendent(cerca); }
        else if (tipus == TipusOrdenacio.PES_ASCENDENT){ ordrePesAscendent(cerca);}
        else ordrePesDescendent(cerca);
    }

    public void ordenarCercaAutors(ArrayList<String> cerca, TipusOrdenacio tipus){
        if (tipus == TipusOrdenacio.ALFABETIC_ASCENDENT){ ordreAscendent(cerca);}
        else if (tipus == TipusOrdenacio.ALFABETIC_DESCENDENT) { ordreDescendent(cerca); }
    }

    private void ordreAlfAscendent(ArrayList<SimpleEntry<String, String>> cerca){
        Collections.sort(cerca, new Comparator<SimpleEntry<String,String>>() {
            @Override
            public int compare(final SimpleEntry<String, String > p1, final SimpleEntry<String, String> p2) {
                if (p1.getKey().compareTo(p2.getKey()) < 0) return -1;
                else if (p1.getKey().compareTo(p2.getKey()) > 0) return 1;
                else {
                    if (p1.getValue().compareTo(p2.getValue()) < 0) return -1;
                    else if (p1.getValue().compareTo(p2.getValue()) > 0) return 1;
                    else return 0;
                }
            }
        });

    }

    private void ordreAlfDescendent(ArrayList<SimpleEntry<String, String>> cerca){
        Collections.sort(cerca, new Comparator<SimpleEntry<String,String>>() {
            @Override
            public int compare(final SimpleEntry<String, String > p1, final SimpleEntry<String, String> p2) {
                if (p1.getKey().compareTo(p2.getKey()) < 0) return 1;
                else if (p1.getKey().compareTo(p2.getKey()) > 0) return -1;
                else {
                    if (p1.getValue().compareTo(p2.getValue()) < 0) return 1;
                    else if (p1.getValue().compareTo(p2.getValue()) > 0) return -1;
                    else return 0;
                }
            }
        });
    }

    private void ordrePesAscendent(ArrayList<SimpleEntry<String, String>> cerca){

    }

    private void ordrePesDescendent(ArrayList<SimpleEntry<String, String>> cerca){

    }

    private void ordreAscendent(ArrayList<String> cerca){
        Collections.sort(cerca, new Comparator<String>() {
            @Override
            public int compare(final String s1, final String s2) {
                if (s1.compareTo(s2) < 0) return -1;
                else if (s1.compareTo(s2) > 0) return 1;
                else { return 0;  }
            }
        });
    }

    private void ordreDescendent(ArrayList<String> cerca){
        Collections.sort(cerca, new Comparator<String>() {
            @Override
            public int compare(final String s1, final String s2) {
                if (s1.compareTo(s2) < 0) return 1;
                else if (s1.compareTo(s2) > 0) return -1;
                else { return 0;  }
            }
        });
    }


}
