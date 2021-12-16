package pirates;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe qui interagit avec l'utilisateur. Elle crée l'équipage de la partie 2
 */
public class Menu2 extends Menu {
	
	/**
	 * Fichier qui contient les pirates qui se détestent et les listes de préferences de butins. Il sera lu pour constituer l'équipage
	 */
	private FileReader dataInput;
	
	/**
	 * Fichier dans lequel on va écrire la solution cad l'attribution des butins entre les pirates
	 */
	private FileWriter dataOutput;
	
	/**
	 * Scanner pour les fichiers
	 */
	private static Scanner sc2;
	
	/**
	 * Contructeur qui lit les infos sur l'équpage dans un fichier
	 * @param fileName	Nom du fichier qui contient les données de l'équipage
	 * 
	 */
	public Menu2(String fileName) {
		super(true);
		dataInput = null;
		dataOutput = null;
		try {
			dataInput = new FileReader("files/" + fileName);
		} catch (IOException e) {
	   		 //e.printStackTrace();
	   		 System.out.println("Fichier indiqué non existant");
	   		 System.exit(1);
		}
		try {
			analyseData(parseData(".\n"));
		} catch (DataFichierErroneeException e) {
			//e.printStackTrace();
	   		 System.out.println("Données dans le fichier sont incorrectes");
	   		 System.exit(1);
		}
	}

	/**
	 * Separe le texte du fichier dataInput en des mots séparés par le délimiteur.
	 * @param str Delimiteur 
	 * @return Liste de mots
	 */
	public ArrayList<String> parseData(String str){
		ArrayList<String> mots = new ArrayList<String>();
		sc2 = new Scanner(dataInput);
		sc2.useDelimiter(str);
		while(sc2.hasNext()) {
			//trim enleve les espaces qui trainent
			mots.add(sc2.next().trim());
		}
		System.out.println(mots);
		return mots; 
	}
	
	/**
	 * Analyse les mots qui proviennent du fichier dataInput et crée un équipage selon les données du fichier
	 * @param tokens Mots
	 * @throws DataFichierErroneeException	Si les données du fichier sont incorrectes et le programme ne poura pas bien fonctionner
	 * @see #parseData(String)
	 */
	public void analyseData(ArrayList<String> tokens) throws DataFichierErroneeException {
		int indiceDeb,indiceFin;
		nbPirate=0;
		StringTokenizer st;
		String mot,mot2;
		Pirate p;
		Butin b;
		for (String token : tokens) {
			indiceDeb = token.indexOf('(')+1;
			indiceFin = token.indexOf(')');
			mot = token.substring(indiceDeb, indiceFin);
			mot = mot.trim();
			if(token.startsWith("pirate")) {
				equipage.ajoutPirate(new Pirate(mot));
				nbPirate++;
			}
			else if(token.startsWith("objet")) {
				equipage.ajoutButin(new Butin(mot));
			}
			else if(token.startsWith("deteste")) {
				st = new StringTokenizer(mot,",");
				if(st.countTokens()==2) {
					mot = st.nextToken();
					mot2 = st.nextToken();
					try {
						equipage.deteste(mot,mot2);
					} catch (SaisieErroneeException e) {
						throw new DataFichierErroneeException();
					}
				}
				//le noms des deux pirates est absent
				else {
					throw new DataFichierErroneeException();
				}

			}
			else if(token.startsWith("preferences")) {
				st = new StringTokenizer(mot,",");
				//on récupère le pirate grace au nom entré
				mot = st.nextToken();
				p = equipage.getPirateFromPirateName(mot);
				//si le pirate entré n'existe pas dans l'équipage
				if(p == null) {
					throw new DataFichierErroneeException("Pirate " + p + "n'existe pas");
				}
				//vérifie presence de tous les objets préférés
	 			if(st.countTokens() != (nbPirate)) {
	 				throw new DataFichierErroneeException("Liste de préference des butins incomplète");
	 			}
				//on recupère les trésors
				while(st.hasMoreTokens()) {
					mot = st.nextToken();
					b = equipage.getButinFromButinName(mot);
					if (b==null) {
						throw new DataFichierErroneeException("Butin " + mot + " n'existe pas");
					}
					p.addPreference(b);
				}
      		 	if(p.getPreference().size() != (nbPirate)) {
      	         	System.out.println("Liste de préférence incorrecte pour : "+p.getName());
      	         	System.exit(1);
      			 }
			}
		}
	}
	
