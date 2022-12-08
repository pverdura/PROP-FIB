package Codi.Domini;

import Codi.Excepcions.*;
import Codi.Util.TipusOrdenacio;
import Codi.Util.Trie;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;

public class CtrlDominiCerca {

    private int ord;    //ordenar ascendentment si val 1,
                        //ordenar descendentment si val -1


    /**
     * Creadora per defecte
     */
    public CtrlDominiCerca() {
    }

    /**
     * Cerca tots els títols dels documents d'un autor
     *
     * @param autor Indica el nom de l'autor dels documents a cercar
     * @param autorsTitols Estructura de dades on cada autor té un llistat dels títols
     * @param ordre Indica l'ordre amb el qual es vol ordenar el resultat
     * @param documents Estructura on estan guardats tots els documents
     * @return Llista, amb l'ordre indicat, dels títols dels documents de l'autor indicat
     * @throws AutorNoExisteixException Si l'autor indicat no existeix
     */
    public ArrayList<SimpleEntry<String,String>> cercaAutor(String autor, HashMap<String, ArrayList<String>> autorsTitols, TipusOrdenacio ordre, HashMap<SimpleEntry<String,String>, Document> documents) throws AutorNoExisteixException{
        return ordenarCerca(CercaAutor.cercaDoc(autor, autorsTitols),ordre, documents);
    }

    /**
     * Cerca tots els autors que tenen un docuemnt amb un títol donat
     *
     * @param titol Indica el nom del títol dels documents dels quals en volem trobar l'autor
     * @param titolsAutors Estructura de dades on cada títol té un llistat dels autors
     * @param ordre Indica l'ordre amb el qual es vol ordenar el resultat
     * @param documents Estructura on estan guardats tots els documents
     * @return Llista, amb l'ordre indicat, dels autors que tenen documents amb el títol indicat
     * @throws TitolNoExisteixException Si el títol indicat no existeix
     */
    public ArrayList<SimpleEntry<String, String>> cercaTitol(String titol,  HashMap<String, ArrayList<String>> titolsAutors, TipusOrdenacio ordre, HashMap<SimpleEntry<String,String>, Document> documents) throws TitolNoExisteixException{
        return ordenarCerca(CercaTitol.cercaDoc(titol, titolsAutors), ordre, documents);
    }

    /**
     * Cerca d'un document donat el seu títol i autor
     *
     * @param titol Indica el títol del document
     * @param autor Indica el nom de l'autor del document
     * @param documents Estructura on estan guardats tots els documents
     * @return El document que té per títol i autor els indicats
     * @throws DocumentInexistentException Si no existeix cap document amb els paràmetres donats
     */
    public Document cercaTitolAutor(String titol, String autor, HashMap<SimpleEntry<String,String>, Document> documents) throws DocumentInexistentException {
        return CercaTitolAutor.cercaDoc(titol, autor, documents);
    }

    /**
     * Cerca que retorna una llista d'autors que comencen per un prefix donat
     *
     * @param prefix Indica el prefix pel qual han de començar els noms dels autors
     * @param autors Estructura on estan guardats tots els noms dels autors de tots els documents
     * @param ordre Indica l'ordre amb el qual es vol ordenar el resultat
     * @return Llista, en l'ordre indicat, amb noms d'autor que tenen el prefix donat
     * @throws PrefixNoExisteixException Si no existeix cap autor que comenci amb el prefix donat
     */
    public ArrayList<String> cercaPrefix(String prefix, Trie<String> autors, TipusOrdenacio ordre) throws PrefixNoExisteixException {
        return ordenarCercaSimple(CercaPrefix.cercaDoc(prefix, autors), ordre);
    }

