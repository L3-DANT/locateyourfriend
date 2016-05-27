package com.locateyourfriend.model;

public class Utilisateur {
	private String nom;
	private String prenom;
	private String email;
	private String motDePasse;
	private Amis mesAmis;
	private Localisation localisation;
	
	public Utilisateur(String email, String motDePasse){
		super();
		this.email = email;
		this.motDePasse = motDePasse;
		mesAmis = new Amis();
		localisation = new Localisation();
	}
	
	public Utilisateur(String nom, String prenom, String email, String motDePasse, Localisation localisation){
		super();
		this.email = email;
		this.motDePasse = motDePasse;
		mesAmis = new Amis();
		this.prenom = prenom;
		this.nom = nom;
		this.localisation = localisation;
	}
	
	public Utilisateur(String nom, String prenom, String email, String motDePasse, String localisation){
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.motDePasse = motDePasse;
		this.localisation = new Localisation(localisation);
		mesAmis = new Amis();
	}
	
	public Utilisateur(String nom, String prenom, String email, String motDePasse, String longitude, String latitude){
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.motDePasse = motDePasse;
		this.localisation = new Localisation(longitude, latitude);
		mesAmis = new Amis();
	}
	
	public Utilisateur(String nom, String prenom, String email, String motDePasse){
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.motDePasse = motDePasse;
		mesAmis = new Amis();
		localisation = new Localisation();
	}
	
	public Utilisateur(){
		super();
	}
	
	public Utilisateur(UtilisateurDTO utilisateurDTO) {
		super();
		this.prenom = utilisateurDTO.getPrenom();
		this.nom = utilisateurDTO.getNom();
		this.email = utilisateurDTO.getEmail();
		this.prenom = utilisateurDTO.getPrenom();
		this.localisation = utilisateurDTO.getLocalisationObject();
		mesAmis = new Amis();
		motDePasse = "";
	}

	public boolean equals(Utilisateur user){
		return this.email.equals(user.getEmail());
	}
	public void ajouterAmi(Utilisateur nouvelAmis){
		mesAmis.ajouterAmi(nouvelAmis);
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

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public Amis getMesAmis() {
		return mesAmis;
	}

	public void setMesAmis(Amis mesAmis) {
		this.mesAmis = mesAmis;
	}

	public void setLocalisation(String localisation) {
		this.localisation = new Localisation(localisation);
	}
}
