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
import com.locateyourfriend.model.UtilisateurDTO;
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
		String message = "Connection au serveur ÔøΩtablie !";
		logger.log(Level.INFO, message);
		return message;
	}
	

	/**
	 * Fonction permettant l'inscription de membres
	 * 
	 * Si une exception est lev√©e lors de l'insertion en base, un logg est enregistr√© au niveau de l'utilisateurService.
	 * Un message d'erreur est ensuite envoy√© √† l'utilisateur
	 * @param message
	 * @return
	 */
	@POST
	@Path("/inscription")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	public String inscription(String message){
		logger.log(Level.INFO, "message re√ßus : " + message);
		Utilisateur u = new Gson().fromJson(message, Utilisateur.class);
		try{
			u = utilisateurService.insertUser(u.getNom(), u.getPrenom(), u.getEmail(), u.getMotDePasse());
		} catch(MongoException e){
			return new Gson().toJson("L'insertion de l'utilisateur a √©chou√©e");
		} catch (ServiceException e) {
			return new Gson().toJson(e.getErrorMessage());
		}
		logger.log(Level.INFO, "retour utilisateur apr√®s passage base");
		return new Gson().toJson(u);
	}
	
	/**
	 * Fonction permettant la modification de membres
	 * 
	 * La fonction se base sur l'adresse mail comme clef primaire, c'est donc le seul champs que l'IOS empeche de modifier
	 * 
	 * Si l'utilisateur n'est pas trouvÈ en base, l'utilisateurService renvoie un exception
	 * 
	 * Si une exception est lev√©e lors de l'insertion en base, un logg est enregistr√© au niveau de l'utilisateurService.
	 * Un message d'erreur est ensuite envoy√© √† l'utilisateur
	 * @param message
	 * @return
	 */
	@POST
	@Path("/modification")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	public String modifierUtil(String message){
		logger.log(Level.INFO, "message reÁus : " + message);
		Utilisateur u = new Gson().fromJson(message, Utilisateur.class);
		try{
			utilisateurService.deleteUser(u.getEmail());
			u = utilisateurService.insertUser(u.getNom(), u.getPrenom(), u.getEmail(), u.getMotDePasse());
		} catch(MongoException e){
			return new Gson().toJson("La modification de l'utilisateur a ÈchouÈ");
		} catch (ServiceException e) {
			return new Gson().toJson(e.getErrorMessage());
		}
		logger.log(Level.INFO, "retour utilisateur aprËs update");
		return new Gson().toJson(u);
	}
	
	/**
	 * Fonction permettant l'authentification de membres
	 * 
	 * Si une exception est lev√©e lors de l'insertion en base, un logg est enregistr√© au niveau de l'utilisateurService.
	 * Un message d'erreur est ensuite envoy√© √† l'utilisateur
	 * @param message
	 * @return
	 */
	@POST
	@Path("/authentification")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	public String authentification(String message){
		logger.log(Level.INFO, "message re√ßus : " + message);
		Utilisateur u = new Gson().fromJson(message, Utilisateur.class);
		System.out.println("Les infos que je traite cote serveur : " + u.getEmail() + u.getMotDePasse());
		try {
			u = utilisateurService.authentification(u.getEmail(), u.getMotDePasse());
			return new Gson().toJson(u);
		} catch (ServiceException e) {
			return new Gson().toJson(e);
		}
	}
	
	
	/**
	 * 
	 * Fonction permettant de partager sa localisation au serveur pour qu'elle soit ensuite rÈpercutÈe
	 * 
	 * @param message
	 * @return
	 */
	@POST
	@Path("/localisation")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String locationReceiving(String message){
		logger.log(Level.INFO, "message re√ßus : " + message);
		Utilisateur u = new Gson().fromJson(message, Utilisateur.class);
		for(Utilisateur user : ServiceLocateYourFriends.getInstance().getListeUtils()){
			if(u.getEmail().equals(user.getEmail())){
				user.setLocalisation(u.getLocalisation());
				logger.log(Level.INFO, "Modifications bdd effectu√©es");
				
				return new Gson().toJson(u);
			}
		}
		return "utilisateur non-present";//a changer

	}
	
	/**
	 * Fonction permettant ‡ l'IOS de rÈclamer les localisations de ses amis 
	 * 
	 * Les utilisateurs sont envoyÈs sous la forme d'utilisateurs DTO : ne possÈdant ni amis ni mot de passe
	 * 
	 * @param message
	 * @return
	 */
	
	@POST
	@Path("/listeAmis")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	public String getListAmis(String message){
		logger.log(Level.INFO, "message re√ßus : " + message);
		Utilisateur u = new Gson().fromJson(message, Utilisateur.class);
		for(Utilisateur user : ServiceLocateYourFriends.getInstance().getListeUtils()){
			if(u.getEmail().equals(user.getEmail())){
				return new Gson().toJson(user);
			}
		}
		return new Gson().toJson("Utilisateur inconnu");
	}
	
	/**
	 * Cette fonction permet de rÈcupÈrer tous les utilisateurs
	 * 
	 * Les utilisateurs sont envoyÈs sous forme d'utilisateursDTO : ne possÈdant ni amis ni mot de passe
	 * @param message
	 * @return
	 */
	@POST
	@Path("/getUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUsers(String message){
		logger.log(Level.INFO, "r√©cup√©rationd e tous les utilisateurs");
		return new Gson().toJson(UtilisateurDTO.toUtilisateurDTO(ServiceLocateYourFriends.getInstance().getListeUtils()));
	}
	
	/**
	 * Cette fonction permet d'ahouter en base de donnÈe une relation d'amitiÈ entre deux utilisateurs
	 * 
	 * @param user1
	 * @param user2
	 * @return
	 */
	
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