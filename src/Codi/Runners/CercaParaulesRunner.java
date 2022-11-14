package Codi.Runners;

import Codi.Tests.CercaParaulesTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class CercaParaulesRunner {
    public static void main (String[] args) {
        Result result = JUnitCore.runClasses(CercaParaulesTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        if (result.wasSuccessful()) {
            System.out.println("El test de CercaParaules ha sortit correctament.");
        } else {
            System.out.println("El test de CercaParaules no ha sortit correctament.");
        }
    }
}
