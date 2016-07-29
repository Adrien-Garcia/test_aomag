
package tests.test_aomagento.BRANDER.tests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import tests.test_aomagento.BRANDER.utilitaries.Constant;
import tests.test_aomagento.BRANDER.utilitaries.pageObjectTools;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.BRANDER.PO.AccountPage;
import tests.test_aomagento.BRANDER.PO.CatalogPage;
import tests.test_aomagento.BRANDER.PO.HomePage;
import tests.test_aomagento.BRANDER.PO.ProductPage;
import tests.test_aomagento.BRANDER.PO.SignInPage;

public class InscriptionNewsletter extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(InscriptionNewsletter.class);
	HomePage homePage;
	SignInPage signInPage;
	AccountPage accountPage;
	CatalogPage catalogPage;
	ProductPage productPage;
	
	pageObjectTools pageTools;

	@Test(description = "Inscription à la newsletter")
	public void testInscriptionNewsletterRwd() throws Exception {
		homePage = new HomePage(driver);

		pageTools = new pageObjectTools();
		accountPage=pageTools.connectionClient(homePage.getDriver(), Constant.Email, Constant.Password);

		accountPage.subscribeToNewsletter();
		Assert.assertTrue(accountPage.isUserSubscribedToNewsletter());
	}

	@Test(description = "Désinscription à la newsletter")
	public void testDesinscriptionNewsletterRwd() throws Exception {
		homePage = new HomePage(driver);

		pageTools = new pageObjectTools();
		accountPage=pageTools.connectionClient(homePage.getDriver(), Constant.Email, Constant.Password);
		
		accountPage.unsubscribeToNewsletter();
		Assert.assertFalse(accountPage.isUserSubscribedToNewsletter());
	}
}
