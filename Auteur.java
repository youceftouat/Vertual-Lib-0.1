/*************************
 *	Nom: Auteur.java
 *	Cours IFT1176
 *	Auteur: Youcef Touat
 *	Matricule : 1068827
 ************************/

public class Auteur
implements Comparable<Auteur>
{
	private int code;
	private String nom;
	private String paysOrigine;
	
	//Constructeur par defaut (sans parametres)
	public Auteur()
	{
	}
	//Constructeur en passant le code en parametre
		public Auteur(int code)
		{
			this.code = code;
		}
	//Constructeur avec parametres
	public Auteur(String nom, int code, String paysOrigine)
	{
		this.nom = nom;
		this.code = code;
		this.paysOrigine = paysOrigine;
	}
	public int getCode()
	{
	return code;	
	}
	public void setCode(int nouvCode)
	{
		code = nouvCode;
	}
	public String getNom()
	{
	return nom;	
	}
	public void setNom(String nouvNom)
	{
		nom = nouvNom;
	}
	//Redefinitin de toString	
	public String toString()
	{
		//return String.format(" %-10d %-30s %-10s",code, nom, paysOrigine);
		return String.format("%s", nom);
	}

	//Redefinition de la methode equals
	public boolean equals(Object obj)
	{
	 if (! (obj instanceof Auteur))
		return false;
	 else
		if (this == obj)
			return true;
		else{
			Auteur autre = (Auteur) obj;
			return code == autre.code;
			}
	 }
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + code;
		return result;
	}
	public int compareTo(Auteur autre) {
		return nom.compareTo(autre.nom);
		//return 0;
	}	
}