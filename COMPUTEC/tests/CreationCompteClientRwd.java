package tests.test_aomagento.COMPUTEC.tests;

import java.sql.Timestamp;
import java.util.Date;

import base.Constant;
import base.DesiredCapabilitiesTestNG;
import base.PageObjectException;
import tests.test_aomagento.RWD.PO.AccountPageRwd;
import tests.test_aomagento.RWD.PO.HomePageRwd;
import tests.test_aomagento.RWD.PO.RegisterPageRwd;
import tests.test_aomagento.RWD.PO.SignInPageRwd;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreationCompteClientRwd extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(CreationCompteClientRwd.class);

	HomePageRwd homePage;
	SignInPageRwd signInPage;
	RegisterPageRwd registerPage;
	AccountPageRwd accountPage;

	@Test(description="Création d'un compte")
	public void testCreationCompte() throws Exception {
		log.info(":: Test Thème Rwd :: création compte utilisateur ::");

		// On crée une @mail au hasard
		java.util.Date date = new java.util.Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		String email = "je.rivoalan" + timestamp.hashCode() + "@jetpulp.fr";
		// String email = "je.rivoalan1"+"@jetpulp.fr";

		// On accéde à la page d'accueil
		homePage = new HomePageRwd(driver);

		// Si un tilisateur est connecte on le deconnecte
		if (homePage.isUserLoggedIn()) {
			homePage.logOutUser();	
		}
		
		// On click sur se créer un compte
		signInPage = homePage.clickConnectionClient();
		registerPage = signInPage.clickCreateAccount();

		// On crée le compte avec l'adresse générée ce qui nous redirige vers la
		// page de compte
		accountPage = registerPage.createAccount(Constant.Firstname, Constant.Lastname, email, Constant.Password,
				false);

		// On se déconnecte
		homePage = accountPage.logOutUser();

		// puis on tente de se reconnecter
		signInPage = homePage.clickConnectionClient();
		accountPage = signInPage.SignInAction(email, Constant.Password);
		Assert.assertTrue(accountPage.isUserLoggedIn());
		
		log.info(":: Test Validé :: création compte utilisateur ::");
	}

}
