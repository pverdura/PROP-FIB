package Codi.Domini;

import Codi.Excepcions.ArrayDeParaulesBuitException;
import Codi.Excepcions.NombreMassaPetitDocumentsException;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class CercaSemblant implements Cerca {

    ///////////////////////////////////////////////////////////
    ///                      CREADORES                      ///
    ///////////////////////////////////////////////////////////

    /**
     * Creadora per defecte
     */
    public CercaSemblant() {}


    ///////////////////////////////////////////////////////////
    ///                      FUNCIONS                       ///
    ///////////////////////////////////////////////////////////

    /**
     * Transforma el contingut del document D en un array sense paraules repetides
     *
     * @param D Aquest és el Document que s'utilitza per fer la cerca
     * @return Retorna el calcul log_2(N/#DocsApareix) del conjunt de paraules del document D
     */
    private static ArrayList<String> obteContingut(Document D) {
        Set<String> paraules = D.getAparicions().keySet();

        return new ArrayList<String>(paraules);
    }

    /**
     * S'obtenen els k documents més semblants al document D
     *
     * @param D Aquest és el Document que s'utilitza per fer la cerca
     * @param k Indica el nombre de documents que volem obtenir
     * @param DocumentsParaules Hi ha es paraules amb els identificadors dels documents on apareix
     * @param Documents Hi ha els documents del sistema
     * @return Retorna un llistat de k Documents (títol,autor) més semblants al document D
     * @throws ArrayDeParaulesBuitException Error si el document D no té contingut
     * @throws NombreMassaPetitDocumentsException Error si k <= 0
     */
    public static ArrayList<SimpleEntry<String,String>> cercaDoc(Document D, int k,  HashMap<String,ArrayList<SimpleEntry<String,String>>> DocumentsParaules,
                                                   HashMap<SimpleEntry<String,String>, Document> Documents) throws ArrayDeParaulesBuitException, NombreMassaPetitDocumentsException {
        if (k <= 0) {
            throw new NombreMassaPetitDocumentsException();
        }

        if (D.getParaules().size() == 0) {
            throw new ArrayDeParaulesBuitException();
        }

        ArrayList<String> paraules = obteContingut(D);

        return EspaiVec.cercaDoc(k, Documents, paraules, DocumentsParaules);
    }
}
