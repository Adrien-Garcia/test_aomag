package tests.test_aomagento.COMPUTEC.tests;

import base.Constant;
import base.DesiredCapabilitiesTest;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.RWD.PO.AccountEditPageRwd;
import tests.test_aomagento.RWD.PO.AccountPageRwd;
import tests.test_aomagento.RWD.PO.AddressBookPageRwd;
import tests.test_aomagento.RWD.PO.HomePageRwd;
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

public class ModifCompteRwd extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(InscriptionNewsletterRwd.class);

	HomePageRwd homePage;
	SignInPageRwd signInPage;
	AccountPageRwd accountPage;
	AccountEditPageRwd accountInfoPage;
	AddressBookPageRwd accountAdressesPage;

	@Test(description="Modification d'un compte client (changement coordonnées)", priority=0)
	public void testModificationInfosCompteRwd() throws Exception {
		log.info(":: Test Thème Rwd :: modification des infos du compte ::");

		homePage = new HomePageRwd(driver);

		// On connecte l'utilisateur
		if (!homePage.isUserLoggedIn()) {
			signInPage = homePage.clickConnectionClient();
			accountPage = signInPage.SignInAction(Constant.Email, Constant.Password);
			Assert.assertTrue(accountPage.isUserLoggedIn());
		} else {
			accountPage = homePage.goToAccountPage();
		}

		// On accéde aux infos
		accountInfoPage = accountPage.goToInfoAccount();

		// On modifie les infos
		accountPage = accountInfoPage.editAccountInfos(Constant.Firstname, Constant.Lastname, Constant.Email);
		
		Assert.assertTrue(accountPage.checkIfSuccess());

		log.info(":: Test Validé :: modification des infos du compte ::");
	}

	@Test(description="Modification d'un compte client (ajout addresse)", priority=1)
	public void testAjoutAdresseSupplementaire() throws Exception {
		log.info(":: Test Thème Rwd :: ajout d'une adresse au compte ::");
		homePage = new HomePageRwd(driver);

		// On connecte l'utilisateur
		if (!homePage.isUserLoggedIn()) {
			signInPage = homePage.clickConnectionClient();
			accountPage = signInPage.SignInAction(Constant.Email, Constant.Password);
			Assert.assertTrue(accountPage.isUserLoggedIn());
		}else{
			accountPage = homePage.goToAccountPage();
		}

		// On accéde aux infos
		accountAdressesPage = accountPage.goToAddressMenuLink();
		accountAdressesPage.addNewAddress(Constant.Firstname, Constant.Lastname, Constant.Street, Constant.City,
				Constant.Postcode, Constant.Phone);
		Assert.assertTrue(accountAdressesPage.checkIfSuccess());

		log.info(":: Test Validé :: ajout d'une adresse au compte ::");
	}
	
	/*@Test(description="Modification d'un compte client (changement MDP)", priority=2)
	public void testModifMotDePasse() throws Exception {
		log.info(":: Test Thème Rwd :: modification  ::");
		homePage = new HomePageRwd(driver);

		// On connecte l'utilisateur
		if (!homePage.isUserLoggedIn()) {
			signInPage = homePage.clickConnectionClient();
			accountPage = signInPage.SignInAction(Constant.Email, Constant.Password);
			Assert.assertTrue(accountPage.isUserLoggedIn());
		}else{
			accountPage = homePage.goToAccountPage();
		}

		// On accéde aux infos
		accountInfoPage = accountPage.goToInfoAccount();
		accountPage = accountInfoPage.changePassword(Constant.Password, Constant.PasswordChange);
		Assert.assertTrue(accountAdressesPage.checkIfSuccess());
		
		homePage = accountPage.logOutUser();
		
		signInPage = homePage.clickConnectionClient();
		accountPage = signInPage.SignInAction(Constant.Email, Constant.PasswordChange);
		
		accountInfoPage = accountPage.goToInfoAccount();
		accountPage = accountInfoPage.changePassword(Constant.PasswordChange, Constant.Password);
	
		Assert.assertTrue(accountAdressesPage.checkIfSuccess());

		log.info(":: Test Validé :: ajout d'une adresse au compte ::");
	}*/

}
