package com.locateyourfriend.model;

import java.util.ArrayList;
import java.util.List;

public class Amis {
	
	/**
	 * Objet contenant la liste d'amis
	 */
	private List<UtilisateurDTO> listUtil;
	
	public Amis(){
		listUtil = new ArrayList<UtilisateurDTO>();
	}

	public void ajouterAmi(Utilisateur nouvelAmis) {
		listUtil.add(new UtilisateurDTO(nouvelAmis));		
	}
	
	public List<UtilisateurDTO> getList(){
		return listUtil;
	}
	
	public void setList(List<UtilisateurDTO> listUtil){
		this.listUtil = listUtil;
	}
}
