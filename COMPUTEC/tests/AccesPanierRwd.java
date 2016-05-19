package tests.test_aomagento.COMPUTEC.tests;

import base.Constant;
import base.DesiredCapabilitiesTest;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.RWD.PO.AccountPageRwd;
import tests.test_aomagento.RWD.PO.CatalogPageRwd;
import tests.test_aomagento.RWD.PO.HomePageRwd;
import tests.test_aomagento.RWD.PO.ProductPageRwd;
import tests.test_aomagento.RWD.PO.ShoppingCartPageRwd;
import tests.test_aomagento.RWD.PO.SignInPageRwd;

import java.net.MalformedURLException;

import java.util.Arrays;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AccesPanierRwd extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(AccesPanierRwd.class);

	HomePageRwd homePage;
	SignInPageRwd signInPage;
	AccountPageRwd accountPage;
	CatalogPageRwd catalogPage;
	ProductPageRwd productPage;
	ShoppingCartPageRwd cartPage;

	@Test(description="Accés au panier par le header")
	public void testAccesPanierLienHeaderRwd() throws Exception {
		log.info(":: Test Thème Rwd :: accès au panier par le lien du header ::");
		
		// On accéde au panier directement par le lien du header
		homePage = new HomePageRwd(driver);
		cartPage = homePage.goToCart();
		
		Assert.assertTrue(cartPage.getPageTitle().equalsIgnoreCase("Shopping Cart"));

		log.info("Test validé :: accès au panier par le lien du header :: ");
	}

	@Test(description="Accés au panier pour un utilisateur connecté", dataProvider = "login")
	public void testAccesPanierUtilisateurConnecteRwd(String email, String password) throws Exception {
		log.info(":: Test Thème Rwd :: accès au panier pour un utilisateur inscrit :: ");
		// On accède à la page d'accueil
		homePage = new HomePageRwd(driver);

		// On connecte l'utilisateur s'il n'est pas déjà connecté
		if (!homePage.isUserLoggedIn()) {
			signInPage = homePage.clickConnectionClient();
			accountPage = signInPage.SignInAction(email, password);
			Assert.assertTrue(accountPage.isUserLoggedIn());
			homePage = accountPage.goToHomePage();
		}

		// On ajoute un produit au panier		
		catalogPage = homePage.goToCategory(0);
		cartPage = catalogPage.addProductToCart(0);
		
		Assert.assertTrue(cartPage.getPageTitle().equalsIgnoreCase("Shopping Cart"));

		log.info("Test validé :: accès au panier pour un utilisateur inscrit :: ");
	}

	@Test(description="Accés au panier pour un utilisateur non inscrit")
	public void testAccesPanierUtilisateurNonInscritRwd() throws Exception {
		log.info(":: Test Thème Rwd :: accès au panier pour un utilisateur non inscrit ::");
		homePage = new HomePageRwd(driver);

		// On déconnecte l'utilisateur s'il est connecté
		if (homePage.isUserLoggedIn()) {
			homePage.logOutUser();
			Assert.assertFalse(homePage.isUserLoggedIn());
		}

		// On ajoute un produit au panier
		catalogPage = homePage.goToCategory(0);
		cartPage = catalogPage.addProductToCart(0);
		
		Assert.assertTrue(cartPage.getPageTitle().equalsIgnoreCase("Shopping Cart"));

		log.info("Test validé :: accès au panier pour un utilisateur non inscrit :: ");
	}

	@Test(description="Accés au panier par la page produit", dataProvider = "login")
	public void testAccesPanierParPageProduit(String email, String password) throws Exception {
		log.info(":: Test Thème Rwd :: accès au panier par la page produit ::");
		homePage = new HomePageRwd(driver);

		// On connecte l'utilisateur s'il n'est pas déjà connecté
		if (!homePage.isUserLoggedIn()) {
			signInPage = homePage.clickConnectionClient();
			accountPage = signInPage.SignInAction(email, password);
			Assert.assertTrue(accountPage.isUserLoggedIn());
		}

		catalogPage = homePage.goToCategory(0);
		productPage = catalogPage.clickOnProduct(0);
		cartPage = productPage.addProductToCart(2);
		Assert.assertTrue(cartPage.getPageTitle().equalsIgnoreCase("Shopping Cart"));

		log.info("Test validé :: accès au panier par la page produit :: ");
	}

}
