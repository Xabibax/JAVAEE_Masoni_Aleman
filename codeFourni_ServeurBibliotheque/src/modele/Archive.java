package modele;

import static configuration.JAXRS.TYPE_MEDIA;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import infrastructure.jaxrs.HyperLien;

public interface Archive {

	Livre sousRessource(IdentifiantLivre id) ;
	
	@GET
	@Path("{id}")
	@Produces(TYPE_MEDIA)
	@Consumes(TYPE_MEDIA)
	Livre getRepresentation(@PathParam("id") IdentifiantLivre id);
	
	@POST
	@Path("")
	@Produces(TYPE_MEDIA)
	HyperLien<Livre> ajouter(Livre l);
}
