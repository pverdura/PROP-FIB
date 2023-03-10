package Codi.Domini;

import Codi.Excepcions.ArrayDeParaulesBuitException;
import Codi.Excepcions.NombreMassaPetitDocumentsException;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Classe que ens implementa la cerca dels documents mes rellevants respecte un document
 *
 * @author Pol Verdura
 */
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
     * @param D Aquest es el Document que s'utilitza per fer la cerca
     * @return Retorna el conjunt de paraules del document D en format d'ArrayList
     */
    private static ArrayList<String> obteContingut(Document D) {
        Set<String> paraules = D.getAparicions().keySet();

        return new ArrayList<String>(paraules);
    }

    /**
     * S'obtenen els k documents mes semblants al document D
     *
     * @param D Aquest es el Document que s'utilitza per fer la cerca
     * @param k Indica el nombre de documents que volem obtenir
     * @param DocumentsParaules Hi ha es paraules amb els identificadors dels documents on apareix
     * @param Documents Hi ha els documents del sistema
     * @return Retorna un llistat de k Documents (titol,autor) mes semblants al document D
     * @throws ArrayDeParaulesBuitException Si el document D no te contingut
     * @throws NombreMassaPetitDocumentsException Si k <= 0
     */
    public static ArrayList<SimpleEntry<String,String>> cercaDoc(Document D, int k, HashMap<String,ArrayList<SimpleEntry<String,String>>> DocumentsParaules, HashMap<SimpleEntry<String,String>, Document> Documents)
            throws ArrayDeParaulesBuitException, NombreMassaPetitDocumentsException {
        // Mirem que es vulgui un nombre de documents natural [1, INF)
        if (k <= 0) {
            throw new NombreMassaPetitDocumentsException();
        }

        // Mirem que el document no tingui el contingut buit
        if (D.getParaules().size() == 0) {
            throw new ArrayDeParaulesBuitException();
        }

        // Obtenim el conjunt de paraules del contingut del document D
        ArrayList<String> paraules = obteContingut(D);

        ArrayList<SimpleEntry<String,String>> documents = EspaiVec.cercaDoc(k+1, Documents, paraules, DocumentsParaules);

        // Ara mirem que no ens aparegui el document que hem posat a la cerca
        int i = 0;

        for(SimpleEntry<String,String> doc : documents) {
            // Mirem que no obtinguem el document que estem cercant
            if(D.getTitol().equals(doc.getKey()) && D.getAutor().equals(doc.getValue())) {
                break;
            }
            ++i; // Incrementem el nombre d'iteracions
        }

        if(i == documents.size()) {
            // Com no tenim el document consultat en l'array obtingut, eliminem l'??lim document de la llista
            // (el menys semblant)
            i = documents.size()-1;
        }
        documents.remove(i);

         // Retornem la cerca
        return documents;
    }
}
