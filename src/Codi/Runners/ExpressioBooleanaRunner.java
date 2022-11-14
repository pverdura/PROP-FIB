package Codi.Runners;

import Codi.Tests.ExpressioBooleanaTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class ExpressioBooleanaRunner {
    public static void main (String[] args) {
        Result result = JUnitCore.runClasses(ExpressioBooleanaTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        if (result.wasSuccessful()) {
            System.out.println("El test de ExpressioBooleana ha sortit correctament.");
        } else {
            System.out.println("El test de ExpressioBooleana no ha sortit correctament.");
        }
    }
}
