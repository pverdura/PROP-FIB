package Codi.Runners;

import Codi.Tests.CercaPrefixTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class CercaPrefixRunner {
    public static void main (String[] args) {
        Result result = JUnitCore.runClasses(CercaPrefixTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        if (result.wasSuccessful()) {
            System.out.println("El test de CercaPrefix ha sortit correctament.");
        } else {
            System.out.println("El test de CercaPrefix no ha sortit correctament.");
        }
    }
}
