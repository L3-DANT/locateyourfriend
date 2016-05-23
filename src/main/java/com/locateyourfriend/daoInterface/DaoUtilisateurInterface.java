package com.locateyourfriend.daoInterface;

import com.locateyourfriend.model.Utilisateur;

public interface DaoUtilisateurInterface {
	
	public Utilisateur getUtilisateur(String email);
	
	public Utilisateur addUser(Utilisateur util);
	
	public void emptyTable();
}
