package tests.test_aomagento.rwd;

import base.Constant;
import base.DesiredCapabilitiesTest;
import tests.test_aomagento.pageobjects.AddressBookPageRwd;
import tests.test_aomagento.pageobjects.AccountEditPageRwd;
import tests.test_aomagento.pageobjects.AccountPageRwd;
import tests.test_aomagento.pageobjects.HomePageRwd;
import tests.test_aomagento.pageobjects.SignInPageRwd;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class ModifCompteRwd extends DesiredCapabilitiesTest {
	private static Log log = LogFactory.getLog(InscriptionNewsletterRwd.class);

	HomePageRwd homePage;
	SignInPageRwd signInPage;
	AccountPageRwd accountPage;
	AccountEditPageRwd accountInfoPage;
	AddressBookPageRwd accountAdressesPage;

	@Parameters
	public static Collection<Object[]> desiredCapabilities() {
		return Arrays.asList(new Object[][] { { "firefox", "", "ANY" } });
	}

	public ModifCompteRwd(String browser, String version, String plateform) throws MalformedURLException {
		super(browser, version, plateform);
	}

	@Test
	public void testModificationInfosCompteRwd() throws Exception {
		log.info(":: Test Thème Rwd :: modification des infos du compte ::");

		homePage = new HomePageRwd(driver);

		// On connecte l'utilisateur
		if (!homePage.isUserLoggedIn()) {
			signInPage = homePage.clickConnectionClient();
			accountPage = signInPage.SignInAction(Constant.Email, Constant.Password);
			assertTrue(accountPage.isUserLoggedIn());
		}

		// On accéde aux infos
		accountInfoPage = accountPage.goToInfoAccount();

		// On modifie les infos
		accountPage = accountInfoPage.editAccountInfos(Constant.Firstname, Constant.Lastname, Constant.Email);
		Thread.sleep(2000);
		assertTrue("Les informations du compte n'ont pas été modifiées", accountPage.checkIfSuccess());

		log.info(":: Test Validé :: modification des infos du compte ::");
	}

	@Test
	public void testAjoutAdresseSupplementaire() throws Exception {
		log.info(":: Test Thème Rwd :: ajout d'une adresse au compte ::");
		homePage = new HomePageRwd(driver);

		// On connecte l'utilisateur
		if (!homePage.isUserLoggedIn()) {
			signInPage = homePage.clickConnectionClient();
			accountPage = signInPage.SignInAction(Constant.Email, Constant.Password);
			assertTrue(accountPage.isUserLoggedIn());
		}

		// On accéde aux infos
		accountAdressesPage = accountPage.goToAddressMenuLink();
		accountAdressesPage.addNewAddress(Constant.Firstname, Constant.Lastname, Constant.Street, Constant.City,
				Constant.Postcode, Constant.Phone);
		Thread.sleep(2000);
		assertTrue("La nouvele adresse n'a pas été ajoutée", accountAdressesPage.checkIfSuccess());

		log.info(":: Test Validé :: ajout d'une adresse au compte ::");
	}

}
