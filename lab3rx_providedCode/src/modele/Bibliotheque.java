package modele;

import static configuration.JAXRS.SOUSCHEMIN_CATALOGUE;
import static configuration.JAXRS.TYPE_MEDIA;

import java.util.Optional;
import java.util.concurrent.Future;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.HyperLiens;
import infrastructure.jaxrs.annotations.ReponsesGETNullEn404;
import infrastructure.jaxrs.annotations.ReponsesPUTOption;

public interface Bibliotheque {

	@PUT
	@ReponsesPUTOption
	@Path("async")
	@Consumes(TYPE_MEDIA)
	@Produces(TYPE_MEDIA)
	Future<Optional<HyperLien<Livre>>> chercherAsynchrone(Livre l, @Suspended final AsyncResponse ar);

	@PUT
	@ReponsesPUTOption
	@Path("")
	@Produces(TYPE_MEDIA)
	@Consumes(TYPE_MEDIA)
	Optional<HyperLien<Livre>> chercher(Livre l);

	@GET
	@Path(SOUSCHEMIN_CATALOGUE)
	@Produces(TYPE_MEDIA)
	HyperLiens<Livre> repertorier();
	
}
