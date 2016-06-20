package tests.test_aomagento.BRANDER.tests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.DesiredCapabilitiesTestNG;
import base.PageObjectException;
import tests.test_aomagento.BRANDER.PO.CatalogPage;
import tests.test_aomagento.BRANDER.PO.HomePage;


public class LiensMenu extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(LiensMenu.class);

	HomePage homePage;
	CatalogPage categoryPage;

	@Test(description="Test de tout les liens du menu principal")
	public void testLiensMenuRwd() throws PageObjectException {

		homePage = new HomePage(driver);

		int nbOfCategories = homePage.getNumberOfCategories();

		for (int i = 1; i < nbOfCategories-1; i++) {
			String categoryTitle = homePage.getNameCategory(i).toLowerCase();
			categoryPage = homePage.goToCategory(i);
			String pageTitle = categoryPage.getH1Title().toLowerCase();
			Assert.assertTrue(categoryTitle.contains(pageTitle));
			homePage = categoryPage.goToHomePage();
		}
	}

	/*@Test(description="Test les liens de tout les sous menu d'un menu principal donné")
	public void testLiensSousMenuRwd() throws PageObjectException, InterruptedException {
		log.info(":: Test Thème Rwd :: accés aux liens du sous menu  ::");

		homePage = new HomePage(driver);

		int nbOfSubCategories = homePage.getNumberOfSubCategories(3);
		
		for (int i = 1; i < nbOfSubCategories; i++) {
			String categoryTitle = homePage.getNameSubCategory(3, i).toLowerCase();
			categoryPage = homePage.goToSubCategory(3, i);
			String pageTitle = categoryPage.getH1Title().toLowerCase();
			Assert.assertTrue(categoryTitle.contains(pageTitle));
			homePage = categoryPage.goToHomePage();
		}

		log.info(":: Test validé :: accés aux liens du menu principal ::");
	}*/

}
