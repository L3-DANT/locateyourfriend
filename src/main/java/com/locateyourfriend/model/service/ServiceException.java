package com.locateyourfriend.model.service;

public class ServiceException extends Exception{

	/**
	 * Cette classe permet de faire remonter les erreurs dans les règles de gestion, 
	 * 
	 * Normalement ces règles de gestion sont déjà implémentées au niveau IOS, ces exceptions permettent donc 
	 * potentiellement de faire remonter une erreur dans l'envoi ou la réception du message, ou bien la présence 
	 * l'identifiant reçus dans la base de données
	 */
	private static final long serialVersionUID = 1L;
	
	private String errorMessage;
	
	private ErrorNumbers errorNumber;
	
	public enum ErrorNumbers{
		NOM,
		PRENOM,
		EMAIL,
		MDP,
		EMAIL_UTILISE, 
		UTILISATEUR, 
		EQUAL_UTILISATEUR, 
		AMIS
	}
	
	
	public ServiceException(String message, ErrorNumbers errorNumber){
		this.errorMessage = message;
		this.errorNumber = errorNumber;
	}
	
	public String getErrorMessage(){
		return errorMessage;
	}
	
	public ErrorNumbers getErrorNumber(){
		return errorNumber;
	}
}
