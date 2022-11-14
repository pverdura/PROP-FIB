package Codi.Runners;

import Codi.Tests.EspaiVecTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class EspaiVecRunner {
    public static void main (String[] args) {
        Result result = JUnitCore.runClasses(EspaiVecTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        if (result.wasSuccessful()) {
            System.out.println("El test de EspaiVec ha sortit correctament.");
        } else {
            System.out.println("El test de EspaiVec no ha sortit correctament.");
        }
    }
}
