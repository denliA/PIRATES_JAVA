package pirates;

/**
 * Classe qui contient une exception qui est levée lorsque les données dans le fichier inputData sont incorrectes
 *
 */
public class DataFichierErroneeException extends Exception {

	/**
	 * Sert à la deserialization
	 */
	private static final long serialVersionUID = 5275681344501160650L;

	/**
	 * Crée l'exception
	 */
	public DataFichierErroneeException() {
	    super("Données dans fichier dataInput sont incorrectes");
	}
	
	/**
	 * Crée l'exception avec un message d'erreur
	 * @param s	message d'erreur
	 */
	public DataFichierErroneeException(String s) {
	    super(s);
	}

}