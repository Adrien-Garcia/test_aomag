package tests.test_aomagento.BRANDER.tests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.Constant;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.BRANDER.PO.AccountPage;
import tests.test_aomagento.BRANDER.PO.CatalogPage;
import tests.test_aomagento.BRANDER.PO.HomePage;
import tests.test_aomagento.BRANDER.PO.ProductPage;
import tests.test_aomagento.BRANDER.PO.ShoppingCartPage;
import tests.test_aomagento.BRANDER.PO.SignInPage;
import tests.test_aomagento.BRANDER.PO.WishlistPage;

public class AjoutProduitWishlist extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(AjoutProduitWishlist.class);
	
	HomePage homePage;
	SignInPage signInPage;
	AccountPage accountPage;
	WishlistPage wishlistPage;
	CatalogPage catalogPage;
	ProductPage productPage;
	ShoppingCartPage cartPage;

	@Test(description="Ajout produit à la wishlist ")
	public void testAjoutProduitWishlist() throws Exception {
		homePage = new HomePage(driver);

		// On connecte un utilisateur
		if (!homePage.isUserLoggedIn()) {
			signInPage = homePage.clickConnectionClient();
			accountPage = signInPage.SignInAction(Constant.Email, Constant.Password);
			Assert.assertTrue(accountPage.isUserLoggedIn());
		} else {
			accountPage=homePage.goToAccountPage();
		}
		
		// On va à la liste d'envie afin de la vider
		wishlistPage = accountPage.goToWishlist();
		wishlistPage.emptyWishlist();

		// On va rajouter un produit à la liste d'envie à partir du shop
		homePage = wishlistPage.goToHomePage();
		catalogPage = homePage.goToCategory(Constant.Category1);
		String productName1 = catalogPage.getProductName(Constant.Product1);
		wishlistPage = catalogPage.addProductToWishlist(Constant.Product1);
		
		// Un deuxieme DIFFERENT a partir de la page produit
		homePage = wishlistPage.goToHomePage();
		catalogPage = homePage.goToCategory(Constant.Category1);
		productPage = catalogPage.clickOnProduct(Constant.Product2);
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
