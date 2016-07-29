package tests.test_aomagento.JETRWD.tests;

import tests.test_aomagento.JETRWD.utilitaries.Constant;
import tests.test_aomagento.JETRWD.utilitaries.pageObjectTools;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.JETRWD.PO.AccountPageRwd;
import tests.test_aomagento.JETRWD.PO.CatalogPageRwd;
import tests.test_aomagento.JETRWD.PO.HomePageRwd;
import tests.test_aomagento.JETRWD.PO.ProductPageRwd;
import tests.test_aomagento.JETRWD.PO.ShoppingCartPageRwd;
import tests.test_aomagento.JETRWD.PO.SignInPageRwd;
import tests.test_aomagento.JETRWD.PO.WishlistPageRwd;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AjoutProduitWishlistRwd extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(AjoutProduitWishlistRwd.class);

	HomePageRwd homePage;
	SignInPageRwd signInPage;
	AccountPageRwd accountPage;
	WishlistPageRwd wishlistPage;
	CatalogPageRwd catalogPage;
	ProductPageRwd productPage;
	ShoppingCartPageRwd cartPage;

	pageObjectTools pageTools;

	@Test(description="Ajout produit à la wishlist ")
	public void testAjoutProduitWishlist() throws Exception {
		log.info(":: Test Thème Rwd :: ajout d'un produit à la wishlist à partir de la page produit ::");
		homePage = new HomePageRwd(driver);

		// On connecte l'utilisateur s'il n'est pas déjà connecté
		pageTools = new pageObjectTools();		
		accountPage = pageTools.connectionClient(driver, Constant.Email, Constant.Password);

		// On va à la liste d'envie afin de la vider
		wishlistPage = accountPage.goToWishlist();
		wishlistPage.emptyWishlist();

		// On va rajouter un produit à la liste d'envie à partir du shop
		homePage = wishlistPage.goToHomePage();
		catalogPage = homePage.goToCategory(0);
		productPage = catalogPage.clickOnProduct(1);

		// On récupere son intitulé
		String productName = productPage.getProductName();
		wishlistPage = productPage.addProductToWishlist();

		// On enregistre le nombre de produit dans la liste d'envie après ajout
		int nbProduitApresAjout = wishlistPage.getNumberProductsInWishlist();

		// On verifie qu'un produit a été ajouté
		Assert.assertTrue(nbProduitApresAjout == 1);

		// On verifie que c'est le bon produit qui s'est ajouté
		Assert.assertTrue(wishlistPage.isProductInWishlist(productName));
		wishlistPage.emptyWishlist();

		log.info("Test validé :: ajout d'un produit à la wishlist à partir de la page produit :: ");
	}

}
