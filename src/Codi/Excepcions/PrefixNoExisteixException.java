package Codi.Excepcions;

public class PrefixNoExisteixException extends NullPointerException{
    String prefix;

    public PrefixNoExisteixException(String prefix) {
        super("Error: no existeix cap autor que comenci pel prefix : " + prefix);
        this.prefix = prefix;
    }

    public String toString () {
        return "Error: no existeix cap autor que comenci pel prefix :" + this.prefix ;
    }
}