    /**
     * Cerca els k documents més semblants al document identificat per titol i autor
     *
     * @param titol Indica el títol del document
     * @param autor Indica el nom de l'autor del document
     * @param k Indica el nombre de documents que volem obtenir
     * @param paraulesDocuments Estructura de dades on cada paraula té una llista dels documents on apareix
     * @param documents Estructura on estan guardats tots els documents
     * @return Llista dels k documents més semblants al document identificat per titol i autor
     * @throws ArrayDeParaulesBuitException Si el contingut del document identificat per titol i autor és buit
     * @throws NombreMassaPetitDocumentsException Si k <= 0
     */
    public ArrayList<SimpleEntry<String, String>> cercaSemblant(String titol, String autor, int k, HashMap<String,ArrayList<SimpleEntry<String,String>>> paraulesDocuments,
                                                           HashMap<SimpleEntry<String, String>, Document> documents) throws ArrayDeParaulesBuitException, NombreMassaPetitDocumentsException {
        SimpleEntry<String, String> id = new SimpleEntry<>(titol, autor);
        if (!documents.containsKey(id)) throw new DocumentInexistentException(titol, autor);
        return CercaSemblant.cercaDoc(documents.get(id), k, paraulesDocuments, documents);
    }

    /**
     * Cerca els k documents més rellevants a l'array paraules
     *
     * @param paraules Indica les paraules que utilitzarem per fer la cerca
     * @param k Indica el nombre de documents que volem obtenir
     * @param paraulesDocuments Estructura de dades on cada paraula té una llista dels documents on apareix
     * @param documents Estructura on estan guardats tots els documents
     * @return Llista dels k documents més rellevants a l'array paraules
     * @throws ArrayDeParaulesBuitException Si l'array de paraules és buit
     * @throws NombreMassaPetitDocumentsException Si k <= 0
     */
    public ArrayList<SimpleEntry<String, String>> cercaParaules(String paraules, int k, HashMap<String,ArrayList<SimpleEntry<String,String>>> paraulesDocuments,
                                                           HashMap<SimpleEntry<String, String>,Document> documents) throws ArrayDeParaulesBuitException, NombreMassaPetitDocumentsException {
        String[] aux = paraules.split(" ");
        ArrayList<String> llistaParaules = new ArrayList<>(Arrays.asList(aux));
        return CercaParaules.cercaDoc(llistaParaules, k, paraulesDocuments, documents);
    }

    /**
     * Cerca dels documents que satisfan una expressió booleana
     *
     * @param expressio Indica l'expressió booleana que s'ha de satisfer
     * @param documents Estructura on estan guardats tots els documents
     * @param ExpressionsBooleanes Estructura on estan guardades totes les expressions booleanes
     * @param ordre Indica l'ordre amb el qual es vol ordenar el resultat
     * @return Llista, amb l'ordre indicat, dels documents que satidfan l'expresssió booleana donada
     * @throws ExpressioBooleanaInexistentException Si no existeix l'expressió booleana donada
     */
    public ArrayList<SimpleEntry<String, String>> cercaBooleana(String expressio,
                                                                HashMap<SimpleEntry<String,String>,Document> documents,HashMap<String,ExpressioBooleana> ExpressionsBooleanes ,TipusOrdenacio ordre) throws ExpressioBooleanaInexistentException {
        if (!ExpressionsBooleanes.containsKey(expressio)) throw new ExpressioBooleanaInexistentException(expressio);
        return ordenarCerca(CercaBooleana.cercaDoc(ExpressionsBooleanes.get(expressio), documents), ordre, documents);
    }

    /**
     * Cerca de tots els docuemnts
     *
     * @param documents Estructura on estan guardats tots els documents
     * @param ordre Indica l'ordre amb el qual es vol ordenar el resultat
     * @return Llista, amb l'ordre indicat, de tots els documents en l'ordre indicat
     */
    public ArrayList<SimpleEntry<String,String>> cercaAllDocuments (HashMap<AbstractMap.SimpleEntry<String, String>, Document> documents, TipusOrdenacio ordre){
        return ordenarCerca(CercaAllDocuments.cercaDoc(documents), ordre, documents);
    }

