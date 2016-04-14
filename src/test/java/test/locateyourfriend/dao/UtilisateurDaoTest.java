package test.locateyourfriend.dao;

import static org.junit.Assert.*;

import java.util.logging.Level;

import org.junit.After;
import org.junit.Test;

import com.locateyourfriend.dao.DaoUtilisateur;
import com.locateyourfriend.daoInterface.DaoUtilisateurInterface;
import com.locateyourfriend.logger.MyLogger;
import com.locateyourfriend.model.Utilisateur;

public class UtilisateurDaoTest {
	
	DaoUtilisateurInterface daoUtilisateur;
	
	public UtilisateurDaoTest(){
		daoUtilisateur = new DaoUtilisateur();
	}

	@Test
	public void addAndSelectUserTest(){
		Utilisateur user = new Utilisateur("robin", "tritan", "tristan.robin1@gmail.com", "test");
		daoUtilisateur.addUser(user);
		Utilisateur user2 = daoUtilisateur.getUtilisateur(user.getEmail());
		assertEquals(user.getEmail(), user2.getEmail());
		assertEquals(user.getMotDePasse(), user2.getMotDePasse());
		assertEquals(user.getNom(), user2.getNom());
		assertEquals(user.getPrenom(), user2.getPrenom());
	}
	
	@After
	public void afterTests(){
		daoUtilisateur.emptyTable();
	}
}