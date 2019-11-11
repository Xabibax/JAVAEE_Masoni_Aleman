package client;

import java.lang.reflect.Proxy;
import java.util.Optional;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.WebTarget;

import infrastructure.jaxrs.ClientRessource;
import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.Outils;
import modele.BibliothequeArchive;
import modele.ImplemLivre;
import modele.Livre;

import org.glassfish.jersey.client.proxy.WebResourceFactory;

import configuration.JAXRS;

public class AppliCliente2 {

	private static final String ADRESSE = JAXRS.SERVEUR + JAXRS.CHEMIN;

	public static void main(String[] args) {

		System.out.println("*************");

		WebTarget cible = JAXRS.client().target(ADRESSE);
		BibliothequeArchive biblio = WebResourceFactory.newResource(BibliothequeArchive.class, cible);

		System.out.println("Biblio (proxy) : " + Proxy.isProxyClass(biblio.getClass()));

		System.out.println("*** 1. Ajouter des livres ***");
		Livre l1 = new ImplemLivre("Restful Java with JAX-RS");
		Livre l2 = new ImplemLivre("Restful Java with JAX-RS");
		Livre l3 = new ImplemLivre("Restful Java with JAX-RS 2.0");
		Livre l4 = new ImplemLivre("Restful Java with JAX-RS 2.0");
		Livre l5 = new ImplemLivre("Restful Java with JAX-RS 3.0");
		Livre l6 = new ImplemLivre("Restful Java with JAX-RS 3.0");
		Livre l7 = new ImplemLivre("Restful Java with JAX-RS 4.0");
		Livre l8 = new ImplemLivre("Restful Java with JAX-RS 4.0");
		Livre l9 = new ImplemLivre("Restful Java with JAX-RS 5.0");
		Livre l10 = new ImplemLivre("Restful Java with JAX-RS 5.0");
		
		HyperLien<Livre> r1 = null;
		HyperLien<Livre> r2 = null;
		HyperLien<Livre> r3 = null;
		HyperLien<Livre> r4 = null;
		HyperLien<Livre> r5 = null;
		HyperLien<Livre> r6 = null;
		HyperLien<Livre> r7 = null;
		HyperLien<Livre> r8 = null;
		HyperLien<Livre> r9 = null;
		HyperLien<Livre> r10 = null;

		r1 = biblio.ajouter(l1); // POST 1
		System.out.println("POST 1 : " + r1.getClass());
		r2 = biblio.ajouter(l2); // POST 2
		System.out.println("POST 2 : " + r2.getClass());
		r3 = biblio.ajouter(l3); // POST 1
		System.out.println("POST 1 : " + r3.getClass());
		r4 = biblio.ajouter(l4); // POST 2
		System.out.println("POST 2 : " + r4.getClass());
		r5 = biblio.ajouter(l5); // POST 1
		System.out.println("POST 1 : " + r5.getClass());
		r6 = biblio.ajouter(l6); // POST 2
		System.out.println("POST 2 : " + r6.getClass());
		r7 = biblio.ajouter(l7); // POST 1
		System.out.println("POST 1 : " + r7.getClass());
		r8 = biblio.ajouter(l8); // POST 2
		System.out.println("POST 2 : " + r8.getClass());
		r9 = biblio.ajouter(l9); // POST 1
		System.out.println("POST 1 : " + r9.getClass());
		r10 = biblio.ajouter(l10); // POST 2
		System.out.println("POST 2 : " + r10.getClass());
		
		System.out.println("POST 1 - uri : " + r1.getUri());
		System.out.println("POST 2 - uri : " + r2.getUri());
		System.out.println("POST 1 - uri : " + r3.getUri());
		System.out.println("POST 2 - uri : " + r4.getUri());
		System.out.println("POST 1 - uri : " + r5.getUri());
		System.out.println("POST 2 - uri : " + r6.getUri());
		System.out.println("POST 1 - uri : " + r7.getUri());
		System.out.println("POST 2 - uri : " + r8.getUri());
		System.out.println("POST 1 - uri : " + r9.getUri());
		System.out.println("POST 2 - uri : " + r10.getUri());
		
		System.out.println("*** 3. Chercher un livre ***");

		System.out.println("GET 1 - uri (présent) : " + biblio.chercher(l1));
		
		System.out.println("GET 2 - livre absent : " + biblio.chercher(new ImplemLivre("absent")));
		
		
		System.out.println("*** 2. RÃ©cupÃ©rer un livre Ã  partir de l'hyperlien ***");

		Livre l11 = ClientRessource.proxy(r2, Livre.class);
		System.out.println("Classe du proxy : " + l11.getClass());
		//System.out.println("Type du proxy : " + l11.getTitre());
		Optional<HyperLien<Livre>> l12 = biblio.chercher(l11);
		System.out.println("GET 3 - uri (présent) : " + l12);
		System.out.println("Classe parente du proxy : " + l12.getClass());

		System.exit(0);
		
	}

}
