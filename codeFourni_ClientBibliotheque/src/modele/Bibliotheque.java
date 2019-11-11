package modele;

import java.util.Optional;

import javax.ws.rs.*;

import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.HyperLiens;
//import infrastructure.jaxrs.annotations.ReponsesPUTOption;

import static configuration.JAXRS.*;

public interface Bibliotheque {
	
	@PUT
	@Path("")
	@Consumes(TYPE_MEDIA)
	@Produces(TYPE_MEDIA)
	Optional<HyperLien<Livre>> chercher(Livre l);

	@GET
	@Path(SOUSCHEMIN_CATALOGUE)
	@Consumes(TYPE_MEDIA)
	@Produces(TYPE_MEDIA)
	HyperLiens<Livre> repertorier();
	
}
