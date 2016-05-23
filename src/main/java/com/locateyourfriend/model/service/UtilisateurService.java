package com.locateyourfriend.model.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.locateyourfriend.dao.DaoAmis;
import com.locateyourfriend.dao.DaoUtilisateur;
import com.locateyourfriend.logger.MyLogger;
import com.locateyourfriend.model.Constantes;
import com.locateyourfriend.model.Utilisateur;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;

public class UtilisateurService {
	
	DaoUtilisateur daoUtilisateur = new DaoUtilisateur();
	DaoAmis daoAmis = new DaoAmis();
	Logger logger;
	
	public UtilisateurService(){
		logger = MyLogger.getInstance();
	}

	public Utilisateur insertUser(String nom, String prenom, String email, String motDePasse){
		if(nom == null || nom.length()<2 || nom.matches("\\D")){
			logger.log(Level.WARNING, "Impossible to insert user, invalid name ");
			return null;
		}
		if(prenom == null || prenom.length()<2 || prenom.matches("\\D")){
			logger.log(Level.WARNING, "Impossible to insert user, invalid first name");
			return null;
		}
		if(email == null || !email.matches("^(.+)@(.+)$")){
			logger.log(Level.WARNING, "Impossible to insert user, invalid email");
			return null;
		}
		daoUtilisateur.beginTransaction();
		if(daoUtilisateur.getUtilisateur(email)!=null){
			logger.log(Level.WARNING, "E-mail already in use");
			return null;
		}
		if(motDePasse == null || motDePasse.length()<8){
			logger.log(Level.WARNING, "Impossible to insert user, invalid password");
			return null;
		}
		Utilisateur user = new Utilisateur(nom, prenom, email, motDePasse);
		try{
			user = daoUtilisateur.addUser(user);
		}catch(MongoException e){
			logger.log(Level.SEVERE, "l'insertion a échoué", e);
		}
		daoUtilisateur.close();
		return user;
	}
	
	public Utilisateur getUtilisateur(String email){
		daoAmis.beginTransaction();
		daoUtilisateur.beginTransaction();
		Utilisateur user = daoUtilisateur.getUtilisateur(email);
		System.out.println("user récupéré : " + user);
		user.setMesAmis(daoAmis.getFriendsByUser(user.getEmail()));
		daoAmis.close();
		daoUtilisateur.close();
		return user;
	}
	
	public void addAmis(Utilisateur user1, Utilisateur user2){
		if(user1 == null || user2 == null){
			logger.log(Level.WARNING, "impossible to insert friendship relation, one of the user is null");
			return;
		}
		if(user1.equals(user2)){
			logger.log(Level.WARNING, "impossible to insert friendship relation, both users are same");
			return;
		}
		user1.ajouterAmi(user2);
		user2.ajouterAmi(user1);
		daoAmis.beginTransaction();
		daoAmis.insertFriendship(user1.getEmail(), user2.getEmail());
		daoAmis.close();
	}
	
	public boolean authentification(String motDePasse, Utilisateur user){
		String userMdp = user.getMotDePasse();
		return userMdp.equals(motDePasse);
	}
	
	public BasicDBObject userToDataBaseObject(Utilisateur user){
		BasicDBObject userDb = new BasicDBObject();
		userDb.append(Constantes.COLONNE_EMAIL, user.getEmail());
		userDb.append(Constantes.COLONNE_NOM, user.getNom());
		userDb.append(Constantes.COLONNE_PRENOM, user.getPrenom());
		userDb.append(Constantes.COLONNE_MDP, user.getMotDePasse());
		userDb.append(Constantes.COLONNE_LOCALISATION, user.getLocalisation().toString());
		return userDb;
	}

	public void emptyTable() {
		daoUtilisateur.beginTransaction();
		daoUtilisateur.emptyTable();
		daoUtilisateur.close();
	}
}
