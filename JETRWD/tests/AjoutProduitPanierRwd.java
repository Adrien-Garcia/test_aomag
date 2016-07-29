package tests.test_aomagento.JETRWD.tests;

import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.JETRWD.PO.AccountPageRwd;
import tests.test_aomagento.JETRWD.PO.CatalogPageRwd;
import tests.test_aomagento.JETRWD.PO.HomePageRwd;
import tests.test_aomagento.JETRWD.PO.ProductPageRwd;
import tests.test_aomagento.JETRWD.PO.ShoppingCartPageRwd;
import tests.test_aomagento.JETRWD.PO.SignInPageRwd;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AjoutProduitPanierRwd extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(AjoutProduitPanierRwd.class);

	HomePageRwd homePage;
	SignInPageRwd signInPage;
	AccountPageRwd accountPage;
	CatalogPageRwd catalogPage;
	ProductPageRwd productPage;
	ShoppingCartPageRwd cartPage;

	@Test(description="Ajout d'un produit au panier (en partant d'une page produit)")
	public void testAjoutProduitPanierEnPartantDuProduit() throws Exception {
		log.info(":: Test Thème Rwd :: ajout d'un produit au panier en partant de la page produit ::");
		homePage = new HomePageRwd(driver);

		// On va au panier pour recuperer le nombre de produits presents avant ajout
		cartPage = homePage.goToCart();
		cartPage.emptyCart();
		int nbProduitAvantAjout = cartPage.getNumberOfProductsInCart();

		// On va ajouter un produit au panier
		homePage = cartPage.goToHomePage();
		catalogPage = homePage.goToCategory(1);
		productPage = catalogPage.clickOnProduct(0);
		// On recupere son Sku
		String skuProduct = productPage.getProductSku();
		cartPage = productPage.addProductToCart(5);

		// On recupere le nombre de produits après ajout
		int nbProduitApresAjout = cartPage.getNumberOfProductsInCart();


		// On test
		Assert.assertEquals(nbProduitAvantAjout, nbProduitApresAjout - 1, "Il y a plus de produits prévus dans le panier");
		Assert.assertTrue(cartPage.isProductInCart(skuProduct), "Le produit n'est pas présent");
		Assert.assertTrue(cartPage.getQuantityOfProduct(skuProduct)==5, "La quantité ajoutée n'est pas la même");

		log.info(":: Test Validé :: ajout d'un produit au panier en partant de la page produit ::");
	}

}
