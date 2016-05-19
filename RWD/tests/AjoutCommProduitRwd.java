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

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Collection;

import base.Constant;
import base.DesiredCapabilitiesTest;
import base.DesiredCapabilitiesTestNG;
import base.PageObjectException;
import tests.test_aomagento.RWD.PO.AccountPageRwd;
import tests.test_aomagento.RWD.PO.CatalogPageRwd;
import tests.test_aomagento.RWD.PO.HomePageRwd;
import tests.test_aomagento.RWD.PO.ProductPageRwd;
import tests.test_aomagento.RWD.PO.SignInPageRwd;

public class AjoutCommProduitRwd extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(AjoutCommProduitRwd.class);
	
	HomePageRwd homePage;
	SignInPageRwd signInPage;
	AccountPageRwd accountPage;
	CatalogPageRwd catalogPage;
	ProductPageRwd productPage;

	@Test(description="Ajout commentaire produit")
	public void testAjoutCommProduitRwd() throws Exception {
		log.info(":: Test Thème Rwd :: ajout de commentaire à un produit ::");
		
		homePage = new HomePageRwd(driver);

		// On va sur un produit d'une catégorie du catalogue
		catalogPage = homePage.goToCategory(0);
		productPage = catalogPage.clickOnProduct(0);
		
		// On lui ajoute un commentaire
		productPage.addReviewToProduct(Constant.Review, Constant.ReviewTitle, Constant.Firstname);
		
		// On test que le commentaire a bien été ajouté en modération
		Assert.assertTrue(productPage.checkIfSuccess());
		
		log.info("Test validé :: ajout de commentaire à un produit :: ");
	}
}
