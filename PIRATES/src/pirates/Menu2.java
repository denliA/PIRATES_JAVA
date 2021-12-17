package pirates;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.File;
import java.io.FileNotFoundException;
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
	private static Scanner myReader;
	
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
			System.out.println(e.getMessage());
			//System.out.println("Fichier indiqué non existant");
			System.exit(1);
		}
		try {
			//analyseData(parseData(".\n"));
			lireFichier("files/" + fileName);
		} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
		} catch (DataFichierErroneeException e) {
			System.out.println(e.getMessage());
			System.out.println("Données dans le fichier sont incorrectes");
			System.exit(1);
		}
	}
	
	/**
	 * Separe le texte du fichier dataInput en des mots séparés par le délimiteur.
	 * @param str Delimiteur 
	 * @return Liste de mots
	 * @deprecated car ne permet pas de vérifier qu'une ligne respecte la syntaxe demandée vu qu'on découpe pas par ligne
	 */
	public ArrayList<String> parseData(String str){
		ArrayList<String> mots = new ArrayList<String>();
		myReader = new Scanner(dataInput);
		myReader.useDelimiter(str);
		while(myReader.hasNext()) {
			//trim enleve les espaces qui trainent
			mots.add(myReader.next().trim());
		}
		return mots; 
	}
	
	/**
	 * Analyse les mots qui proviennent du fichier dataInput et crée un équipage selon les données du fichier
	 * @param tokens Mots
	 * @throws DataFichierErroneeException	Si les données du fichier sont incorrectes et le programme ne poura pas bien fonctionner
	 * @see #parseData(String)
	 * @deprecated car les exceptions sont difficiles à gérer
	 */
	public void analyseData(ArrayList<String> tokens) throws DataFichierErroneeException {
		int indiceDeb,indiceFin;
		int nbButin=0;
		nbPirate=0;
		StringTokenizer st;
		String mot,mot2;
		Pirate p = null;;
		Butin b = null;
		for (String token : tokens) {
			indiceDeb = token.indexOf('(')+1;
			indiceFin = token.indexOf(')');
			try {
				mot = token.substring(indiceDeb, indiceFin);
			}catch(StringIndexOutOfBoundsException e) {
				throw new DataFichierErroneeException("Lignes incorrectes dans le fichier");
			}
			mot = mot.trim();
			if(token.startsWith("pirate")) {
			/*	if (equipage.getPirates().contains(equipage.getPirateFromPirateName(mot))) {
					throw new DataFichierErroneeException("Pirate "+mot+" existe déjà");
				}*/
				equipage.ajoutPirate(new Pirate(mot));
				nbPirate++;
			}
			else if(token.startsWith("objet")) {
/*
				if (equipage.getButins().contains(equipage.getButinFromButinName(mot))) {
					throw new DataFichierErroneeException("Butin "+mot+" existe déjà");
				}*/
				equipage.ajoutButin(new Butin(mot));
				nbButin++;
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
				try {
				p = equipage.getPirateFromPirateName(mot);
				}catch(PirateNotFoundException e) {
					//si le pirate entré n'existe pas dans l'équipage
					throw new DataFichierErroneeException("Pirate " + p + "n'existe pas");
				}
				//si la liste de préférence a déjà été entrée
				if(p.getPreference().size()==nbPirate) {
					throw new DataFichierErroneeException("Liste de préférence déjà remplie");
				}
				//vérifie presence de tous les objets préférés
	 			if(st.countTokens() != (nbPirate)) {
	 				throw new DataFichierErroneeException("Liste de préference des butins incomplète");
	 			}
				//on recupère les trésors
				while(st.hasMoreTokens()) {
					mot = st.nextToken();
					try {
					b = equipage.getButinFromButinName(mot);
					}catch(ButinNotFoundException e) {
						throw new DataFichierErroneeException("Butin " + mot + " n'existe pas");
					}
					p.addPreference(b);
				}
				//vérifie que la liste de préférence est complète
      		 	if(p.getPreference().size() != (nbPirate)) {
      	         	throw new DataFichierErroneeException("Liste de préférence incorrecte pour : "+p.getName());
      			 }
			}
			else {
				System.out.println(token);
				throw new DataFichierErroneeException("Lignes incorrectes dans le fichier");
			}
		}
		if(nbPirate!=nbButin) {
			throw new DataFichierErroneeException("Nombre de pirates different du nombre de trésors");
		}
	}
	
	/**
	 * Verifie qu'une ligne se termine bien par ")."
	 * @param data	Ligne à vérifier (=String)
	 * @return Vrai si une ligne se termine par ")."
	 */
	public boolean verifPointFinDeLigne(String data) {
		if (data.equals("")) {
			return false;
		}

		String splitted[] = data.split("\\)");
		if (splitted.length != 2) {
			return false;
		}
		if (splitted[1].equals(".")) {
			return true;
		}
		return false;
	}

	/**
	 * Permet de lire le fichier contenant les valeurs de configuration de
	 * l'equipage.
	 * @param nomFichier le fichier de configuration des pirates à lire
	 * @throws DataFichierErroneeException       Si les données du fichier sont incorrectes et ne permettent pas de construire l'Equipage
	 * @throws FileNotFoundException Si le fichier donné en argument n'existe pas
	 */
	public void lireFichier(String nomFichier) throws DataFichierErroneeException, FileNotFoundException {
		// pour appliquer la configuration
		File FIC = new File(nomFichier);
		myReader = new Scanner(FIC);
		int numeroLigneFichier = 0;

		while (myReader.hasNextLine()) {
			String lineData = myReader.nextLine();
			numeroLigneFichier++;

			if (lineData.equals("")) {
				throw new DataFichierErroneeException("Ligne vide à la ligne " + numeroLigneFichier, 89);
			}
			if (!verifPointFinDeLigne(lineData)) {
				throw new DataFichierErroneeException(
						"La ligne ne se finit pas par un seul point à la ligne " + numeroLigneFichier + "\n" + lineData,
						49);
			}

			String arguments[] = lineData.split("\\(|\\,|\\).");
			switch (arguments[0]) {
			case "pirate":
				Pirate p = null;
				if (arguments.length != 2) {
					throw new DataFichierErroneeException(
							"Le nombre d'argument n'est pas bon, la syntaxe pour ajouter un pirate n'est pas bonne à la ligne "
									+ numeroLigneFichier + "\n" + lineData,
							1);
				}
				try {
					p = equipage.getPirateFromPirateName(arguments[1]);
					if(p!=null) {
						throw new DataFichierErroneeException(
								"Le pirate existe déjà à la ligne " + numeroLigneFichier + "\n" + lineData, 90);
					}
				} catch (PirateNotFoundException e) {
					equipage.ajoutPirate(new Pirate(arguments[1]));
				}
				break;
			case "objet":
				Butin b = null;
				if (arguments.length != 2) {
					throw new DataFichierErroneeException(
							"Le nombre d'argument n'est pas bon, la syntaxe pour ajouter un objet n'est pas bonne à la ligne "
									+ numeroLigneFichier + "\n" + lineData,
							1);
				}
				try {
					b = equipage.getButinFromButinName(arguments[1]);
					if(b!=null) {
						throw new DataFichierErroneeException("L'objet existe déjà à la ligne " + numeroLigneFichier + "\n" + lineData,
								90);
					}
				} catch (ButinNotFoundException e) {
					equipage.ajoutButin(new Butin(arguments[1]));
				}
				break;
			case "deteste":
				if (arguments.length != 3) {
					throw new DataFichierErroneeException(
							"Le nombre d'argument n'est pas bon, la syntaxe pour deteste n'est pas bonne à la ligne "
									+ numeroLigneFichier + "\n" + lineData,
							1);
				}
				Pirate pDeteste;
				try {
					pDeteste = equipage.getPirateFromPirateName(arguments[1]);
				} catch (PirateNotFoundException e) {
					throw new DataFichierErroneeException("Le premier pirate " + arguments[1] + " n'existe pas dans deteste à la ligne "
							+ numeroLigneFichier + "\n" + lineData, 1);
				}

				try {
					pDeteste.addHating(equipage.getPirateFromPirateName(arguments[2]));
					equipage.getPirateFromPirateName(arguments[2]).addHating(pDeteste);
				} catch (PirateNotFoundException e) {
					throw new DataFichierErroneeException("Le deuxième pirate n'existe pas dans deteste à la ligne "
							+ numeroLigneFichier + "\n" + lineData, 1);
				}
				break;

			case "preferences":
				Pirate pPreference;
				if (arguments.length != equipage.getPirates().size() + 2) {
					throw new DataFichierErroneeException(
							"Le nombre d'argument n'est pas bon, la syntaxe pour preferences n'est pas bonne à la ligne "
									+ numeroLigneFichier + "\n" + lineData,
							1);
				}

				try {
					pPreference = equipage.getPirateFromPirateName(arguments[1]);
				} catch (PirateNotFoundException e) {
					throw new DataFichierErroneeException("Le pirate n'existe pas dans preferences à la ligne " + numeroLigneFichier
							+ "\n" + lineData, 1);
				}

				for (int i = 2; i < arguments.length; i++) {
					try {
						Butin c = equipage.getButinFromButinName(arguments[i]);
						pPreference.addPreference(c);
					} catch (ButinNotFoundException e) {
						if (e.getCode() == 1) {
							throw new DataFichierErroneeException("Objet pas présent dans le butin à la ligne " + numeroLigneFichier
									+ "\n" + lineData, 1);
						} else {
							throw new DataFichierErroneeException("Objet déjà présent dans les préférences à la ligne "
									+ numeroLigneFichier + "\n" + lineData, 1);
						}
					}
				}
				break;
			default:
				throw new DataFichierErroneeException("commande inconnu à la ligne " + numeroLigneFichier + "\n" + lineData, 79);

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
		System.out.println("0 : résolution automatique\n1 : résolution automatique améliorée\n2 : résolution manuelle\n3 : sauvegarde\n4 : fin");
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
			case 0 : 
				equipage.displayEquipageDetail();
				System.out.println("Solution approximative");
				System.out.println("Entrez nombre d'itérations cad le nombre d'échanges de butins");
				nbEchanges = saisieEntier();
				//equipage.approximerSolution(nbEchanges);
				equipage.approximerSolution(nbEchanges);
				butinsAttribues = true;
				System.out.println("\n***** partage des butins *****");
				equipage.displayPartage();
				System.out.println("\n***** cout partage *****");
				System.out.println(equipage.getCoutIteratif());
				break;
			case 1 : 
				equipage.displayEquipageDetail();
				System.out.println("Solution approximative ameliorée");
				System.out.println("Entrez nombre d'itérations cad le nombre d'échanges de butins");
				nbEchanges = saisieEntier();
				//equipage.approximerSolution(nbEchanges);
				equipage.approximerSolution2(nbEchanges);
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
		myReader.close();
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