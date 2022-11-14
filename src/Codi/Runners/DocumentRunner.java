package Codi.Runners;

import Codi.Tests.DocumentTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class DocumentRunner {
    public static void main (String[] args) {
        Result result = JUnitCore.runClasses(DocumentTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        if (result.wasSuccessful()) {
            System.out.println("El test de Document ha sortit correctament.");
        } else {
            System.out.println("El test de Document no ha sortit correctament.");
        }
    }
}
