package tests.test_aomagento.rwd;

import base.Constant;
import base.DesiredCapabilitiesTest;
import tests.test_aomagento.pageobjects.AccountPageRwd;
import tests.test_aomagento.pageobjects.CatalogPageRwd;
import tests.test_aomagento.pageobjects.ShoppingCartPageRwd;
import tests.test_aomagento.pageobjects.HomePageRwd;
import tests.test_aomagento.pageobjects.ProductPageRwd;
import tests.test_aomagento.pageobjects.SignInPageRwd;

import java.net.MalformedURLException;

import java.util.Arrays;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.*;

public class AccesPanierRwd extends DesiredCapabilitiesTest {
	private static Log log = LogFactory.getLog(AccesPanierRwd.class);

	HomePageRwd homePage;
	SignInPageRwd signInPage;
	AccountPageRwd accountPage;
	CatalogPageRwd catalogPage;
	ProductPageRwd productPage;
	ShoppingCartPageRwd cartPage;

	@Parameters
	public static Collection<Object[]> desiredCapabilities() {
		return Arrays.asList(new Object[][] { { "firefox", "", "ANY" } });
	}

	public AccesPanierRwd(String browser, String version, String plateform) throws MalformedURLException {
		super(browser, version, plateform);
	}

	@Test
	public void testAccesPanierLienHeaderRwd() throws Exception {
		log.info(":: Test Thème Rwd :: accès au panier par le lien du header ::");
		
		// On accéde au pnaier directement par le lien du header
		homePage = new HomePageRwd(driver);
		cartPage = homePage.goToCart();
		
		assertTrue(cartPage.getPageTitle().equalsIgnoreCase("Shopping Cart"));

		log.info("Test validé :: accès au panier par le lien du header :: ");
	}

	@Test
	public void testAccesPanierUtilisateurInscritRwd() throws Exception {
		log.info(":: Test Thème Rwd :: accès au panier pour un utilisateur inscrit ::");

		// On accède à la page d'accueil
		homePage = new HomePageRwd(driver);

		// On connecte l'utilisateur s'il n'est pas déjà connecté
		if (!homePage.isUserLoggedIn()) {
			signInPage = homePage.clickConnectionClient();
			accountPage = signInPage.SignInAction(Constant.Email, Constant.Password);
			assertTrue(accountPage.isUserLoggedIn());
		}

		// On ajoute un produit au panier
		homePage = accountPage.goToHomePage();
		catalogPage = homePage.goToCategory(0);
		cartPage = catalogPage.addProductToCart(1);
		
		assertTrue(cartPage.getPageTitle().equalsIgnoreCase("Shopping Cart"));

		log.info("Test validé :: accès au panier pour un utilisateur inscrit :: ");
	}

	@Test
	public void testAccesPanierUtilisateurNonInscritRwd() throws Exception {
		log.info(":: Test Thème Rwd :: accès au panier pour un utilisateur non inscrit ::");
		homePage = new HomePageRwd(driver);

		// On déconnecte l'utilisateur s'il est connecté
		if (homePage.isUserLoggedIn()) {
			homePage.logOutUser();
			assertFalse(accountPage.isUserLoggedIn());
		}

		// On ajoute un produit au panier
		catalogPage = homePage.goToCategory(0);
		cartPage = catalogPage.addProductToCart(1);
		
		assertTrue(cartPage.getPageTitle().equalsIgnoreCase("Shopping Cart"));

		log.info("Test validé :: accès au panier pour un utilisateur non inscrit :: ");
	}

	@Test
	public void testAccesPanierParPageProduit() throws Exception {
		log.info(":: Test Thème Rwd :: accès au panier par la page produit ::");
		homePage = new HomePageRwd(driver);

		// On connecte l'utilisateur s'il n'est pas déjà connecté
		if (!homePage.isUserLoggedIn()) {
			signInPage = homePage.clickConnectionClient();
			accountPage = signInPage.SignInAction(Constant.Email, Constant.Password);
			assertTrue(accountPage.isUserLoggedIn());
		}

		catalogPage = homePage.goToCategory(0);
		productPage = catalogPage.clickOnProduct(0);
		cartPage = productPage.addProductToCart(2);
		assertTrue(cartPage.getPageTitle().equalsIgnoreCase("Shopping Cart"));

		log.info("Test validé :: accès au panier par la page produit :: ");
	}

}
