package modele;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.client.Client;

import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.HyperLiens;
import infrastructure.jaxrs.LienVersRessource;

public class RechercheSynchroneStreamParallele extends RechercheSynchroneAbstraite implements AlgorithmeRecherche {

	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<BibliothequeArchive>> bibliotheques,
			Client client) {
		 
		Optional<HyperLien<Livre>> ol =  bibliotheques.parallelStream()
				.map(n -> (LienVersRessource.proxy(client,n, BibliothequeArchive.class)).chercher(l))
				.filter(map -> map.isPresent())
				.findFirst().get();
		 if(ol.isPresent()) {
			 return ol;
		 }
		 return null;
	}

	@Override
	public NomAlgorithme nom() {
		return new NomAlgorithme() {

			@Override
			public String getNom() {
				return "recherche sync stream 8";
			}
		};
	}

}
