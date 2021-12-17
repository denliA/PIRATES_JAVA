package pirates;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe qui représente un équipage
 * <p>
 * Contient la liste de pirates, la liste de butins et un dictionnaire qui montre à qui les butins sont attribués
 * <p>
 * Nombre de butins = nombre de pirates. Chaque Butin est unique
 * 
 */
public class Equipage {
	/**
	 * Liste de pirates de l'équipage
	 */
	private ArrayList<Pirate> pirates;
	
	/**
	 * Liste de butins cad de trésors de l'équipage. Il y a autant de butins que de pirates
	 */
	private ArrayList<Butin> butins;
	
	/**
	 * Dictionnaire qui represénte les attributions des trésors
	 * <p> 
	 * Pirate (clé) = Butin (valeur)
	 */
	public HashMap<Pirate, Butin> partage;
	
	/**
	 * Dictionnaire qui represénte une attribution des trésors alternative.
	 * Elle est uniquement utilisée pour approximer une solution optimale
	 * <p>
	 * Pirate (clé) = Butin (valeur)
	 * @see #approximerSolution(int)
	 */
	public HashMap<Pirate, Butin> fauxPartage;
	
	/**
	 * Fausse liste de pirates de l'équipage. Elle sera décrementée par les fonctions suivantes :
	 * @see Equipage#attribuerButinAutoRandom()
	 * @see Equipage#getCoutRecursif()
	 */
	private ArrayList<Pirate> fauxEquipage; 
	
	/**
	 * Crée un équipage avec le nombres de pirates spécifié
	 * @param n	Nombre de pirates
	 * 
	 */
	public Equipage(int n) {
		pirates = new ArrayList<Pirate>(n);
		butins = new ArrayList<Butin>(n);
		partage = new HashMap<Pirate, Butin>(n);
		for (int i = 1; i <= n; i++) {
			pirates.add(new Pirate());
			butins.add(new Butin("o" + i));
		}
	}
	
	/**
	 * Crée un équipage vide
	 * 
	 */
	public Equipage() {
		pirates = new ArrayList<Pirate>();
		butins = new ArrayList<Butin>();
		partage = new HashMap<Pirate, Butin>();
	}
	
	/**
	 * Donne l'attribution des butins parmi les pirates de l'équipage
	 * @return dictionnaire dans lequel clé=pirate et valeur=butin
	 */
	public HashMap<Pirate, Butin> getPartage() {
		return partage;
	}
	
	/**
	 * Donne la liste des pirates de l'équipage
	 * @return pirates
	 */
	public ArrayList<Pirate> getPirates() {
		return pirates;
	}
	
	/**
	 * Donne la liste des trésors de l'équipage
	 * @return butins
	 */
	public ArrayList<Butin> getButins() {
		return butins;
	}
	
	/**
	 * Ajoute un pirate dans l'équipage
	 * @param p	Pirate
	 * @throws DataFichierErroneeException 
	 */
	public void ajoutPirate(Pirate p) {
		pirates.add(p);
	}
	
	/**
	 * Ajoute un trésor dans l'équipage
	 * @param b	Butin
	 */
	public void ajoutButin(Butin b) {
		butins.add(b);
	}
	
	/**
	 * Rajoute deux pirates qui s'aiment pas dans leur liste de hating
	 * @param a Pirate
	 * @param b autre Pirate
	 */
	public void deteste(String s1,String s2) throws SaisieErroneeException {
		Pirate a = getPirateFromPirateName(s1);
		Pirate b = getPirateFromPirateName(s2);
		if((a==null) || (b==null)) {
			throw new SaisieErroneeException("Pirates n'existent pas");
		}
		if (a.equals(b)) {
			throw new SaisieErroneeException("Pas de self hate svp");
		}
		a.addHating(b);
		b.addHating(a);
	}
	
	/**
	 * Donne un Pirate à partir de son nom
	 * @param name	Nom du Pirate recherché
	 * @return Pirate trouvé
	 * 
	 */
	public Pirate getPirateFromPirateName(String name) {
		for (Pirate p : pirates) {
			if (p.getName().equals(name)) {
				return p;
			}
		}
		System.out.println("Le pirate " + name + " n'existe pas dans l'équipage");
		return null;
	}
	
