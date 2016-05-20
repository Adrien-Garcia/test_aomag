package tests.test_aomagento.COMPUTEC.tests;

import base.Constant;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.COMPUTEC.PO.AccountPage;
import tests.test_aomagento.COMPUTEC.PO.HomePage;
import tests.test_aomagento.COMPUTEC.PO.SignInPage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ConnectionClient extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(ConnectionClient.class);

	HomePage homePage;
	SignInPage signInPage;
	AccountPage accountPage;


	@Test(description="Connection client")
	public void testConnectionClient() throws Exception {
		log.info(":: Test COMPUTEC :: connection utilisateur ::");
		homePage = new HomePage(driver);

		// On test si l'utilisateur n'est pas déjà connecté
		if (homePage.isUserLoggedIn()) {
			homePage.logOutUser();
		} 
		signInPage = homePage.clickConnectionClient();
		accountPage = signInPage.SignInAction(Constant.Email, Constant.Password);
		Assert.assertTrue(accountPage.isUserLoggedIn());
	}

}
