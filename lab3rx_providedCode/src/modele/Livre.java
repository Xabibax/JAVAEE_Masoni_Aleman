package modele;

import configuration.JAXRS;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "livre")
public interface Livre {
    static Livre fromString(String titre) {
        return new ImplemLivre(titre);
    }

    @GET
    @Path(JAXRS.SOUSCHEMIN_TITRE)
    String getTitre();
}
