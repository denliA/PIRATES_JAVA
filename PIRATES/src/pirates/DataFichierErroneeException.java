package pirates;

/**
 * Classe qui contient une exception qui est levée lorsque les données dans le fichier inputData sont incorrectes
 *
 */
public class DataFichierErroneeException extends Exception {
	/**
	 * Code qui permet de mieux identifier une exception
	 */
	int code;
	/**
	 * Sert à la deserialization
	 */
	private static final long serialVersionUID = 5275681344501160650L;

	/**
	 * Crée l'exception
	 */
	public DataFichierErroneeException() {
	    super("Données dans fichier dataInput sont incorrectes");
	    code = 2;
	}
	
	/**
	 * Crée l'exception avec un message d'erreur
	 * @param s	message d'erreur qui permet de mieux identifier une exception
	 * @param code	entier 
	 */
	public DataFichierErroneeException(String s,int code) {
	    super(s);
	    this.code = code;
	}
	
	/**
	 * Crée l'exception avec un message d'erreur
	 * @param s	message d'erreur
	 */
	public DataFichierErroneeException(String s) {
	    super(s);
	    this.code = 2;
	}
	
	/**
	 * Donne le code d'erreur
	 * @return code d'erreur qui est un entier
	 */
	public int getCode() {
		return code;
	}

}