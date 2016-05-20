package tests.test_aomagento.COMPUTEC.tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import base.Constant;
import base.DesiredCapabilitiesTest;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.RWD.PO.AccountPageRwd;
import tests.test_aomagento.RWD.PO.HomePageRwd;
import tests.test_aomagento.RWD.PO.SignInPageRwd;

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


	@Test(description="Connection client")
	public void verifySignInFunction() throws Exception {
		log.info(":: Test Thème Rwd :: connection utilisateur ::");
		homePage = new HomePageRwd(driver);

		// On test si l'utilisateur n'est pas déjà connecté
		if (homePage.isUserLoggedIn()) {
			homePage.logOutUser();
		} 
		signInPage = homePage.clickConnectionClient();
		accountPage = signInPage.SignInAction(Constant.Email, Constant.Password);
		Assert.assertTrue(accountPage.isUserLoggedIn());

		log.info(":: Test validé :: connection utilisateur ::");
	}

}
