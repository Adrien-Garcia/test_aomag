package tests.test_aomagento.JETRWD.tests;

import tests.test_aomagento.JETRWD.utilitaries.Constant;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.JETRWD.utilitaries.pageObjectTools;
import tests.test_aomagento.JETRWD.PO.AccountPageRwd;
import tests.test_aomagento.JETRWD.PO.CatalogPageRwd;
import tests.test_aomagento.JETRWD.PO.ComparatorPageRwd;
import tests.test_aomagento.JETRWD.PO.HomePageRwd;
import tests.test_aomagento.JETRWD.PO.ProductPageRwd;
import tests.test_aomagento.JETRWD.PO.ShoppingCartPageRwd;
import tests.test_aomagento.JETRWD.PO.SignInPageRwd;
import tests.test_aomagento.JETRWD.PO.WishlistPageRwd;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	
	pageObjectTools pageTools;

	@Test(description="Ajout d'un produit au comparateur")
	public void testAjoutProduitComparateurEnPartantDuShop() throws Exception {
		log.info(":: Test Thème Rwd :: ajout produits au comparateur en partant du catalogue ::");
		homePage = new HomePageRwd(driver);

		// On connecte l'utilisateur
		pageTools = new pageObjectTools();
		accountPage=pageTools.connectionClient(homePage.getDriver(), Constant.Email, Constant.Password);
		homePage = accountPage.goToHomePage();

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