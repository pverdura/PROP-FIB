package Codi.Domini;

import Codi.Excepcions.ArrayDeParaulesBuitException;
import Codi.Excepcions.NombreMassaPetitDocumentsException;

import javax.swing.text.html.parser.DocumentParser;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

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
     * Cerca que implementa l'obtenció d'un llistat de k documents més rellevants al llistat paraules
     *
     * @param paraules Aquest és l'array de paraules que s'utilitza per fer la cerca
     * @param k Indica el nombre de documents que volem obtenir
     * @param DocumentsParaules Hi ha es paraules amb els identificadors dels documents on apareix
     * @param Documents Hi ha els documents del sistema
     * @return Retorna un llistat de k Documents (títol,autor) més rellevants a l'array paraules
     * @throws ArrayDeParaulesBuitException Si l'array paraules és buit
     * @throws NombreMassaPetitDocumentsException Si k <= 0
     */
    public static ArrayList<SimpleEntry<String,String>> cercaDoc(ArrayList<String> paraules, int k, HashMap<String,ArrayList<SimpleEntry<String,String>>> DocumentsParaules,
                                                                             HashMap<SimpleEntry<String,String>,Document> Documents) throws ArrayDeParaulesBuitException, NombreMassaPetitDocumentsException {
        if (k <= 0) {
            throw new NombreMassaPetitDocumentsException();
        }

        if (paraules.size() == 0) {
            throw new ArrayDeParaulesBuitException();
        }

        return EspaiVec.cercaDoc(k, Documents, paraules, DocumentsParaules);
    }
}
