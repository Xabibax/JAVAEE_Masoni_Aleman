package modele;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Singleton;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.container.AsyncResponse;

import configuration.Orchestrateur;
import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.HyperLiens;
import infrastructure.jaxrs.LienVersRessource;

@Singleton
@Path("portail")
public class ImplemPortail implements Portail {

	ConcurrentMap<NomAlgorithme, AlgorithmeRecherche> tableType;
	Client client;
	AlgorithmeRecherche algo;
	List<HyperLien<BibliothequeArchive>> hyperliens;

	public ImplemPortail() {
		tableType = new ConcurrentHashMap<NomAlgorithme, AlgorithmeRecherche>();
		client = Orchestrateur.clientJAXRS();
		algo = null;
		hyperliens = configuration.Initialisation.bibliotheques();

		AlgorithmeRecherche a1 = new RechercheAsynchroneMultiTaches();
		tableType.put(a1.nom(),a1 );
		AlgorithmeRecherche a2 = new RechercheAsynchroneSequentielle();
		tableType.put(a2.nom(),a2 );
		AlgorithmeRecherche a3 = new RechercheAsynchroneStreamParallele();
		tableType.put(a3.nom(),a3 );
		AlgorithmeRecherche a4 = new RechercheAsynchroneStreamRx();
		tableType.put(a4.nom(),a4 );
		AlgorithmeRecherche a5 = new RechercheSynchroneMultiTaches();
		tableType.put(a5.nom(),a5 );
		AlgorithmeRecherche a6 = new RechercheSynchroneSequentielle();
		tableType.put(a6.nom(),a6 );
		AlgorithmeRecherche a7 = new RechercheSynchroneStreamParallele();
		tableType.put(a7.nom(),a7 );
		AlgorithmeRecherche a8 = new RechercheSynchroneStreamRx();
		tableType.put(a8.nom(),a8 );
	}

	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l) {
		return algo.chercher(l, hyperliens, client);
	}

	@Override
	public Future<Optional<HyperLien<Livre>>> chercherAsynchrone(Livre l, AsyncResponse ar) {
		for(HyperLien<BibliothequeArchive> h : hyperliens) {
			Optional<HyperLien<Livre>> ol = LienVersRessource.proxy(client,h, BibliothequeArchive.class).chercher(l);
			if(ol.isPresent()){
				ar.resume(ol);
			} 
		}
		ar.resume(Optional.empty());
		return null;
	}

	@Override
	public HyperLiens<Livre> repertorier() {
		return new HyperLiens(
				this.hyperliens.parallelStream()
				.map(n -> (LienVersRessource.proxy(client,n, BibliothequeArchive.class)).repertorier().getLiens().stream())
				.flatMap(x -> x)
				.collect(Collectors.toList())
				);
	}

	@Override
	public void changerAlgorithmeRecherche(NomAlgorithme algo) {
		if(this.tableType.containsKey(algo)) {
			this.algo = this.tableType.get(algo);
		}
	}
}
