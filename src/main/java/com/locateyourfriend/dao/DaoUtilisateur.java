package com.locateyourfriend.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.google.gson.Gson;
import com.locateyourfriend.daoInterface.DaoAmisInterface;
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
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

public class DaoUtilisateur extends DaoAbstract implements DaoUtilisateurInterface {

	DaoAmisInterface daoAmis;
	
	//UtilisateurService userService = new UtilisateurService();
	
	public DaoUtilisateur(){
		super();
		beginTransaction();
		createTables();
		close();
		daoAmis = new DaoAmis();
	}

	@Override
	public Utilisateur getUtilisateur(String email) throws MongoException{
		logger.log(Level.INFO, "récupération d'un utilisateur : " + email);
		DBCollection collection = dataBase.getCollection(Constantes.TABLE_USER);
		DBObject userDb = collection.findOne(new BasicDBObject(Constantes.COLONNE_EMAIL, email));
		if(userDb == null){
			return null;
		}
		Utilisateur user = gson.fromJson(userDb.toString(), Utilisateur.class);
		return user;
	}

	@Override
	public Utilisateur addUser(Utilisateur util) throws MongoException{
		logger.log(Level.INFO, "insertion d'un utilisateur : " + util.getEmail());
		DBCollection collection = dataBase.getCollection(Constantes.TABLE_USER);
		BasicDBObject userDb = (BasicDBObject)JSON.parse(gson.toJson(util));
		collection.insert(userDb);
		return util;
	}
	
	public void createTables() throws MongoException{
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
	
	public void emptyTable() throws MongoException{
		dataBase.getCollection(Constantes.TABLE_USER).drop();
		dataBase.getCollection(Constantes.TABLE_JOINTURE_AMIS).drop();
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
