package com.locateyourfriend.daoInterface;

import org.bson.Document;

import com.mongodb.client.MongoCursor;

public interface DaoAmisInterface {
	
	public MongoCursor<Document> getFriends(String username);

	void insertFriendship(String user1Name, String user2Name);
}
