package Codi.Domini;

import Codi.Excepcions.*;
import Codi.Util.TipusOrdenacio;
import Codi.Util.Trie;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;

public class CtrlDominiCerca {

    private int ord;
    private int pes;
    private int ordS;

    public CtrlDominiCerca() {
    }

    //Retorna
    public ArrayList<SimpleEntry<String,String>> cercaAutor(String autor, HashMap<String, ArrayList<String>> autorsTitols, TipusOrdenacio ordre, HashMap<SimpleEntry<String,String>, Document> documents) throws AutorNoExisteixException{
        return ordenarCerca(CercaAutor.cercaDoc(autor, autorsTitols),ordre, documents);
    }

    public ArrayList<SimpleEntry<String, String>> cercaTitol(String titol,  HashMap<String, ArrayList<String>> titolsAutors, TipusOrdenacio ordre, HashMap<SimpleEntry<String,String>, Document> documents) throws TitolNoExisteixException{
        return ordenarCerca(CercaTitol.cercaDoc(titol, titolsAutors), ordre, documents);
    }

    public Document cercaTitolAutor(String titol, String autor, HashMap<SimpleEntry<String,String>, Document> documents) throws DocumentInexistentException {
        return CercaTitolAutor.cercaDoc(titol, autor, documents);
    }

    public ArrayList<String> cercaPrefix(String prefix, Trie<String> autors, TipusOrdenacio ordre) throws PrefixNoExisteixException {
        return ordenarCercaSimple(CercaPrefix.cercaDoc(prefix, autors), ordre);
    }

    public ArrayList<SimpleEntry<String, String>> cercaSemblant(String titol, String autor, int k, HashMap<String,ArrayList<SimpleEntry<String,String>>> paraulesDocuments,
                                                           HashMap<SimpleEntry<String, String>, Document> documents) throws ArrayDeParaulesBuitException, NombreMassaPetitDocumentsException {
        SimpleEntry<String, String> id = new SimpleEntry<>(titol, autor);
        if (!documents.containsKey(id)) throw new DocumentInexistentException(titol, autor);
        return CercaSemblant.cercaDoc(documents.get(id), k, paraulesDocuments, documents);
    }

    public ArrayList<SimpleEntry<String, String>> cercaParaules(String paraules, int k, HashMap<String,ArrayList<SimpleEntry<String,String>>> paraulesDocuments,
                                                           HashMap<SimpleEntry<String, String>,Document> documents) throws ArrayDeParaulesBuitException, NombreMassaPetitDocumentsException {
        String[] aux = paraules.split(" ");
        ArrayList<String> llistaParaules = new ArrayList<>(Arrays.asList(aux));
        return CercaParaules.cercaDoc(llistaParaules, k, paraulesDocuments, documents);
    }

    public ArrayList<SimpleEntry<String, String>> cercaBooleana(String expressio,
                                                                HashMap<SimpleEntry<String,String>,Document> documents,HashMap<String,ExpressioBooleana> ExpressionsBooleanes ,TipusOrdenacio ordre) throws ExpressioBooleanaInexistentException {
        if (!ExpressionsBooleanes.containsKey(expressio)) throw new ExpressioBooleanaInexistentException(expressio);
        return ordenarCerca(CercaBooleana.cercaDoc(ExpressionsBooleanes.get(expressio), documents), ordre, documents);
    }


    public ArrayList<SimpleEntry<String,String>> cercaAllDocuments (HashMap<AbstractMap.SimpleEntry<String, String>, Document> documents, TipusOrdenacio ordre){
        return ordenarCerca(CercaAllDocuments.cercaDoc(documents), ordre, documents);
    }

    public ArrayList<String> cercaAllExpressionsBool (HashMap<String,ExpressioBooleana> ExpressionsBooleanes, TipusOrdenacio ordre){
        return ordenarCercaSimple(CercaAllExpressionsBool.cercaDoc(ExpressionsBooleanes), ordre);
    }

    private ArrayList<SimpleEntry<String,String>> ordenarCerca(ArrayList<SimpleEntry<String, String>> cerca, TipusOrdenacio tipus, HashMap<SimpleEntry<String,String>, Document> documents){
        if (tipus == TipusOrdenacio.ALFABETIC_ASCENDENT){ ordreAlfAscendent(cerca);}
        else if (tipus == TipusOrdenacio.ALFABETIC_DESCENDENT) { ordreAlfDescendent(cerca); }
        else if (tipus == TipusOrdenacio.PES_ASCENDENT){ cerca = ordrePesAscendent(cerca, documents); }
        else cerca = ordrePesDescendent(cerca, documents);
        return cerca;
    }

    private ArrayList<String> ordenarCercaSimple(ArrayList<String> cerca, TipusOrdenacio tipus){
        if (tipus == TipusOrdenacio.ALFABETIC_ASCENDENT){ ordreAscendent(cerca);}
        else if (tipus == TipusOrdenacio.ALFABETIC_DESCENDENT) { ordreDescendent(cerca); }
        return cerca;
    }

