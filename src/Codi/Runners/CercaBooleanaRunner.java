package Codi.Runners;

import Codi.Tests.CercaBooleanaTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class CercaBooleanaRunner {
    public static void main (String[] args) {
        Result result = JUnitCore.runClasses(CercaBooleanaTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        if (result.wasSuccessful()) {
            System.out.println("El test de CercaBooleana ha sortit correctament.");
        } else {
            System.out.println("El test de CercaBooleana no ha sortit correctament.");
        }
    }
}
