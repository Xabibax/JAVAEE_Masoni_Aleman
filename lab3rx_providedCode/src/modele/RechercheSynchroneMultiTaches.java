package modele;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.client.Client;

import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.LienVersRessource;

public class RechercheSynchroneMultiTaches extends RechercheAsynchroneAbstraite implements AlgorithmeRecherche {

	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<BibliothequeArchive>> bibliotheques, Client client) {
		// défini un objet qui va se décrémenter à chaque fois qu'une tâche sera finie
		CountDownLatch latch = new CountDownLatch(bibliotheques.size());
		// agent qui gère les tâches
		ExecutorService exec = Executors.newCachedThreadPool();
		
		//List<Callable<Optional<HyperLien<Livre>>>> callables = new ArrayList<>();
		
		for(HyperLien<BibliothequeArchive> h : bibliotheques) {
			
			Callable<Optional<HyperLien<Livre>>> callableTask = () -> {
				Optional<HyperLien<Livre>> ol = LienVersRessource.proxy(client,h, BibliothequeArchive.class).chercher(l);
				// décremente
				latch.countDown();
				return ol;
			};
			// execution
			Optional<HyperLien<Livre>> result;
			try {
				result = exec.submit(callableTask).get();
				if(result.isPresent()){
					return result;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
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
	public NomAlgorithme nom() {
		return new NomAlgorithme() {

			@Override
			public String getNom() {
				return "recherche sync multi";
			}
		};
	}

}
