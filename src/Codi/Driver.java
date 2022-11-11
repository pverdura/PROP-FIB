package Codi;

import Codi.Domini.CtrlDomini;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    private CtrlDomini cd = new CtrlDomini();

    public static void main (String[] args) {
        Driver dv = new Driver();
        Scanner sc = new Scanner(System.in);

        dv.llegirStopWords();

        dv.menu();
        int op = sc.nextInt();

        boolean running = true;

        while (running) {
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
        System.out.println("11. Sortir");
    }

    void llegirStopWords () {
        ArrayList<String> s = new ArrayList<>();
        String basePath = new File("").getAbsolutePath();
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

    }
    void testEliminarDocument () {

    }
    void testModificarDocument () {

    }
    void testVeureDocument () {

    }
    void testResumDocuments () {

    }
    void testCercaDocuments () {

    }
    void testVeureExpressionsBooleanes () {

    }
    void testCrearExpressioBooleana () {

    }
    void testModificarExpressioBooleana () {

    }
}
