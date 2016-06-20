
package tests.test_aomagento.BRANDER.tests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.Constant;
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

	@Test(description = "Inscription à la newsletter", priority = 0)
	public void testInscriptionNewsletterRwd() throws Exception {
		homePage = new HomePage(driver);

		if (!homePage.isUserLoggedIn()) {
			signInPage = homePage.clickConnectionClient();
			accountPage = signInPage.SignInAction(Constant.Email, Constant.Password);
			Assert.assertTrue(accountPage.isUserLoggedIn());
		} else{
			accountPage = homePage.goToAccountPage();
		}

		accountPage.subscribeToNewsletter();
		Assert.assertTrue(accountPage.isUserSubscribedToNewsletter());
	}

	@Test(description = "Désinscription à la newsletter", dependsOnMethods = { "testInscriptionNewsletterRwd" })
	public void testDesinscriptionNewsletterRwd() throws Exception {
		homePage = new HomePage(driver);

		if (!homePage.isUserLoggedIn()) {
			signInPage = homePage.clickConnectionClient();
			accountPage = signInPage.SignInAction(Constant.Email, Constant.Password);
			Assert.assertTrue(accountPage.isUserLoggedIn());
		} else {
			accountPage = homePage.goToAccountPage();
		}
		accountPage.unsubscribeToNewsletter();
		Assert.assertFalse(accountPage.isUserSubscribedToNewsletter());
	}
}
