package tests.test_aomagento.COMPUTEC.tests;

import tests.test_aomagento.COMPUTEC.utilitaries.Constant;
import tests.test_aomagento.COMPUTEC.utilitaries.pageObjectTools;
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

	pageObjectTools pageTools;

	@Test(description="Connection client")
	public void testConnectionClient() throws Exception {
		log.info(":: Test COMPUTEC :: connection utilisateur ::");
		homePage = new HomePage(driver);

		// On test si l'utilisateur n'est pas déjà connecté
		pageTools = new pageObjectTools();
		accountPage=pageTools.connectionClient(homePage.getDriver(), Constant.Email, Constant.Password);
		
		Assert.assertTrue(accountPage.isUserLoggedIn());
	}

}
