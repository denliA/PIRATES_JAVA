package pirates;

/**
 * Classe qui contient une exception qui est levée lorsque l'utilisateur tape pas la valeur attendue au clavier
 *
 */
public class SaisieErroneeException extends Exception {
	/**
	 * Code qui permet de mieux identifier une exception
	 */
	int code;
	/**
	 * Sert à la deserialization
	 */
	private static final long serialVersionUID = -8697003205853628889L;
	
	/**
	 * Crée l'exception
	 */
	public SaisieErroneeException() {
	    super();
	    code = 6;
	}
	
	/**
	 * Crée l'exception avec un message d'erreur
	 * @param s	message d'erreur
	 * @param code	entier qui permet de mieux identifier une exception
	 */
	public SaisieErroneeException(String s,int code) {
	    super(s);
	    this.code = code;
	}
	
	/**
	 * Crée l'exception avec un message d'erreur
	 * @param s	message d'erreur
	 */
	public SaisieErroneeException(String s) {
	    super(s);
	    this.code = 6;
	}

}
