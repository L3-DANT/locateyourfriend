package com.locateyourfriend.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.locateyourfriend.dao.DaoAmis;
import com.locateyourfriend.dao.DaoUtilisateur;
import com.locateyourfriend.logger.MyLogger;
import com.locateyourfriend.model.Amis;
import com.locateyourfriend.model.Constantes;
import com.locateyourfriend.model.Utilisateur;
import com.locateyourfriend.model.UtilisateurDTO;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCursor;

public class UtilisateurService {

	DaoUtilisateur daoUtilisateur;
	DaoAmis daoAmis;
	Logger logger;

	public UtilisateurService(){
		logger = MyLogger.getInstance();
		daoUtilisateur = new DaoUtilisateur();
		daoAmis = new DaoAmis();
	}
	
	/**
	 * Fonction construisant un utilisateur, elle l'envoie ensuite en base de donn�e et le renvoie
	 * 
	 * Elle met �galent � jour la liste d'utilisateurs apr�s insertion
	 * 
	 * Applique les r�gles de gestion de l'application :
	 * -le nom doit etre non-null, de 2 caract�res minimum compos� de lettres (majs/mins)
	 * -le prenom doit etre non-null, de 2 caract�res minimum compos� de lettres (majs/mins)
	 * -l'email doit �tre non-null et correspondre au regex de mails
	 * -l'email ne doit pas �tre d�j� utilis�
	 * -le mot de passe doit �tre non-null et faire au moins 8 caract�res
	 * 
	 * @param nom
	 * @param prenom
	 * @param email
	 * @param motDePasse
	 * @return
	 * @throws MongoException
	 * @throws ServiceException
	 */

	public Utilisateur insertUser(String nom, String prenom, String email, String motDePasse) throws MongoException, ServiceException{
		String message;
		if(nom == null || nom.length()<2 || nom.matches("\\D")){
			message = "Impossible to insert user, invalid name ";
			logger.log(Level.WARNING, message);
			throw new ServiceException(message, ServiceException.ErrorNumbers.NOM);
		}
		if(prenom == null || prenom.length()<2 || prenom.matches("\\D")){
			message = "Impossible to insert user, invalid first firstname";
			logger.log(Level.WARNING, message);
			throw new ServiceException(message, ServiceException.ErrorNumbers.PRENOM);
		}
		if(email == null || !email.matches("^(.+)@(.+)$")){
			message = "Impossible to insert user, invalid email";
			logger.log(Level.WARNING, message);
			throw new ServiceException(message, ServiceException.ErrorNumbers.EMAIL);
		}
		if(this.getUtilisateur(email)!=null){
			message = "E-mail already in use";
			logger.log(Level.WARNING, message);
			throw new ServiceException(message, ServiceException.ErrorNumbers.EMAIL_UTILISE);
		}
		if(motDePasse == null || motDePasse.length()<8){
			message = "Impossible to insert user, invalid password";
			logger.log(Level.WARNING, message);
			throw new ServiceException(message, ServiceException.ErrorNumbers.MDP);
		}
		Utilisateur user = new Utilisateur(nom, prenom, email, motDePasse);
		try{
			user = daoUtilisateur.addUser(user);
		}catch(MongoException e){
			logger.log(Level.SEVERE, "l'insertion a échoué", e);
			throw e;
		}
		ServiceLocateYourFriends.getInstance().setListeUtils((ArrayList<Utilisateur>) this.getUtilisateurs());
		return user;
	}

	/**
	 * R�cup�re l'utilisateur correspondant � l'email en argument
	 * 
	 * @param email
	 * @return
	 * @throws MongoException
	 */
	public Utilisateur getUtilisateur(String email) throws MongoException{
		Utilisateur user = daoUtilisateur.getUtilisateur(email);
		if(user!=null){
			logger.log(Level.INFO, "user récupéré : " + user.getNom());
			ArrayList<UtilisateurDTO> listeUser = new ArrayList<UtilisateurDTO>();
			try (MongoCursor<Document> cursor = daoAmis.getFriends(user.getEmail())) {
				while (cursor.hasNext()) {
					/**
					 * Pour chaque enregistrement dans la table de jointure, si l'identifiant passé en argument
					 * correspond à un des deux emails de l'enregistrement, on ajoute l'utilisateur possédant cet identifiant 
					 * à la liste d'amis qui sera retournée
					 */
					Document doc = cursor.next();
					if( doc.getString(Constantes.COLONNE_AMI_CIBLE).equals(email)){
						listeUser.add(new UtilisateurDTO(daoUtilisateur.getUtilisateur(doc.getString(Constantes.COLONNE_AMI_ID))));
					}
					if( doc.getString(Constantes.COLONNE_AMI_ID).equals(email)){
						listeUser.add(new UtilisateurDTO(daoUtilisateur.getUtilisateur(doc.getString(Constantes.COLONNE_AMI_CIBLE))));
					}
				}
			}
			/**
			 * cr�ation de l'objet Amis, assignation de la liste d'amis
			 */
			Amis amis = new Amis();
			amis.setList(listeUser);
			user.setMesAmis(amis);
		}
		return user;
	}

