package Codi.Stubs;

import Codi.Domini.Document;

public class StubDocumentCB1 extends Document {

    public StubDocumentCB1(String titol, String autor) {
        super(titol, autor);
    }

    static int i = 0;

    @Override
    public String getContingut () {

        if (i == 0) {
            i++;
            return "Document de la judit : Ahir vaig anar a fer escalada";

        } else if (i == 1) {
            i++;
            return "Document del jordi : Sóc professional del tetris";

        } else if (i == 2) {
            i++;
            return "Document del pol : Això és una obra d'art";
        }


        i = 0;
        return "Document del pau : Bon dia a tothom";
    }

    @Override
    public String getAutor () {
        if (i == 1) return "judit";
        else if (i == 2) return "jordi";
        else if (i == 3) return "pol";

       return "pau";
    }

    @Override
    public String getTitol () {
        return "titol";
    }

}
