package modele;

import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.LienVersRessource;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.container.AsyncResponse;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

public class RechercheAsynchroneMultiTaches extends RechercheAsynchroneAbstraite implements AlgorithmeRecherche {

    @Override
    public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<BibliothequeArchive>> bibliotheques, Client client) {
        CountDownLatch latch = new CountDownLatch(bibliotheques.size());
        ExecutorService exec = Executors.newCachedThreadPool();
        AsyncResponse ar = null;

        // Une itération sur les hyperliens
        for (HyperLien<BibliothequeArchive> h : bibliotheques) {
            // A chaque étape, créer une fonction de retour de type InvocationCallback<Optional<HyperLien<Livre>>>
            // puis appeler la méthode de recherche asynchrone.
            InvocationCallback<Optional<HyperLien<Livre>>> callableTask = new InvocationCallback<Optional<HyperLien<Livre>>>() {
                @Override
                public void completed(Optional<HyperLien<Livre>> response) {
                    Future<Optional<HyperLien<Livre>>> ol = LienVersRessource.proxy(client, h, BibliothequeArchive.class).chercherAsynchrone(l, ar);
                    // Utiliser une barrière de synchronisation de type CountDownLatch : dans la fonction de retour,
                    // utiliser la méthode countDown, dans la tâche principale (celle de la méthode), utiliser await.
                    // Dans le cas d'une réponse positive, on peut passer la barrière directement.
                    latch.countDown();
                }

                @Override
                public void failed(Throwable arg0) {
                    arg0.printStackTrace();
                }
            };
            Optional<HyperLien<Livre>> result;
            try {
                result = (Optional<HyperLien<Livre>>) exec.submit((Runnable) callableTask).get();
                if (result.isPresent()) {
                    return result;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        // attends que toutes les tâches soient finies
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        exec.shutdown();
        return null;
    }

    @Override
    // Nom : "recherche async multi"
    public NomAlgorithme nom() {
        return new NomAlgorithme() {

            @Override
            public String getNom() {
                return "recherche async multi";
            }
        };
    }

}
