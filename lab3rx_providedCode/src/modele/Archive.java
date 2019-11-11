package modele;

import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.annotations.ReponsesGETNullEn404;
import infrastructure.jaxrs.annotations.ReponsesPOSTEnCreated;

import javax.ws.rs.*;

import static configuration.JAXRS.TYPE_MEDIA;

public interface Archive {

    Livre sousRessource(IdentifiantLivre id);

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
