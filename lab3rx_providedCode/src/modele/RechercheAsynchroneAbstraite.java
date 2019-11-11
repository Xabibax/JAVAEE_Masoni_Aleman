package modele;

import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.LienVersRessource;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.InvocationCallback;
import java.util.Optional;
import java.util.concurrent.Future;

public abstract class RechercheAsynchroneAbstraite implements AlgorithmeRecherche {

    protected Future<Optional<HyperLien<Livre>>> rechercheAsync(HyperLien<BibliothequeArchive> h, Livre l, Client client) {
        return LienVersRessource.proxy(client, h, BibliothequeArchive.class).chercherAsynchrone(l, null);
    }

    protected Future<Optional<HyperLien<Livre>>> rechercheAsyncAvecRappel(HyperLien<BibliothequeArchive> h, Livre l, Client client, InvocationCallback<Optional<HyperLien<Livre>>> retour) {
        //return retour.completed(LienVersRessource.proxy(client, h, BibliothequeArchive.class).chercherAsynchrone(l,null));
        return null;
    }
}
