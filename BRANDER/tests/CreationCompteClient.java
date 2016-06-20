package tests.test_aomagento.BRANDER.tests;

import java.sql.Timestamp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.Constant;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.BRANDER.PO.AccountPage;
import tests.test_aomagento.BRANDER.PO.HomePage;
import tests.test_aomagento.BRANDER.PO.RegisterPage;
import tests.test_aomagento.BRANDER.PO.SignInPage;

public class CreationCompteClient extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(CreationCompteClient.class);

	HomePage homePage;
	SignInPage signInPage;
	RegisterPage registerPage;
	AccountPage accountPage;

	@Test(description="Création d'un compte")
	public void testCreationCompte() throws Exception {

		// On crée une @mail au hasard
		java.util.Date date = new java.util.Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		String email = "je.rivoalan" + timestamp.hashCode() + "@jetpulp.fr";
//		 String email = "je.rivoalan"+"@jetpulp.fr";

		// On accéde à la page d'accueil
		homePage = new HomePage(driver);

		// Si un tilisateur est connecte on le deconnecte
		if (homePage.isUserLoggedIn()) {
			homePage.logOutUser();	
		}
		
		// On click sur se créer un compte
		signInPage = homePage.clickConnectionClient();
		accountPage = signInPage.registerNewAccount(Constant.Firstname, Constant.Lastname, email, Constant.Password);
		Thread.sleep(20);
		// On se déconnecte
		homePage = accountPage.logOutUser();

		// puis on tente de se reconnecter
		signInPage = homePage.clickConnectionClient();
		accountPage = signInPage.SignInAction(email, Constant.Password);
		Assert.assertTrue(accountPage.isUserLoggedIn());
	}

}
