package Codi.Util;

/**
 * Tipus d'ordenacions de documents que existeixen
 *
 * @author Jordi Palomera
 * @since 13-12-2022
 */
public enum TipusOrdenacio {
    /**
     * Ordenar per ordre alfabètic del títol i, després, de l'autor del document de forma ascendent
     */
    ALFABETIC_ASCENDENT,
    /**
     * Ordenar per ordre alfabètic del títol i, després, de l'autor del document de forma descendent
     */
    ALFABETIC_DESCENDENT,
    /**
     * Ordenar per pes del document de forma ascendent
     */
    PES_ASCENDENT,
    /**
     * Ordenar per pes del document de forma descendent
     */
    PES_DESCENDENT
}
