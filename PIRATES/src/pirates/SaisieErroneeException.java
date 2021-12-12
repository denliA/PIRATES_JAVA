package pirates;

/**
 * Classe qui contient une exception qui est levée lorsque l'utilisateur tape pas la valeur attendue au clavier
 *
 */
public class SaisieErroneeException extends Exception {

	/**
	 * Sert à la deserialization
	 */
	private static final long serialVersionUID = -8697003205853628889L;
	
	/**
	 * Crée l'exception
	 */
	public SaisieErroneeException() {
	    super();
	}
	
	/**
	 * Crée l'exception avec un message d'erreur
	 * @param s	message d'erreur
	 */
	public SaisieErroneeException(String s) {
	    super(s);
	}

}
