package modele;

public interface IdentifiantLivre {
    static IdentifiantLivre fromString(String id) {
        return new ImplemIdentifiantLivre(id);
    }

    String getId();
}
