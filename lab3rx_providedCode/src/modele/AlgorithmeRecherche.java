package modele;

import infrastructure.jaxrs.HyperLien;

import javax.ws.rs.client.Client;
import java.util.List;
import java.util.Optional;

public interface AlgorithmeRecherche {
    Optional<HyperLien<Livre>>
    chercher(Livre l, List<HyperLien<BibliothequeArchive>> bibliotheques, Client client);

    NomAlgorithme nom();
}
