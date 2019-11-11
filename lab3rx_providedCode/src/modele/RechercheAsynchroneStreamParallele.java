package modele;

import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.LienVersRessource;

import javax.ws.rs.client.Client;
import javax.ws.rs.container.AsyncResponse;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

public class RechercheAsynchroneStreamParallele extends RechercheAsynchroneAbstraite implements AlgorithmeRecherche {

    // Une définition d'un flot suivie d'une réduction à une option contenant un hyperlien vers un livre
    //
    // Partir de la liste d'hyperliens vers des bibliothèques archives.
    // Créer un flot parallèle.
    // Appliquer la fonction de recherche asynchrone.
    // Appliquer la fonction Outils::remplirPromesse.
    // Retirer les options vides.
    // Réduire à n'importe quelle valeur ou à défaut à l'option vide.
    @Override
    public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<BibliothequeArchive>> bibliotheques,
                                               Client client) {

        AsyncResponse ar = null;
        Future<Optional<HyperLien<Livre>>> ol =
                // Partir de la liste d'hyperliens vers des bibliothèques archives.
                // Créer un flot parallèle.
                bibliotheques.parallelStream()
                        .map(n -> (LienVersRessource.proxy(client, n, BibliothequeArchive.class))
                                // Appliquer la fonction de recherche synchrone.
                                .chercherAsynchrone(l, ar))
                        // Retirer les options vides.
                        .filter(Future::isDone)
                        // Réduire à n'importe quelle valeur ou à défaut à l'option vide.
                        .findFirst().get();
        if (ol.isDone()) {
            try {
                return ol.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public NomAlgorithme nom() {
        return new NomAlgorithme() {

            @Override
            public String getNom() {
                return "recherche async stream 8";
            }
        };
    }

}
