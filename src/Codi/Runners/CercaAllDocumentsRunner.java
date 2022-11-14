package Codi.Runners;

import Codi.Tests.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class CercaAllDocumentsRunner {
    public static void main (String[] args) {
        Result result = JUnitCore.runClasses(CercaAllDocumentsTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        if (result.wasSuccessful()) {
            System.out.println("El test de CercaAllDocuments ha sortit correctament.");
        } else {
            System.out.println("El test de CercaAllDocuments no ha sortit correctament.");
        }
    }
}
