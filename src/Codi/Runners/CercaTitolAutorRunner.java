package Codi.Runners;

import Codi.Tests.CercaTitolAutorTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class CercaTitolAutorRunner {
    public static void main (String[] args) {
        Result result = JUnitCore.runClasses(CercaTitolAutorTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        if (result.wasSuccessful()) {
            System.out.println("El test de CercaTitolAutor ha sortit correctament.");
        } else {
            System.out.println("El test de CercaTitolAutor no ha sortit correctament.");
        }
    }
}
