package tests.test_aomagento.BRANDER.tests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import tests.test_aomagento.BRANDER.utilitaries.Constant;
import tests.test_aomagento.BRANDER.utilitaries.pageObjectTools;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.BRANDER.PO.AccountPage;
import tests.test_aomagento.BRANDER.PO.CatalogPage;
import tests.test_aomagento.BRANDER.PO.ComparatorPage;
import tests.test_aomagento.BRANDER.PO.HomePage;
import tests.test_aomagento.BRANDER.PO.ProductPage;
import tests.test_aomagento.BRANDER.PO.ShoppingCartPage;
import tests.test_aomagento.BRANDER.PO.SignInPage;
import tests.test_aomagento.BRANDER.PO.WishlistPage;

public class AjoutProduitComparateur extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(AjoutProduitComparateur.class);

	HomePage homePage;
	SignInPage signInPage;
	AccountPage accountPage;
	WishlistPage wishlistPage;
	CatalogPage catalogPage;
	ComparatorPage comparatorPage;
	ProductPage productPage;
	ShoppingCartPage cartPage;

	pageObjectTools pageTools;

	@Test(description="Ajout d'un produit au comparateur")
	public void testAjoutProduitComparateur() throws Exception {
		homePage = new HomePage(driver);

		pageTools = new pageObjectTools();
		accountPage=pageTools.connectionClient(homePage.getDriver(), Constant.Email, Constant.Password);

		// On accéde à une catégorie du shop
		catalogPage = homePage.goToCategory(1);

		// On ajoute 2 produits au comparateur directement du shop, on instancie
		// leur nom dans deux variables
		productPage = catalogPage.clickOnProduct(0);
		productPage.addProductToComparator();
		String produit1 = productPage.getProductName();
		
		homePage = productPage.goToHomePage();
		catalogPage = homePage.goToCategory(1);
		productPage = catalogPage.clickOnProduct(1);
		productPage.addProductToComparator();
		String produit2 = productPage.getProductName();

		// On vérifie qu'ils aient été ajouté
		Assert.assertTrue(productPage.getNumberProductsInComparator() == 2);
		log.info("Les 2 produits ont bien été ajoutés");

		// On ouvre le comparateur
		comparatorPage = productPage.openComparator();
		// On vérifie qu'il ya bien 2 produits dans le comprateur
		Assert.assertTrue(comparatorPage.getNumberProductsInComparator() == 2);
		// Et que ce sont ceux qu'on a ajouté
		Assert.assertTrue(comparatorPage.isProductInComparator(produit1));
		Assert.assertTrue(comparatorPage.isProductInComparator(produit2));

		comparatorPage.emptyComparator();
	}


}