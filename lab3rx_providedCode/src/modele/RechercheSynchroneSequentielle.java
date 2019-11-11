package modele;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.client.Client;

import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.HyperLiens;
import infrastructure.jaxrs.LienVersRessource;

public class RechercheSynchroneSequentielle extends RechercheSynchroneAbstraite implements AlgorithmeRecherche {

	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<BibliothequeArchive>> bibliotheques,
			Client client) {
		for(HyperLien<BibliothequeArchive> h : bibliotheques) {
			Optional<HyperLien<Livre>> ol = LienVersRessource.proxy(client,h, BibliothequeArchive.class).chercher(l);
			if(ol.isPresent()){
				return ol;
			} 
		}
		
		return null;
	}

	@Override
	public NomAlgorithme nom() {
		return new NomAlgorithme() {
			
			@Override
			public String getNom() {
				return "recherche sync seq";
			}
		};
	}

}
