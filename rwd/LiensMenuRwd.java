package tests.test_aomagento.rwd;

import base.DesiredCapabilitiesTest;
import base.PageObjectException;
import tests.test_aomagento.pageobjects.AccountPageRwd;
import tests.test_aomagento.pageobjects.CatalogPageRwd;
import tests.test_aomagento.pageobjects.HomePageRwd;
import tests.test_aomagento.pageobjects.SignInPageRwd;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.*;

public class LiensMenuRwd extends DesiredCapabilitiesTest {
	private static Log log = LogFactory.getLog(LiensMenuRwd.class);

	HomePageRwd homePage;
	CatalogPageRwd categoryPage;

	@Parameters
	public static Collection<Object[]> desiredCapabilities() {
		return Arrays.asList(new Object[][] { { "firefox", "", "ANY" } });
	}

	public LiensMenuRwd(String browser, String version, String plateform) throws MalformedURLException {
		super(browser, version, plateform);
	}

	@Test
	public void testLiensMenuRwd() throws PageObjectException {
		log.info(":: Test Thème Rwd :: accés aux liens du menu principal ::");

		homePage = new HomePageRwd(driver);

		int nbOfCategories = homePage.getNumberOfCategories();

		for (int i = 0; i < nbOfCategories; i++) {
			String categoryTitle = homePage.getNameCategory(i);
			categoryPage = homePage.goToCategory(i);
			assertTrue(categoryTitle.equalsIgnoreCase(categoryPage.getH1Title()));
			homePage = categoryPage.goToHomePage();
		}

		log.info(":: Test validé :: accés aux liens du menu principal ::");
	}

	@Test
	public void testLiensSousMenuRwd() throws PageObjectException, InterruptedException {
		log.info(":: Test Thème Rwd :: accés aux liens du sous menu  ::");

		homePage = new HomePageRwd(driver);

		int nbOfSubCategories = homePage.getNumberOfSubCategories(3);
		
		for (int i = 1; i < nbOfSubCategories; i++) {
			String categoryTitle = homePage.getNameSubCategory(3, i);
			categoryPage = homePage.goToSubCategory(3, i);
			assertTrue(categoryTitle.equalsIgnoreCase(categoryPage.getH1Title()));
			homePage = categoryPage.goToHomePage();
		}


		log.info(":: Test validé :: accés aux liens du menu principal ::");
	}

}
