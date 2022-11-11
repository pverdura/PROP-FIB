package Codi;

import Codi.Domini.CtrlDomini;
import Codi.Util.TipusExtensio;
import Codi.Util.TipusOrdenacio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Driver {
    private CtrlDomini cd = new CtrlDomini();

    public static void main (String[] args) {
        Driver dv = new Driver();
        Scanner sc = new Scanner(System.in);

        dv.llegirStopWords();

        boolean running = true;

        while (running) {
            dv.menu();
            int op = sc.nextInt();
            switch (op) {
                case 1:
                    dv.testCrearDocument();
                    break;
                case 2:
                    dv.testEliminarDocument();
                    break;
                case 3:
                    dv.testModificarDocument();
                    break;
                case 4:
                    dv.testVeureDocument();
                    break;
                case 5:
                    dv.testStopWords();
                    break;
                case 6:
                    dv.testResumDocuments();
                    break;
                case 7:
                    dv.testCercaDocuments();
                    break;
                case 8:
                    dv.testVeureExpressionsBooleanes();
                    break;
                case 9:
                    dv.testCrearExpressioBooleana();
                    break;
                case 10:
                    dv.testModificarExpressioBooleana();
                    break;
                case 11:
                    dv.testEliminarExpressioBooleana();
                    break;
                case 12:
                    dv.testVeureParaules();
                    break;
                case 13:
                    running = false;
                    break;
                default:
                    System.out.println("Opció no vàlida.");
                    break;
            }
            System.out.println("-----------------------\n");
        }
    }
    void menu () {
        System.out.println("Què vols fer? ");
        System.out.println("1. Crear un document");
        System.out.println("2. Eliminar un document");
        System.out.println("3. Modificar atributs d'un document");
        System.out.println("4. Veure un document");
        System.out.println("5. Veure stop words");
        System.out.println("6. Veure resum documents");
        System.out.println("7. Cerca documents");
        System.out.println("8. Veure expressions booleanes");
        System.out.println("9. Crear expressió booleana");
        System.out.println("10. Modificar expressió booleana");
        System.out.println("11. Eliminar expressió booleana");
        System.out.println("12. Veure documents on apareixen les paraules");
        System.out.println("13. Sortir");
    }

    void llegirStopWords () {
        ArrayList<String> s = new ArrayList<>();
        String path = new File("src/codi/stop_words.txt").getAbsolutePath();

        try {
            File file = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            while ((st = br.readLine()) != null) {
                s.add(st);
            }
        } catch (Exception e) {
            System.out.println("Error en carregar les stop words");
        }

        cd.setStopWords(s);
    }    void testStopWords() {
        ArrayList<String> stopWords = cd.getStopWords();
        for (String s : stopWords) {
            System.out.println(s);
        }
    }
    void testCrearDocument () {
        Scanner sc = new Scanner(System.in);
        System.out.println("Títol del document:\t");
        String nom = sc.nextLine();
        System.out.println("Autor del document:\t");
        String autor = sc.nextLine();

        try {
            cd.creaDocument(nom, autor);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    void testEliminarDocument () {
        Scanner sc = new Scanner(System.in);
        System.out.println("Títol del document:\t");
        String nom = sc.nextLine();
        System.out.println("Autor del document:\t");
        String autor = sc.nextLine();

        try {
            cd.eliminaDocument(nom, autor);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    void testModificarDocument () {
        //títol, autor, contingut, path, extensió,
        Scanner sc = new Scanner(System.in);
        System.out.println("Títol del document:\t");
        String nom = sc.nextLine();
        System.out.println("Autor del document:\t");
        String autor = sc.nextLine();

        System.out.println("Què vols modificar?");
        System.out.println("1. Títol");
        System.out.println("2. Autor");
        System.out.println("3. Contingut");
        System.out.println("4. Path");
        System.out.println("5. Extensió");

        int op = sc.nextInt();
        sc.nextLine();
        switch (op) {
            case 1:
                System.out.println("Nou títol del document:\t");
                String nomNou = sc.nextLine();

                try {
                    cd.setTitol(nom, autor, nomNou);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
                break;
            case 2:
                System.out.println("Nou autor del document:\t");
                String autorNou = sc.nextLine();

                try {
                    cd.setAutor(nom, autor, autorNou);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
                break;
            case 3:
                System.out.println("Nou contingut del document:\t");
                String contingutNou = sc.nextLine();

                try {
                    cd.setContingut(nom, autor, contingutNou);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
                break;
            case 4:
                System.out.println("Nova path del document:\t");
                String novaPath = sc.nextLine();

                try {
                    cd.setPath(nom, autor, novaPath);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
                break;
            case 5:
                System.out.println("Nova extensió del document:\t");
                System.out.println("1. TXT");
                System.out.println("2. XML");
                System.out.println("2. BOL");
                int aux = sc.nextInt();
                TipusExtensio novaExtensio;
                if (aux == 1) novaExtensio = TipusExtensio.TXT;
                else if (aux == 2) novaExtensio = TipusExtensio.XML;
                else  novaExtensio = TipusExtensio.BOL;

                try {
                    cd.setExtensio(nom, autor, novaExtensio);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
                break;
            default:
                System.out.println("Error: opció no vàlida.");
                break;
        }
    }
    void testVeureDocument () {
        Scanner sc = new Scanner(System.in);
        System.out.println("Títol del document:\t");
        String nom = sc.nextLine();
        System.out.println("Autor del document:\t");
        String autor = sc.nextLine();

        try {
            System.out.println("Títol: "+nom);
            System.out.println("Autor: "+autor);
            System.out.println("Path: "+cd.getPath(nom, autor));
            System.out.println("Extensió: "+cd.getExtensio(nom, autor));
            System.out.println("Contingut: "+cd.getContingut(nom, autor));
            System.out.println("Pes: "+cd.getPes(nom, autor));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    void testResumDocuments () {
        ArrayList<SimpleEntry<String, String>> docs = cd.cercaAllDocuments(TipusOrdenacio.ALFABETIC_ASCENDENT);

        for (SimpleEntry<String, String> se : docs) {
            System.out.println(se.getKey() + " " + se.getValue());
        }
    }
    void testCercaDocuments () {
        //cerca documents
        Scanner sc = new Scanner(System.in);
        System.out.println("Quina cerca vols fer?");
        System.out.println("1. Per títol");
        System.out.println("2. Per prefix d'autor");
        System.out.println("3. Per paraules");
        System.out.println("4. Per expressió booleana");
        System.out.println("5. Per document semblant");
        System.out.println("6. Per autor");

        int op = sc.nextInt();

        System.out.println("Com ho vols ordenar?");
        System.out.println("1. Alfabèticament ascendent");
        System.out.println("2. Alfabèticament descendent");
        System.out.println("3. Per pes ascendent");
        System.out.println("4. Per pes descendent");
        int ord = sc.nextInt();
        sc.nextLine();
        TipusOrdenacio tipusOrdenacio;
        if (ord == 1) tipusOrdenacio = TipusOrdenacio.ALFABETIC_ASCENDENT;
        else if (ord == 2) tipusOrdenacio = TipusOrdenacio.ALFABETIC_DESCENDENT;
        else if (ord == 3) tipusOrdenacio = TipusOrdenacio.PES_ASCENDENT;
        else tipusOrdenacio = TipusOrdenacio.PES_DESCENDENT;

        ArrayList<SimpleEntry<String, String>> res = new ArrayList<>();
        String s;

        switch (op) {
            case 1:
                System.out.println("Títol:\t");
                s = sc.nextLine();
                try {
                    res = cd.cercaTitol(s, tipusOrdenacio);
                } catch (Exception e) {System.out.println(e.toString());}

                break;
            case 2:
                System.out.println("Prefix d'autor:\t");
                s = sc.nextLine();
                ArrayList<String> res2 = cd.cercaPrefix(s, tipusOrdenacio);
                for (String se : res2) {
                    System.out.println(se);
                }
                break;
            case 3:
                System.out.println("Paraules:\t");
                s = sc.nextLine();
                System.out.println("Nombre de documents:\t");
                int k = sc.nextInt();

                sc.nextLine();

                try {
                    res = cd.cercaParaules(s, k);
                } catch (Exception e) {System.out.println(e.toString()); e.printStackTrace();}

                break;
            case 4:
                System.out.println("Expressió booleana:\t");
                s = sc.nextLine();
                try {
                    res = cd.cercaBooleana(s, tipusOrdenacio);
                } catch (Exception e) {System.out.println(e.toString());}

                break;
            case 5:
                System.out.println("Títol document:\t");
                String nom = sc.nextLine();
                System.out.println("Autor document:\t");
                String autor = sc.nextLine();
                System.out.println("Nombre de documents:\t");
                k = sc.nextInt();

                try {
                    res = cd.cercaSemblant(nom, autor, k);
                } catch (Exception e) {System.out.println(e.toString());}

                break;
            case 6:
                System.out.println("Autor:\t");
                s = sc.nextLine();
                res = cd.cercaAutor(s, tipusOrdenacio);

                break;
            default:
                System.out.println("Error: opció no vàlida.");
                break;
        }

        for (SimpleEntry<String, String> se : res) {
            System.out.println(se.getKey() + " " + se.getValue());
        }
    }
    void testVeureExpressionsBooleanes () {
        ArrayList<String> exprs = cd.cercaAllExpressionsBool(TipusOrdenacio.ALFABETIC_ASCENDENT);

        for (String se : exprs) {
            System.out.println(se);
        }
    }
    void testCrearExpressioBooleana () {
        Scanner sc = new Scanner(System.in);
        System.out.println("Expressió booleana:\t");
        String expr = sc.nextLine();

        try {
            cd.creaExpressioBool(expr);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    void testModificarExpressioBooleana () {
        Scanner sc = new Scanner(System.in);
        System.out.println("Expressió booleana a modificar:\t");
        String expr = sc.nextLine();
        System.out.println("Nova booleana a modificar:\t");
        String exprNova = sc.nextLine();

        try {
            cd.modificaExpressioBool(expr, exprNova);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    void testEliminarExpressioBooleana () {
        Scanner sc = new Scanner(System.in);
        System.out.println("Expressió booleana a eliminar:\t");
        String expr = sc.nextLine();

        try {
            cd.eliminaExpressioBool(expr);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    void testVeureParaules () {
        HashMap<String, ArrayList<SimpleEntry<String, String>>> p =  cd.ListgetParaules();

        for (String s : p.keySet()) {
            System.out.println(s+": ");
            for (SimpleEntry<String, String> doc : p.get(s)) {
                System.out.println(doc.getKey()+" "+doc.getValue());
            }
        }
    }
}