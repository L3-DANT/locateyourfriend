package com.locateyourfriend.model.service;

public class ServiceException extends Exception{

	/**
	 * Cette classe permet de faire remonter les erreurs dans les r�gles de gestion, 
	 * 
	 * Normalement ces r�gles de gestion sont d�j� impl�ment�es au niveau IOS, ces exceptions permettent donc 
	 * potentiellement de faire remonter une erreur dans l'envoi ou la r�ception du message, ou bien la pr�sence 
	 * l'identifiant re�us dans la base de donn�es
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
