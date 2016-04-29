package tests.test_aomagento.rwd;

import base.Constant;
import base.DesiredCapabilitiesTest;
import tests.test_aomagento.pageobjects.AccountPageRwd;
import tests.test_aomagento.pageobjects.WishlistPageRwd;
import tests.test_aomagento.pageobjects.CatalogPageRwd;
import tests.test_aomagento.pageobjects.ShoppingCartPageRwd;
import tests.test_aomagento.pageobjects.HomePageRwd;
import tests.test_aomagento.pageobjects.ProductPageRwd;
import tests.test_aomagento.pageobjects.SignInPageRwd;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.*;

public class AjoutProduitListeEnvieRwd extends DesiredCapabilitiesTest {
	private static Log log = LogFactory.getLog(AjoutProduitListeEnvieRwd.class);
	
	HomePageRwd homePage;
	SignInPageRwd signInPage;
	AccountPageRwd accountPage;
	WishlistPageRwd wishlistPage;
	CatalogPageRwd catalogPage;
	ProductPageRwd productPage;
	ShoppingCartPageRwd cartPage;

	@Parameters
	public static Collection<Object[]> desiredCapabilities() {
		return Arrays.asList(new Object[][] { { "firefox", "", "ANY" } });
	}

	public AjoutProduitListeEnvieRwd(String browser, String version, String plateform) throws MalformedURLException {
		super(browser, version, plateform);
	}

	@Test
	public void testAjoutProduitListeEnvieEnPartantDuShop() throws Exception {
		log.info(":: Test Thème Rwd :: ajout d'un produit à la wishlist à  partir du catalogue ::");
		homePage = new HomePageRwd(driver);

		// On connecte l'utilisateur s'il n'est pas déjà connecté
		if (!homePage.isUserLoggedIn()) {
			signInPage = homePage.clickConnectionClient();
			accountPage = signInPage.SignInAction(Constant.Email, Constant.Password);
			assertTrue(accountPage.isUserLoggedIn());
		}

		// On va à la liste d'envie afin de la vider
		wishlistPage = accountPage.goToWishlist();
		wishlistPage.emptyWishlist();
		
		// On va rajouter un produit à la liste d'envie à partir de la page produit
		homePage = wishlistPage.goToHomePage();
		catalogPage = homePage.goToCategory(0);
		
		// On récupere son intitulé
		String productName = catalogPage.getProductName(0);
		wishlistPage = catalogPage.addProductToWishlist(0);

		// On enregistre le nombre de produit dans la liste d'envie après ajout
		int nbProduitApresAjout = wishlistPage.getNumberProductsInWishlist();

		// On verifie qu'un produit a été ajouté
		assertTrue(nbProduitApresAjout == 1);
		log.info("Le produit bien ajouté à la liste d'envie");
		// On verifie que c'est le bon produit qui s'est ajouté
		assertTrue(wishlistPage.isProductInWishlist(productName));
		log.info("Le produit est bien présent dans la liste d'envie");
		
		log.info("Test validé :: ajout d'un produit à la wishlist à  partir du catalogue :: ");

	}

	@Test
	public void testAjoutProduitListeEnvieEnPartantDuProduit() throws Exception {
		log.info(":: Test Thème Rwd :: ajout d'un produit à la wishlist à partir de la page produit ::");
		homePage = new HomePageRwd(driver);

		// On connecte un utilisateur
		if (!homePage.isUserLoggedIn()) {
			signInPage = homePage.clickConnectionClient();
			accountPage = signInPage.SignInAction(Constant.Email, Constant.Password);
			assertTrue(accountPage.isUserLoggedIn());
		}

		// On va à la liste d'envie afin de la vider
		wishlistPage = accountPage.goToWishlist();
		wishlistPage.emptyWishlist();

		// On va rajouter un produit à la liste d'envie à partir du shop
		homePage = wishlistPage.goToHomePage();
		catalogPage = homePage.goToCategory(0);
		productPage = catalogPage.clickOnProduct(0);

		// On récupere son intitulé
		String productName = productPage.getProductName();
		wishlistPage = productPage.addProductToWishlist();

		// On enregistre le nombre de produit dans la liste d'envie après ajout
		int nbProduitApresAjout = wishlistPage.getNumberProductsInWishlist();

		// On verifie qu'un produit a été ajouté
		assertTrue(nbProduitApresAjout == 1);
		
		// On verifie que c'est le bon produit qui s'est ajouté
		assertTrue(wishlistPage.isProductInWishlist(productName));
		
		log.info("Test validé :: ajout d'un produit à la wishlist à partir de la page produit :: ");
	}

}
