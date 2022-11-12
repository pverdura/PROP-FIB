package Codi.Tests;

import static org.junit.Assert.*;
import org.junit.Test;
import Codi.Util.BinaryTree;

public class BinaryTreeTest {
    BinaryTree tree;
    String contingut_doc;

    @Test
    public void testConstructora() {
        tree = new BinaryTree("{p1 p2 p3} & (\"hola adéu\" | pep) & !joan");
        assertNotNull(tree);
    }

    @Test
    public void testCercaCorrecte() {
        tree = new BinaryTree("{p1 p2 p3} & (\"hola adéu\" | pep) & !joan");
        contingut_doc = "pep ha fet els problemes p1 p2 i p3";
        assertTrue(tree.cerca(contingut_doc));
    }

    @Test
    public void testCercaIncorrecte() {
        tree = new BinaryTree("{p1 p2 p3} & (\"hola adéu\" | pep) & !joan");
        contingut_doc = "pep i joan han fet els problemes p1 p2 i p3";
        assertFalse(tree.cerca(contingut_doc));
    }

    @Test
    public void testCercaCorrecte2() {
        tree = new BinaryTree("{p1 p2 p3} & (!\"hola adéu\" | pep) & !joan");
        contingut_doc = "pep ha fet els problemes p1 p2 i p3";
        assertTrue(tree.cerca(contingut_doc));
    }

    @Test
    public void testCercaIncorrecte2() {
        tree = new BinaryTree("{p1 p2 p3} & (!\"els problemes\" | pep) & !joan");
        contingut_doc = "Pau ha fet els problemes p1 p2 i p3";
        assertFalse(tree.cerca(contingut_doc));
    }

    @Test
    public void testCercaCorrecte3() {
        tree = new BinaryTree("jose & !({p1 p2} & {hola que tal})");
        contingut_doc = "Hola como estas jose , has hecho los problemas p2 i p3 ?";
        assertTrue(tree.cerca(contingut_doc));
    }

    @Test
    public void testCercaIncorrecte3() {
        tree = new BinaryTree("jose & !({p1 p2} & {hola que tal})");
        contingut_doc = "hola que tal jose , has hecho los problemas p1 i p2 ?";
        assertFalse(tree.cerca(contingut_doc));
    }

    @Test
    public void testCercaCorrecte4() {
        tree = new BinaryTree("!(!Gei) & (\"el millor\" & ({es una} | {es un}))");
        contingut_doc = "Gei es un grau espectacular i és el millor";
        assertTrue(tree.cerca(contingut_doc));
    }

    @Test
    public void testCercaIncorrecte4() {
        tree = new BinaryTree("!(!Gei) & (\"el millor\" & ({es una} & {es un}))");
        contingut_doc = "Gei es un grau espectacular i és el millor";
        assertFalse(tree.cerca(contingut_doc));
    }
}
