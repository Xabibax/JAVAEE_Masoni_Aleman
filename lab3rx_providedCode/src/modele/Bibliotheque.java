package modele;

import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.HyperLiens;
import infrastructure.jaxrs.annotations.ReponsesPUTOption;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import java.util.Optional;
import java.util.concurrent.Future;

import static configuration.JAXRS.SOUSCHEMIN_CATALOGUE;
import static configuration.JAXRS.TYPE_MEDIA;

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
