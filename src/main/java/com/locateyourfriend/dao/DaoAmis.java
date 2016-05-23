package com.locateyourfriend.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bson.Document;

import com.locateyourfriend.daoInterface.DaoAmisInterface;
import com.locateyourfriend.model.Amis;
import com.locateyourfriend.model.Constantes;
import com.locateyourfriend.model.UtilisateurDTO;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

public class DaoAmis extends DaoAbstract implements DaoAmisInterface {
	DaoUtilisateur daoUtilisateur;

	public DaoAmis(){
		super();
		this.daoUtilisateur = new DaoUtilisateur();
	}
	
	@Override
	public Amis getFriendsByUser(String username) {
		logger.log(Level.INFO, "récupération d'un utilisateur : " + username);
		MongoCollection<Document> collection = mongoDatabase.getCollection(Constantes.TABLE_JOINTURE_AMIS);
		List<UtilisateurDTO> listeUser = new ArrayList<UtilisateurDTO>();
		try (MongoCursor<Document> cursor = collection.find().iterator()) {
		    while (cursor.hasNext()) {
		       Document doc = cursor.next();
		       if( doc.getString(Constantes.COLONNE_AMI_CIBLE).equals(username)){
		    	   listeUser.add(new UtilisateurDTO(daoUtilisateur.getUtilisateur(doc.getString(Constantes.COLONNE_AMI_ID))));
		       }
		       if( doc.getString(Constantes.COLONNE_AMI_ID).equals(username)){
		    	   listeUser.add(new UtilisateurDTO(daoUtilisateur.getUtilisateur(doc.getString(Constantes.COLONNE_AMI_CIBLE))));
		       }
		    }
		}
//		while(userDb.){
//			DBObject joint = userDb.next();
//			if(joint.get(username)!=null){
//				listeUser.add(new UtilisateurDTO(daoUtilisateur.getUtilisateur(joint.get(username).toString())));
//			}
//		}
		Amis amis = new Amis();
		amis.setList(listeUser);
		return amis;
	}

	@Override
	public void insertFriendship(String user1Name, String user2Name) {
		MongoCollection<Document> collection = mongoDatabase.getCollection(Constantes.TABLE_JOINTURE_AMIS);
		Document jointure = new Document();
		jointure.append(Constantes.COLONNE_AMI_CIBLE, user1Name);
		jointure.append(Constantes.COLONNE_AMI_ID, user2Name);
		collection.insertOne(jointure);
		jointure = new Document();
		jointure.append(Constantes.COLONNE_AMI_ID, user2Name);
		jointure.append(Constantes.COLONNE_AMI_CIBLE, user1Name);
		collection.insertOne(jointure);
	}

}
