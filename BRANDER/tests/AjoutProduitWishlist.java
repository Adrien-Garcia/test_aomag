package tests.test_aomagento.BRANDER.tests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.BRANDER.PO.AccountPage;
import tests.test_aomagento.BRANDER.PO.CatalogPage;
import tests.test_aomagento.BRANDER.PO.HomePage;
import tests.test_aomagento.BRANDER.PO.ProductPage;
import tests.test_aomagento.BRANDER.PO.ShoppingCartPage;
import tests.test_aomagento.BRANDER.PO.SignInPage;
import tests.test_aomagento.BRANDER.PO.WishlistPage;
import tests.test_aomagento.BRANDER.utilitaries.Constant;
import tests.test_aomagento.BRANDER.utilitaries.pageObjectTools;

public class AjoutProduitWishlist extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(AjoutProduitWishlist.class);
	
	HomePage homePage;
	SignInPage signInPage;
	AccountPage accountPage;
	WishlistPage wishlistPage;
	CatalogPage catalogPage;
	ProductPage productPage;
	ShoppingCartPage cartPage;
	
	pageObjectTools pageTools;

	@Test(description="Ajout produit à la wishlist ")
	public void testAjoutProduitWishlist() throws Exception {
		homePage = new HomePage(driver);

		// On connecte un utilisateur
		pageTools = new pageObjectTools();
		accountPage=pageTools.connectionClient(homePage.getDriver(), Constant.Email, Constant.Password);
		
		// On va à la liste d'envie afin de la vider
		wishlistPage = accountPage.goToWishlist();
		wishlistPage.emptyWishlist();

		// On va rajouter un produit à la liste d'envie à partir du shop
		homePage = wishlistPage.goToHomePage();
		catalogPage = homePage.goToCategory(1);
		String productName1 = catalogPage.getProductName(0);
		wishlistPage = catalogPage.addProductToWishlist(0);
		
		// Un deuxieme DIFFERENT a partir de la page produit
		homePage = wishlistPage.goToHomePage();
		catalogPage = homePage.goToCategory(2);
		productPage = catalogPage.clickOnProduct(0);
		String productName2 = productPage.getProductName();
		wishlistPage = productPage.addProductToWishlist();

		// On enregistre le nombre de produit dans la liste d'envie après ajout
		int nbProduitApresAjout = wishlistPage.getNumberProductsInWishlist();

		// On verifie qu'un produit a été ajouté
		// 2 si on a ajouté 2 produits différents
		Assert.assertTrue(nbProduitApresAjout == 2);
		
		// On verifie que c'est le bon produit qui s'est ajouté
		Assert.assertTrue(wishlistPage.isProductInWishlist(productName1));
		Assert.assertTrue(wishlistPage.isProductInWishlist(productName2));
		wishlistPage.emptyWishlist();
	}

}
