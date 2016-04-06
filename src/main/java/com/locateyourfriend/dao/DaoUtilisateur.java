package com.locateyourfriend.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.locateyourfriend.daoInterface.DaoUtilisateurInterface;
import com.locateyourfriend.model.Constantes;
import com.locateyourfriend.model.Utilisateur;
import com.locateyourfriend.model.service.UtilisateurService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class DaoUtilisateur extends DaoAbstract implements DaoUtilisateurInterface {

	UtilisateurService userService = new UtilisateurService();
	
	public DaoUtilisateur(){
		super();
		createTables();
	}
	
	@Override
	public boolean findUtilisateur(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Utilisateur getUtilisateur(String email) {
		DBCollection collection = dataBase.getCollection(Constantes.TABLE_USER);
		DBObject userDb = collection.findOne(new Document("email", email));
		Utilisateur user = new Utilisateur();
		user.setEmail(userDb.get(Constantes.COLONNE_EMAIL).toString());
		user.setNom(userDb.get(Constantes.COLONNE_NOM).toString());
		user.setPrenom(userDb.get(Constantes.COLONNE_PRENOM).toString());
		user.setMotDePasse(userDb.get(Constantes.COLONNE_MDP).toString());
		user.setLocalisation(userDb.get(Constantes.COLONNE_LOCALISATION).toString());
		return null;
	}

	@Override
	public Utilisateur addUser(Utilisateur util) {
		DBCollection collection = dataBase.getCollection(Constantes.TABLE_USER);
		BasicDBObject userDb = userService.userToDataBaseObject(util);
		collection.insert(userDb);
		collection = dataBase.getCollection(Constantes.TABLE_JOINTURE_AMIS);
		for(Utilisateur ami : util.getMesAmis().getList()){
			BasicDBObject jointure = new BasicDBObject(util.getEmail(), ami.getEmail());
			collection.insert(jointure);
		}
		return util;
	}
	
	public void createTables(){
		DBCollection collection = dataBase.getCollection(Constantes.TABLE_USER);
		collection.createIndex(Constantes.COLONNE_EMAIL);
		collection.createIndex(Constantes.COLONNE_NOM);
		collection.createIndex(Constantes.COLONNE_PRENOM);
		collection.createIndex(Constantes.COLONNE_MDP);
		collection.createIndex(Constantes.COLONNE_LOCALISATION);
		collection = dataBase.getCollection(Constantes.TABLE_JOINTURE_AMIS);
		collection.createIndex(Constantes.COLONNE_AMI_ID);
		collection.createIndex(Constantes.COLONNE_AMI_CIBLE);
	}

	
	/*
	 * public static void main(String [] args){
		Test test = new Test();
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("locate");
		DBCollection collection = db.getCollection("message");
		List<BasicDBObject> doc = new ArrayList<BasicDBObject>();
		doc.add(new BasicDBObject("message", "yo 1"));
		doc.add(new BasicDBObject("message", "yi"));
		doc.add(new BasicDBObject("message", "yo 2"));
		doc.add(new BasicDBObject("message", "ya"));
		doc.add(new BasicDBObject("message", "yo 3"));
		doc.add(new BasicDBObject("message", "toto"));
		doc.add(new BasicDBObject("message", "tata"));
		collection.insert(doc);
		System.out.println(test.getMessages(collection, "yo"));		
	}
	
	public String getMessages(DBCollection coll, String message){
		DBCursor cursor = coll.find();
		StringBuilder retour = new StringBuilder();
		String currentMessage;
		try{
			while(cursor.hasNext()){
				currentMessage = (String) cursor.next().get("message");
				if(currentMessage.contains(message)){
					retour = retour.append(currentMessage + "; ");
				}
			}
		}catch(Exception e){
			System.out.println(e);
		}finally{
			cursor.close();
		}
		if(retour.length() == 0){
			return "non-trouve";
		}
		return retour.toString();
	}
	 */
}