	/**
	 * Donne un Butin à partir du nom du Pirate qui le détient
	 * @param name	Nom du Pirate qui possède le trésor
	 * @param bool Vrai si on utilise fauxPartage
	 * @return Butin trouvé
	 * @see #fauxPartage
	 * 
	 */
	public Butin getButinFromPirateName(String name,boolean bool) {
		HashMap<Pirate,Butin> dict;
		if(bool) {
			dict = fauxPartage;
		}
		else {
			dict = partage;
		}
		Pirate p = getPirateFromPirateName(name);
		if(dict.containsKey(p)) {
			return dict.get(p);
		}
		System.out.println("Le butin de " + name + " n'existe pas");
		return null;
	}
	
	/**
	 * Surcharge
	 * Donne un Butin à partir du nom du Pirate qui le détient
	 * @param name	Nom du Pirate qui possède le trésor
	 * @return Butin trouvé
	 * 
	 */
	public Butin getButinFromPirateName(String name) {
		return getButinFromPirateName(name,false);
	}
	
	/** 
	 * Donne un Butin à partir de son nom. Attention : les noms des trésors vont de "o1" à "o26"
	 * @param name	Nom du trésor recherché
	 * @return Butin trouvé
	 * 
	 */
	public Butin getButinFromButinName(String name) {
		for (Butin b : butins) {
			if (b.getName().equals(name)) {
				return b;
			}
		}
		//System.out.println("Le butin " + name + " n'existe pas dans l'équipage");
		return null;
	} 
	
	/**
	 * Affiche tous les pirates de l'équipage sous forme d'une liste. Montre uniquement le nom du pirate
	 * 
	 */
	public void displayEquipage() {
		System.out.println("\n-------------------- Pirates --------------------");
		System.out.print("[");
		for (int i = 0; i < pirates.size(); i++) {
			System.out.print(pirates.get(i).getName());
			if(i!=pirates.size()-1) {
				System.out.print(",");
			}
		}
		System.out.print("]\n");
	}
	
	/**
	 * Affiche tous les pirates de l'équipage en détail. Montre le nom du pirate, sa liste de préférénce des butins et le trésor qu'il possède
	 * 
	 */
	public void displayEquipageDetail() {
		System.out.println("\n-------------------- Pirates --------------------");
		for (int i = 0; i < pirates.size(); i++) {
			System.out.println(pirates.get(i).toString());
			System.out.println("----------");
		}
		System.out.println();
	}
	
	/**
	 * Affcihe le nom du pirate et le butin qu'il détient. On affiche "rien" si le trésor n'a pas été attribué
	 * @param p	Pirate
	 * 
	 */
	public void displayButin(Pirate p) {
		String butinAttribue = "rien";
		if (partage.containsKey(p)) {
			butinAttribue = partage.get(p).getName();
		}
		System.out.println(p.getName() + ":" + butinAttribue);
		System.out.println();
	}
	
	/**
	 * Surcharge
	 * Affiche tous les pirates de l'équipage et le butin que chacun détient. On affiche "rien" si le trésor n'a pas été attribué
	 * 
	 */
	public void displayPartage() {
		displayPartage(false);
	}
	

	/**
	 * Affiche tous les pirates de l'équipage et le butin que chacun détient. On affiche "rien" si le trésor n'a pas été attribué
	 * @param bool	vrai si fauxPartage utilisé
	 * @see Equipage#fauxPartage
	 * 
	 */
	public void displayPartage(boolean bool) {
		HashMap<Pirate,Butin> dict;
		if(bool) {
			dict = fauxPartage;
		}
		else {
			dict = partage;
		}
		System.out.println("\n---------------- Butins attribués ----------------");
		String butinAttribue = "rien";
		for (Pirate p : pirates) {
			if (dict.containsKey(p)) {
				butinAttribue = dict.get(p).getName();
			}
			System.out.println(p.getName() + ":" + butinAttribue);
		}
		System.out.println();
	}
	
	/**
	 * Attribue un trésor à un Pirate
	 * @param p Pirate
	 * @param b	Butin
	 * 
	 */
	public void attribuerButin(Pirate p, Butin b) {
		partage.put(p, b);
	}
	
