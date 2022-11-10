package Codi.Test;


import Codi.Domini.Document;

public class DocumentTest {
    public void testSetContingut () {

    }
    public static void main (String[] args) {
        Document d = new Document("títol", "autor");

        d.setContingut("delatar avui he fet delat");
    }
}
