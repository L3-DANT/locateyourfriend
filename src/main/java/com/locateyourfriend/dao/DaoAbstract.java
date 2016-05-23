package com.locateyourfriend.dao;

import java.util.logging.Logger;

import javax.xml.crypto.Data;

import com.google.gson.Gson;
import com.locateyourfriend.logger.MyLogger;
import com.locateyourfriend.model.Constantes;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class DaoAbstract {
	
	Mongo mongo;
	DB dataBase;
	Logger logger;
	Gson gson;
	
	
	/**
	 * Ce dao abstract permet d'initialiser la base de donnée pour tous les Daos
	 */
	public DaoAbstract(){
		logger = MyLogger.getInstance();
		gson = new Gson();
		
		//MongoClient mongoClient = new MongoClient();
		//MongoDatabase mongoDatabase = mongoClient.getDatabase(Constantes.DB_ADRESS);
	}
	
	public void beginTransaction(){
		mongo = new Mongo();
		dataBase = mongo.getDB(Constantes.DB_ADRESS);
	}
	
	public void close(){
		mongo.close();
	}

}
