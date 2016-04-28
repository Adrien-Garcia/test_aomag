package tests.test_aomagento.rwd;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import base.Constant;
import base.DesiredCapabilitiesTest;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;

import base.DesiredCapabilitiesTest;
import tests.test_aomagento.pageobjects.AccountPageRwd;
import tests.test_aomagento.pageobjects.HomePageRwd;
import tests.test_aomagento.pageobjects.SignInPageRwd;

public class ConnectionClientRwd extends DesiredCapabilitiesTest {
	private static Log log = LogFactory.getLog(ConnectionClientRwd.class);

	HomePageRwd homePage;
	SignInPageRwd signInPage;
	AccountPageRwd accountPage;

	@Parameters
	public static Collection<Object[]> desiredCapabilities() {
		return Arrays.asList(new Object[][] { { "firefox", "", "ANY" } });
	}

	public ConnectionClientRwd(String browser, String version, String plateform) throws MalformedURLException {
		super(browser, version, plateform);
	}

	@Test
	public void verifySignInFunction() throws Exception {
		log.info(":: Test Thème Rwd :: connection utilisateur ::");
		homePage = new HomePageRwd(driver);

		// On test si l'utilisateur n'est pas déjà connecté
		if (!homePage.isUserLoggedIn()) {
			signInPage = homePage.clickConnectionClient();
			accountPage = signInPage.SignInAction(Constant.Email, Constant.Password);
			assertTrue(accountPage.isUserLoggedIn());
		} else {
			log.info("L'utilisateur était déjà connecté");
		}

		log.info(":: Test validé :: connection utilisateur ::");
	}

}
