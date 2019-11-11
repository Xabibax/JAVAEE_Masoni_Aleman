package modele;

import static configuration.JAXRS.TYPE_MEDIA;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.annotations.ReponsesGETNullEn404;
import infrastructure.jaxrs.annotations.ReponsesPOSTEnCreated;

public interface Archive {

	Livre sousRessource(IdentifiantLivre id) ;
	
	@GET
	@ReponsesGETNullEn404
	@Path("{id}")
	@Produces(TYPE_MEDIA)
	@Consumes(TYPE_MEDIA)
	Livre getRepresentation(@PathParam("id") IdentifiantLivre id);
	
	@POST
	@ReponsesPOSTEnCreated
	@Path("")
	@Produces(TYPE_MEDIA)
	@Consumes(TYPE_MEDIA)
	HyperLien<Livre> ajouter(Livre l);
}
