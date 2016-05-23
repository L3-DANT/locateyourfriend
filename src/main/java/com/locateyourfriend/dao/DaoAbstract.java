package com.locateyourfriend.dao;

import java.util.logging.Logger;

import com.google.gson.Gson;
import com.locateyourfriend.logger.MyLogger;
import com.locateyourfriend.model.Constantes;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class DaoAbstract {
	
	/*Mongo mongo;
	DB dataBase;*/
	Logger logger;
	Gson gson;
	MongoClient mongoClient;
	MongoDatabase mongoDatabase;
	
	/**
	 * Ce dao abstract permet d'initialiser la base de donnée pour tous les Daos
	 */
	public DaoAbstract(){
		logger = MyLogger.getInstance();
		gson = new Gson();
		
		mongoClient = new MongoClient();
	}
	
	public void beginTransaction(){
		
		mongoDatabase = mongoClient.getDatabase(Constantes.DB_ADRESS);
		/*mongo = new Mongo();
		dataBase = mongo.getDB(Constantes.DB_ADRESS);*/
	}
	
	public void close(){
		//mongoDatabase.drop();
		//mongo.close();
	}

}
