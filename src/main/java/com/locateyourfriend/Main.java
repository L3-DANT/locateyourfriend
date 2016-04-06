package com.locateyourfriend;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.locateyourfriend.logger.MyLogger;


public class Main {

	public static void main(String[] args) {
		MyLogger.setup();
		Logger logger = Logger.getLogger("MonJournalDeLogs");
		logger.log(Level.INFO, "Démarrage du serveur");
	}

}
