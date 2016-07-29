package tests.test_aomagento.COMPUTEC.tests;

import tests.test_aomagento.COMPUTEC.utilitaries.Constant;
import tests.test_aomagento.COMPUTEC.utilitaries.pageObjectTools;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.COMPUTEC.PO.AccountPage;
import tests.test_aomagento.COMPUTEC.PO.CatalogPage;
import tests.test_aomagento.COMPUTEC.PO.HomePage;
import tests.test_aomagento.COMPUTEC.PO.ProductPage;
import tests.test_aomagento.COMPUTEC.PO.ShoppingCartPage;
import tests.test_aomagento.COMPUTEC.PO.SignInPage;
import tests.test_aomagento.COMPUTEC.PO.WishlistPage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

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
		log.info(":: Test COMPUTEC :: ajout d'un produit à la wishlist ::");
		homePage = new HomePage(driver);

		// On connecte un utilisateur
		pageTools = new pageObjectTools();
		accountPage=pageTools.connectionClient(homePage.getDriver(), Constant.Email, Constant.Password);
		
		// On va à la liste d'envie afin de la vider
		wishlistPage = accountPage.goToWishlist();
		wishlistPage.emptyWishlist();

		// On va rajouter un produit à la liste d'envie à partir du shop
		homePage = wishlistPage.goToHomePage();
		catalogPage = homePage.goToCategory(0);
		String productName1 = catalogPage.getProductName(0);
		wishlistPage = catalogPage.addProductToWishlist(0);
		
		// Un deuxieme DIFFERENT a partir de la page produit
		homePage = wishlistPage.goToHomePage();
		catalogPage = homePage.goToCategory(0);
		productPage = catalogPage.clickOnProduct(1);
		String productName2 = productPage.getProductName();
		wishlistPage = productPage.addProductToWishlist();

		// On enregistre le nombre de produit dans la liste d'envie après ajout
		int nbProduitApresAjout = wishlistPage.getNumberProductsInWishlist();

		// On verifie qu'un produit a été ajouté
		Assert.assertTrue(nbProduitApresAjout == 2);
		
		// On verifie que c'est le bon produit qui s'est ajouté
		Assert.assertTrue(wishlistPage.isProductInWishlist(productName1));
		Assert.assertTrue(wishlistPage.isProductInWishlist(productName2));
		wishlistPage.emptyWishlist();
	}

}
