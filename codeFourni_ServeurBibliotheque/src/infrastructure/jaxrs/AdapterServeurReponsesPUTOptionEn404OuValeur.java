package infrastructure.jaxrs;

import java.io.IOException;
import java.util.Optional;

import infrastructure.jaxrs.annotations.ReponsesPUTOption;
import modele.Livre;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;

import configuration.Service;

import static configuration.JAXRS.*;

@Provider
@ReponsesPUTOption
@Priority(Priorities.HEADER_DECORATOR)
public class AdapterServeurReponsesPUTOptionEn404OuValeur implements ReaderInterceptor, ContainerResponseFilter {
	public AdapterServeurReponsesPUTOptionEn404OuValeur() {
		System.out.println("** Initialisation du filtre de type " + this.getClass());
	}

	@Override
	public void filter(ContainerRequestContext requete, ContainerResponseContext reponse) throws IOException {
		// Cas PUT
		if (requete.getMethod().equalsIgnoreCase("PUT")) {
			Optional<HyperLien<?>> entite = ((Optional<HyperLien<?>>) reponse.getEntity());
			if(reponse.getStatus() == Response.Status.OK.getStatusCode()) {	
				if(entite.isPresent()) {
					reponse.setEntity(entite.get());
				} else {
					convertirEn404(requete, reponse);
					return;
				}
			}
		}
	}
	
	private static String DESCRIPTION_LIVRE = "descriptionLivre";
	
	private void convertirEn404(ContainerRequestContext requete, ContainerResponseContext reponse) {
		System.out.println("recherche : 404 NOT FOUND !");
		String contenu = requete.getUriInfo().getRequestUri().toString();
		contenu = contenu + "-" + requete.getProperty(DESCRIPTION_LIVRE);
		reponse.setEntity(contenu, null, MediaType.TEXT_PLAIN_TYPE);
		reponse.setStatus(Response.Status.NOT_FOUND.getStatusCode());
	}

	@Override
	public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException, WebApplicationException {
		Livre livre = ((Livre) context.proceed());
		context.setProperty("livre",livre);
		return livre;
	}
	
	

}
