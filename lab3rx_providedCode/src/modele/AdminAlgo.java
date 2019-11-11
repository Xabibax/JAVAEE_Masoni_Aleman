package modele;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public interface AdminAlgo {

    @PUT
    @Path("admin/recherche")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    void changerAlgorithmeRecherche(NomAlgorithme algo);
}
