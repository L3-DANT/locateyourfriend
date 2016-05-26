package com.locateyourfriend.model.service;

import java.util.ArrayList;
import com.locateyourfriend.model.Utilisateur;

public class ServiceLocateYourFriends {
	
	/**
	 * Le ServiceLocateYourFriends est le coeur de l'application, il g�re le service utilisateur, la liste des utilisateurs.
	 * 
	 * C'est un singleton
	 */
	
	private static ServiceLocateYourFriends servicePrincipal;
	
	private UtilisateurService utilisateurService;
	
	private ArrayList<Utilisateur> listeUtils;
	
	private ServiceLocateYourFriends(){
		super();
		utilisateurService = new UtilisateurService();
		setListeUtils((ArrayList<Utilisateur>) utilisateurService.getUtilisateurs());
	}
	
	public ServiceLocateYourFriends getInstance(){
		if(servicePrincipal==null){
			servicePrincipal = new ServiceLocateYourFriends();
		}
		return servicePrincipal;
	}

	public ArrayList<Utilisateur> getListeUtils() {
		return listeUtils;
	}

	public void setListeUtils(ArrayList<Utilisateur> listeUtils) {
		this.listeUtils = listeUtils;
	}

}
