package com.locateyourfriend.daoInterface;

import com.locateyourfriend.model.Amis;

public interface DaoAmisInterface {
	
	public Amis getFriendsByUser(String username);
	
	public void insertFriendsByUser(String username, Amis listeAmis);
}
