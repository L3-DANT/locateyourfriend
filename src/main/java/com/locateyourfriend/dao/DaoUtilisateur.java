package com.locateyourfriend.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.locateyourfriend.daoInterface.DaoUtilisateurInterface;
import com.locateyourfriend.logger.MyLogger;
import com.locateyourfriend.model.Constantes;
import com.locateyourfriend.model.Localisation;
import com.locateyourfriend.model.Utilisateur;
import com.locateyourfriend.model.service.UtilisateurService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class DaoUtilisateur extends DaoAbstract implements DaoUtilisateurInterface {

	Logger logger;
	
	UtilisateurService userService = new UtilisateurService();
	
	public DaoUtilisateur(){
		super();
		createTables();
		logger = MyLogger.getInstance();
	}
	
	@Override
	public boolean findUtilisateur(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Utilisateur getUtilisateur(String email) {
		logger.log(Level.INFO, "récupération d'un utilisateur : " + email);
		DBCollection collection = dataBase.getCollection(Constantes.TABLE_USER);
		DBObject userDb = collection.findOne(new BasicDBObject(Constantes.COLONNE_EMAIL, email));
		Utilisateur user = new Utilisateur();
		user.setEmail(userDb.get(Constantes.COLONNE_EMAIL).toString());
		user.setNom(userDb.get(Constantes.COLONNE_NOM).toString());
		user.setPrenom(userDb.get(Constantes.COLONNE_PRENOM).toString());
		user.setMotDePasse(userDb.get(Constantes.COLONNE_MDP).toString());
		user.setLocalisation(userDb.get(Constantes.COLONNE_LOCALISATION).toString());
		return user;
	}

	@Override
	public Utilisateur addUser(Utilisateur util) {
		logger.log(Level.INFO, "insertion d'un utilisateur : " + util.getEmail());
		DBCollection collection = dataBase.getCollection(Constantes.TABLE_USER);
		BasicDBObject userDb = userService.userToDataBaseObject(util);
		collection.insert(userDb);
		collection = dataBase.getCollection(Constantes.TABLE_JOINTURE_AMIS);
		for(Utilisateur ami : util.getMesAmis().getList()){
			BasicDBObject jointure = new BasicDBObject(util.getEmail(), ami.getEmail());
			collection.insert(jointure);
		}
		return util;
	}
	
	public void createTables(){
		DBCollection collection = dataBase.getCollection(Constantes.TABLE_USER);
		collection.createIndex(Constantes.COLONNE_EMAIL);
		collection.createIndex(Constantes.COLONNE_NOM);
		collection.createIndex(Constantes.COLONNE_PRENOM);
		collection.createIndex(Constantes.COLONNE_MDP);
		collection.createIndex(Constantes.COLONNE_LOCALISATION);
		collection = dataBase.getCollection(Constantes.TABLE_JOINTURE_AMIS);
		collection.createIndex(Constantes.COLONNE_AMI_ID);
		collection.createIndex(Constantes.COLONNE_AMI_CIBLE);
	}

	public static void main(String[] args){
		DaoUtilisateurInterface daoUtilisateur = new DaoUtilisateur();
		Utilisateur user = new Utilisateur("robin", "tritan", "tristan.robin1@gmail.com", "test");
		daoUtilisateur.addUser(user);
		Utilisateur userEnRetour = daoUtilisateur.getUtilisateur(user.getEmail());
		if(user.equals(userEnRetour)){
			MyLogger.getInstance().log(Level.INFO, "test réussit");
		}
		else{
			MyLogger.getInstance().log(Level.INFO, "test raté");
		}
	}
}
