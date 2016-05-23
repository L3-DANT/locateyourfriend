package com.locateyourfriend.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;

import com.locateyourfriend.daoInterface.DaoAmisInterface;
import com.locateyourfriend.model.Amis;
import com.locateyourfriend.model.Constantes;
import com.locateyourfriend.model.Utilisateur;
import com.locateyourfriend.model.UtilisateurDTO;
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
		List<UtilisateurDTO> listeUser = new ArrayList<UtilisateurDTO>();
		while(userDb.hasNext()){
			listeUser.add(gson.fromJson(userDb.next().toString(), UtilisateurDTO.class));
		}
		Amis amis = new Amis();
		amis.setList(listeUser);
		return amis;
	}

	@Override
	public void insertFriendship(String user1Name, String user2Name) {
		DBCollection collection = dataBase.getCollection(Constantes.COLONNE_AMI_CIBLE);
		BasicDBObject jointure = new BasicDBObject(user1Name, user2Name);
		collection.insert(jointure);
	}

}
