package com.locateyourfriend.model.service;

import java.util.Map;

import com.locateyourfriend.model.Utilisateur;

public class ServiceLocateYourFriends {
	
	private static ServiceLocateYourFriends servicePrincipal;
	
	private Map<String, Utilisateur> mapUtils;
	
	private ServiceLocateYourFriends(){
		super();
	}
	
	public ServiceLocateYourFriends getInstance(){
		if(servicePrincipal==null){
			servicePrincipal = new ServiceLocateYourFriends();
		}
		return servicePrincipal;
	}

}
