package Codi.Runners;

import Codi.Tests.CercaTitolTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class CercaTitolRunner {
    public static void main (String[] args) {
        Result result = JUnitCore.runClasses(CercaTitolTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        if (result.wasSuccessful()) {
            System.out.println("El test de CercaTitol ha sortit correctament.");
        } else {
            System.out.println("El test de CercaTitol no ha sortit correctament.");
        }
    }
}
