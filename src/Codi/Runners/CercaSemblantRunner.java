package Codi.Runners;

import Codi.Tests.CercaSemblantTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class CercaSemblantRunner {
    public static void main (String[] args) {
        Result result = JUnitCore.runClasses(CercaSemblantTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        if (result.wasSuccessful()) {
            System.out.println("El test de CercaSemblant ha sortit correctament.");
        } else {
            System.out.println("El test de CercaSemblant no ha sortit correctament.");
        }
    }
}
