package com.locateyourfriend.model;

import java.util.ArrayList;
import java.util.List;

public class Amis {
	private List<Utilisateur> listUtil;
	
	public Amis(){
		listUtil = new ArrayList<Utilisateur>();
	}

	public void ajouterAmi(Utilisateur nouvelAmis) {
		listUtil.add(nouvelAmis);		
	}
	
	public List<Utilisateur> getList(){
		return listUtil;
	}
}
