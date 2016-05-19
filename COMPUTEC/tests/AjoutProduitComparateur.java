package tests.test_aomagento.COMPUTEC.tests;

import base.Constant;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.COMPUTEC.PO.AccountPage;
import tests.test_aomagento.COMPUTEC.PO.CatalogPage;
import tests.test_aomagento.COMPUTEC.PO.ComparatorPage;
import tests.test_aomagento.COMPUTEC.PO.HomePage;
import tests.test_aomagento.COMPUTEC.PO.ProductPage;
import tests.test_aomagento.COMPUTEC.PO.ShoppingCartPage;
import tests.test_aomagento.COMPUTEC.PO.SignInPage;
import tests.test_aomagento.COMPUTEC.PO.WishlistPage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AjoutProduitComparateur extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(AjoutProduitComparateur.class);
	
	HomePage homePage;
	SignInPage signInPage;
	AccountPage accountPage;
	WishlistPage wishlistPage;
	CatalogPage catalogPage;
	ComparatorPage comparatorPage;
	ProductPage productPage;
	ShoppingCartPage cartPage;

	@Test(description="Ajout d'un produit au comparateur")
	public void testAjoutProduitComparateur() throws Exception {
		log.info(":: Test Thème Rwd :: ajout produits au comparateur ::");
		homePage = new HomePage(driver);

		if (!homePage.isUserLoggedIn()) {
			signInPage = homePage.clickConnectionClient();
			accountPage = signInPage.SignInAction(Constant.Email, Constant.Password);
			Assert.assertTrue(accountPage.isUserLoggedIn());
		} else {
			accountPage=homePage.goToAccountPage();
		}

		// On accéde à une catégorie du shop
		catalogPage = homePage.goToCategory(0);

		// On vide le comparateur pour le test
		comparatorPage = catalogPage.openComparator();
		comparatorPage.emptyComparator();
		// On retourne à la catégorie précedente
		comparatorPage.goToCategory(0);

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

		comparatorPage.emptyComparator();
	}


}