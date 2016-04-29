
package tests.test_aomagento.rwd;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.*;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import base.Constant;
import base.DesiredCapabilitiesTest;
import tests.test_aomagento.pageobjects.AccountPageRwd;
import tests.test_aomagento.pageobjects.CatalogPageRwd;
import tests.test_aomagento.pageobjects.HomePageRwd;
import tests.test_aomagento.pageobjects.ProductPageRwd;
import tests.test_aomagento.pageobjects.SignInPageRwd;

public class InscriptionNewsletterRwd extends DesiredCapabilitiesTest {
	private static Log log = LogFactory.getLog(InscriptionNewsletterRwd.class);
	HomePageRwd homePage;
	SignInPageRwd signInPage;
	AccountPageRwd accountPage;
	CatalogPageRwd catalogPage;
	ProductPageRwd productPage;

	@Parameters
	public static Collection<Object[]> desiredCapabilities() {
		return Arrays.asList(new Object[][] { { "firefox", "", "ANY" } });
	}

	public InscriptionNewsletterRwd(String browser, String version, String plateform) throws MalformedURLException {
		super(browser, version, plateform);
	}

	@Test
	public void testInscriptionNewsletterRwd() throws Exception {
		log.info(":: Test Thème Rwd :: inscription à la newsletter ::");
		homePage = new HomePageRwd(driver);
		
		if (!homePage.isUserLoggedIn()) {
			signInPage = homePage.clickConnectionClient();
			accountPage = signInPage.SignInAction(Constant.Email, Constant.Password);
			assertTrue(accountPage.isUserLoggedIn());
		}
		
		accountPage.subscribeToNewsletter();
		Thread.sleep(1000);
		assertTrue("L'inscription à la newsletter a echoué", accountPage.isUserSubscribedToNewsletter());
		
		log.info(":: Test Validé :: inscription à la newsletter ::");
	}
	@Test
	public void testDesinscriptionNewsletterRwd() throws Exception {
		log.info(":: Test Thème Rwd :: desinscription à la newsletter ::");
		homePage = new HomePageRwd(driver);
		
		if (!homePage.isUserLoggedIn()) {
			signInPage = homePage.clickConnectionClient();
			accountPage = signInPage.SignInAction(Constant.Email, Constant.Password);
			assertTrue(accountPage.isUserLoggedIn());
		}
		
		accountPage.unsubscribeToNewsletter();
		Thread.sleep(1000);
		assertFalse("La desinscription à la newsletter a echoué", accountPage.isUserSubscribedToNewsletter());
		
		log.info(":: Test Validé :: desinscription à la newsletter ::");
	}
}

