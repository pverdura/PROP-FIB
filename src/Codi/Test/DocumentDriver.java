package Codi.Test;

import Codi.Domini.CtrlDominiDocument;
import Codi.Domini.Document;
import Codi.Util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

import Codi.Util.TipusExtensio;

public class DocumentDriver {

    static void print (HashMap<String, Integer> h) {
        for (HashMap.Entry<String, Integer> set : h.entrySet()) {

            System.out.println(set.getKey() + " = "
                    + set.getValue());
        }
    }
    public static void main (String[] args) {
        ArrayList<String> a = new ArrayList<String>();
        a.add("el"); a.add("la"); a.add("els"); a.add("les");
        a.add("un"); a.add("una"); a.add("uns"); a.add("unes");
        a.add("últim"); a.add("última"); a.add("últims"); a.add("últimes");
        a.add("de"); a.add("per"); a.add("en"); a.add("i"); a.add("del"); a.add("pel"); a.add("a");
        a.add("al"); a.add("algun"); a.add("això"); a.add("allò"); a.add("amb"); a.add("cada"); a.add("més");
        Document.setStopWords(a);

        Document d1 = new Document("titol1", "autor1");
        System.out.println(d1.getAutor());
        System.out.println(d1.getTitol());
        System.out.println(d1.getPes());
        System.out.println(d1.getPath());
        System.out.println(d1.getExtensio());
        print(d1.getAparicions());
        System.out.println(d1.getContingut());

        System.out.println("---------------");
        d1.setPath("./hola.txt");
        d1.setAutor("autor2");
        d1.setTitol("titol2");
        d1.setExtensio(TipusExtensio.TXT);
        String c = "El Regne Unit (en anglès: The United Kingdom) oficialment, el Regne Unit de Gran Bretanya i Irlanda del Nord (en anglès: The United Kingdom of Great Britain and Northern Ireland) és un estat insular sobirà localitzat al nord-oest d'Europa. El Regne Unit està integrat, geogràficament, per l'illa de Gran Bretanya, una secció del nord de l'illa d'Irlanda i altres illes més petites. Irlanda del Nord és l'única regió (a més a més de Gibraltar) del Regne Unit amb frontera terrestre, que comparteix amb la República d'Irlanda. A més d'aquesta frontera, el Regne Unit està envoltat per l'oceà Atlàntic, el mar del Nord, el Canal de la Mànega i el mar d'Irlanda.\n" +
                "\n" +
                "El Regne Unit és una unió política de quatre nacions que el constitueixen: Anglaterra, Irlanda del Nord, Escòcia i Gal·les. El Regne Unit és una monarquia constitucional organitzada com a democràcia parlamentària, la seu del govern de la qual és la ciutat de Londres, i el cap d'Estat de la qual és el rei Carles III. Les dependències de la Corona –les illes del Canal i l'illa de Man– no són part de la Unió, però estan federats al Regne Unit. El Regne Unit també té jurisdicció sobre 14 territoris d'ultramar, que inclouen Gibraltar i les Illes Malvines. Les dependències de la corona pertanyen a les Illes Britàniques però no són part del Regne Unit. Com a monarquia constitucional, el Regne Unit està relacionat políticament amb altres 15 Reialmes de la Commonwealth amb els quals comparteix el monarca, el rei Carles III com a cap d'estat.\n" +
                "\n" +
                "El Regne Unit és un país desenvolupat, amb el cinquè Producte interior brut nominal més gran del món. Fou la potència internacional més important durant el segle xix i les primeres dècades del segle xx, però els costos econòmics de les dues guerres mundials i la desaparició de l'Imperi van fer disminuir el seu paper en els afers mundials. El Regne Unit té un seient permanent del Consell de Seguretat de les Nacions Unides, i és membre del G8, l'OTAN, l'OMC i la Unió Europea. El juny del 2016, però, el Regne Unit va votar deixar la UE, en el referèndum convocat per David Cameron, sobre la seva permanència a la Unió Europea. I el març del 2017 el govern del Regne Unit notifica oficialment a la Unió Europea la seva sortida, a través de la invocació de l'Article 50 del Tractat de la Unió Europea.";

        d1.setContingut("l'hora d'hora veure'm veure's veure't mirar-me digues-m'ho donar-los-hi cantar-les");
        d1.setContingut(c);
        System.out.println(d1.getAutor());
        System.out.println(d1.getTitol());
        System.out.println(d1.getPes());
        System.out.println(d1.getPath());
        System.out.println(d1.getExtensio());
        print(d1.getAparicions());
        System.out.println(d1.getContingut());
    }
}
