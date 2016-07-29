package tests.test_aomagento.JETRWD.tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import tests.test_aomagento.JETRWD.utilitaries.Constant;
import tests.test_aomagento.JETRWD.utilitaries.pageObjectTools;
import base.DesiredCapabilitiesTest;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.JETRWD.PO.AccountPageRwd;
import tests.test_aomagento.JETRWD.PO.HomePageRwd;
import tests.test_aomagento.JETRWD.PO.SignInPageRwd;

import java.util.List;

import org.openqa.selenium.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ConnectionClientRwd extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(ConnectionClientRwd.class);

	HomePageRwd homePage;
	SignInPageRwd signInPage;
	AccountPageRwd accountPage;

	pageObjectTools pageTools;

	@Test(description="Connection client")
	public void verifySignInFunction() throws Exception {
		log.info(":: Test Thème Rwd :: connection utilisateur ::");
		homePage = new HomePageRwd(driver);

		// On test si l'utilisateur n'est pas déjà connecté
		pageTools = new pageObjectTools();
		accountPage=pageTools.connectionClient(homePage.getDriver(), Constant.Email, Constant.Password);
		Assert.assertTrue(accountPage.isUserLoggedIn());

		log.info(":: Test validé :: connection utilisateur ::");
	}

}
