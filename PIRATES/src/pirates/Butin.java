package pirates;

/**
 * Classe qui represente un trésor.
 * Chaque Butin est unique
 * 
 */
public class Butin {
	/**
	 * Nom du trésor
	 */
	private String name;
	
	/**
	 * Crée un trésor avec le nom spécifié
	 * @param name	Nom du trésor
	 * 
	 */
	public Butin(String name) {
		this.name = name;
	}
	
	/** 
	 * Donne le nom du trésor (ex: o1)
	 * @return	Nom du trésor
	 * 
	 */
	public String getName() {
		return name;
	}
}
