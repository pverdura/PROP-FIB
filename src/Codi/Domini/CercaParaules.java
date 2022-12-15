package Codi.Domini;

import Codi.Excepcions.ArrayDeParaulesBuitException;
import Codi.Excepcions.NombreMassaPetitDocumentsException;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe que ens implementa la cerca dels documents mes rellevants respecte unes paraules
 * 
 * @author pol
 * @since 15/12/2022
 */
public class CercaParaules implements Cerca {

    ///////////////////////////////////////////////////////////
    ///                      CREADORES                      ///
    ///////////////////////////////////////////////////////////

    /**
     * Creadora per defecte
     */
    public CercaParaules() {}

    ///////////////////////////////////////////////////////////
    ///                      FUNCIONS                       ///
    ///////////////////////////////////////////////////////////

    /**
     * S'obte d'un llistat de k documents mes rellevants al llistat paraules
     *
     * @param paraules Aquest es l'array de paraules que s'utilitza per fer la cerca
     * @param k Indica el nombre de documents que volem obtenir
     * @param DocumentsParaules Hi ha les paraules amb els identificadors dels documents on apareix
     * @param Documents Hi ha els documents del sistema
     * @return Retorna un llistat de k Documents (titol,autor) mes rellevants a l'array paraules
     * @throws ArrayDeParaulesBuitException Si l'array paraules es buit
     * @throws NombreMassaPetitDocumentsException Si k <= 0
     */
    public static ArrayList<SimpleEntry<String,String>> cercaDoc(ArrayList<String> paraules, int k, HashMap<String,ArrayList<SimpleEntry<String,String>>> DocumentsParaules,
                                                                             HashMap<SimpleEntry<String,String>,Document> Documents) throws ArrayDeParaulesBuitException, NombreMassaPetitDocumentsException {
        // Mirem que es vulgui un nombre de documents natural [1, INF)
        if (k <= 0) {
            throw new NombreMassaPetitDocumentsException();
        }

        // Mirem que el array de paraules que ens donen no estigui buit
        if (paraules.size() == 0) {
            throw new ArrayDeParaulesBuitException();
        }

        // Retornem la cerca
        return EspaiVec.cercaDoc(k, Documents, paraules, DocumentsParaules);
    }
}
