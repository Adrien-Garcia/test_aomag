package tests.test_aomagento.rwd;

import base.Constant;
import base.DesiredCapabilitiesTest;
import tests.test_aomagento.pageobjects.AccountPageRwd;
import tests.test_aomagento.pageobjects.WishlistPageRwd;
import tests.test_aomagento.pageobjects.CatalogPageRwd;
import tests.test_aomagento.pageobjects.ComparatorPageRwd;
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

public class AjoutProduitComparateurRwd extends DesiredCapabilitiesTest {
	private static Log log = LogFactory.getLog(AjoutProduitComparateurRwd.class);
	
	HomePageRwd homePage;
	SignInPageRwd signInPage;
	AccountPageRwd accountPage;
	WishlistPageRwd wishlistPage;
	CatalogPageRwd catalogPage;
	ComparatorPageRwd comparatorPage;
	ProductPageRwd productPage;
	ShoppingCartPageRwd cartPage;

	@Parameters
	public static Collection<Object[]> desiredCapabilities() {
		return Arrays.asList(new Object[][] { { "firefox", "", "ANY" } });
	}

	public AjoutProduitComparateurRwd(String browser, String version, String plateform) throws MalformedURLException {
		super(browser, version, plateform);
	}

	@Test
	public void testAjoutProduitComparateurEnPartantDuShop() throws Exception {
		log.info(":: Test Thème Rwd :: ajout produits au comparateur en partant du catalogue ::");
		homePage = new HomePageRwd(driver);

		// On connecte l'utilisateur s'il ne l'est pas
		if (!homePage.isUserLoggedIn()) {
			signInPage = homePage.clickConnectionClient();
			accountPage = signInPage.SignInAction(Constant.Email, Constant.Password);
			assertTrue(accountPage.isUserLoggedIn());
		}

		// On accéde à une catégorie du shop
		homePage = accountPage.goToHomePage();
		catalogPage = homePage.goToCategory(1);

		// On vide le comparateur pour le test
		catalogPage.emptyComparator();

		// On ajoute 2 produits au comparateur directement du shop, on instancie
		// leur nom dans deux variables
		catalogPage.addProductToComparator(0);
		String produit1 = catalogPage.getProductName(0);
		catalogPage.addProductToComparator(1);
		String produit2 = catalogPage.getProductName(1);

		// On vérifie qu'ils aient été ajouté
		assertTrue(catalogPage.getNumberProductsInComparator() == 2);
		log.info("Les 2 produits ont bien été ajoutés");

		// On ouvre le comparateur
		comparatorPage = catalogPage.openComparator();
		// On vérifie qu'il ya bien 2 produits dans le comprateur
		assertTrue("Les 2 produits sont dans le comparateur", comparatorPage.getNumberProductsInComparator() == 2);
		// Et que ce sont ceux qu'on a ajouté
		assertTrue(comparatorPage.isProductInComparator(produit1));
		assertTrue(comparatorPage.isProductInComparator(produit2));

		catalogPage = comparatorPage.closeComparator();
		Thread.sleep(1000);
		log.info("Test validé :: ajout produits au comparateur en partant du catalogue :: ");

	}

	@Test
	public void testAjoutProduitsComparateurEnPartantDesPagesProduits() throws Exception {
		log.info(":: Test Thème Rwd :: ajout produit au comparateur en partant de la page produit ::");
		homePage = new HomePageRwd(driver);

		if (!homePage.isUserLoggedIn()) {
			signInPage = homePage.clickConnectionClient();
			accountPage = signInPage.SignInAction(Constant.Email, Constant.Password);
			assertTrue(accountPage.isUserLoggedIn());
		}

		// Une fois connecté on accéde à une catégorie du shop
		homePage = accountPage.goToHomePage();
		catalogPage = homePage.goToCategory(1);

		// Ici on vide le comparateur au cas ou celui ci est rempli
		catalogPage.emptyComparator();

		// On ajoute un premier produit au comparateur
		productPage = catalogPage.clickOnProduct(0);
		String produit1 = productPage.getProductName();
		productPage.addProductToComparator();

		// On retourne à la home pour re acceder à un autre produit
		homePage = productPage.goToHomePage();
		catalogPage = homePage.goToCategory(1);
		productPage = catalogPage.clickOnProduct(1);
		String produit2 = productPage.getProductName();
		productPage.addProductToComparator();

		// On test qu'il y a bien 2 produits ajoutés au comparateur
		homePage = productPage.goToHomePage();
		catalogPage = homePage.goToCategory(1);
		assertTrue("Les 2 produits ont bien été ajoutés au comparateur", catalogPage.getNumberProductsInComparator() == 2);

		// On ouvre le comparateur
		comparatorPage = catalogPage.openComparator();
		Thread.sleep(3000);
		
		// On vérifie qu'il ya bien 2 produits dans le comprateur
		assertTrue("Les 2 produits sont dans le comparateur", comparatorPage.getNumberProductsInComparator() == 2);
		// Et que ce sont ceux qu'on a ajouté
		assertTrue(comparatorPage.isProductInComparator(produit1));
		assertTrue(comparatorPage.isProductInComparator(produit2));

		catalogPage = comparatorPage.closeComparator();
		Thread.sleep(1000);
		
		log.info("Test validé :: ajout produit au comparateur en partant de la page produit :: ");

	}

}