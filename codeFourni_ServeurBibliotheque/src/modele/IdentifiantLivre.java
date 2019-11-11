package modele;

public interface IdentifiantLivre {
	String getId();	
	public static IdentifiantLivre fromString(String id) {
		return new ImplemIdentifiantLivre(id);
	}
}
