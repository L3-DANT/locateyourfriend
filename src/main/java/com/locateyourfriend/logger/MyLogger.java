package com.locateyourfriend.logger;

import java.io.IOException;
import java.util.logging.*;

import java.text.SimpleDateFormat; 
import java.util.Date; 

public class MyLogger {
	
	private static Logger logger;

	public static Logger getInstance(){
		if(logger == null){
			logger = MyLogger.setup();
		}
		return logger;
	}
	
	static private FileHandler fh = null; //C'est un pointeur vers notre fichier log
	static private SimpleDateFormat f = new SimpleDateFormat("dd_MM_yy_H'h'm'm's's'");//On formate la date : 01/01/16 par exemple 
	static private Date d = new Date(); //Ici on recupere la date du systeme pour l'incorporer au nom de notre fichier log
	
	public MyLogger(){ 
		
	}	
	
	private static Logger setup(){
		// Le logger correspond au journal de log qui va traiter nos logs
		Logger logger = Logger.getLogger("MonJournalDeLogs");
			   	
			try {
				//Ici on indique au FileHandler le nom et l'emplacement du fichier log
				//Il le creera si il n'existe pas
				System.out.println("je suis rentré dans le try");
				fh = new FileHandler("%h/log"+f.format(d)+".log"); //%h correspond au repertoire user
				System.out.println("log"+f.format(d)+".log");
				logger.addHandler(fh);
				
				//On set le format du fichier log (Choix XML ou simple), dans notre cas, simple
				fh.setFormatter(new SimpleFormatter());
//				fh.close();
				
			} catch (SecurityException e) {
				
				e.printStackTrace();
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			return logger;
	}

}
