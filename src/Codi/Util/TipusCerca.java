package Codi.Util;

/**
 * Tipus de cerca de documents que es poden fer
 */
public enum TipusCerca {
    /**
     * Cerca de tots els documents existents
     */
    TOTS,
    /**
     * Cerca pel títol dels documents
     */
    TITOL,
    /**
     * Cerca per l'autor dels documents
     */
    AUTOR,
    /**
     * Cerca pel títol i l'autor dels documents
     */
    TITOLAUTOR,
    /**
     * Cerca per paraules destacables del contingut dels documents
     */
    PARAULES,
    /**
     * Cerca dels documents més semblants a un document determinat
     */
    SEMBLANT,
    /**
     * Cerca per una expressió booleana
     */
    BOOLEANA,
    /**
     * Cerca d'autors per prefix
     */
    PREFIX
}