	/**
	 * Supprime le trésor attribué au Pirate
	 * @param p	Pirate
	 * 
	 */
	public void supprimerButin(Pirate p) {
		partage.remove(p);
	}

	/**
	 * Supprime toutes les clés du dictionnaire qui contient le nom des pirates et le butin attribué à chacun
	 *
	 */
	public void supprimerPartage() {
		partage.clear();
	}

	/** 
	 * Donne le Butin détenu par un Pirate
	 * @param p	Pirate
	 * @return Tresor attribué à ce Pirate
	 * 
	 */
	public Butin getButin(Pirate p) {
		return partage.get(p);
	}
	
	/**
	 * Donne le propriétaire d'un Butin
	 * @param b	Butin
	 * @return Pirate qui détient le Butin
	 * 
	 */
	public Pirate getOwner(Butin b) {
		if (partage.containsValue(b)) {
			// iterer sur les clés
			for (Pirate i : partage.keySet()) {
				if (partage.get(i).equals(b)) {
					return i;
				}
			}
		}
		return null;
	}
	
	/** Echange le butin détenu entre deux Pirates entrés par l'utilisateur
	 * <p>
	 * Lève une exception quand les pirates entrés n'existent pas dans l'équipage
	 * <p>
	 * 
	 * @param p1	Pirate
	 * @param p2	Pirate
	 * @param bool	vrai si fauxPartage utilisé pour approximer une solution optimale
	 * @throws	SaisieErroneeException	Quand utilisateur rentre un mauvais String au clavier
	 * @see	SaisieErroneeException
	 * @see #fauxPartage
	 * @see #approximerSolution(int)
	 */
	
	public void echangerButin(Pirate p1,Pirate p2,boolean bool) throws SaisieErroneeException {
		HashMap<Pirate,Butin> dict;
		if(bool) {
			dict = fauxPartage;
		}
		else {
			dict = partage;
		}
		if (!dict.containsKey(p1)) {
			throw new SaisieErroneeException("Pas de trésor attribué pour le pirate " + p1.getName());
		}
		if (!dict.containsKey(p2)) {
			throw new SaisieErroneeException("Pas de trésor attribué pour le pirate " + p2.getName());
		}
		// echange de butins
		Butin tampon = dict.get(p1);
		dict.replace(p1, dict.get(p2));
		dict.replace(p2, tampon);
	}

	/**
	 * Surcharge
	 * @param p1	Pirate
	 * @param p2	Pirate
	 * @throws	SaisieErroneeException	Quand utilisateur rentre un mauvais String au clavier
	 * @see	SaisieErroneeException
	 * 
	 */
	public void echangerButin(Pirate p1,Pirate p2) throws SaisieErroneeException {
		echangerButin(p1,p2,false);
	}
	
	/**
	 * Surcharge
	 * @throws	SaisieErroneeException	Quand utilisateur rentre un mauvais String au clavier
	 * @see	SaisieErroneeException
	 * 
	 */
	public void echangerButin() throws SaisieErroneeException{
		Pirate p1 = null;
		Pirate p2 = null;
		String a,b;

		do {
			System.out.println("Entrez le nom des pirates dont on veut échanger les objets");
			a = Menu.saisieMot();
			b = Menu.saisieMot();

			// On vérifie que les pirates entrés existent dans l'équipage
			for (int i = 0; i < pirates.size(); i++) {
				if (pirates.get(i).getName().equalsIgnoreCase(a)) {
					p1 = pirates.get(i);
				} else if (pirates.get(i).getName().equalsIgnoreCase(b)) {
					p2 = pirates.get(i);
				}
			}
			if (p1 == null) {
				throw new SaisieErroneeException("Le pirate " + a + " n'existe pas dans l'équipage");
			}
			if (p2 == null) {
				throw new SaisieErroneeException("Le pirate " + b + " n'existe pas dans l'équipage");
			}
		} while ((p1 == null) || (p2 == null));

		echangerButin(p1,p2);
	}

