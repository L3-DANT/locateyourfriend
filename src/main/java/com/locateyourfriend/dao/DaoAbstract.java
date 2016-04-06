package com.locateyourfriend.dao;

import com.locateyourfriend.model.Constantes;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class DaoAbstract {
	
	DB dataBase;
	
	
	public DaoAbstract(){
		Mongo mongo = new Mongo();
		dataBase = mongo.getDB(Constantes.DB_ADRESS);
		
		//MongoClient mongoClient = new MongoClient();
		//MongoDatabase mongoDatabase = mongoClient.getDatabase(Constantes.DB_ADRESS);
	}

}
