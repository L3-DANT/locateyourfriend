package com.locateyourfriend.daoInterface;

import com.locateyourfriend.model.Amis;

public interface DaoAmisInterface {
	
	public Amis getFriendsByUser(String username);

	void insertFriendship(String user1Name, String user2Name);
}
