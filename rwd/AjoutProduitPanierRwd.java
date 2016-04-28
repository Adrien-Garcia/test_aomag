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
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.*;

public class AjoutProduitPanierRwd extends DesiredCapabilitiesTest {
	private static Log log = LogFactory.getLog(AjoutProduitPanierRwd.class);
	
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

	public AjoutProduitPanierRwd(String browser, String version, String plateform) throws MalformedURLException {
		super(browser, version, plateform);
	}

	@Test
	public void testAjoutProduitPanierEnPartantDuShop() throws Exception {
		log.info(":: Test Thème Rwd :: ajout d'un produit au panier en partant du catalogue ::");
		homePage = new HomePageRwd(driver);
		
		// On va au panier pour recuperer le nombre de produits presents avant
		// ajout
		cartPage = homePage.goToCart();
		int nbProduitAvantAjout = cartPage.getNumberOfProductsInCart();
		
		// On va ajouter un produit au panier
		homePage = cartPage.goToHomePage();
		catalogPage = homePage.goToCategory(1);
		Thread.sleep(2000);
		
		// On recupere son Sku
		String skuProduct = catalogPage.getProductSku(0);
		cartPage = catalogPage.addProductToCart(0);

		// On recupere le nombre de produits après ajout
		int nbProduitApresAjout = cartPage.getNumberOfProductsInCart();
		
		// On test
		assertEquals(nbProduitAvantAjout, nbProduitApresAjout - 1);
		assertTrue(cartPage.isProductInCart(skuProduct));
		Thread.sleep(2000);
		log.info("Ajout du produit validé");
		
		log.info("Test validé :: ajout d'un produit au panier pour un utilisateur inscrit :: ");
	}

	@Test
	public void testAjoutProduitPanierEnPartantDuProduit() throws Exception {
		log.info(":: Test Thème Rwd :: ajout d'un produit au panier en partant de la page produit ::");
		homePage = new HomePageRwd(driver);

		// On va au panier pour recuperer le nombre de produits presents avant
		// ajout
		cartPage = homePage.goToCart();
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
		assertEquals("Ajout du produit validé", nbProduitAvantAjout, nbProduitApresAjout - 1);
		// On test que le bon produit est dans le panier
		assertTrue(cartPage.isProductInCart(skuProduct));
		// Et que c'est la bonne quantité qui a été ajoutée
		assertTrue(cartPage.getQuantityOfProduct(skuProduct) == 5);
		log.info("ajout du produit validé");
		
		log.info(":: Test Validé :: ajout d'un produit au panier en partant de la page produit ::");
	}

}
