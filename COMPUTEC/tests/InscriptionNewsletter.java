
package tests.test_aomagento.COMPUTEC.tests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.Constant;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.COMPUTEC.PO.AccountPage;
import tests.test_aomagento.COMPUTEC.PO.CatalogPage;
import tests.test_aomagento.COMPUTEC.PO.HomePage;
import tests.test_aomagento.COMPUTEC.PO.ProductPage;
import tests.test_aomagento.COMPUTEC.PO.SignInPage;

public class InscriptionNewsletter extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(InscriptionNewsletter.class);
	HomePage homePage;
	SignInPage signInPage;
	AccountPage accountPage;
	CatalogPage catalogPage;
	ProductPage productPage;

	@Test(description = "Inscription à la newsletter", priority = 0)
	public void testInscriptionNewsletterRwd() throws Exception {
		log.info(":: Test COMPUTEC:: inscription à la newsletter ::");
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

		log.info(":: Test Validé :: inscription à la newsletter ::");
	}

	@Test(description = "Désinscription à la newsletter", dependsOnMethods = { "testInscriptionNewsletterRwd" })
	public void testDesinscriptionNewsletterRwd() throws Exception {
		log.info(":: Test COMPUTEC :: desinscription à la newsletter ::");
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

		log.info(":: Test Validé :: desinscription à la newsletter ::");
	}
}
