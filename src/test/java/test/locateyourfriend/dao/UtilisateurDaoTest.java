package test.locateyourfriend.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import com.locateyourfriend.model.Utilisateur;
import com.locateyourfriend.model.service.ServiceException;
import com.locateyourfriend.model.service.UtilisateurService;
import com.mongodb.MongoException;

public class UtilisateurDaoTest {
	
	UtilisateurService utilisateurService;
	
	public UtilisateurDaoTest(){
		utilisateurService = new UtilisateurService();
	}

	@Test
	public void addAndSelectUserTest(){
		Utilisateur user;
		try {
			user = utilisateurService.insertUser("Robin", "Tritan", "tristan.robin1@gmail.com", "testAvec8caractères");
			assertEquals(user.getEmail(), "tristan.robin1@gmail.com");
			assertEquals(user.getMotDePasse(), "testAvec8caractères");
			assertEquals(user.getNom(), "Robin");
			assertEquals(user.getPrenom(), "Tritan");
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDaoAmis(){
		Utilisateur user;
		try {
			user = utilisateurService.insertUser("Robin", "Tristan", "tristan.robin1@gmail.com", "testAvec8caractères");
			Utilisateur user2 = utilisateurService.insertUser("Zitoun", "Khaoula", "khaoula.zitoun@gmail.com", "testAvec8caractères");
			Utilisateur user3 = utilisateurService.insertUser("Caurel", "Brandon", "brandon.caurel@gmail.com", "testAvec8caractères");
			assertEquals(0, user.getMesAmis().getList().size());
			utilisateurService.addAmis(user,  user2);
			utilisateurService.addAmis(user,  user3);
			user = utilisateurService.getUtilisateur(user.getEmail());
			assertEquals(2,user.getMesAmis().getList().size());
			user = utilisateurService.getUtilisateur(user.getEmail());
			Utilisateur userToTest = new Utilisateur(user.getMesAmis().getList().get(0));
			assertTrue(user2.equals(userToTest));
			userToTest = new Utilisateur(user.getMesAmis().getList().get(1));
			assertTrue(user3.equals(userToTest));
			assertEquals(2, user.getMesAmis().getList().size());
			user2 = utilisateurService.getUtilisateur(user2.getEmail());
			assertEquals(1, user2.getMesAmis().getList().size());
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetUtilisateurs(){
		try {
			utilisateurService.insertUser("Robin", "Tristan", "tristan.robin1@gmail.com", "testAvec8caractères");
			utilisateurService.insertUser("Zitoun", "Khaoula", "khaoula.zitoun@gmail.com", "testAvec8caractères");
			utilisateurService.insertUser("Caurel", "Brandon", "brandon.caurel@gmail.com", "testAvec8caractères");
			assertEquals(3, utilisateurService.getUtilisateurs().size());
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDelete(){
		try {
			utilisateurService.insertUser("Robin", "Tristan", "tristan.robin1@gmail.com", "testAvec8caractères");
			utilisateurService.deleteUser("tristan.robin1@gmail.com");
			Utilisateur u = utilisateurService.getUtilisateur("tristan.robin1@gmail.com");
			assertEquals(null, u);
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void afterTests(){
		utilisateurService.emptyTable();
	}
}