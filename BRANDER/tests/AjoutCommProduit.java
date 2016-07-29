package tests.test_aomagento.BRANDER.tests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import tests.test_aomagento.BRANDER.utilitaries.Constant;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.BRANDER.PO.AccountPage;
import tests.test_aomagento.BRANDER.PO.CatalogPage;
import tests.test_aomagento.BRANDER.PO.HomePage;
import tests.test_aomagento.BRANDER.PO.ProductPage;
import tests.test_aomagento.BRANDER.PO.SignInPage;

public class AjoutCommProduit extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(AjoutCommProduit.class);

	HomePage homePage;
	SignInPage signInPage;
	AccountPage accountPage;
	CatalogPage catalogPage;
	ProductPage productPage;

	@Test(description = "Ajout commentaire produit")
	public void testAjoutCommProduit() throws Exception {
		homePage = new HomePage(driver);

		// On va sur un produit d'une catégorie du catalogue
		catalogPage = homePage.goToCategory(1);
		productPage = catalogPage.clickOnProduct(0);

		// On lui ajoute un commentaire
		productPage.addReviewToProduct(Constant.Review, Constant.ReviewTitle, Constant.Firstname);

		// On test que le commentaire a bien été ajouté en modération
		Assert.assertTrue(productPage.checkIfSuccess());
	}
}