    /**
     * Cerca de totes les expressions booleanes
     *
     * @param ExpressionsBooleanes Estructura on estan guardades totes les expressions booleanes
     * @param ordre Indica l'ordre amb el qual es vol ordenar el resultat
     * @return Llista, amb l'ordre indicat, de totes les expressions booleanes
     */
    public ArrayList<String> cercaAllExpressionsBool (HashMap<String,ExpressioBooleana> ExpressionsBooleanes, TipusOrdenacio ordre){
        return ordenarCercaSimple(CercaAllExpressionsBool.cercaDoc(ExpressionsBooleanes), ordre);
    }

    /**
     * Ordena els resultats d'una cerca
     *
     * @param cerca Llista del resultat d'una cerca que es vol ordenar
     * @param tipus Indica l'ordre amb el qual es vol ordenar
     * @param documents Estructura on estan guardats tots els documents
     * @return La cerca donada ordenada segons l'ordre indicat
     */
    public ArrayList<SimpleEntry<String,String>> ordenarCerca(ArrayList<SimpleEntry<String, String>> cerca, TipusOrdenacio tipus, HashMap<SimpleEntry<String,String>, Document> documents){
        if (tipus == TipusOrdenacio.ALFABETIC_ASCENDENT){ ordreAlfAscendent(cerca);}
        else if (tipus == TipusOrdenacio.ALFABETIC_DESCENDENT) { ordreAlfDescendent(cerca); }
        else if (tipus == TipusOrdenacio.PES_ASCENDENT){ cerca = ordrePesAscendent(cerca, documents); }
        else cerca = ordrePesDescendent(cerca, documents);
        return cerca;
    }

    /**
     * Ordena els resultats d'una cerca
     *
     * @param cerca Llista del resultat d'una cerca que es vol ordenar
     * @param tipus Indica l'ordre amb el qual es vol ordenar
     * @return La cerca donada ordenada segons l'ordre indicat
     */

    public ArrayList<String> ordenarCercaSimple(ArrayList<String> cerca, TipusOrdenacio tipus){
        if (tipus == TipusOrdenacio.ALFABETIC_ASCENDENT){ ordreAscendent(cerca);}
        else if (tipus == TipusOrdenacio.ALFABETIC_DESCENDENT) { ordreDescendent(cerca); }
        return cerca;
    }

    /**
     * Ordena alfabèticament ascendent el resultat d'una cerca
     *
     * @param cerca Llista del resultat d'una cerca que es vol ordenar
     */
    private void ordreAlfAscendent(ArrayList<SimpleEntry<String, String>> cerca){
        ord = 1;
        cerca.sort(ordreAlf);

    }

    /**
     * Ordena alfabèticament descendent el resultat d'una cerca
     *
     * @param cerca Llista del resultat d'una cerca que es vol ordenar
     */
    private void ordreAlfDescendent(ArrayList<SimpleEntry<String, String>> cerca){
        ord = -1;
        cerca.sort(ordreAlf);
    }

    /**
     * Ordena la cerca per pes ascendent, i en cas de ser iguals, alfabèticament ascendent
     *
     * @param cerca Llista del resultat d'una cerca que es vol ordenar
     * @param documents Estructura on estan guardats tots els documents
     * @return La cerca donada ordenada segons l'ordre indicat
     */
    private ArrayList<SimpleEntry<String, String>> ordrePesAscendent(ArrayList<SimpleEntry<String, String>> cerca, HashMap<SimpleEntry<String,String>, Document> documents){
        ArrayList<SimpleEntry<SimpleEntry<String,String>, Integer>> docs = new ArrayList<>();
        for (SimpleEntry<String,String> c : cerca){
            Document d = CercaTitolAutor.cercaDoc(c.getKey(),c.getValue(),documents);
            SimpleEntry<SimpleEntry<String,String>, Integer> aux = new SimpleEntry<>(c,d.getPes());
            docs.add(aux);
        }
        ord = 1;
        docs.sort(ordrePes);

        ArrayList<SimpleEntry<String,String>> cercaOrd = new ArrayList<>();
        for(SimpleEntry<SimpleEntry<String,String>, Integer> d : docs){
            cercaOrd.add(d.getKey());
        }
        return cercaOrd;
    }

