package modele;

import static configuration.JAXRS.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="livre")
public interface Livre {
	
	@GET
	@Path("id/titre")
	@Produces(TYPE_MEDIA)
	@Consumes(TYPE_MEDIA)
	String getTitre();
	
	public static Livre fromString(String id) {
		//return IdentifiantLivre.fromString(id);
		return new ImplemLivre(id);
	}
}
