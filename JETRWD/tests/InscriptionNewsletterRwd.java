
package tests.test_aomagento.JETRWD.tests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import tests.test_aomagento.JETRWD.utilitaries.Constant;
import tests.test_aomagento.JETRWD.utilitaries.pageObjectTools;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.JETRWD.PO.AccountPageRwd;
import tests.test_aomagento.JETRWD.PO.CatalogPageRwd;
import tests.test_aomagento.JETRWD.PO.HomePageRwd;
import tests.test_aomagento.JETRWD.PO.ProductPageRwd;
import tests.test_aomagento.JETRWD.PO.SignInPageRwd;

public class InscriptionNewsletterRwd extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(InscriptionNewsletterRwd.class);
	HomePageRwd homePage;
	SignInPageRwd signInPage;
	AccountPageRwd accountPage;
	CatalogPageRwd catalogPage;
	ProductPageRwd productPage;

	pageObjectTools pageTools;

	@Test(description = "Inscription à la newsletter", priority = 0)
	public void testInscriptionNewsletterRwd() throws Exception {
		log.info(":: Test Thème Rwd :: inscription à la newsletter ::");
		homePage = new HomePageRwd(driver);

		// On test si l'utilisateur n'est pas déjà connecté
		pageTools = new pageObjectTools();
		accountPage=pageTools.connectionClient(homePage.getDriver(), Constant.Email, Constant.Password);

		accountPage.subscribeToNewsletter();
		Assert.assertTrue(accountPage.isUserSubscribedToNewsletter());

		log.info(":: Test Validé :: inscription à la newsletter ::");
	}

	@Test(description = "Désinscription à la newsletter", dependsOnMethods = { "testInscriptionNewsletterRwd" })
	public void testDesinscriptionNewsletterRwd() throws Exception {
		log.info(":: Test Thème Rwd :: desinscription à la newsletter ::");
		homePage = new HomePageRwd(driver);

		// On test si l'utilisateur n'est pas déjà connecté
		pageTools = new pageObjectTools();
		accountPage=pageTools.connectionClient(homePage.getDriver(), Constant.Email, Constant.Password);
		accountPage.unsubscribeToNewsletter();
		
		Assert.assertFalse(accountPage.isUserSubscribedToNewsletter());

		log.info(":: Test Validé :: desinscription à la newsletter ::");
	}
}
