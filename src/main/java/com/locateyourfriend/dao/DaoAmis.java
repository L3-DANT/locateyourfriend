package com.locateyourfriend.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;

import com.locateyourfriend.daoInterface.DaoAmisInterface;
import com.locateyourfriend.model.Amis;
import com.locateyourfriend.model.Constantes;
import com.locateyourfriend.model.Utilisateur;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class DaoAmis extends DaoAbstract implements DaoAmisInterface {

	public DaoAmis(){
		super();
	}
	
	@Override
	public Amis getFriendsByUser(String username) {
		logger.log(Level.INFO, "récupération d'un utilisateur : " + username);
		DBCollection collection = dataBase.getCollection(Constantes.TABLE_USER);
		DBCursor userDb = collection.find(new BasicDBObject(Constantes.COLONNE_EMAIL, username));
		List<Utilisateur> listeUser = new ArrayList<Utilisateur>();
		while(userDb.hasNext()){
			listeUser.add(gson.fromJson(userDb.toString(), Utilisateur.class));
		}
		Amis amis = new Amis();
		amis.setList(listeUser);
		return amis;
	}

	@Override
	public void insertFriendsByUser(String username, Amis listeAmis) {
		DBCollection collection = dataBase.getCollection(Constantes.COLONNE_AMI_CIBLE);
		for(Utilisateur ami : listeAmis.getList()){
			BasicDBObject jointure = new BasicDBObject(username, ami.getEmail());
			collection.insert(jointure);
		}
	}

}
