package Codi.Persistencia;

import Codi.Domini.*;

import java.io.IOException;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class GestorDades {

    public void guardaDocument(String titol, String autor, String contingut, String path) {
        try {
            Path pth = Paths.get(path); // Passem el string path en format Path
            Files.writeString(pth,titol,StandardCharsets.UTF_8);
        }
        catch (IOException ex) {
            System.out.println("mec");
        }
    }

    public Document carregaDocument(String path) {
        return null;
    }

    public void guardaExpressioBool(String expr, String path) {

    }

    public ExpressioBooleana carregaExpressioBooleana(String path) {
        return null;
    }
}
