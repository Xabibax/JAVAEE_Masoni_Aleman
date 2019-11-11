package modele;

import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.LienVersRessource;

import javax.ws.rs.client.Client;
import java.util.Optional;

public abstract class RechercheSynchroneAbstraite implements AlgorithmeRecherche {

    protected Optional<HyperLien<Livre>> rechercheSync(HyperLien<BibliothequeArchive> h, Livre l, Client client) {
        return LienVersRessource.proxy(client, h, BibliothequeArchive.class).chercher(l);
    }

}
