package infrastructure.jaxrs;

import org.glassfish.jersey.client.proxy.WebResourceFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

public class LienVersRessource {

    public static <T> T proxy(Client c, HyperLien<T> lien, Class<T> type) {
        WebTarget cible = c.target(lien.getUri());
        return WebResourceFactory.newResource(type, cible);
    }

}
