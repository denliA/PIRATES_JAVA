package pirates;

/**
 * Classe qui contient une exception qui est levée si le pirate demandé n'existe pas
 *
 */
public class PirateNotFoundException extends Exception {
	/**
	 * Code qui permet de mieux identifier une exception
	 */
	int code;
	
	/**
	 * Sert à la deserialization
	 */
	private static final long serialVersionUID = 1962963584420398384L;

	/**
	 * Crée l'exception
	 */
	public PirateNotFoundException() {
	    super("Données dans fichier dataInput sont incorrectes mais nous avons réglé ce problème");
	    code = 4;
	}
	
	/**
	 * Crée l'exception avec un message d'erreur
	 * @param s	message d'erreur
	 * @param code	entier qui permet de mieux identifier une exception
	 */
	public PirateNotFoundException(String s,int code) {
	    super(s);
	    this.code = code;
	}
	
	/**
	 * Crée l'exception avec un message d'erreur
	 * @param s	message d'erreur
	 */
	public PirateNotFoundException(String s) {
	    super(s);
	    this.code = 4;
	}
	
	/**
	 * Donne le code d'erreur
	 * @return code d'erreur qui est un entier
	 */
	public int getCode() {
		return code;
	}

}
