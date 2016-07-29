package tests.test_aomagento.JETRWD.tests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.JETRWD.utilitaries.pageObjectTools;
import tests.test_aomagento.JETRWD.PO.AccountPageRwd;
import tests.test_aomagento.JETRWD.PO.CatalogPageRwd;
import tests.test_aomagento.JETRWD.PO.HomePageRwd;
import tests.test_aomagento.JETRWD.PO.ProductPageRwd;
import tests.test_aomagento.JETRWD.PO.ShoppingCartPageRwd;
import tests.test_aomagento.JETRWD.PO.SignInPageRwd;
import tests.test_aomagento.JETRWD.utilitaries.Constant;

public class AccesPanierRwd extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(AccesPanierRwd.class);

	HomePageRwd homePage;
	SignInPageRwd signInPage;
	AccountPageRwd accountPage;
	CatalogPageRwd catalogPage;
	ProductPageRwd productPage;
	ShoppingCartPageRwd cartPage;
	
	pageObjectTools pageTools;

	@Test(description="Accés au panier par le header")
	public void testAccesPanierLienHeaderRwd() throws Exception {
		log.info(":: Test Thème Rwd :: accès au panier par le lien du header ::");
		
		// On accéde au panier directement par le lien du header
		homePage = new HomePageRwd(driver);
		cartPage = homePage.goToCart();
		
		Assert.assertTrue(cartPage.getPageTitle().equalsIgnoreCase("Panier"));

		log.info("Test validé :: accès au panier par le lien du header :: ");
	}

	@Test(description="Accés au panier pour un utilisateur connecté")
	public void testAccesPanierUtilisateurConnecteRwd() throws Exception {
		log.info(":: Test Thème Rwd :: accès au panier pour un utilisateur inscrit :: ");
		// On accède à la page d'accueil
		homePage = new HomePageRwd(driver);

		// On connecte l'utilisateur s'il n'est pas déjà connecté
		pageTools = new pageObjectTools();		
		accountPage = pageTools.connectionClient(driver, Constant.Email, Constant.Password);
		homePage = accountPage.goToHomePage();

		// On ajoute un produit au panier		
		catalogPage = homePage.goToCategory(0);
		cartPage = catalogPage.addProductToCart(0);
		
		Assert.assertTrue(cartPage.getPageTitle().equalsIgnoreCase("Panier"));

		log.info("Test validé :: accès au panier pour un utilisateur inscrit :: ");
	}


}
