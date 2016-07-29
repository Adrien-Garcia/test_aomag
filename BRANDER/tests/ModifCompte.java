package tests.test_aomagento.BRANDER.tests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import tests.test_aomagento.BRANDER.utilitaries.Constant;
import tests.test_aomagento.BRANDER.utilitaries.pageObjectTools;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.BRANDER.PO.AccountEditPage;
import tests.test_aomagento.BRANDER.PO.AccountPage;
import tests.test_aomagento.BRANDER.PO.AddressBookPage;
import tests.test_aomagento.BRANDER.PO.HomePage;
import tests.test_aomagento.BRANDER.PO.SignInPage;

public class ModifCompte extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(InscriptionNewsletter.class);

	HomePage homePage;
	SignInPage signInPage;
	AccountPage accountPage;
	AccountEditPage accountInfoPage;
	AddressBookPage accountAdressesPage;
	
	pageObjectTools pageTools;

	@Test(description="Modification d'un compte client (changement coordonnées)", priority=0)
	public void testModificationInfosCompteRwd() throws Exception {
		homePage = new HomePage(driver);

		// On connecte l'utilisateur
		pageTools = new pageObjectTools();
		accountPage=pageTools.connectionClient(homePage.getDriver(), Constant.Email, Constant.Password);

		// On accéde aux infos
		accountInfoPage = accountPage.goToInfoAccount();

		// On modifie les infos
		accountPage = accountInfoPage.editAccountInfos(Constant.Firstname, Constant.Lastname, Constant.Email);
		
		Assert.assertTrue(accountPage.checkIfSuccess());
	}

	@Test(description="Modification d'un compte client (ajout addresse)", priority=1)
	public void testAjoutAdresseSupplementaire() throws Exception {
		homePage = new HomePage(driver);

		// On connecte l'utilisateur
		pageTools = new pageObjectTools();
		accountPage=pageTools.connectionClient(homePage.getDriver(), Constant.Email, Constant.Password);

		// On accéde aux infos
		accountAdressesPage = accountPage.goToAddressMenuLink();
		accountAdressesPage.addNewAddress(Constant.Firstname, Constant.Lastname, Constant.Street, Constant.City,
				Constant.Postcode, Constant.Phone);
		Assert.assertTrue(accountAdressesPage.checkIfSuccess());
	}
	
	/*@Test(description="Modification d'un compte client (changement MDP)", priority=2)
	public void testModifMotDePasse() throws Exception {
		log.info(":: Test COMPUTEC :: modification  ::");
		homePage = new HomePage(driver);

		// On connecte l'utilisateur
		pageTools = new pageObjectTools();
		accountPage=pageTools.connectionClient(homePage.getDriver(), Constant.Email, Constant.Password);

		// On accéde aux infos et on change le mdp
		accountInfoPage = accountPage.goToInfoAccount();
		accountPage = accountInfoPage.changePassword(Constant.Password, Constant.PasswordChange);
		Assert.assertTrue(accountAdressesPage.checkIfSuccess());
		
		homePage = accountPage.logOutUser();
		
		signInPage = homePage.clickConnectionClient();
		accountPage = signInPage.SignInAction(Constant.Email, Constant.PasswordChange);
		
		// On remet le mdp comme avant
		accountInfoPage = accountPage.goToInfoAccount();
		accountPage = accountInfoPage.changePassword(Constant.PasswordChange, Constant.Password);
	
		Assert.assertTrue(accountAdressesPage.checkIfSuccess());
	}*/

}
