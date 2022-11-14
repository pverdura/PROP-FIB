package Codi.Tests;

import Codi.Domini.CercaAllExpressionsBool;
import Codi.Domini.ExpressioBooleana;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class CercaAllExpressionsBoolTest {
    @Test
    public void cercaDoc() {
        HashMap<String,ExpressioBooleana> exprBool= new HashMap<String, ExpressioBooleana>();

        ExpressioBooleana expr1 = new ExpressioBooleana("hola | adeu");
        ExpressioBooleana expr2 = new ExpressioBooleana("hola & adeu");
        ExpressioBooleana expr3 = new ExpressioBooleana("!mec");

        exprBool.put("hola | adeu",expr1);
        exprBool.put("hola & adeu",expr2);
        exprBool.put("!mec",expr3);

        ArrayList<String> teoric = new ArrayList<String>();
        teoric.add("hola & adeu");
        teoric.add("hola | adeu");
        teoric.add("!mec");

        ArrayList<String> resultat = CercaAllExpressionsBool.cercaDoc(exprBool);

        assertEquals(teoric,resultat);
    }
}