	/** 
	 * Donne une liste de pirates qui n'ont pas défini leur préférence de trésors
	 * @return	Liste de noms de pirates qui n'ont pas une liste de préférence de butins complète
	 * 
	 */
	public ArrayList<String> listePirateSansPref() {
		ArrayList<String> nomPirateSansPref = new ArrayList<String>();

		for (Pirate p : pirates) {
			// Si tous les pirates n'ont pas une liste de préférence pour tous les butins
			if (p.getPreference().size() != pirates.size()) {
				nomPirateSansPref.add(p.getName());
			}
		}
		return nomPirateSansPref;
	}
	
	/**
	 * Test si si tous les pirates ont défini leur préférence de trésors
	 * @return	Boolean vrai si tous les pirates ont une liste de préférence de butins complète
	 * 
	 */
	public boolean verifPrefComplete() {
		ArrayList<String> nomPiratesSansPref = listePirateSansPref();
		if (nomPiratesSansPref.size()==0) {
			return true;
		}
		//si la liste de pirates sans pref n'est pas vide
		System.out.print("Attention, ces pirates ne possèdent pas une liste de préférences complète : ");
		System.out.println(nomPiratesSansPref.toString());
		System.out.println("Chosissez l'option 2 pour rentrer des préferences de butins");
		return false;
	}
	
	/**
	 * Surcharge
	 * Test si le Pirate a est jaloux du Pirate b
	 * @param a Pirate qui est jaloux
	 * @param b Pirate dont on est jaloux
	 * @return Boolean qui indique si un Pirate est jaloux d'un autre Pirate
	 * @see #jealous(Pirate, Pirate, boolean)
	 * 
	 */
	public boolean jealous(Pirate a,Pirate b) {
		return jealous(a,b,false);
	}
	
	/**
	 * Test si le Pirate a est jaloux du Pirate b
	 * @param a Pirate qui est jaloux
	 * @param b Pirate dont on est jaloux
	 * @param bool Vrai si on utilise fauxPartage pour approximer une solution optimale
	 * @return Boolean qui indique si un Pirate est jaloux d'un autre Pirate
	 * 
	 */
	public boolean jealous(Pirate a,Pirate b,boolean bool) {
		boolean res = false;
		if (!a.hates(b)) { //si les deux pirates ne se détestent pas
			return false;
		}
		//indices du Butin attribué dans la liste de préférence des pirates
		int indiceA = -1;
		int indiceB = -1;
		//on récupère les butins que les deux pirates possèdent
		Butin oA = getButinFromPirateName(a.getName(),bool);
		Butin oB = getButinFromPirateName(b.getName(),bool);
		//on parcourt tous les trésors
		for (int i=0;i<butins.size();i++) {
			if(a.getPreference().get(i).equals(oA)){
				indiceA = i;
			}
			if(a.getPreference().get(i).equals(oB)){
				indiceB = i;
			}
		}
		//System.out.println(a.getName() + ":" + indiceA1 + "\t" + b.getName() + ":" + indiceB1);
		if ((indiceA==-1) || (indiceB==-1)){
			//TODO EXCEPTION car butin attribué pas dans la liste de preference
		}
		else if(indiceB < indiceA) {
			res = true;
		}
		return res;
	}
	
	/**
	 * Met l'attribut jealous de tous les pirates à faux. Evite de corrompre les données à chaque nouvel appel de getCoutIteratif()
	 * @see Pirate
	 * @see #getCoutIteratif()
	 * 
	 */
	public void initialiseJealousy() {
		for (Pirate p : pirates) {
			if (p.getJealous()) {
				p.setJealous(false);
			}
		}
	}
	
	/**
	 * Surcharge
	 * @see #evaluateJealousy(boolean)
	 * 
	 */
	public void evaluateJealousy() {
		evaluateJealousy(false);
	}
	
