package Codi.Runners;

import Codi.Tests.TrieTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TrieRunner {
    public static void main (String[] args) {
        Result result = JUnitCore.runClasses(TrieTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        if (result.wasSuccessful()) {
            System.out.println("El test de Trie ha sortit correctament.");
        } else {
            System.out.println("El test de Trie no ha sortit correctament.");
        }
    }
}
