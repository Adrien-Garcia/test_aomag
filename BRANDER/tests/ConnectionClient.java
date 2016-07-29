package tests.test_aomagento.BRANDER.tests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import tests.test_aomagento.BRANDER.utilitaries.Constant;
import tests.test_aomagento.BRANDER.utilitaries.pageObjectTools;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.BRANDER.PO.AccountPage;
import tests.test_aomagento.BRANDER.PO.HomePage;
import tests.test_aomagento.BRANDER.PO.SignInPage;

public class ConnectionClient extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(ConnectionClient.class);

	HomePage homePage;
	SignInPage signInPage;
	AccountPage accountPage;

	pageObjectTools pageTools;

	@Test(description="Connection client")
	public void testConnectionClient() throws Exception {
		homePage = new HomePage(driver);

		pageTools = new pageObjectTools();
		accountPage=pageTools.connectionClient(homePage.getDriver(), Constant.Email, Constant.Password);
		Assert.assertTrue(accountPage.isUserLoggedIn());
	}

}
