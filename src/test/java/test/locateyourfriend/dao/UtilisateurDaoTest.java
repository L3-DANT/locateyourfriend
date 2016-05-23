package test.locateyourfriend.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import com.locateyourfriend.model.Utilisateur;
import com.locateyourfriend.model.service.UtilisateurService;

public class UtilisateurDaoTest {
	
	UtilisateurService utilisateurService;
	
	public UtilisateurDaoTest(){
		utilisateurService = new UtilisateurService();
	}

	@Test
	public void addAndSelectUserTest(){
		Utilisateur user = utilisateurService.insertUser("Robin", "Tritan", "tristan.robin1@gmail.com", "testAvec8caractères");
		assertEquals(user.getEmail(), "tristan.robin1@gmail.com");
		assertEquals(user.getMotDePasse(), "testAvec8caractères");
		assertEquals(user.getNom(), "Robin");
		assertEquals(user.getPrenom(), "Tritan");
	}
	
	@Test
	public void testDaoAmis(){
		Utilisateur user = utilisateurService.insertUser("Robin", "Tritan", "tristan.robin1@gmail.com", "testAvec8caractères");
		Utilisateur user2 = utilisateurService.insertUser("Zitoun", "Khaoula", "khaoula.zitoun@gmail.com", "testAvec8caractères");
		Utilisateur user3 = utilisateurService.insertUser("Caurel", "Brandon", "brandon.caurel@gmail.com", "testAvec8caractères");
		utilisateurService.addAmis(user,  user2);
		System.out.println(user.getMesAmis().getList().get(0));
		System.out.println(user2.getMesAmis().getList().get(0));
		utilisateurService.addAmis(user,  user3);
		user = utilisateurService.getUtilisateur(user.getEmail());
		Utilisateur userToTest = new Utilisateur(user.getMesAmis().getList().get(0));
		System.out.println(userToTest.getEmail());
		System.out.println(user.getEmail());
		assertTrue(user2.equals(userToTest));
		userToTest = new Utilisateur(user.getMesAmis().getList().get(1));
		assertEquals(user3, userToTest);
	}
	
	@After
	public void afterTests(){
		utilisateurService.emptyTable();
	}
}