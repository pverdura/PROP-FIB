package Codi.Runners;

import Codi.Tests.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class CercaAutorRunner {
    public static void main (String[] args) {
        Result result = JUnitCore.runClasses(CercaAutorTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        if (result.wasSuccessful()) {
            System.out.println("El test de CercaAutor ha sortit correctament.");
        } else {
            System.out.println("El test de CercaAutor no ha sortit correctament.");
        }
    }
}
