package Codi.Domini;

import Codi.Excepcions.*;
import Codi.Util.TipusOrdenacio;
import Codi.Util.Trie;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;

/**
 * Classe per gestionar les cerques de la capa de domini
 *
 * @author Judit Serna
 */
public class CtrlDominiCerca {

    private int ord;    //ordenar ascendentment si val 1,
                        //ordenar descendentment si val -1


    /**
     * Creadora per defecte
     */
    public CtrlDominiCerca() {
    }

    /**
     * Cerca tots els titols dels documents d'un autor
     *
     * @param autor Indica el nom de l'autor dels documents a cercar
     * @param autorsTitols Estructura de dades on cada autor te un llistat dels titols
     * @param ordre Indica l'ordre amb el qual es vol ordenar el resultat
     * @param documents Estructura on estan guardats tots els documents
     * @return {@code ArrayList<SimpleEntry<String,String>>} Llista, amb l'ordre indicat, de parells titol-autor que identifiquen els documents que tenen per autor l'indicat
     * @throws AutorNoExisteixException Si l'autor indicat no existeix
     */
    public ArrayList<SimpleEntry<String,String>> cercaAutor(String autor, HashMap<String, ArrayList<String>> autorsTitols, TipusOrdenacio ordre, HashMap<SimpleEntry<String,String>, Document> documents) throws AutorNoExisteixException{
        return ordenarCerca(CercaAutor.cercaDoc(autor, autorsTitols),ordre, documents);
    }

    /**
     * Cerca tots els documents que tenen un cert titol
     *
     * @param titol Indica el nom del titol dels documents a cercar
     * @param titolsAutors Estructura de dades on cada titol te un llistat dels autors
     * @param ordre Indica l'ordre amb el qual es vol ordenar el resultat
     * @param documents Estructura on estan guardats tots els documents
     * @return {@code ArrayList<SimpleEntry<String,String>>} Llista, amb l'ordre indicat, de parells titol-autor que identifiquen els documents que tenen per titol l'indicat
     * @throws TitolNoExisteixException Si el titol indicat no existeix
     */
    public ArrayList<SimpleEntry<String, String>> cercaTitol(String titol,  HashMap<String, ArrayList<String>> titolsAutors, TipusOrdenacio ordre, HashMap<SimpleEntry<String,String>, Document> documents) throws TitolNoExisteixException{
        return ordenarCerca(CercaTitol.cercaDoc(titol, titolsAutors), ordre, documents);
    }


    /**
     * Cerca que retorna una llista d'autors que comencen per un prefix donat
     *
     * @param prefix Indica el prefix pel qual han de comencar els noms dels autors
     * @param autors Estructura on estan guardats tots els noms dels autors de tots els documents
     * @param ordre Indica l'ordre amb el qual es vol ordenar el resultat
     * @return {@code ArrayList<String>} Llista, en l'ordre indicat, amb noms d'autors que tenen el prefix donat
     * @throws PrefixNoExisteixException Si no existeix cap autor que comenci amb el prefix donat
     */
    public ArrayList<String> cercaPrefix(String prefix, Trie autors, TipusOrdenacio ordre) throws PrefixNoExisteixException {
        return ordenarCercaSimple(CercaPrefix.cercaDoc(prefix, autors), ordre);
    }

    /**
     * Cerca els k documents mes semblants al document identificat per titol i autor
     *
     * @param titol Indica el titol del document
     * @param autor Indica el nom de l'autor del document
     * @param k Indica el nombre de documents que volem obtenir
     * @param paraulesDocuments Estructura de dades on cada paraula te una llista dels documents on apareix
     * @param documents Estructura on estan guardats tots els documents
     * @return {@code ArrayList<SimpleEntry<String, String>} Llista de parells titol-autor que identifiquen els k documents mes semblants al document identificat per titol i autor
     * @throws ArrayDeParaulesBuitException Si el contingut del document identificat per titol i autor es buit
     * @throws NombreMassaPetitDocumentsException Si k <= 0
     */
    public ArrayList<SimpleEntry<String, String>> cercaSemblant(String titol, String autor, int k, HashMap<String,ArrayList<SimpleEntry<String,String>>> paraulesDocuments,
                                                           HashMap<SimpleEntry<String, String>, Document> documents) throws ArrayDeParaulesBuitException, NombreMassaPetitDocumentsException {
        SimpleEntry<String, String> id = new SimpleEntry<>(titol, autor);
        if (!documents.containsKey(id)) throw new DocumentInexistentException(titol, autor);
        return CercaSemblant.cercaDoc(documents.get(id), k, paraulesDocuments, documents);
    }

    /**
     * Cerca els k documents mes rellevants a l'array paraules
     *
     * @param paraules Indica les paraules que utilitzarem per fer la cerca
     * @param k Indica el nombre de documents que volem obtenir
     * @param paraulesDocuments Estructura de dades on cada paraula te una llista dels documents on apareix
     * @param documents Estructura on estan guardats tots els documents
     * @return {@code ArrayList<SimpleEntry<String, String>} Llista de parells titol-autor que identifiquen els k documents mes rellevants a l'array paraules
     * @throws ArrayDeParaulesBuitException Si l'array de paraules es buit
     * @throws NombreMassaPetitDocumentsException Si k <= 0
     */
    public ArrayList<SimpleEntry<String, String>> cercaParaules(String paraules, int k, HashMap<String,ArrayList<SimpleEntry<String,String>>> paraulesDocuments,
                                                           HashMap<SimpleEntry<String, String>,Document> documents) throws ArrayDeParaulesBuitException, NombreMassaPetitDocumentsException {
        String[] aux = paraules.split(" ");
        ArrayList<String> llistaParaules = new ArrayList<>(Arrays.asList(aux));
        return CercaParaules.cercaDoc(llistaParaules, k, paraulesDocuments, documents);
    }