    /**
     * Ordena la cerca per pes descendent, i en cas de ser iguals, alfabèticament descendent
     *
     * @param cerca Llista del resultat d'una cerca que es vol ordenar
     * @param documents Estructura on estan guardats tots els documents
     * @return La cerca donada ordenada segons l'ordre indicat
     */
    private ArrayList<SimpleEntry<String, String>> ordrePesDescendent(ArrayList<SimpleEntry<String, String>> cerca, HashMap<SimpleEntry<String,String>, Document> documents){
        ArrayList<SimpleEntry<SimpleEntry<String,String>, Integer>> docs = new ArrayList<>();
        for (SimpleEntry<String,String> c : cerca){
            Document d = CercaTitolAutor.cercaDoc(c.getKey(),c.getValue(),documents);
            SimpleEntry<SimpleEntry<String,String>, Integer> aux = new SimpleEntry<>(c,d.getPes());
            docs.add(aux);
        }
        ord = -1;
        docs.sort(ordrePes);

        ArrayList<SimpleEntry<String,String>> cercaOrd = new ArrayList<>();
        for(SimpleEntry<SimpleEntry<String,String>, Integer> d : docs){
            cercaOrd.add(d.getKey());
        }
        return cercaOrd;
    }

    /**
     * Ordena alfabèticament ascendent la cerca donada
     *
     * @param cerca Llista del resultat d'una cerca que es vol ordenar
     */
    private void ordreAscendent(ArrayList<String> cerca){
        ord = 1;
        cerca.sort(ordreSimple);
    }

    /**
     * Ordena alfabèticament descendent la cerca donada
     *
     * @param cerca Llista del resultat d'una cerca que es vol ordenar
     */
    private void ordreDescendent(ArrayList<String> cerca){
        ord = -1;
        cerca.sort(ordreSimple);
    }

    /**
     * Comparador por ordenar dos strings
     * Si ord és 1, es vol ascendent
     * Si ord és -1, es vol descendent
     */
    private final Comparator<String> ordreSimple = new Comparator<>() {
        public int compare(String o1, String o2) {
            String s1 = o1.toLowerCase();
            String s2 = o2.toLowerCase();
            if (s1.compareTo(s2) == 0) {
                if (o1.compareTo(o2) < 0) return -ord;
                else if (o1.compareTo(o2) > 0) return ord;
                else return 0;
            }
            else {
                if (s1.compareTo(s2) < 0) return -ord;
                else if (s1.compareTo(s2) > 0) return ord;
                else return 0;
            }
        }
    };

    /**
     *
     * Ordena alfabèticament ascendent si ret és 1
     * Ordena alfabèticament descendent si ret és -1
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
            if (s1.compareTo(s2) < 0) return -ret;
            else if (s1.compareTo(s2) > 0) return ret;
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


    /**
     * Comparador per ordenar alfabèticament dos SimpleEntry<String, String>
     * Si ord és 1, es vol ascendent
     * Si ord és -1, es vol descendent
     */
    private final Comparator<SimpleEntry<String,String>> ordreAlf = new Comparator<>() {
        @Override
        public int compare(SimpleEntry<String, String> p1, SimpleEntry<String, String> p2) {
            return ordena(p1,p2, ord);
        }
    };

    /**
     * Comparador per ordenar pel pes del documents donats dos identificadors SimpleEntry<String, String>
     * Si ord és 1, es vol ascendent
     * Si ord és -1, es vol descendent
     */
    private final Comparator<SimpleEntry<SimpleEntry<String, String>, Integer>> ordrePes = new Comparator<>() {
        @Override
        public int compare(SimpleEntry<SimpleEntry<String, String>, Integer> p1, SimpleEntry<SimpleEntry<String, String>, Integer> p2) {
            if (p1.getValue() < p2.getValue()) return -ord;
            else if (p1.getValue()> p2.getValue()) return ord;
            else {
                return ordena(p1.getKey(), p2.getKey(), ord);
            }
        }
    };

}
