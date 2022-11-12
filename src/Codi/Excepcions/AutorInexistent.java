package Codi.Excepcions;

public class AutorInexistent extends NullPointerException{
    String prefix;

    public AutorInexistent (String prefix) {
        super("Error: no existeix cap autor que comenci pel prefix : " + prefix);
        this.prefix = prefix;
    }

    public String toString () {
        return "Error: no existeix cap autor que comenci pel prefix :" + this.prefix ;
    }
}