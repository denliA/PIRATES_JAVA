package pirates;
import java.util.ArrayList;

/**
 * Classe qui représente un pirate de l'équipage
 */
public class Pirate {
	/**
	 * Nom du pirate. Charactère de A à Z (26 pirates max)
	 * <p>
	 * Attribué automatiquement en incrémentant le compteur. Convertit le code ascii en lettre majuscule
	 */
	private String  name ;
	
	/**
	 * Liste de pirates détestés. Si A déteste B, alors B déteste A (haine réciproque)
	 */
	private ArrayList <Pirate> hating;
	
	/**
	 * Liste de preference de butins dans l'ordre décroissant
	 */
	private ArrayList <Butin> preference;
	
	/**
	 * Compteur qui est incrémenté à chaque nouvel Pirate crée. Sert à l'attribution automatique d'un nom de pirates en converstissant le code ascii
	 * @see #Pirate()
	 */
	static private int compteur;
	
	/**
	 * Boolean est vrai si le pirate est jaloux d'un ou plusieurs autres pirates
	 */
	private boolean jealous;
	
	/**
	 * Crée un pirate dont le nom va de A à Z
	 *
	 */
	public Pirate() {
		compteur++;
		this.name = Character.toString(64+compteur);
		this.hating = new ArrayList<Pirate>();
		this.preference = new ArrayList<Butin>();
		this.jealous = false;
	}
	
	/**
	 * Crée un pirate dont le nom est passé en paramètres
	 * @param name Nom du Pirate
	 */
	public Pirate(String name) {
		this.name = name;
		this.hating = new ArrayList<Pirate>();
		this.preference = new ArrayList<Butin>();
		this.jealous = false;
	}
	
	/**
	 * Donne le nom du pirate
	 * @return	Nom du pirate
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Donne la liste de pirates détestés
	 * @return	Liste de pirates détestés
	 */
	public ArrayList<Pirate> getHating() {
		return this.hating;
	}
	
	/**
	 * Donne la liste de préference des butins
	 * @return	Liste de préference des butins
	 */
	public ArrayList<Butin> getPreference() {
		return preference;
	}
	
	/**
	 * Donne le boolean jealous
	 * @return	Boolean qui indique si ce pirate est jaloux
	 */
	public boolean getJealous() {
		return jealous;
	}
	
	/**
	 * Modifie le boolean jealous
	 * @param jealous	Boolean qui indique si ce pirate est jaloux
	 */
	public void setJealous(boolean jealous) {
		this.jealous = jealous;
	}
	
	/**
	 * Donne les noms de pirates détestés
	 * @return	String qui représente une liste de pirates détestés
	 */
	public String showHating() {
		StringBuffer ch = new StringBuffer();
		ch.append("[");
	    for(int i=0;i<hating.size();i++) {
	    	ch.append(hating.get(i).getName());
	    	//pas de virgule pour le dernier élement
	    	if(i!=hating.size()-1) {
	    		ch.append(",");
	    	}
	    }
	    ch.append("]");
		return ch.toString();
	}

	/**
	 * Donne les noms des trésors préférés
	 * @return	String qui représente une liste de préférence des butins
	 */
	public String showPref() {
		StringBuffer ch = new StringBuffer();
		ch.append("[");
	    for(int i=0;i<preference.size();i++) {
	    	ch.append(preference.get(i).getName());
	    	if(i!=preference.size()-1) {
	    		ch.append(",");
	    	}
	    }
	    ch.append("]");
		return ch.toString();
	}
	
	/**
	 * Modifie la liste de pirates détestés en remplacant par la liste passée en paramètres
	 * @param hating	Liste de pirates que ce pirate déteste
	 */
	public void setHating(ArrayList<Pirate> hating) {
		this.hating = hating;
	}
	
	/**
	 * Modifie la liste de préférence des trésors en remplacant par la liste passée en paramètres
	 * @param preference	Liste de preference de butins dans l'ordre décroissant
	 */
	public void setButin(ArrayList<Butin> preference) {
		this.preference = preference;
	}
	
	/**
	 * Modifie le nom du pirate
	 * @param name	Nom du pirate
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Ajoute un Pirate à la liste de pirates détestés
	 * @param a	Pirate à ajouter
	 */
	public void addHating(Pirate a) {
		//On ajoute pas si le pirate est déjà présent dans la liste de hating
		if(hating.contains(a)) {
			System.out.println("Pirate " + a.getName() + " déjà contenu dans la liste de hating");
			return;
		}
		//On se déteste pas soi meme
		if ((a instanceof Pirate) && (!this.equals(a))){
			hating.add(a);
			a.getHating().add(this);
		}
	}
	
	/**
	 * Ajoute un Butin à la liste de préférence des trésors
	 * <p>
	 * Cette liste a un ordre décroissant.
	 * Donc, l'indice d'ajout est important
	 * @param i	Indice de l'élement à insérer dans la liste
	 * @param b	Butin à ajouter
	 * 
	 */
	public void addPreference(int i,Butin b) {
		//on ajoute pas si la liste contient déjà ce butin
		if ((b instanceof Butin) && (!preference.contains(b))) {
			preference.add(i,b);
		}
	}
	
	/**	
	 * Ajoute un Butin à la liste de préférence des trésors.
	 * Cette liste a un ordre décroissant
	 * <p>
	 * Surcharge de la méthode suivante :
	 * @param b	Butin à ajouter
	 * @see #addPreference(int,Butin)
	 * 
	 */
	public void addPreference(Butin b) {
		//on ajoute pas si la liste contient déjà ce butin
		if ((b instanceof Butin) && (!preference.contains(b))) {
			preference.add(b);
		}
	}
	
	/**
	 * Enlève un Pirate de la liste de pirates détestés
	 * @param a Pirate à supprimer
	 */
	public void removeHating(Pirate a) {
		hating.remove(a);
	}
	
	/**
	 * Enlève un Butin de liste de préférence des trésors
	 * @param b	Butin à supprimer
	 */
	public void removePreference(Butin b) {
		preference.remove(b);
	}
	
	/**
	 * Nettoie la liste de préférence des trésors
	 * <p>
	 * Utile pour que cette liste devienne vide en cas de mauvaise saisie
	 * @see Menu#interfaceTextuelle1_2()
	 * 
	 */
	public void clearPreference() {
		for(int i=0; i<preference.size(); i++) {
			preference.remove(preference.get(i));
		}
	}
	
	/**
	 * Redéfinition de la méthode toString() qui represente un Pirate
	 * @return	String qui représente un Pirate
	 */
	@Override
	public String toString() {
		return "Nom : " + getName() + "\nOrdre de préférence : " + showPref() + "\nPirate(s) détesté(s) : " + showHating();
	}
	
	/**
	 * Test si le Pirate courant déteste le Pirate passé en paramètres. Cette relation de haine est réciproque
	 * @param b	Pirate
	 * @return	Boolean vrai si les deux pirates se détestent
	 * 
	 */
	public boolean hates(Pirate b) {
		if(this.hating.contains(b)) {
			return true;
		}
		return false;
	}
}