	/**
	 * Affiche le menu de l'utilisateur
	 * 
	 */
	public void affiche() {
		System.out.println("\n*************************** Menu **************************");
		System.out.println("Rentrez un chiffre selon l'option choisie");
		System.out.println("1 : résolution automatique\n2 : résolution manuelle\n3 : sauvegarde\n4 : fin");
		System.out.print(">");
	}
	
	/**
	 * Méthode qui interagit avec l'utilisateur et lance une fonction du menu selon son choix
	 * 
	 */
	public void interfaceTextuelle() {
		int choix = -1;
		boolean butinsAttribues = false;
		File f = null;
		String ch;
		String butinAttribue;
		int nbEchanges;

		do {
			affiche();
			choix = saisieEntier();
			System.out.println();
			switch (choix) {
			case 1 : 
				equipage.displayEquipageDetail();
				System.out.println("Solution approximative");
				System.out.println("Entrez nombre d'itérations cad le nombre d'échanges de butins");
				nbEchanges = saisieEntier();
				equipage.approximerSolution(nbEchanges);
				butinsAttribues = true;
				System.out.println("\n***** partage des butins *****");
				equipage.displayPartage();
				System.out.println("\n***** cout partage *****");
				System.out.println(equipage.getCoutIteratif());
	
//				System.out.println("***** fauxPartage *****");
//				equipage.displayPartage(true);
//				System.out.println("***** cout fauxPartage *****");
//				System.out.println(equipage.getCoutIteratif(true));
				break;
			case 2 :
				if(!butinsAttribues) {
					interfaceTextuelle2();
					butinsAttribues = true;
				}
				interfaceTextuelle3();
				break;
			case 3 :
				System.out.println("Entrez nom du fichier de sauvegarde");
				ch = saisieMot();
				if(!ch.contains(".")) {
					ch = ch + ".txt";
				}
				try {
					f = new File("files/" + ch);
					dataOutput = new FileWriter(f);
				} catch (IOException e) {
					e.printStackTrace();
				}

				for (Pirate p : equipage.getPirates()) {
					if (equipage.getPartage().containsKey(p)) {
						butinAttribue = equipage.getPartage().get(p).getName();
						try {
							dataOutput.write(p.getName() + ":" + butinAttribue+"\n");
							dataOutput.flush();
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else {
						System.out.println("Le pirate " + p.getName() + " ne possède pas de trésor");
						butinsAttribues = false;
					}
				}
				if(!butinsAttribues) {
					System.out.println("\nChoisissez l'option 1 ou 2 pour attribuer les butins");
					//On supprime le fichier de sauvegarde vide
					f.delete();
				}
				else {
					System.out.println("\nSauvegarde réussie");
				}
				break;
			case 4 :
				System.out.println("Au revoir capitaine");
				break;
			default:
				System.out.println("Choix incorrect");
				break;
			}
		}while(choix!=4);
}

	@Override
	/**
	 * Ferme le scanner. Attention on ne peut plus ouvrir le flux System.in une fois que le scanner a été fermé
	 */
	public void fermerScanner() {
		sc.close();
		sc2.close();
	}
	
	/**
	 * Ferme tous les fichiers pour une terminaison propre du programme
	 * @see dataInput
	 * @see dataOutput
	 */
	public void fermerFichier() {
		try {
			if(dataInput!=null) {
				dataInput.close();
			}
			if(dataOutput!=null) {
				dataOutput.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}