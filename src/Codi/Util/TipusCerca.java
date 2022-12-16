package Codi.Util;

/**
 * Tipus de cerca de documents que es poden fer
 *
 * @author Jordi Palomera
 * @since 13-12-2022
 */
public enum TipusCerca {
    /**
     * Cerca de tots els documents existents
     */
    TOTS,
    /**
     * Cerca pel titol dels documents
     */
    TITOL,
    /**
     * Cerca per l'autor dels documents
     */
    AUTOR,
    /**
     * Cerca pel titol i l'autor dels documents
     */
    TITOLAUTOR,
    /**
     * Cerca per paraules destacables del contingut dels documents
     */
    PARAULES,
    /**
     * Cerca dels documents mes semblants a un document determinat
     */
    SEMBLANT,
    /**
     * Cerca per una expressio booleana
     */
    BOOLEANA
}
