package com.locateyourfriend.restServer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.locateyourfriend.model.Utilisateur;
import com.locateyourfriend.model.service.UtilisateurService;
import com.locateyourfriend.logger.MyLogger;


import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/appli")
public class RestServer{
	
	Logger logger = MyLogger.getInstance();
	UtilisateurService utilisateurService = new UtilisateurService();
	
	@GET
	public String bienvenue()
	{	
		String message = "Connection au serveur Ã©tablie !";
		logger.log(Level.INFO, message);
		return message;
	}
	
	@POST
	@Path("/inscription")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	public String getMessage(String message){
		logger.log(Level.INFO, "message reçus : " + message);
		Utilisateur u = new Gson().fromJson(message, Utilisateur.class);
		u = utilisateurService.insertUser(u.getNom(), u.getPrenom(), u.getEmail(), u.getMotDePasse());
		logger.log(Level.INFO, "retour utilisateur après passage base");
		return new Gson().toJson(u);
	}

}
