package Codi.Util;

/**
 * Classe que representa un document llegit
 *
 * @author Judit Serna
 */
public class DocumentLlegit {
    private String titol;
    private String autor;
    private String path;
    private String contingut;
    private TipusExtensio extensio;

    /**
     * Creadora per defecte
     */
    public DocumentLlegit(){
        titol = "";
        autor = "";
        contingut = "";
        path = "";
    }

    /**
     * Obte el titol del document llegit
     *
     * @return Retorna el titol del document llegit
     */
    public String getTitol(){
        return titol;
    }

    /**
     * Obte l'autor del document llegit
     *
     * @return Retorna l'autor del document llegit
     */
    public String getAutor(){
        return autor;
    }

    /**
     * Obte la path del document llegit
     *
     * @return Retorna la path del document llegit
     */
    public String getPath(){
        return path;
    }

    /**
     * Obte el contingut del document llegit
     *
     * @return Retorna el contingut del document llegit
     */
    public String getContingut(){
        return contingut;
    }

    /**
     * Obte l'exteniso del document llegit
     *
     * @return Retorna l'extensio del document llegit
     */
    public TipusExtensio getExtensio(){ return extensio;}

    /**
     * Modifica el títol del document
     *
     * @param titol Nou títol del document
     */
    public void setTitol(String titol){
        this.titol = titol;
    }

    /**
     * Modifica l'autor del document
     *
     * @param autor Nou autor del document
     */
    public void setAutor(String autor){
        this.autor = autor;
    }

    /**
     * Modifica la path del document
     *
     * @param path Nova path del document
     */
    public void setPath(String path){
        this.path = path;
    }

    /**
     * Modifica el contingut del document
     *
     * @param contingut Nou contingut del document
     */
    public void setContingut(String contingut){
        this.contingut = contingut;
    }

    /**
     * Modifica l'extensió i la path del document
     *
     * @param extensio Nova extensió del document
     */
    public void setExtensio(TipusExtensio extensio){ this.extensio = extensio; }
}
