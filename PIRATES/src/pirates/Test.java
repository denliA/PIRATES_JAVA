package pirates;

/**
 * Classe depuis laquelle on lance le programme
 */
public class Test {
	/**
	 * Méthode main
	 * @param args	tableau des arguments qui sont des String
	 */
	public static void main(String[] args) {
		String fileName = args[0];
//		Menu m1 = new Menu();
//		m1.interfaceTextuelle1();
		Menu2 m = new Menu2(fileName);
		m.interfaceTextuelle();
		m.fermerScanner();
		m.fermerFichier();
	}

}
