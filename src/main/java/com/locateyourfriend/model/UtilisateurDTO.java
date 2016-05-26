package com.locateyourfriend.model;


/**
 * L'utilisateur DTO est un objet qui permet de partager la localisation des utilisateurs à un autre utilisateur
 * sans partager d'informations sensibles telles que le mot de passe 
 * 
 * @author tristan
 *
 */
public class UtilisateurDTO {
	private String nom;
	private String prenom;
	private String email;
	private Localisation localisation;
	
	public UtilisateurDTO(String email, String motDePasse){
		super();
		this.email = email;
		localisation = new Localisation();
	}
	
	public UtilisateurDTO(String nom, String prenom, String email, Localisation localisation){
		super();
		this.email = email;
		this.prenom = prenom;
		this.nom = nom;
		this.localisation = localisation;
	}
	
	public UtilisateurDTO(String nom, String prenom, String email){
		super();
		this.email = email;
		this.prenom = prenom;
		this.nom = nom;
		localisation = new Localisation();
	}
	
	public UtilisateurDTO(Utilisateur user){
		super();
		this.email = user.getEmail();
		this.prenom = user.getPrenom();
		this.nom = user.getNom();
		this.localisation = user.getLocalisationObject();
	}
	
	public UtilisateurDTO(){
		super();
	}
	
	public boolean equals(Utilisateur user){
		return this.email.equals(user.getEmail());
	}
	
	public String getLocalisation(){
		return localisation.toString(); 
	}

	public Localisation getLocalisationObject(){
		return localisation; 
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setLocalisation(String localisation) {
		this.localisation = new Localisation(localisation);
	}
	
	public String toString(){
		return prenom + " " + nom;
	}
}
