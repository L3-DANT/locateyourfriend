package com.locateyourfriend.dao;

import java.util.logging.Logger;

import com.google.gson.Gson;
import com.locateyourfriend.logger.MyLogger;
import com.locateyourfriend.model.Constantes;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class DaoAbstract {

	Logger logger;
	Gson gson;
	public static MongoClient mongoClient = new MongoClient(); //pitton static final MongoClient mongoClient;
	
	public static MongoDatabase mongoDatabase = mongoClient.getDatabase(Constantes.DB_ADRESS);
	
	/**
	 * Ce dao abstract permet d'initialiser la base de donn�e pour tous les Daos
	 */
	public DaoAbstract(){
		logger = MyLogger.getInstance();
		gson = new Gson();			
	}
}