    /**
     * Cerca dels documents que satisfan una expressio booleana
     *
     * @param expressio Indica l'expressio booleana que s'ha de satisfer
     * @param documents Estructura on estan guardats tots els documents
     * @param ExpressionsBooleanes Estructura on estan guardades totes les expressions booleanes
     * @param ordre Indica l'ordre amb el qual es vol ordenar el resultat
     * @return {@code ArrayList<SimpleEntry<String, String>} Llista, amb l'ordre indicat, de parells titol-autor que identifiquen els documents que satidfan l'expresssio booleana donada
     * @throws ExpressioBooleanaInexistentException Si no existeix l'expressio booleana donada
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
     * @return {@code ArrayList<SimpleEntry<String, String>} Llista, amb l'ordre indicat, de parells titol-autor que identifiquen tots els documents
     */
    public ArrayList<SimpleEntry<String,String>> cercaAllDocuments (HashMap<AbstractMap.SimpleEntry<String, String>, Document> documents, TipusOrdenacio ordre){
        return ordenarCerca(CercaAllDocuments.cercaDoc(documents), ordre, documents);
    }

    /**
     * Cerca de totes les expressions booleanes
     *
     * @param ExpressionsBooleanes Estructura on estan guardades totes les expressions booleanes
     * @param ordre Indica l'ordre amb el qual es vol ordenar el resultat
     * @return {@code ArrayList<String>} Llista, amb l'ordre indicat, de totes les expressions booleanes
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
     * Ordena alfabeticament ascendent el resultat d'una cerca
     *
     * @param cerca Llista del resultat d'una cerca que es vol ordenar
     */
    private void ordreAlfAscendent(ArrayList<SimpleEntry<String, String>> cerca){
        ord = 1;
        cerca.sort(ordreAlf);

    }

    /**
     * Ordena alfabeticament descendent el resultat d'una cerca
     *
     * @param cerca Llista del resultat d'una cerca que es vol ordenar
     */
    private void ordreAlfDescendent(ArrayList<SimpleEntry<String, String>> cerca){
        ord = -1;
        cerca.sort(ordreAlf);
    }

    /**
     * Ordena la cerca per pes ascendent, i en cas de ser iguals, alfabeticament ascendent
     *
     * @param cerca Llista del resultat d'una cerca que es vol ordenar
     * @param documents Estructura on estan guardats tots els documents
     * @return {@code ArrayList<SimpleEntry<String, String>>} El parametre cerca ordenat per pes ascendent
     */
    private ArrayList<SimpleEntry<String, String>> ordrePesAscendent(ArrayList<SimpleEntry<String, String>> cerca, HashMap<SimpleEntry<String,String>, Document> documents){
        ArrayList<SimpleEntry<SimpleEntry<String,String>, Integer>> docs = new ArrayList<>();
        for (SimpleEntry<String,String> c : cerca){
            Document d = documents.get(c);
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
     * Ordena la cerca per pes descendent, i en cas de ser iguals, alfabeticament descendent
     *
     * @param cerca Llista del resultat d'una cerca que es vol ordenar
     * @param documents Estructura on estan guardats tots els documents
     * @return {@code ArrayList<SimpleEntry<String, String>>} El parametre cerca ordenat per pes descendent
     */
    private ArrayList<SimpleEntry<String, String>> ordrePesDescendent(ArrayList<SimpleEntry<String, String>> cerca, HashMap<SimpleEntry<String,String>, Document> documents){
        ArrayList<SimpleEntry<SimpleEntry<String,String>, Integer>> docs = new ArrayList<>();
        for (SimpleEntry<String,String> c : cerca){
            Document d = documents.get(c);
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
     * Ordena alfabeticament ascendent la cerca donada
     *
     * @param cerca Llista del resultat d'una cerca que es vol ordenar
     */
    private void ordreAscendent(ArrayList<String> cerca){
        ord = 1;
        cerca.sort(ordreSimple);
    }

    /**
     * Ordena alfabeticament descendent la cerca donada
     *
     * @param cerca Llista del resultat d'una cerca que es vol ordenar
     */
    private void ordreDescendent(ArrayList<String> cerca){
        ord = -1;
        cerca.sort(ordreSimple);
    }

    /**
     * Comparador per ordenar dos strings
     * Si ord es 1, es vol ascendent
     * Si ord es -1, es vol descendent
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
     * Ordena alfabeticament ascendent si ret es 1
     * Ordena alfabeticament descendent si ret es -1
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
     * Comparador per ordenar alfabeticament dos SimpleEntry<String, String>
     * Si ord es 1, es vol ascendent
     * Si ord es -1, es vol descendent
     */
    private final Comparator<SimpleEntry<String,String>> ordreAlf = new Comparator<>() {
        @Override
        public int compare(SimpleEntry<String, String> p1, SimpleEntry<String, String> p2) {
            return ordena(p1,p2, ord);
        }
    };

    /**
     * Comparador per ordenar pel pes dels documents donats dos identificadors SimpleEntry<String, String>
     * Si ord es 1, es vol ascendent
     * Si ord es -1, es vol descendent
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