    private void ordreAlfAscendent(ArrayList<SimpleEntry<String, String>> cerca){
        ord = 1;
        cerca.sort(ordreAlf);

    }

    private void ordreAlfDescendent(ArrayList<SimpleEntry<String, String>> cerca){
        ord = -1;
        cerca.sort(ordreAlf);
    }

    private ArrayList<SimpleEntry<String, String>> ordrePesAscendent(ArrayList<SimpleEntry<String, String>> cerca, HashMap<SimpleEntry<String,String>, Document> documents){
        ArrayList<SimpleEntry<SimpleEntry<String,String>, Integer>> docs = new ArrayList<>();
        for (SimpleEntry<String,String> c : cerca){
            Document d = CercaTitolAutor.cercaDoc(c.getKey(),c.getValue(),documents);
            SimpleEntry<SimpleEntry<String,String>, Integer> aux = new SimpleEntry<>(c,d.getPes());
            docs.add(aux);
        }
        pes = 1;
        docs.sort(ordrePes);

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
        pes = -1;
        docs.sort(ordrePes);

        ArrayList<SimpleEntry<String,String>> cercaOrd = new ArrayList<>();
        for(SimpleEntry<SimpleEntry<String,String>, Integer> d : docs){
            cercaOrd.add(d.getKey());
        }
        return cercaOrd;
    }

    private void ordreAscendent(ArrayList<String> cerca){
        ordS = 1;
        cerca.sort(ordreSimple);
    }

    private void ordreDescendent(ArrayList<String> cerca){
        ordS = -1;
        cerca.sort(ordreSimple);
    }

    private final Comparator<String> ordreSimple = new Comparator<>() {
        public int compare(String o1, String o2) {
            String s1 = o1.toLowerCase();
            String s2 = o2.toLowerCase();
            if (s1.compareTo(s2) == 0) {
                if (o1.compareTo(o2) < 0) return -ordS;
                else if (o1.compareTo(o2) > 0) return ordS;
                else return 0;
            }
            else {
                if (s1.compareTo(s2) < 0) return -ordS;
                else if (s1.compareTo(s2) > 0) return ordS;
                else return 0;
            }
        }
    };

    /*ordena alfabèticament ascendent si ret és 1
      ordena alfabèticament descendent si ret és -1
     */
    private int ordena (SimpleEntry<String,String> p1,SimpleEntry<String,String> p2, int ret){
        String s1 = p1.getKey().toLowerCase();
        String s2 = p2.getKey().toLowerCase();
        String s12 = p1.getValue().toLowerCase();
        String s21 = p2.getValue().toLowerCase();
        if (s1.compareTo(s2) == 0) {
            if (p1.getKey().compareTo(p2.getKey()) < 0) return -ret;
            else if (p1.getKey().compareTo(p2.getKey()) > 0) return ret;
            else {
                if (s12.compareTo(s21) == 0) {
                    if (p1.getValue().compareTo(p2.getValue()) < 0) return -ret;
                    else if (p1.getValue().compareTo(p2.getValue()) > 0) return ret;
                    return 0;
                } else {
                    if (s12.compareTo(s21) < 0) return -ret;
                    else if (s12.compareTo(s21) > 0) return ret;
                    else return 0;
                }
            }
        } else if (s12.compareTo(s21) == 0) {
            if (p1.getKey().compareTo(p2.getKey()) < 0) return -ret;
            else if (p1.getKey().compareTo(p2.getKey()) > 0) return ret;
            else {
                if (p1.getValue().compareTo(p2.getValue()) < 0) return -ret;
                else if (p1.getValue().compareTo(p2.getValue()) > 0) return ret;
                else return 0;
            }
        } else {
            if (s1.compareTo(s2) < 0) return -ret;
            else if (s1.compareTo(s2) > 0) return ret;
            else {
                if (s12.compareTo(s21) < 0) return -ret;
                else if (s12.compareTo(s21) > 0) return ret;
                else return 0;
            }
        }
    }



    private final Comparator<SimpleEntry<String,String>> ordreAlf = new Comparator<>() {
        @Override
        public int compare(SimpleEntry<String, String> p1, SimpleEntry<String, String> p2) {
            return ordena(p1,p2, ord);
        }
    };

    private final Comparator<SimpleEntry<SimpleEntry<String, String>, Integer>> ordrePes = new Comparator<>() {
        @Override
        public int compare(SimpleEntry<SimpleEntry<String, String>, Integer> p1, SimpleEntry<SimpleEntry<String, String>, Integer> p2) {
            if (p1.getValue() < p2.getValue()) return -pes;
            else if (p1.getValue()> p2.getValue()) return pes;
            else {
                return ordena(p1.getKey(), p2.getKey(), pes);
            }
        }
    };

}
