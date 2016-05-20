package tests.test_aomagento.RWD.tests;

import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.RWD.PO.AccountPageRwd;
import tests.test_aomagento.RWD.PO.CatalogPageRwd;
import tests.test_aomagento.RWD.PO.HomePageRwd;
import tests.test_aomagento.RWD.PO.ProductPageRwd;
import tests.test_aomagento.RWD.PO.ShoppingCartPageRwd;
import tests.test_aomagento.RWD.PO.SignInPageRwd;

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

	@Test(description="Ajout d'un produit au panier (en partant du catalogue)", priority=0)
	public void testAjoutProduitPanierEnPartantDuShop() throws Exception {
		log.info(":: Test Thème Rwd :: ajout d'un produit au panier en partant du catalogue ::");
		homePage = new HomePageRwd(driver);
		
		// On va au panier pour recuperer le nombre de produits presents avant
		// ajout
		cartPage = homePage.goToCart();
		cartPage.emptyCart();
		int nbProduitAvantAjout = cartPage.getNumberOfProductsInCart();
		
		// On va ajouter un produit au panier
		homePage = cartPage.goToHomePage();
		catalogPage = homePage.goToCategory(1);
		
		// On recupere son Sku
		String skuProduct = catalogPage.getProductSku(0);
		cartPage = catalogPage.addProductToCart(0);

		// On recupere le nombre de produits après ajout
		int nbProduitApresAjout = cartPage.getNumberOfProductsInCart();
		
		// On test
		Assert.assertEquals(nbProduitAvantAjout, nbProduitApresAjout - 1);
		Assert.assertTrue(cartPage.isProductInCart(skuProduct));
		Assert.assertTrue(cartPage.getQuantityOfProduct(skuProduct)==1);
		
		log.info("Ajout du produit validé");
		
		log.info("Test validé :: ajout d'un produit au panier pour un utilisateur inscrit :: ");
	}

	@Test(description="Ajout d'un produit au panier (en partant d'une page produit)", priority=1)
	public void testAjoutProduitPanierEnPartantDuProduit() throws Exception {
		log.info(":: Test Thème Rwd :: ajout d'un produit au panier en partant de la page produit ::");
		homePage = new HomePageRwd(driver);

		// On va au panier pour recuperer le nombre de produits presents avant
		// ajout
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
		Assert.assertEquals(nbProduitAvantAjout, nbProduitApresAjout - 1);
		// On test que le bon produit est dans le panier
		Assert.assertTrue(cartPage.isProductInCart(skuProduct));
		// Et que c'est la bonne quantité qui a été ajoutée
		Assert.assertTrue(cartPage.getQuantityOfProduct(skuProduct) == 5);
		log.info("ajout du produit validé");
		
		log.info(":: Test Validé :: ajout d'un produit au panier en partant de la page produit ::");
	}

}
