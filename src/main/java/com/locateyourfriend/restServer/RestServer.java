package com.locateyourfriend.restServer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.locateyourfriend.model.Utilisateur;
import com.locateyourfriend.model.service.ServiceException;
import com.locateyourfriend.model.service.ServiceLocateYourFriends;
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
		String message = "Connection au serveur établie !";
		logger.log(Level.INFO, message);
		return message;
	}
	
	/**
	 * Fonction permettant l'inscription de membres
	 * 
	 * Si une exception est levée lors de l'insertion en base, un logg est enregistré au niveau de l'utilisateurService.
	 * Un message d'erreur est ensuite envoyé à l'utilisateur
	 * @param message
	 * @return
	 */
	@POST
	@Path("/inscription")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	public String inscription(String message){
		logger.log(Level.INFO, "message reçus : " + message);
		Utilisateur u = new Gson().fromJson(message, Utilisateur.class);
		try{
			u = utilisateurService.insertUser(u.getNom(), u.getPrenom(), u.getEmail(), u.getMotDePasse());
		} catch(MongoException e){
			return new Gson().toJson("L'insertion de l'utilisateur a échouée");
		} catch (ServiceException e) {
			return new Gson().toJson(e.getErrorMessage());
		}
		logger.log(Level.INFO, "retour utilisateur après passage base");
		return new Gson().toJson(u);
	}
	
	/**
	 * Fonction permettant l'inscription de membres
	 * 
	 * Si une exception est levée lors de l'insertion en base, un logg est enregistré au niveau de l'utilisateurService.
	 * Un message d'erreur est ensuite envoyé à l'utilisateur
	 * @param message
	 * @return
	 */
	@POST
	@Path("/authentification")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	public String authentification(String message){
		logger.log(Level.INFO, "message reçus : " + message);
		Utilisateur u = new Gson().fromJson(message, Utilisateur.class);
		try {
			u = utilisateurService.authentification(u.getEmail(), u.getMotDePasse());
			return new Gson().toJson(u);
		} catch (ServiceException e) {
			return new Gson().toJson(e);
		}
	}
	
	@POST
	@Path("/localisation")
	@Consumes(MediaType.APPLICATION_JSON)
	public void locationReceiving(String message){
		logger.log(Level.INFO, "message reçus : " + message);
		Utilisateur u = new Gson().fromJson(message, Utilisateur.class);
		for(Utilisateur user : ServiceLocateYourFriends.getInstance().getListeUtils()){
			if(u.getEmail().equals(user.getEmail())){
				ServiceLocateYourFriends.getInstance().getListeUtils().remove(user);
				ServiceLocateYourFriends.getInstance().getListeUtils().add(u);
			}
		}
	}
	
	@POST
	@Path("/listeAmis")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	public String getListAmis(String message){
		logger.log(Level.INFO, "message reçus : " + message);
		Utilisateur u = new Gson().fromJson(message, Utilisateur.class);
		for(Utilisateur user : ServiceLocateYourFriends.getInstance().getListeUtils()){
			if(u.getEmail().equals(user.getEmail())){
				return new Gson().toJson(user.getMesAmis());
			}
		}
		return new Gson().toJson("Utilisateur inconnu");
	}
	
	@POST
	@Path("/getUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUsers(String message){
		logger.log(Level.INFO, "récupérationd e tous les utilisateurs");
		return new Gson().toJson(ServiceLocateYourFriends.getInstance().getListeUtils());
	}
	
	@POST
	@Path("/addAmis")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addFriends(@QueryParam(value = "user1") String user1, @QueryParam(value = "user2") String user2){
		Utilisateur utilisateur1 = utilisateurService.getUtilisateur(user1);
		Utilisateur utilisateur2 = utilisateurService.getUtilisateur(user2);
		try {
			utilisateurService.addAmis(utilisateur1, utilisateur2);
		} catch (MongoException e) {
			return new Gson().toJson("the friendship relation has failed to be inserted");
		} catch (ServiceException e) {
			return new Gson().toJson(e);
		}
		utilisateur1 = utilisateurService.getUtilisateur(user1);
		return new Gson().toJson(utilisateur1);
	}

}
