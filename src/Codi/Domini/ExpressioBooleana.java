package Codi.Domini;

import java.util.Objects;

public class ExpressioBooleana {

    private String expressio;

    public ExpressioBooleana() { this.expressio = null; }
    public ExpressioBooleana(String expressio) { this.expressio = expressio; }

    public String getExpressio() { return this.expressio; }
    public void setExpressio(String valor) { this.expressio = valor; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpressioBooleana that = (ExpressioBooleana) o;
        return Objects.equals(expressio, that.expressio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expressio);
    }
}