	/**
	 * Modifie la valeur de l'attribut jealous de tous les pirates de l'équipage
	 * <p>
	 * Si un Pirate est jaloux de plusieurs autres pirates, il ne compte qu'une fois dans getCoutIteratif()
	 * @see #getCoutIteratif()
	 * 
	 */
	public void evaluateJealousy(boolean bool) {
		int i,j;
		//On parcourt tous les pirates de l'équipage
		for (i=0; i<pirates.size(); i++) {
			if (pirates.get(i).getJealous()) {
				continue; //optimisation : si le pirate courant est déjà jaloux d'un autre pirate, on ne le considère pas
			}
			//On parcourt les voisins qu'il déteste
			for (j=0; j<pirates.get(i).getHating().size(); j++) {
				//si le pirate courant est jaloux de son voisin d'indice j
				if (jealous(pirates.get(i), pirates.get(i).getHating().get(j),bool)) {
					pirates.get(i).setJealous(true);
					break; //optimisation : dés que le pirate courant est jaloux d'un pirate, on ne regarde pas s'il est jaloux de ses autres voisins
				}
				//si le pirate d'indice j est jaloux du pirate courant
				if(jealous(pirates.get(i).getHating().get(j), pirates.get(i),bool)) {
					pirates.get(i).getHating().get(j).setJealous(true);
				}
				
			}
		}
	}
	
	/** 
	 * Surcharge
	 * Donne le cout de l'attribution actuelle des Butins
	 * @return	Nombre de pirates jaloux dans l'équipage
	 * @see #getCoutIteratif(boolean)
	 * 
	 */
	public int getCoutIteratif() {
		return getCoutIteratif(false);
	}
	
	/** 
	 * Donne le cout de l'attribution actuelle des Butins
	 * @param bool Vrai si on utilise fauxPartage
	 * @return	Nombre de pirates jaloux dans l'équipage
	 * @see #fauxPartage
	 * 
	 */
	public int getCoutIteratif(boolean bool) {
		initialiseJealousy();
		evaluateJealousy(bool);
		int somme = 0;
		for(Pirate p : pirates) {
			//System.out.println(p.getName() + ":" + p.getJealous());
			if (p.getJealous()) {
				somme += 1;
			}
		}
		return somme;
	}
	
	/**
	 * Donne le cout de l'attribution actuelle des butins
	 * <p>
	 * On imite le fonctionnement d'une fonction récursive
	 * La liste des pirates diminue à chaque appel de getCout()
	 * @return Entier qui est le cout. Représente le nombre de pirates jaloux
	 * @deprecated	NE FONCTIONNE PAS CAR SI ON ENLEVE P, ON PARCOURT PLUS SES VOISINS
	 * @see #getCoutIteratif() A utiliser cette méthode qui marche
	 * @see #fauxEquipage
	 */
	public int getCoutRecursif() {
		boolean b1 = false;
		boolean b2 = false;
		if ((fauxEquipage.size() == 0) || (fauxEquipage.size() == 1)) {
			return 0;
		}
		Pirate p = fauxEquipage.get(0);
		//on parcourt les pirates que p déteste
		for (Pirate q : p.getHating()) {
			if (fauxEquipage.contains(q)) {
				if ((b1 = jealous(p,q)) || (b2 = jealous(q,p))) {
					if(b1&&b2) {
						fauxEquipage.remove(p);
						fauxEquipage.remove(q);
						return getCoutRecursif() + 2;
					}
					if (b1) {
						fauxEquipage.remove(p);
						return getCoutRecursif() + 1;
					}
					if (b2) {
						fauxEquipage.remove(q);
						return getCoutRecursif() + 1;
					}
				}
			}
		}
		fauxEquipage.remove(p);
		return getCoutRecursif();
	}
	
	
	/**
	 * Initialise la liste qui sera decrementée par la suite
	 * <p>
	 * Au départ, cette liste contient tous les pirates de l'équipage. Ensuite, on enlève le pirate qui n'est plus disponible
	 * Cette liste factice est utilisée par les méthodes suivantes :
	 * @see #attribuerButinAutoRandom()
	 * @see #getCoutRecursif()
	 * 
	 */
	public void initialiserFauxEquipage() {
		fauxEquipage = new ArrayList<>(pirates);
	}
	
