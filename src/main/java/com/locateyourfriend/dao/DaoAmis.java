package com.locateyourfriend.dao;

import java.util.logging.Level;

import org.bson.Document;

import com.locateyourfriend.daoInterface.DaoAmisInterface;
import com.locateyourfriend.model.Constantes;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

public class DaoAmis extends DaoAbstract implements DaoAmisInterface {

	public DaoAmis(){
		super();
	}
	
	/**
	 * Récupère toutes les relations d'amitié
	 */
	@Override
	public MongoCursor<Document> getFriends(String email) throws MongoException {
		logger.log(Level.INFO, "récupération d'un utilisateur : " + email);
		MongoCollection<Document> collection = mongoDatabase.getCollection(Constantes.TABLE_JOINTURE_AMIS);
		return collection.find().iterator();
	}

	/**
	 * Insère une amitié dans la base de données, sous la forme d'une jointure dans la table de jointure des amitiés
	 */
	@Override
	public void insertFriendship(String user1Name, String user2Name) throws MongoException{
		MongoCollection<Document> collection = mongoDatabase.getCollection(Constantes.TABLE_JOINTURE_AMIS);
		Document jointure = new Document();
		jointure.append(Constantes.COLONNE_AMI_CIBLE, user1Name);
		jointure.append(Constantes.COLONNE_AMI_ID, user2Name);
		collection.insertOne(jointure);
	}

}
