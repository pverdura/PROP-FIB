package Codi.Runners;

import Codi.Tests.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class CercaAllExpressionsBoolRunner {
    public static void main (String[] args) {
        Result result = JUnitCore.runClasses(CercaAllExpressionsBoolTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        if (result.wasSuccessful()) {
            System.out.println("El test de CercaAllExpressionsBool ha sortit correctament.");
        } else {
            System.out.println("El test CercaAllExpressionsBool no ha sortit correctament.");
        }
    }
}
