/*******************************
 *	Nom: Bdd.java          *
 *	Auteur: Youcef Touat   *
 *******************************/

import java.io.*;
import java.util.*;

public class Bdd implements Signatures {
	private Map<Auteur, Set<Livre>> MapAuteur; //Map compos� d'auteurs comme clef
	
	//constructeur par defaut
public Bdd(){
		MapAuteur = new LinkedHashMap<Auteur, Set<Livre>>(); //vide
}

/**************************************************************
 * Methode pour lire le fichier d�auteurs pass� en argument   *
 * de cr�er la map et ajouter les auteurs � la map.           *
 **************************************************************/

public void lireBddAut(String nomFichier) throws IOException{
	boolean existeFichier = true ; // � ajuster apr�s
	FileReader fr = null; // initialiser pour Java
	//LOCALISER le fichier � partir de son nom
	try {
		fr = new FileReader (nomFichier) ;
	}
	// intercepter l'erreur si le fichier n'existe pas
	catch ( java.io.FileNotFoundException erreur) {
	System.out.println("Probleme d'ouvrir le fichier " + nomFichier);
	existeFichier = false ; 
	}
	if (existeFichier) {
	BufferedReader entree = new BufferedReader(fr);
	boolean finFichier = false ;
	while ( !finFichier ) {
	// lire une ligne
	String uneLigne = entree.readLine();
	if (uneLigne == null)
		finFichier = true ;
	else {
		StringTokenizer st = new StringTokenizer(uneLigne, "\t");
		String codeStr = st.nextToken();
		int code = Integer.parseInt(codeStr.trim());
		String nom = st.nextToken();
		String paysOrigine = st.nextToken();
		MapAuteur.put(new Auteur(nom, code, paysOrigine), new TreeSet<Livre>());
		}
	}
	entree.close(); // fermer le fichier apres la lecture
	}
}

/*********************************************************************
 * Methode pour ajoute un auteur, pass� en argument, dans la map.    *  
 * La partie valeur associ�e a cet est une nouvelle collection vide. * 
 *********************************************************************/

public void addAuteur(Auteur unAuteur){
		if(MapAuteur.containsKey(unAuteur))
			System.out.println("L�auteur existe d�j�!!");
		else
			MapAuteur.put(unAuteur, new TreeSet<Livre>());
}

/*******************************************************************
 * Methode pour lire les livres dans un fichier pass� en argument  *
 * et d'ajouter chaque livre a la collection associ� � son auteur. * 
 *******************************************************************/

public void lireBddLivre(String nomFichier) throws IOException{
	boolean existeFichier = true ; // � ajuster apr�s
	FileReader fr = null; // initialiser pour Java
	//LOCALISER le fichier � partir de son nom
		try {
			fr = new FileReader (nomFichier) ;
		     }
	// intercepter l'erreur si le fichier n'existe pas
	catch ( java.io.FileNotFoundException erreur) {
		System.out.println("Probleme d'ouvrir le fichier " + nomFichier);
		existeFichier = false ; 
		}
	if (existeFichier) {
		BufferedReader entree = new BufferedReader(fr);
		boolean finFichier = false ;
	while ( !finFichier ) {
	// lire une ligne
	String uneLigne = entree.readLine();
		if (uneLigne == null)
		finFichier = true ;
		else {
			StringTokenizer st = new StringTokenizer(uneLigne, "\t");
			int codeLivre = Integer.parseInt(st.nextToken().trim());
			String titre = st.nextToken();
			String categorie = st.nextToken();
			int codeAuteur = Integer.parseInt(st.nextToken().trim());
			double prix = Double.parseDouble(st.nextToken().trim());
			int nbrPages = Integer.parseInt(st.nextToken().trim());
			//Ajouter chaque livre au Set de livres par tous les livres du fichier Livres
			Livre unLivre = new Livre(titre, codeLivre, codeAuteur, categorie, nbrPages, prix);
			for (Iterator<Map.Entry<Auteur, Set<Livre>>> entry = MapAuteur.entrySet().iterator();entry.hasNext();){
				Map.Entry<Auteur, Set<Livre>> a = entry.next();
				if(a.getKey().getCode()== unLivre.getCodeAuteur())
				a.getValue().add(unLivre);
				}
			}
	
	
		}
			entree.close(); // fermer le fichier apres la lecture
	}
}

/**************************************************************
 * Methode pour ajouter un livres a la base de donnees et de  *
 * l'associer a la collection correspondante a son auteur     *
 **************************************************************/
public void addLivre(Livre unLivre){
	Auteur auteurLivre = new Auteur(unLivre.getCodeAuteur());
	if(MapAuteur.containsKey(auteurLivre))
		{
			for (Iterator<Map.Entry<Auteur, Set<Livre>>> entry = MapAuteur.entrySet().iterator();entry.hasNext();)
			{
			Map.Entry<Auteur, Set<Livre>> a = entry.next();
			if(a.getKey().getCode()== unLivre.getCodeAuteur())
				a.getValue().add(unLivre);
			}
		}
		else
		System.out.println("L'auteur de ce livre n'est pas dans la base de donn�es");
}

/**********************************************************************
 * Methode pour associer a chaque auteur une collection de ces livres  *
 ***********************************************************************/
/*
public void remplireMap(){ 
		for (Map.Entry<Auteur, Set<Livre>> entry : MapAuteur.entrySet()){
			Set<Livre> OeuvrsAuteur = new TreeSet<Livre>();
			for(Livre unLivre : Oeuvrs){
			if(entry.getKey().getCode()== unLivre.getCodeAuteur())
				OeuvrsAuteur.add(unLivre);
				}
				MapAuteur.put(entry.getKey(), OeuvrsAuteur);
				}
}*/

/****************************************************************************
 * Methode pour chercher un auteur present dans la map en utilisant son nom  * 
 ****************************************************************************/
public Auteur getAuteur(String nom){
	Auteur auteurAchercher = new Auteur();
	for (Map.Entry<Auteur, Set<Livre>> entry : MapAuteur.entrySet()){
		if(entry.getKey().getNom().equalsIgnoreCase(nom))
			auteurAchercher = entry.getKey();
	}
	return  auteurAchercher;
}

/******************************************************************************
 * Methode pour chercher un auteur present dans la map en utilisant son code.  * 
 *******************************************************************************/

public Auteur getAuteur(int codeAuteur){
	Auteur auteurAchercher = new Auteur();
	for (Iterator<Auteur> itClef = MapAuteur.keySet().iterator(); itClef.hasNext();){
		Auteur clef = itClef.next();
		if(clef.getCode() == codeAuteur)
			auteurAchercher = clef;
		}
	return  auteurAchercher;
}

/*****************************************************************
 * Methode pour chercher un Livre d'un auteur present dans la map 
 * en utilisant le titre de ce livre   
 ****************************************************************/

public Livre getLivre(String titre){
	//Livre unLivre = new Livre(titre);
	Livre livreAchercher = new Livre();
	for (Map.Entry<Auteur, Set<Livre>> entry : MapAuteur.entrySet()){
		Iterator<Livre> itValeur = entry.getValue().iterator();
		while(itValeur.hasNext()){
			if(itValeur.next().getTitre() == titre)
				livreAchercher = itValeur.next();	
		}
	}
	/*
	for(Livre ceLivre : Oeuvrs){
		if(ceLivre.equals(unLivre))
			livreAchercher = ceLivre;
	}*/
	return livreAchercher;
}

/*******************************************************************
 * Methode pour chercher un Livre d'un auteur present dans la map  *
 * en utilisant le code de l'auteur de ce livre                    *
 *******************************************************************/

public Livre getLivre(int codeLivre){
	Livre livreAchercher = new Livre();
/*	for(Livre ceLivre : Oeuvrs){
		if(ceLivre.getCodeLivre() == codeLivre )
			livreAchercher = ceLivre;
	}*/
	for (Map.Entry<Auteur, Set<Livre>> entry : MapAuteur.entrySet()){
		Iterator<Livre> itValeur = entry.getValue().iterator();
		while(itValeur.hasNext()){
			if(itValeur.next().getCodeLivre() == codeLivre)
				livreAchercher = itValeur.next();	
		}
	}

	return livreAchercher;
}

/**********************************************************************
 * Methode pour chercher la collection de livres associer a un auteur * 
 * present dans la map en passant cet auteur en param�tre             *
 **********************************************************************/

public Collection getColLivresAut(Auteur unAuteur){
	Collection<Livre> OeuvresAuteur = new TreeSet<Livre>();
	for (Map.Entry<Auteur, Set<Livre>> entry : MapAuteur.entrySet()){
			if(unAuteur.getCode()== entry.getKey().getCode())
			OeuvresAuteur = entry.getValue();		
	}
	return OeuvresAuteur;
}

/*****************************************************************************
 * Methode pour cr�e un fichier parAuteur.txt contenant la liste des auteurs *
 * et de leurs livres par ordre alphab�tique des auteurs puis des livres.    *
 *****************************************************************************/

public void rapportParAuteurs()
		throws IOException{
	boolean probleme = false;
	FileWriter fw = null;
	String nomACreer = "parAuteur.txt";
	Map<Auteur, Set<Livre>> MapTrier = new LinkedHashMap<Auteur, Set<Livre>>();
	//Trier le map par ordre alphab�tique des auteurs.
	MapTrier = trierMap(MapAuteur);
	try{
		fw = new FileWriter(nomACreer);			
	    } 
	catch (java.io.FileNotFoundException erreur){
	System.out.println("Probl�me de pr�paration de lecturl'�criture\n");
	probleme = true;
	}
	if (!probleme){
	PrintWriter aCreer = new PrintWriter( fw );
	for (Map.Entry<Auteur, Set<Livre>> entry : MapTrier.entrySet()){
		aCreer.println(entry.getKey().getNom()+" :\n");
		if(entry.getValue().isEmpty())
			aCreer.println("\t\nCet auteur n�a pas de livres r�pertori�s.");
		else{
			Iterator<Livre> itValeur = entry.getValue().iterator();
				while(itValeur.hasNext()){
					aCreer.println("\t\n" + itValeur.next());
				}
			}
		}	
		aCreer.close();
		System.out.println("Cr�ation du fichier " + nomACreer);				
		}
}

/****************************************************************************
 * Methode pour Cr�e un fichier parLivre.txt contenant la liste des livres  * 
 * et des auteurs par ordre alphab�tique des titres de livre.               *
 ***************************************************************************/
public void rapportParLivres()
		throws IOException{
		boolean probleme = false;
		FileWriter fw = null;
		String nomACreer = "parLivre.txt";
			try{
				fw = new FileWriter(nomACreer);			
			} 
			catch (java.io.FileNotFoundException erreur){
			System.out.println("Probl�me de pr�paration de lecturl'�criture\n");
			probleme = true;
		}
		if (!probleme){
		PrintWriter aCreer = new PrintWriter( fw );
		Set<Livre> Oeuvrs = new TreeSet<Livre>();
		for (Map.Entry<Auteur, Set<Livre>> entry : MapAuteur.entrySet()){
			Iterator<Livre> itValeur = entry.getValue().iterator();
			while(itValeur.hasNext()){
			Oeuvrs.add(itValeur.next());
			}
		}
		for(Livre ceLivre : Oeuvrs){
			aCreer.print(ceLivre);
			for(Auteur clef : MapAuteur.keySet()){
				if(clef.getCode()== ceLivre.getCodeAuteur())
				aCreer.println(clef.getNom());
			}
		}
		aCreer.close();
		System.out.println("Cr�ation du fichier " + nomACreer);				
		}
}

/****************************************************************************
 * Methode pour trier un LinkedHashMap par ordre alphab�tique des auteurs   *
 * tout en gardant chaque collection de livres avec leur auteur.            *
 ****************************************************************************/
public Map<Auteur, Set<Livre>> trierMap(Map<Auteur, Set<Livre>> mapAtrier){	
	List<Map.Entry<Auteur, Set<Livre>>> ListAuteur = new ArrayList<Map.Entry<Auteur, Set<Livre>>>(mapAtrier.entrySet());
		Collections.sort(ListAuteur, new Comparator<Map.Entry<Auteur, Set<Livre>>>() {
		//implementation de l'interface comparator //just pour cette fonction
		public int compare(Map.Entry<Auteur, Set<Livre>> a, Map.Entry<Auteur, Set<Livre>> b){
		return (a.getKey()).compareTo(b.getKey());
		}
	});
	Map<Auteur, Set<Livre>> MapAuteurTrier = new LinkedHashMap<Auteur, Set<Livre>>();
	for (Map.Entry<Auteur, Set<Livre>> entry : ListAuteur) {
		MapAuteurTrier.put(entry.getKey(), entry.getValue());
	}
	return MapAuteurTrier;
}

/************************************************************
*   Redefinition de la methode toString pour la classe Bdd  *
*************************************************************/
public String toString(){
	String resultat ="";
	for (Map.Entry<Auteur, Set<Livre>> entry : MapAuteur.entrySet()){
		resultat += "\n"+entry.getKey().getNom()+" :\n";
		if(entry.getValue().isEmpty())
			resultat +=	"Cet auteur n�a pas de livres r�pertori�s.";
		else{
		Iterator<Livre> itValeur = entry.getValue().iterator();
			while(itValeur.hasNext()){
				resultat += "\t"+itValeur.next()+"\n";
				}
		}
	}
	return resultat;
	}
}
