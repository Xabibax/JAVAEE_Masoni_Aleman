package modele;

import infrastructure.jaxrs.HyperLien;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import javax.ws.rs.client.Client;
import java.util.List;
import java.util.Optional;

public class RechercheAsynchroneStreamRx extends RechercheAsynchroneAbstraite implements AlgorithmeRecherche {

    @Override
    public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<BibliothequeArchive>> bibliotheques,
                                               Client client) {
        // Partir d'un observable créé à partir de la liste d'hyperliens.
        Observable<HyperLien<BibliothequeArchive>> ob = (Observable<HyperLien<BibliothequeArchive>>) bibliotheques;
        // Appliquer en linéarisant (en ordonnant suivant le temps) la fonction (h -> Observable.fromCallable(() -> rechercheSync(h, l, client))). Indication : voir la méthode Observable.flatMap.

        return ob.flatMap(h -> Observable.fromFuture(rechercheAsync(h, l, client))
                // Pour une exécution multi-tâche, ajouter un appel à subscribeOn(Schedulers.io()) après l'appel à fromCallable.
                .subscribeOn(Schedulers.io()))
                // Retirer les options vides.
                .filter(map -> map.isPresent())
                // Prendre la première valeur ou à défaut l'option vide. Indication : voir la méthode Observable.blockingFirst.
                .blockingFirst();
    }

    @Override
    public NomAlgorithme nom() {
        return new NomAlgorithme() {

            @Override
            public String getNom() {
                return "recherche async stream rx";
            }
        };
    }

}
