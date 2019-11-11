package modele;

import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.LienVersRessource;

import javax.ws.rs.client.Client;
import java.util.List;
import java.util.Optional;

public class RechercheAsynchroneSequentielle extends RechercheAsynchroneAbstraite implements AlgorithmeRecherche {

    @Override
    public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<BibliothequeArchive>> bibliotheques, Client client) {
        List<Optional<HyperLien<Livre>>> lp = null;
        // Une première itération sur les hyperliens permettant d'obtenir une liste de promesses
        for (HyperLien<BibliothequeArchive> h : bibliotheques) {
            lp.add(
                    LienVersRessource.proxy(
                            client,
                            h,
                            BibliothequeArchive.class
                    ).chercher(l)
            );
        }
        // Une seconde itération sur les promesses permettant de produire le résultat
        for (Optional<HyperLien<Livre>> h : lp) {
            if (h.isPresent()) {
                return h;
            }
        }
        return null;
    }

    @Override
    // Nom : "recherche async seq"
    public NomAlgorithme nom() {
        return new NomAlgorithme() {

            @Override
            public String getNom() {
                return "recherche async seq";
            }
        };
    }

}
