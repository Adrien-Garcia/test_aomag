package tests.test_aomagento.COMPUTEC.tests;

import java.sql.Timestamp;

import base.Constant;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.COMPUTEC.PO.AccountPage;
import tests.test_aomagento.COMPUTEC.PO.HomePage;
import tests.test_aomagento.COMPUTEC.PO.RegisterPage;
import tests.test_aomagento.COMPUTEC.PO.SignInPage;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreationCompteClient extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(CreationCompteClient.class);

	HomePage homePage;
	SignInPage signInPage;
	RegisterPage registerPage;
	AccountPage accountPage;

	@Test(description="Création d'un compte")
	public void testCreationCompte() throws Exception {
		log.info(":: Test COMPUTEC:: création compte utilisateur ::");

		// On crée une @mail au hasard
		java.util.Date date = new java.util.Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		String email = "je.rivoalan" + timestamp.hashCode() + "@jetpulp.fr";
		// String email = "je.rivoalan1"+"@jetpulp.fr";

		// On accéde à la page d'accueil
		homePage = new HomePage(driver);

		// Si un tilisateur est connecte on le deconnecte
		if (homePage.isUserLoggedIn()) {
			homePage.logOutUser();	
		}
		
		// On click sur se créer un compte
		signInPage = homePage.clickConnectionClient();
		registerPage = signInPage.registerNewAccount(Constant.Firstname, Constant.Lastname, email, Constant.Password);
		
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
	}

}
