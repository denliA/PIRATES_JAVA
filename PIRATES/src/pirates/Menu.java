package pirates;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe qui interagit avec l'utilisateur. Contient toutes les variables nécessaires pour créer un équipage
 */
public class Menu {
	/**
	 * Unique Scanner pour les I/O. On accède au scanner via les méthodes suivantes :
	 * @see #saisieEntier()
	 * @see #saisieLigne()
	 */
	protected static Scanner sc;
	
	/**
	 * Nombre de pirates dans l'équipage rentré par l'utilisateur. Correspond au nombre de trésors disponibles
	 */
	protected int nbPirate;
	
	/**
	 * Représente un équipage. Contient la liste de pirates, la liste de butins et un dictionnaire qui montre à qui les butins sont attribués
	 * @see Equipage
	 */
	protected Equipage equipage;
	
	/**
	 * Constructeur qui demande le nombre de pirates dans l'équipage
	 * 
	 */
	public Menu() {
		sc = new Scanner(System.in);
		System.out.println("Combien de pirates dans ton équipage ?");
		do {
			nbPirate = saisieEntier();
		}while (nbPirate < 1);
		equipage = new Equipage(nbPirate);
	}
	
	/**
	 * Constructeur utilisé dans Menu2 pour initaliser les attributs de la classe mère
	 * @param bool	Boolean pour distinguer ce constructeur
	 * @see Menu2#Menu2(String)
	 */
	public Menu(boolean bool) {
		if (bool) {
			sc = new Scanner(System.in);
			nbPirate = 0;
			equipage = new Equipage();
		}
	}
	
	/**
	 * Donne l'Equipage
	 * @return	Equipage
	 */
	public Equipage getEquipage(){
		return equipage;
	}
	
	/**
	 * Affiche le premier menu
	 * 
	 */
	public void affiche1() {
		System.out.println("************************** Menu 1 **************************");
		System.out.println("Rentrez un chiffre selon l'option choisie");
		System.out.println("1 : ajouter une relation\n2 : ajouter des préférences\n3 : Fin");
		System.out.print(">");
	}
	
	/**
	 * Affiche le deuxième menu
	 * 
	 */
	public void affiche2() {
		System.out.println("************************** Menu 2 **************************");
		System.out.println("Attribuer les trésors au hasard (façon équitable) ?");
    	System.out.println("0 : non\n1 : oui");
    	System.out.print(">");
	}
	
	/**
	 * Affiche le troisième menu
	 * 
	 */
	public void affiche3() {
		System.out.println("************************** Menu 3 **************************");
    	System.out.println("Rentrez un chiffre selon l'option choisie");
    	System.out.println("1 : échanger objets\n2 : afficher cout\n3 : Fin");
    	System.out.print(">");
	}
	
	/**
	 * Permet de vérifier que l'utilisateur saisit les bonnes valeurs lors du choix 1 du menu 1
	 * <p>
	 * Quand un mauvais String est entré au clavier, on lève une exception nommée SaisieErroneeException
	 * @see #interfaceTextuelle1()
	 * @see SaisieErroneeException
	 * 
	 */
	public void interfaceTextuelle1_1() {
		StringBuffer sb;
		String chaine1 = "", chaine2 = "";
		String regex;
		String lastPirate = Character.toString(64+nbPirate).toUpperCase();
		Character.toString(64+nbPirate);
		
		//On crée un regex dynamique selon nbPirate
		//On cherche des caractères qui vont de 'A' à le nom du dernier Pirate. On utilise le code ascii
		regex = "[a-";
		sb = new StringBuffer(regex);
		sb.append(Character.toString(64+nbPirate).toLowerCase());
		sb.append("A-");
		sb.append(Character.toString(64+nbPirate).toUpperCase());
		sb.append("]");
		regex = sb.toString();
		
		System.out.println("Entrez les deux pirates qui s'aiment pas");
		try {
			chaine1 = saisieMot();
			chaine2 = saisieMot();
			if(!chaine1.matches(regex)) {
				throw new SaisieErroneeException();
			}
			if(!chaine2.matches(regex)) {
				throw new SaisieErroneeException();
			}
			equipage.deteste(chaine1,chaine2);
		} catch (SaisieErroneeException e1) {
			System.out.println("Veuillez saisir deux lettres entre a et " + lastPirate.toLowerCase() + " ou A et " + lastPirate + "\n");
			interfaceTextuelle1_1();
			//e1.printStackTrace();
		}
		
	}
	
