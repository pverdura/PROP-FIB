package Codi.Domini;

import Codi.Util.TipusExtensio;
import Codi.Util.TipusOrdenacio;
import Codi.Util.Trie;

import javax.print.Doc;
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

    public ArrayList<SimpleEntry<String, String>> cercaBooleana(ExpressioBooleana expressio,
                                                                HashMap<SimpleEntry<String,String>,Document> documents){
        return CercaBooleana.cercaDoc(expressio, documents);
    }



    public ArrayList<SimpleEntry<String,String>> ordenarCerca(ArrayList<SimpleEntry<String, String>> cerca, TipusOrdenacio tipus, HashMap<SimpleEntry<String,String>, Document> documents){
        if (tipus == TipusOrdenacio.ALFABETIC_ASCENDENT){ ordreAlfAscendent(cerca);}
        else if (tipus == TipusOrdenacio.ALFABETIC_DESCENDENT) { ordreAlfDescendent(cerca); }
        else if (tipus == TipusOrdenacio.PES_ASCENDENT){
            cerca = ordrePesAscendent(cerca, documents);
        }
        else cerca = ordrePesDescendent(cerca, documents);
        return cerca;
    }

    public void ordenarCercaAutors(ArrayList<String> cerca, TipusOrdenacio tipus){
        if (tipus == TipusOrdenacio.ALFABETIC_ASCENDENT){ ordreAscendent(cerca);}
        else if (tipus == TipusOrdenacio.ALFABETIC_DESCENDENT) { ordreDescendent(cerca); }
    }

    private void ordreAlfAscendent(ArrayList<SimpleEntry<String, String>> cerca){
        cerca.sort(new Comparator<SimpleEntry<String,String>>() {
            @Override
            public int compare(SimpleEntry<String, String > p1, SimpleEntry<String, String> p2) {
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
        cerca.sort(new Comparator<SimpleEntry<String,String>>() {
            @Override
            public int compare(SimpleEntry<String, String > p1, SimpleEntry<String, String> p2) {
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

    private ArrayList<SimpleEntry<String, String>> ordrePesAscendent(ArrayList<SimpleEntry<String, String>> cerca, HashMap<SimpleEntry<String,String>, Document> documents){
        ArrayList<SimpleEntry<SimpleEntry<String,String>, Integer>> docs = new ArrayList<>();
        for (SimpleEntry<String,String> c : cerca){
            Document d = CercaTitolAutor.cercaDoc(c.getKey(),c.getValue(),documents);
            SimpleEntry<SimpleEntry<String,String>, Integer> aux = new SimpleEntry<>(c,d.getPes());
            docs.add(aux);
        }

        Collections.sort(docs, new Comparator<SimpleEntry<SimpleEntry<String, String>, Integer>>() {
            @Override
            public int compare(SimpleEntry<SimpleEntry<String, String>, Integer> p1, SimpleEntry<SimpleEntry<String, String>, Integer> p2) {
                if (p1.getValue() < p2.getValue()) return -1;
                else if (p1.getValue()> p2.getValue()) return 1;
                else return 0;
            }
        });

        ArrayList<SimpleEntry<String,String>> cerca2 = new ArrayList<>();
        for(SimpleEntry<SimpleEntry<String,String>, Integer> d : docs){
            cerca2.add(d.getKey());
        }

        for (SimpleEntry<String,String> s : cerca2){
            System.out.println(s);
        }
        return cerca2;
    }

    private ArrayList<SimpleEntry<String, String>> ordrePesDescendent(ArrayList<SimpleEntry<String, String>> cerca, HashMap<SimpleEntry<String,String>, Document> documents){
        ArrayList<SimpleEntry<SimpleEntry<String,String>, Integer>> docs = new ArrayList<>();
        for (SimpleEntry<String,String> c : cerca){
            Document d = CercaTitolAutor.cercaDoc(c.getKey(),c.getValue(),documents);
            SimpleEntry<SimpleEntry<String,String>, Integer> aux = new SimpleEntry<>(c,d.getPes());
            docs.add(aux);
        }

        Collections.sort(docs, new Comparator<SimpleEntry<SimpleEntry<String, String>, Integer>>() {
            @Override
            public int compare(SimpleEntry<SimpleEntry<String, String>, Integer> p1, SimpleEntry<SimpleEntry<String, String>, Integer> p2) {
                if (p1.getValue() < p2.getValue()) return 1;
                else if (p1.getValue()> p2.getValue()) return -1;
                else return 0;
            }
        });

        ArrayList<SimpleEntry<String,String>> cerca2 = new ArrayList<>();
        for(SimpleEntry<SimpleEntry<String,String>, Integer> d : docs){
            cerca2.add(d.getKey());
        }

        for (SimpleEntry<String,String> s : cerca2){
            System.out.println(s);
        }
        return cerca2;
    }

    private void ordreAscendent(ArrayList<String> cerca){
        cerca.sort(Comparator.naturalOrder());
    }

    private void ordreDescendent(ArrayList<String> cerca){
        cerca.sort(Comparator.reverseOrder());
    }


}
