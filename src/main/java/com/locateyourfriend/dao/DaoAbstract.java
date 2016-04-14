package com.locateyourfriend.dao;

import java.util.logging.Logger;

import com.google.gson.Gson;
import com.locateyourfriend.logger.MyLogger;
import com.locateyourfriend.model.Constantes;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class DaoAbstract {
	
	DB dataBase;
	Logger logger;
	Gson gson;
	
	public DaoAbstract(){
		Mongo mongo = new Mongo();
		dataBase = mongo.getDB(Constantes.DB_ADRESS);
		logger = MyLogger.getInstance();
		gson = new Gson();
		
		//MongoClient mongoClient = new MongoClient();
		//MongoDatabase mongoDatabase = mongoClient.getDatabase(Constantes.DB_ADRESS);
	}

}
