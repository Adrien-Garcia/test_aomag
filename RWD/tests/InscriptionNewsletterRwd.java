
package tests.test_aomagento.RWD.tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.Constant;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.RWD.PO.AccountPageRwd;
import tests.test_aomagento.RWD.PO.CatalogSearchRwd;
import tests.test_aomagento.RWD.PO.HomePageRwd;
import tests.test_aomagento.RWD.PO.ProductPageRwd;
import tests.test_aomagento.RWD.PO.SignInPageRwd;

public class InscriptionNewsletterRwd extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(InscriptionNewsletterRwd.class);
	HomePageRwd homePage;
	SignInPageRwd signInPage;
	AccountPageRwd accountPage;
	CatalogSearchRwd catalogPage;
	ProductPageRwd productPage;

	@Test(description = "Inscription à la newsletter", priority = 0)
	public void testInscriptionNewsletterRwd() throws Exception {
		log.info(":: Test Thème Rwd :: inscription à la newsletter ::");
		homePage = new HomePageRwd(driver);

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
		log.info(":: Test Thème Rwd :: desinscription à la newsletter ::");
		homePage = new HomePageRwd(driver);

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
