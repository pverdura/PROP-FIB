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

public class DriverInput {
    private CtrlDomini cd = new CtrlDomini();
    private static Scanner sc;

    public static void main (String[] args) {
        DriverInput dv = new DriverInput();
        sc = new Scanner(System.in);

        dv.llegirStopWords();

        boolean running = true;

        while (running) {
            int op = 0;
            if (sc.hasNextInt())
                op = sc.nextInt();
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
        }
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
    }
    void testStopWords() {
        ArrayList<String> stopWords = cd.getStopWords();
        for (String s : stopWords) {
            System.out.println(s);
        }
    }
    void testCrearDocument () {
        sc = new Scanner(System.in);
        //Títol del document:
        String nom = "";
        if (sc.hasNextLine())
            nom = sc.nextLine();

        //"Autor del document:
        String autor = "";
        if (sc.hasNextLine())
            autor = sc.nextLine();

        try {
            cd.creaDocument(nom, autor);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    void testEliminarDocument () {
        sc = new Scanner(System.in);
        //Títol del document:
        String nom = "";
        if (sc.hasNextLine())
            nom = sc.nextLine();

        //Autor del document:
        String autor = "";
        if (sc.hasNextLine())
            autor = sc.nextLine();

        try {
            cd.eliminaDocument(nom, autor);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    void testModificarDocument () {
        //títol, autor, contingut, path, extensió,
        sc = new Scanner(System.in);
        //Títol del document:
        String nom = "";
        if (sc.hasNextLine())
            nom = sc.nextLine();

        //Autor del document:
        String autor = "";
        if (sc.hasNextLine())
            autor = sc.nextLine();

        int op = 0;
        if (sc.hasNextInt())
            op = sc.nextInt();
        switch (op) {
            case 1:
                sc.nextLine();
                //Nou títol del document:
                String nomNou = "";
                if (sc.hasNextLine())
                    nomNou = sc.nextLine();

                try {
                    cd.setTitol(nom, autor, nomNou);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
                break;
            case 2:
                sc.nextLine();
                //Nou autor del document:
                String autorNou = "";
                if (sc.hasNextLine())
                    autorNou = sc.nextLine();

                try {
                    cd.setAutor(nom, autor, autorNou);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
                break;
            case 3:
                sc.nextLine();
                //Nou contingut del document:
                String contingutNou = "";
                if (sc.hasNextLine())
                    contingutNou = sc.nextLine();

                try {
                    cd.setContingut(nom, autor, contingutNou);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
                break;
            case 4:
                sc.nextLine();
                //Nova path del document:
                String novaPath = "";
                if (sc.hasNextLine())
                    novaPath = sc.nextLine();

                try {
                    cd.setPath(nom, autor, novaPath);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
                break;
            case 5:
                sc.nextLine();
                //Nova extensió del document:
                int aux = 0;
                if (sc.hasNextInt())
                    aux = sc.nextInt();
                TipusExtensio novaExtensio;
                if (aux == 1) novaExtensio = TipusExtensio.TXT;
                else if (aux == 2) novaExtensio = TipusExtensio.XML;
                else novaExtensio = TipusExtensio.BOL;

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
        sc = new Scanner(System.in);
        //"Títol del document:
        String nom = "";
        if (sc.hasNextLine())
            nom = sc.nextLine();

        //"Autor del document:
        String autor = "";
        if (sc.hasNextLine())
            autor = sc.nextLine();

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
        System.out.println("Quantitat de documents existents: " + cd.getNombreDocuments());
        ArrayList<SimpleEntry<String, String>> docs = cd.cercaAllDocuments(TipusOrdenacio.ALFABETIC_ASCENDENT);

        for (SimpleEntry<String, String> se : docs) {
            System.out.println(se.getKey() + "\t" + se.getValue());
        }
    }
    void testCercaDocuments () {
        //cerca documents
        sc = new Scanner(System.in);
        /*Quina cerca vols fer?
        1. Per títol
        2. Per prefix d'autor
        3. Per paraules
        4. Per expressió booleana
        5. Per document semblant
        6. Per autor*/

        int op = 0;
        if (sc.hasNextInt())
            op = sc.nextInt();

        int ord = 0;
        if (sc.hasNextInt())
            ord = sc.nextInt();
        sc.nextLine();
        TipusOrdenacio tipusOrdenacio;
        if (ord == 1) tipusOrdenacio = TipusOrdenacio.ALFABETIC_ASCENDENT;
        else if (ord == 2) tipusOrdenacio = TipusOrdenacio.ALFABETIC_DESCENDENT;
        else if (ord == 3) tipusOrdenacio = TipusOrdenacio.PES_ASCENDENT;
        else tipusOrdenacio = TipusOrdenacio.PES_DESCENDENT;

        ArrayList<SimpleEntry<String, String>> res = new ArrayList<>();
        String s = "";

        switch (op) {
            case 1:
                //Títol:
                if (sc.hasNextLine())
                    s = sc.nextLine();
                try {
                    res = cd.cercaTitol(s, tipusOrdenacio);
                } catch (Exception e) {System.out.println(e.toString());}

                break;
            case 2:
                //Prefix d'autor:
                if (sc.hasNextLine())
                    s = sc.nextLine();
                ArrayList<String> res2 = cd.cercaPrefix(s, tipusOrdenacio);
                for (String se : res2) {
                    System.out.println(se);
                }
                break;
            case 3:
                //Paraules:
                if (sc.hasNextLine())
                    s = sc.nextLine();

                //Nombre de documents:
                int k = 0;
                if (sc.hasNextInt())
                    k = sc.nextInt();

                sc.nextLine();

                try {
                    res = cd.cercaParaules(s, k);
                } catch (Exception e) {System.out.println(e.toString()); e.printStackTrace();}

                break;
            case 4:
                //Expressió booleana:
                if (sc.hasNextLine())
                    s = sc.nextLine();
                try {
                    res = cd.cercaBooleana(s, tipusOrdenacio);
                } catch (Exception e) {System.out.println(e.toString());}

                break;
            case 5:
                //Títol document:
                String nom = "";
                if (sc.hasNextLine())
                    sc.nextLine();

                //Autor document:
                String autor = "";
                if (sc.hasNextLine())
                    sc.nextLine();

                //Nombre de documents:
                k = 0;
                if (sc.hasNextInt())
                    k = sc.nextInt();

                try {
                    res = cd.cercaSemblant(nom, autor, k);
                } catch (Exception e) {System.out.println(e.toString());}

                break;
            case 6:
                //Autor:
                if (sc.hasNextLine())
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
        sc = new Scanner(System.in);
        //Expressió booleana:
        String expr = "";
        if (sc.hasNextLine())
            expr = sc.nextLine();

        try {
            cd.creaExpressioBool(expr);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    void testModificarExpressioBooleana () {
        sc = new Scanner(System.in);
        //Expressió booleana a modificar:
        String expr = "";
        if (sc.hasNextLine())
            expr = sc.nextLine();

        //Nova booleana a modificar:
        String exprNova = "";
        if (sc.hasNextLine())
            exprNova =sc.nextLine();

        try {
            cd.modificaExpressioBool(expr, exprNova);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    void testEliminarExpressioBooleana () {
        sc = new Scanner(System.in);
        //Expressió booleana a eliminar:
        String expr = "";
        if (sc.hasNextLine())
            expr = sc.nextLine();

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