package com.locateyourfriend.restServer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.locateyourfriend.model.Utilisateur;
import com.locateyourfriend.model.service.ServiceException;
import com.locateyourfriend.model.service.UtilisateurService;
import com.mongodb.MongoException;
import com.locateyourfriend.logger.MyLogger;


import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/appli")
public class RestServer{
	
	Logger logger = MyLogger.getInstance();
	UtilisateurService utilisateurService = new UtilisateurService();
	
	/**
	 * Fonction de test
	 * @return
	 */
	@GET
	@Path("/test")
	public String bienvenue()
	{	
		String message = "Connection au serveur �tablie !";
		logger.log(Level.INFO, message);
		return message;
	}
	
	@GET
	@Path("/testlog")
	public String testlogs()
	{	
		String message = "Test des logs en cours";
		logger.log(Level.INFO, message);
		return message;
	}
	
	/**
	 * Fonction permettant l'inscription de membres
	 * 
	 * Si une exception est lev�e lors de l'insertion en base, un logg est enregistr� au niveau de l'utilisateurService.
	 * Un message d'erreur est ensuite envoy� � l'utilisateur
	 * @param message
	 * @return
	 */
	@POST
	@Path("/inscription")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	public String inscription(String message){
		logger.log(Level.INFO, "message re�us : " + message);
		Utilisateur u = new Gson().fromJson(message, Utilisateur.class);
		try{
			u = utilisateurService.insertUser(u.getNom(), u.getPrenom(), u.getEmail(), u.getMotDePasse());
		} catch(MongoException e){
			return new Gson().toJson("L'insertion de l'utilisateur a échouée");
		} catch (ServiceException e) {
			return new Gson().toJson(e.getErrorMessage());
		}
		logger.log(Level.INFO, "retour utilisateur apr�s passage base");
		return new Gson().toJson(u);
	}
	
	/*@POST
	@Path("/inscription")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String locationReceiving(String message){
		logger.log(Level.INFO, "message re�us : " + message);
		Utilisateur u = new Gson().fromJson(message, Utilisateur.class);
		try{
			u = utilisateurService.insertUser(u.getNom(), u.getPrenom(), u.getEmail(), u.getMotDePasse());
		} catch(MongoException e){
			return new Gson().toJson("L'insertion de l'utilisateur a �chou�e");
		} catch (ServiceException e) {
			return new Gson().toJson(e.getErrorMessage());
		}
		for
		return "yolo";
	}*/
	

}
