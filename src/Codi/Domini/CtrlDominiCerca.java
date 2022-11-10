package Codi.Domini;

import Codi.Util.Pair;
import Codi.Util.TipusExtensio;
import Codi.Util.TipusOrdenacio;
import Codi.Util.Trie;

import javax.lang.model.util.SimpleElementVisitor6;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
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


    /*
    public ArrayList<SimpleEntry<String, String>> ordenarCerca(ArrayList<SimpleEntry<String, String>> cerca, TipusOrdenacio tipus){
        if (tipus == TipusOrdenacio.ALFABETIC_ASCENDENT){ cerca.sort(cerca);}
        else if (tipus == TipusOrdenacio.ALFABETIC_DESCENDENT) { ordreAlfDescendent(cerca); }
        else if (tipus == TipusOrdenacio.PES_ASCENDENT){ ordrePesAscendent(cerca);}
        else ordrePesDescendent(cerca);
    }

    public ArrayList<String> ordenarCercaAutors(ArrayList<String> cerca, TipusOrdenacio tipus){
        if (tipus == TipusOrdenacio.ALFABETIC_ASCENDENT){ ordreAscendent(cerca);}
        else if (tipus == TipusOrdenacio.ALFABETIC_DESCENDENT) { ordreDescendent(cerca); }
    }

    private ArrayList<SimpleEntry<String, String>> ordreAlfAscendent(ArrayList<SimpleEntry<String, String>> cerca){

    }

    private ArrayList<SimpleEntry<String, String>> ordreAlfDescendent(ArrayList<SimpleEntry<String, String>> cerca){

    }

    private ArrayList<SimpleEntry<String, String>> ordrePesAscendent(ArrayList<SimpleEntry<String, String>> cerca){

    }

    private ArrayList<SimpleEntry<String, String>> ordrePesDescendent(ArrayList<SimpleEntry<String, String>> cerca){

    }

    private ArrayList<String> ordreAscendent(ArrayList<String> cerca){

    }

    private ArrayList<String> ordreDescendent(ArrayList<String> cerca){

    }

     */
}