	/**
	 * Construit et ajoute une relation d'amiti� en base, elle met �galent � jour la liste d'utilisateurs apr�s insertion. 
	 * 
	 * Applique les r�gles de gestions :
	 * -la relation d'amiti� ne doit pas d�j� exister
	 * -aucun des utilisateurs ne doit �tre null
	 * -les deux utilisateurs ne doivent pas �tre �gaux (m�me email)
	 * 
	 * @param user1
	 * @param user2
	 * @throws MongoException
	 * @throws ServiceException
	 */
	public void addAmis(Utilisateur user1, Utilisateur user2)throws MongoException, ServiceException{
		String message;
		if(daoAmis.friendshipExists(user1, user2)){
			message="friendship relation already existing";
			logger.log(Level.WARNING, message);
			throw new ServiceException(message, ServiceException.ErrorNumbers.AMIS);
		}
		if(user1 == null || user2 == null){
			message = "impossible to insert friendship relation, one of the user is null";
			logger.log(Level.WARNING, message);
			throw new ServiceException(message, ServiceException.ErrorNumbers.UTILISATEUR);
		}
		if(user1.equals(user2)){
			message = "impossible to insert friendship relation, both users are same";
			logger.log(Level.WARNING, message);
			throw new ServiceException(message, ServiceException.ErrorNumbers.EQUAL_UTILISATEUR);
		}
		try{
			daoAmis.insertFriendship(user1.getEmail(), user2.getEmail());
		}catch(MongoException e){
			logger.log(Level.SEVERE, "l'insertion a échoué", e);
			throw e;
		}
		ServiceLocateYourFriends.getInstance().setListeUtils((ArrayList<Utilisateur>) this.getUtilisateurs());
	}

	/**
	 * Fonction d'authentification, renvoie une erreur si l'utilisateur ,n'existe pas
	 * @param email
	 * @param motDePasse
	 * @return
	 * @throws ServiceException
	 */
	public Utilisateur authentification(String email, String motDePasse) throws ServiceException{
		Utilisateur utilisateur = this.getUtilisateur(email);
		logger.log(Level.SEVERE, "dans authentification : " + email + motDePasse);
		if(utilisateur == null){
			String message = "the user does not exist";
			logger.log(Level.WARNING, message);
			throw new ServiceException(message, ServiceException.ErrorNumbers.EMAIL_UTILISE);
		}else{
			String userMdp = utilisateur.getMotDePasse();
			if(userMdp.equals(motDePasse)){
				return utilisateur;
			}
			String message = "The password is not valid";
			logger.log(Level.WARNING, message);
			throw new ServiceException(message, ServiceException.ErrorNumbers.UTILISATEUR);
		}
	}

	/**Fonction de r�cup�ration de tous les utilisateurs
	 * 
	 * @return
	 */
	public List<Utilisateur> getUtilisateurs(){
		List<Utilisateur> listeUtilisateurs = daoUtilisateur.getUtilisateurs();
		List<Utilisateur> listeUtilisateursAvecAmis = new ArrayList<Utilisateur>();
		for(Utilisateur u : listeUtilisateurs){
			listeUtilisateursAvecAmis.add(this.getUtilisateur(u.getEmail()));
		}
		
		return listeUtilisateursAvecAmis;
	}

	/**
	 * Fonction permettant de transformer un utilisateur en objet BasicDBObject pr�t � �tre ins�r� en base de donn�es
	 * @param user
	 * @return
	 */
	public BasicDBObject userToDataBaseObject(Utilisateur user){
		BasicDBObject userDb = new BasicDBObject();
		userDb.append(Constantes.COLONNE_EMAIL, user.getEmail());
		userDb.append(Constantes.COLONNE_NOM, user.getNom());
		userDb.append(Constantes.COLONNE_PRENOM, user.getPrenom());
		userDb.append(Constantes.COLONNE_MDP, user.getMotDePasse());
		userDb.append(Constantes.COLONNE_LOCALISATION, user.getLocalisation().toString());
		return userDb;
	}

	
	/**
	 * Vide les deux tables de l'application
	 */
	public void emptyTable() {
		daoUtilisateur.emptyTable();
	}

	/**
	 * Supprime un utilisateur (utilis� uniquement lors de la modification d'un user)
	 * @param email
	 * @throws ServiceException
	 */
	public void deleteUser(String email) throws ServiceException {
		if(daoUtilisateur.getUtilisateur(email)==null){
			String message = "the user does not exist, impossible to update";
			logger.log(Level.WARNING, message);
			throw new ServiceException(message, ServiceException.ErrorNumbers.UTILISATEUR);
		}
		daoUtilisateur.remove(email);
	}
}