	/**
	 * Permet de vérifier que l'utilisateur saisit les bonnes valeurs lors du choix 2 du menu 1
	 * <p>
	 * Quand un mauvais String est entré au clavier, on lève une exception nommée SaisieErroneeException.
	 * Concernant les butins, pour s'assurer que les nombres entrés vont de 1 à nbPirates : on utilise une expression régulière
	 * @see #interfaceTextuelle1()
	 * @see SaisieErroneeException
	 * 
	 */	
	public void interfaceTextuelle1_2() {
    	String chaine = "";
    	Pirate p1 = null;
    	Butin k;
		String number = String.valueOf(nbPirate);
		String regex = "";
		StringBuffer sb;
		int cpt = 0;
		
		//On crée un regex dynamique selon nbPirate
		if(nbPirate<=9) {
			regex = "[1-";
			sb = new StringBuffer(regex);
			sb.append(number.charAt(0));
			sb.append("]");
			regex = sb.toString();
		}
		else if(nbPirate<=19) {
			regex = "([1-9]|1[0-";
			sb = new StringBuffer(regex);
			sb.append(number.charAt(1));
			sb.append("])");
			regex = sb.toString();
		}
		else if(nbPirate<=26) {
			regex = "([1-9]|1[0-9]|2[0-";
			sb = new StringBuffer(regex);
			sb.append(number.charAt(1));
			sb.append("])");
			regex = sb.toString();
		}
		
		System.out.println("Entrez le nom du pirate et tous ses objets préferés dans l'ordre décroissant séparés par un espace");
		try {
			//vérifie que le nom du pirate entré est correct (lettre de l'alphabet)
			chaine = saisieMot();
			if (!chaine.matches("[a-zA-Z]")) { //on compare le String à un regex
				throw new SaisieErroneeException();
			}
			//on récupère le pirate grace au nom entré
			p1 = equipage.getPirateFromPirateName(chaine);
			//si le pirate entré n'existe pas dans l'équipage
			if(p1 == null) {
				throw new SaisieErroneeException();
			}
			//si la liste de préférence a déjà été entrée
			if(p1.getPreference().size()==nbPirate) {
				System.out.println("Liste de préférence déjà remplie");
				return; //on n'ajoute rien
			}
			//si la liste de préférence a été mal entrée cad incomplète
			if((p1.getPreference().size()!=0) && (p1.getPreference().size()!=nbPirate)){
				System.out.println("Bug étrange. Continuez à saisir la liste de préférence dans l'option 2");
				p1.clearPreference();
				throw new SaisieErroneeException();
			}
			//on récupère la liste des préférences des butins
			while(cpt<nbPirate){
				//vérifie que le numéro de butin entré est correct (entre 1 et nbPirates)
				chaine = saisieMot();
				if (!chaine.matches(regex)) {
					System.out.println("Mauvais numéro de trésor entré (1 -> " + nbPirate + ")");
					throw new SaisieErroneeException();
				}
				k = equipage.getButinFromButinName("o" + chaine);
				if(k==null) {
					System.out.println("Trésor o" + chaine + " n'existe pas");
					throw new SaisieErroneeException();
				}
				p1.addPreference(k);
				cpt++;
		    }
			//On vérifie que la liste de préférence est complète
			if (p1.getPreference().size() != nbPirate) {
				p1.clearPreference();
				System.out.println("Liste incomplète");
				throw new SaisieErroneeException();
			}
		} catch (SaisieErroneeException e) {
			System.out.println("Saisie incorrecte\n");
			if (p1 != null) {
				p1.clearPreference();
			}
			interfaceTextuelle1_2();
			//e.printStackTrace();
		}
	}
	
	/**
	 * Permet de vérifier que l'utilisateur saisit les bonnes valeurs lors du choix 1 du menu 3
	 * <p>
	 * Quand un mauvais String est entré au clavier, on lève une exception nommée SaisieErroneeException
	 * @see #interfaceTextuelle3()
	 * @see SaisieErroneeException
	 * 
	 */
	public void interfaceTextuelle3_1() {
		try {
			System.out.println();
			equipage.echangerButin();
			equipage.displayPartage();
		} catch (SaisieErroneeException e) {
			System.out.println("Saisie incorrecte");
			interfaceTextuelle3_1();
			//e.printStackTrace();
		}
	}
	
