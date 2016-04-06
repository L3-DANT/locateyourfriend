package com.locateyourfriend.model.service;

import com.locateyourfriend.model.Constantes;
import com.locateyourfriend.model.Utilisateur;
import com.mongodb.BasicDBObject;

public class UtilisateurService {

	
	public boolean authentification(String motDePasse, Utilisateur user){
		String userMdp = user.getMotDePasse();
		return userMdp.equals(motDePasse);
	}
	
	public BasicDBObject userToDataBaseObject(Utilisateur user){
		BasicDBObject userDb = new BasicDBObject();
		userDb.append(Constantes.COLONNE_EMAIL, user.getEmail());
		userDb.append(Constantes.COLONNE_NOM, user.getNom());
		userDb.append(Constantes.COLONNE_PRENOM, user.getPrenom());
		userDb.append(Constantes.COLONNE_MDP, user.getMotDePasse());
		userDb.append(Constantes.COLONNE_LOCALISATION, user.getLocalisation().toString());
		return userDb;
	}
}
