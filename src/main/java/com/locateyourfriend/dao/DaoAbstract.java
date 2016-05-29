package com.locateyourfriend.dao;

import java.util.logging.Logger;

import com.google.gson.Gson;
import com.locateyourfriend.logger.MyLogger;
import com.locateyourfriend.model.Constantes;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoDatabase;

public class DaoAbstract {

	Logger logger;
	Gson gson;
	public static MongoClient mongoClient; //pitton static final MongoClient mongoClient;
	
	public static MongoDatabase mongoDatabase;
	
	/**
	 * Ce dao abstract permet d'initialiser la base de donn�e pour tous les Daos
	 */
	public DaoAbstract(){
		logger = MyLogger.getInstance();
		gson = new Gson();
		
		mongoClient = new MongoClient();
		mongoDatabase = mongoClient.getDatabase(Constantes.DB_ADRESS);
		
		//Pour le problème de conexion ouverte à chaque requete et qui bloque apres 205 connexion
		
		/*MongoClientOptions mongoClientOption = new MongoClientOptions.Builder()
			    .connectionsPerHost(10)
			    .threadsAllowedToBlockForConnectionMultiplier(10)
			    .build();
			mongoClient = new MongoClient("127.0.0.1", mongoClientOption);
			mongoDatabase = mongoClient.getDatabase(Constantes.DB_ADRESS);*/
			
	}
}