	/**
	 * Attribue un Butin à chaque Pirate de l'équipage
	 * <p>
	 * Vu qu'on utilise un indice random dans la liste des pirates, aucun Pirate n'est priviligié
	 * Donc, le partage des trésors est équitable
	 * @see #fauxEquipage	Liste qui diminue à chaque fois qu'un Pirate n'est plus disponible
	 * 
	 */
	public void attribuerButinAutoRandom() {
		initialiserFauxEquipage();
		Pirate pirateChoisi;
		int indiceRandom;
		for (int i=0;i<pirates.size();i++) {
			//On choisit un pirate au hasard parmi les pirates de l'équipage qui n'ont pas de trésor
			indiceRandom = (int) Math.floor(Math.random()*(fauxEquipage.size()));
			pirateChoisi = fauxEquipage.get(indiceRandom);
			//On enlève le pirate de la liste de pirates sans trésor
			fauxEquipage.remove(pirateChoisi);
			//On lui attribue le premier trésor disponible parmi sa liste de préférence
			for (Butin b : pirateChoisi.getPreference()) {
				//Clé n'existe pas -> butin pas attribué
				if(!partage.containsValue(b)) {
					partage.put(pirateChoisi, b);
					break;
				}
			}
		}
	}
	
	/**
	 * Attribue un Butin à chaque Pirate de l'équipage
	 * <p>
	 * Vu qu'on utilise un iterator.next(), les pirates au début de la liste sont priviligiés
	 * Donc, le partage des trésors n'est pas équitable
	 * 
	 */
	public void attribuerButinAuto() {
		//On parcourt les pirates de l'équipage dans l'ordre
		for (Pirate p : pirates) {
			//On lui attribue le premier trésor disponible parmi sa liste de préférence
			for (Butin b : p.getPreference()) {
				//Clé n'existe pas -> butin pas attribué
				if(!partage.containsValue(b)) {
					partage.put(p, b);
					break;
				}
			}
		}
	}
	
	/**
	 * Approche une meilleure attribution de butins via une méthode naive. Attention : ce n'est pas forcément la solution optimale
	 * @param k	indice pour la boucle qui correspond au nombre d'echanges de butins
	 * @see #echangerButin(Pirate, Pirate, HashMap)
	 */
	public void approximerSolution(int k) {
		Pirate a = null;
		Pirate b = null;
		int i = 0;
		int indiceRandom;
		
		//Solution naive
		attribuerButinAuto();
		fauxPartage = new HashMap<Pirate,Butin>(partage);
		
		while(i<k) {
			//On choisit un pirate au hasard parmi les pirates de l'équipage
			indiceRandom = (int) Math.floor(Math.random()*(pirates.size()));
			a = pirates.get(indiceRandom);
			//On choisit un pirate au hasard parmi les pirates voisins de a (ceux qu'ils déteste)
			indiceRandom = (int) Math.floor(Math.random()*(a.getHating().size()));
			b = a.getHating().get(indiceRandom);
			try {
				//on modifie le dictionnaire fauxPartage
				echangerButin(a,b,true);
			} catch (SaisieErroneeException e) {
				e.printStackTrace();
			}
			if(getCoutIteratif()>getCoutIteratif(true)) {
				partage = new HashMap<Pirate,Butin>(fauxPartage);
			}
			i++;
		}
	}
	
	/**
	 * Approche une meilleure attribution de butins via une méthode qui choisit un pirate au hasard puis échange les butins avec tous ses ennemis. Attention : ce n'est pas forcément la solution optimale
	 * @param k	indice pour la boucle. Nombre d'échanges = k * nombre de voisins du pirate choisi
	 * @see #echangerButin(Pirate, Pirate, HashMap)
	 */
	public void approximerSolution2(int k) {
		Pirate a = null;
		int i = 0;
		int indiceRandom;
		
		//Solution naive
		attribuerButinAuto();
		fauxPartage = new HashMap<Pirate,Butin>(partage);
		
		while(i<k) {
			//On choisit un pirate au hasard parmi les pirates de l'équipage
			indiceRandom = (int) Math.floor(Math.random()*(pirates.size()));
			a = pirates.get(indiceRandom);
			//On parcourt tous les pirates qu'il déteste
			for (Pirate b : a.getHating()) {
				try {
					//on modifie le dictionnaire fauxPartage
					echangerButin(a,b,true);
				} catch (SaisieErroneeException e) {
					e.printStackTrace();
				}
				if(getCoutIteratif()>getCoutIteratif(true)) {
					partage = new HashMap<Pirate,Butin>(fauxPartage);
				}
			}
			i++;
		}
	}

}