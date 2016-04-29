package tests.test_aomagento.rwd;

import java.sql.Timestamp;
import java.util.Date;

import base.Constant;
import base.DesiredCapabilitiesTest;
import tests.test_aomagento.pageobjects.AccountPageRwd;
import tests.test_aomagento.pageobjects.HomePageRwd;
import tests.test_aomagento.pageobjects.RegisterPageRwd;
import tests.test_aomagento.pageobjects.SignInPageRwd;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class CreationCompteClientRwd extends DesiredCapabilitiesTest {
	private static Log log = LogFactory.getLog(CreationCompteClientRwd.class);

	HomePageRwd homePage;
	SignInPageRwd signInPage;
	RegisterPageRwd registerPage;
	AccountPageRwd accountPage;

	@Parameters
	public static Collection<Object[]> desiredCapabilities() {
		return Arrays.asList(new Object[][] { { "firefox", "", "ANY" } });
	}

	public CreationCompteClientRwd(String browser, String version, String plateform) throws MalformedURLException {
		super(browser, version, plateform);
	}

	@Test
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

		// On crée le compte avec l'adresse fictive ce qui nous redirige vers la
		// page de compte
		accountPage = registerPage.createAccount(Constant.Firstname, Constant.Lastname, email, Constant.Password,
				false);
		assertTrue(accountPage.checkIfSuccess());

		// On se déconnecte
		homePage = accountPage.logOutUser();

		// puis on tente de se reconnecter
		signInPage = homePage.clickConnectionClient();
		accountPage = signInPage.SignInAction(email, Constant.Password);
		assertTrue("Le nouvel utilisateur s'est bien connecté", accountPage.isUserLoggedIn());
		
		log.info(":: Test Validé :: création compte utilisateur ::");
	}

}
