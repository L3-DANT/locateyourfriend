package com.locateyourfriend;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.locateyourfriend.logger.MyLogger;


public class Main {

	public static void main(String[] args) {
		Logger logger = MyLogger.getInstance();
		logger.log(Level.INFO, "Démarrage du serveur");
	}

}
