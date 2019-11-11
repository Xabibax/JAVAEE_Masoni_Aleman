package modele;

import static configuration.JAXRS.TYPE_MEDIA;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlRootElement;

import configuration.JAXRS;

@XmlRootElement(name="livre")
public interface Livre {
	@GET
	@Path(JAXRS.SOUSCHEMIN_TITRE)
	@Produces(TYPE_MEDIA)
	String getTitre();
	
	
}
