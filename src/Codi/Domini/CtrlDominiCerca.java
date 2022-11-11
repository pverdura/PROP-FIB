package Codi.Domini;

import Codi.Excepcions.ArrayDeParaulesBuitException;
import Codi.Excepcions.DocumentInexistentException;
import Codi.Util.TipusOrdenacio;
import Codi.Util.Trie;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;

public class CtrlDominiCerca {

    public CtrlDominiCerca() {
    }

    //Retorna
    public ArrayList<SimpleEntry<String,String>> cercaAutor(String autor, HashMap<String, ArrayList<String>> autorsTitols, TipusOrdenacio ordre, HashMap<SimpleEntry<String,String>, Document> documents){
        return ordenarCerca(CercaAutor.cercaDoc(autor, autorsTitols),ordre, documents);
    }

    public ArrayList<SimpleEntry<String, String>> cercaTitol(String titol,  HashMap<String, ArrayList<String>> titolsAutors, TipusOrdenacio ordre, HashMap<SimpleEntry<String,String>, Document> documents){
        return ordenarCerca(CercaTitol.cercaDoc(titol, titolsAutors), ordre, documents);
    }

    public Document cercaTitolAutor(String titol, String autor, HashMap<SimpleEntry<String,String>, Document> documents) throws DocumentInexistentException {
        return CercaTitolAutor.cercaDoc(titol, autor, documents);
    }

    public ArrayList<String> cercaPrefix(String prefix, Trie<String> autors, TipusOrdenacio ordre){
        return ordenarCercaSimple(CercaPrefix.cercaDoc(prefix, autors), ordre);
    }

    public ArrayList<SimpleEntry<String, String>> cercaSemblant(String titol, String autor, int k, HashMap<String,ArrayList<SimpleEntry<String,String>>> paraulesDocuments,
                                                           HashMap<SimpleEntry<String, String>, Document> documents, TipusOrdenacio ordre) throws DocumentInexistentException, ArrayDeParaulesBuitException {
        SimpleEntry<String, String> id = new SimpleEntry<>(titol, autor);
        if (!documents.containsKey(id)) throw new DocumentInexistentException(titol, autor);
        return ordenarCerca(CercaSemblant.cercaDoc(documents.get(id), k, paraulesDocuments, documents),ordre, documents);
    }

    public ArrayList<SimpleEntry<String, String>> cercaParaules(String paraules, int k, HashMap<String,ArrayList<SimpleEntry<String,String>>> paraulesDocuments,
                                                           HashMap<SimpleEntry<String, String>,Document> documents, TipusOrdenacio ordre) throws ArrayDeParaulesBuitException{
        String[] aux = paraules.split(" ");
        ArrayList<String> llistaParaules = new ArrayList<>(Arrays.asList(aux));
        return ordenarCerca(CercaParaules.cercaDoc(llistaParaules, k, paraulesDocuments, documents), ordre, documents);
    }

    public ArrayList<SimpleEntry<String, String>> cercaBooleana(ExpressioBooleana expressio,
                                                                HashMap<SimpleEntry<String,String>,Document> documents, TipusOrdenacio ordre){
        return ordenarCerca(CercaBooleana.cercaDoc(expressio, documents), ordre, documents);
    }


    public ArrayList<SimpleEntry<String,String>> cercaAllDocuments (HashMap<AbstractMap.SimpleEntry<String, String>, Document> documents, TipusOrdenacio ordre){
        return ordenarCerca(CercaAllDocuments.cercaDoc(documents), ordre, documents);
    }

    public ArrayList<String> cercaAllExpressionsBool (HashMap<String,ExpressioBooleana> ExpressionsBooleanes, TipusOrdenacio ordre){
        return ordenarCercaSimple(CercaAllExpressionsBool.cercaBool(ExpressionsBooleanes), ordre);
    }

    private ArrayList<SimpleEntry<String,String>> ordenarCerca(ArrayList<SimpleEntry<String, String>> cerca, TipusOrdenacio tipus, HashMap<SimpleEntry<String,String>, Document> documents){
        if (tipus == TipusOrdenacio.ALFABETIC_ASCENDENT){ ordreAlfAscendent(cerca);}
        else if (tipus == TipusOrdenacio.ALFABETIC_DESCENDENT) { ordreAlfDescendent(cerca); }
        else if (tipus == TipusOrdenacio.PES_ASCENDENT){
            cerca = ordrePesAscendent(cerca, documents);
        }
        else cerca = ordrePesDescendent(cerca, documents);
        return cerca;
    }

    private ArrayList<String> ordenarCercaSimple(ArrayList<String> cerca, TipusOrdenacio tipus){
        if (tipus == TipusOrdenacio.ALFABETIC_ASCENDENT){ ordreAscendent(cerca);}
        else if (tipus == TipusOrdenacio.ALFABETIC_DESCENDENT) { ordreDescendent(cerca); }
        return cerca;
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

        ArrayList<SimpleEntry<String,String>> cercaOrd = new ArrayList<>();
        for(SimpleEntry<SimpleEntry<String,String>, Integer> d : docs){
            cercaOrd.add(d.getKey());
        }
        return cercaOrd;
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

        ArrayList<SimpleEntry<String,String>> cercaOrd = new ArrayList<>();
        for(SimpleEntry<SimpleEntry<String,String>, Integer> d : docs){
            cercaOrd.add(d.getKey());
        }
        return cercaOrd;
    }

    private void ordreAscendent(ArrayList<String> cerca){
        cerca.sort(Comparator.naturalOrder());
    }

    private void ordreDescendent(ArrayList<String> cerca){
        cerca.sort(Comparator.reverseOrder());
    }


}