	/**
	 * Méthode qui interagit avec l'utilisateur et lance une fonction du menu 1 selon son choix
	 * 
	 */
	public void interfaceTextuelle1() {
		boolean exit = false;
    	int choix = -1;
    	System.out.println();
    	do {
    		affiche1();
    		choix = saisieEntier();
    		System.out.println();
    		switch (choix) {
    		case 1 : 
    			interfaceTextuelle1_1();
    			break;
    		case 2 :
    			interfaceTextuelle1_2();
    			break;
    		case 3 :
    			if(equipage.verifPrefComplete()) {
    				System.out.println("Tous les pirates ont une liste de préférence complète");
    				exit = true;
    			}
    			break;
    		default:
    			System.out.println("\nChoix incorrect");
    			break;
    		}
    		equipage.displayEquipageDetail();
    	}while(!exit);
    	interfaceTextuelle2();
    	equipage.displayPartage();
    	interfaceTextuelle3();
    }
	
	/**
	 * Méthode qui interagit avec l'utilisateur et lance une fonction du menu 2 selon son choix
	 * 
	 */
	public void interfaceTextuelle2() {
    	int choix = -1;
    	boolean exit = false;
    	do {
    		affiche2();
    		choix = saisieEntier();
    		switch (choix) {
    		case 0 : 
    			equipage.attribuerButinAuto();
    			exit = true;
    			break;
    		case 1 :
    			equipage.attribuerButinAutoRandom();
    			exit = true;
    			break;
    		default:
    			System.out.println("\nChoix incorrect");
    			break;
    		}
    	}while(!exit);
    	System.out.println();
    }
	
	/**
	 * Méthode qui interagit avec l'utilisateur et lance une fonction du menu 3 selon son choix
	 * 
	 */
	public void interfaceTextuelle3() {
		boolean exit = false;
    	int choix = -1;
    	do {
    		affiche3();
    		choix = saisieEntier();
    		switch (choix) {
    		case 1 : 
    			interfaceTextuelle3_1();
    			break;
    		case 2 :
    			System.out.println("\nCout actuel : " + equipage.getCoutIteratif());
    			equipage.displayPartage();
    			break;
    		case 3 :
    			System.out.println("\nFin menu 3");
    			equipage.displayPartage();
    			exit=true;
    			break;
    		default:
    			System.out.println("\nChoix incorrect\n");
    			break;
    		}
    	}while(!exit);
    }
	
	/**
	 * Méthode du scanner pour saisir un mot (qui se finit par un espace)
	 * <p>
	 * @return	mot entré au clavier
	 */
	public static String saisieMot() {
		return sc.next();
	}
	
	/**
	 * Méthode du scanner pour saisir un entier
	 * <p>
	 * Lors d'un mélange de nextLine() et de nextInt(), il faut vider le buffer du scanner car il y a des retours chariots qui trainent.
	 * <p>
	 * Vérifie que l'utilisateur saisit bien un entier. Sinon, il lui demande de refaire la saisie.
	 * nextInt() peut lever l'exception InputMismatchException si la valeur saisie n'est pas un entier
	 * @return	Entier entré au clavier
	 * @see Scanner#nextInt()
	 */
	public static int saisieEntier(){
		boolean sortie = false;
		int number = -1;
		do {
			try {
				number = sc.nextInt();
				sortie = true;
			}catch(InputMismatchException e) {
				System.out.println("Attention entrez un entier");
				sc.nextLine(); //nettoie le buffer du scanner (retour chatiot qui traine)
			}
		}while(!sortie);
		return number;
	}
	
	/**
	 * Méthode du scanner pour voir si il reste un element à lire
	 * <p>
	 * Attention : méthode bloquante tant qu'il y a pas de token saisi
	 * @return	vrai si un token a été saisi
	 * @see Scanner#hasNext()
	 */
	public static boolean hasNext() {
		return sc.hasNext();
	}
	
	/**
	 * Ferme le scanner. Attention on ne peut plus ouvrir le flux System.in une fois que le scanner a été fermé
	 */
	public void fermerScanner() {
		sc.close();
	}

}
