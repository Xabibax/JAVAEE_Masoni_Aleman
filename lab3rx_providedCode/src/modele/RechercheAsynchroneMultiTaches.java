package modele;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.client.Client;

import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.HyperLiens;
import infrastructure.jaxrs.LienVersRessource;

public class RechercheAsynchroneMultiTaches extends RechercheSynchroneAbstraite implements AlgorithmeRecherche {

	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<BibliothequeArchive>> bibliotheques,
			Client client) {
		/*
		for(HyperLien<BibliothequeArchive> h : bibliotheques) {
			HyperLiens<Livre> hyperliensLivres = LienVersRessource.proxy(client,h , infrastructure.langage.Types.convertirTypeEnClasse(Livre)).repertorier();
			
			for(HyperLien<Livre> hl : hyperliensLivres.getLiens()) {
				ExecutorService exec = Executors.newCachedThreadPool();
				CountDownLatch count = new CountDownLatch(0);
				Livre l1 = LienVersRessource.proxy(client, hl, Livre);
				count.await();
				if(l1.equals(l)) {
					//return ...
					count.countDown();
				}
			}
		}
		*/
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
