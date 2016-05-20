package tests.test_aomagento.RWD.tests;

import base.Constant;
import base.DesiredCapabilitiesTest;
import base.DesiredCapabilitiesTestNG;
import base.PageObjectException;
import tests.test_aomagento.RWD.PO.AccountPageRwd;
import tests.test_aomagento.RWD.PO.CatalogPageRwd;
import tests.test_aomagento.RWD.PO.ComparatorPageRwd;
import tests.test_aomagento.RWD.PO.HomePageRwd;
import tests.test_aomagento.RWD.PO.ProductPageRwd;
import tests.test_aomagento.RWD.PO.ShoppingCartPageRwd;
import tests.test_aomagento.RWD.PO.SignInPageRwd;
import tests.test_aomagento.RWD.PO.WishlistPageRwd;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AjoutProduitComparateurRwd extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(AjoutProduitComparateurRwd.class);
	
	HomePageRwd homePage;
	SignInPageRwd signInPage;
	AccountPageRwd accountPage;
	WishlistPageRwd wishlistPage;
	CatalogPageRwd catalogPage;
	ComparatorPageRwd comparatorPage;
	ProductPageRwd productPage;
	ShoppingCartPageRwd cartPage;

	@Test(description="Ajout d'un produit au comparateur")
	public void testAjoutProduitComparateurEnPartantDuShop() throws Exception {
		log.info(":: Test Thème Rwd :: ajout produits au comparateur en partant du catalogue ::");
		homePage = new HomePageRwd(driver);

		// On connecte l'utilisateur s'il ne l'est pas
		if (!homePage.isUserLoggedIn()) {
			signInPage = homePage.clickConnectionClient();
			accountPage = signInPage.SignInAction(Constant.Email, Constant.Password);
			Assert.assertTrue(accountPage.isUserLoggedIn());
			homePage = accountPage.goToHomePage();
		}

		// On accéde à une catégorie du shop
		catalogPage = homePage.goToCategory(0);

		// On vide le comparateur pour le test
		catalogPage.emptyComparator();

		// On ajoute 2 produits au comparateur directement du shop, on instancie
		// leur nom dans deux variables
		catalogPage.addProductToComparator(0);
		String produit1 = catalogPage.getProductName(0);
		catalogPage.addProductToComparator(1);
		String produit2 = catalogPage.getProductName(1);

		// On vérifie qu'ils aient été ajouté
		Assert.assertTrue(catalogPage.getNumberProductsInComparator() == 2);
		log.info("Les 2 produits ont bien été ajoutés");

		// On ouvre le comparateur
		comparatorPage = catalogPage.openComparator();
		// On vérifie qu'il ya bien 2 produits dans le comprateur
		Assert.assertTrue(comparatorPage.getNumberProductsInComparator() == 2);
		// Et que ce sont ceux qu'on a ajouté
		Assert.assertTrue(comparatorPage.isProductInComparator(produit1));
		Assert.assertTrue(comparatorPage.isProductInComparator(produit2));

		catalogPage = comparatorPage.closeComparator();
		log.info("Test validé :: ajout produits au comparateur en partant du catalogue :: ");
	}
}