package tests.test_aomagento.JETRWD.tests;

import base.DesiredCapabilitiesTest;
import base.DesiredCapabilitiesTestNG;
import base.PageObjectException;
import tests.test_aomagento.JETRWD.PO.AccountPageRwd;
import tests.test_aomagento.JETRWD.PO.CatalogPageRwd;
import tests.test_aomagento.JETRWD.PO.HomePageRwd;
import tests.test_aomagento.JETRWD.PO.SignInPageRwd;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;


public class LiensMenuRwd extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(LiensMenuRwd.class);

	HomePageRwd homePage;
	CatalogPageRwd categoryPage;

	@Test(description="Test de tout les liens du menu principal")
	public void testLiensMenuRwd() throws PageObjectException {
		log.info(":: Test Thème Rwd :: accés aux liens du menu principal ::");

		homePage = new HomePageRwd(driver);

		int nbOfCategories = homePage.getNumberOfCategories();

		for (int i = 0; i < nbOfCategories; i++) {
			String categoryTitle = homePage.getNameCategory(i);
			categoryPage = homePage.goToCategory(i);
			String pageTitle = categoryPage.getH1Title().toLowerCase();
			log.info(pageTitle);
			Assert.assertTrue(categoryTitle.equalsIgnoreCase(pageTitle));
			homePage = categoryPage.goToHomePage();
		}

		log.info(":: Test validé :: accés aux liens du menu principal ::");
	}

//	@Test(description="Test les liens de tout les sous menu d'un menu principal donné")
//	public void testLiensSousMenuRwd() throws PageObjectException, InterruptedException {
//		log.info(":: Test Thème Rwd :: accés aux liens du sous menu  ::");
//
//		homePage = new HomePageRwd(driver);
//
//		int nbOfSubCategories = homePage.getNumberOfSubCategories(3);
//		
//		for (int i = 1; i < nbOfSubCategories; i++) {
//			String categoryTitle = homePage.getNameSubCategory(3, i);
//			categoryPage = homePage.goToSubCategory(3, i);
//			System.out.println(categoryTitle);
//			System.out.println(categoryPage.getH1Title());
//			Assert.assertTrue(categoryTitle.equalsIgnoreCase(categoryPage.getH1Title()));
//			homePage = categoryPage.goToHomePage();
//		}
//
//
//		log.info(":: Test validé :: accés aux liens du menu principal ::");
//	}

}
