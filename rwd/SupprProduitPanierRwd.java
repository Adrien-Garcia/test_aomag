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
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.*;

public class SupprProduitPanierRwd extends DesiredCapabilitiesTest {

	private static Log log = LogFactory.getLog(SupprProduitPanierRwd.class);
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

	public SupprProduitPanierRwd(String browser, String version, String plateform) throws MalformedURLException {
		super(browser, version, plateform);
	}

	@Test
	public void testSupprProduitPanier() throws Exception {
		log.info(":: Test Thème Rwd :: suppression produit du panier ::");
		homePage = new HomePageRwd(driver);

		// Il faut vider le paniers'il n'est pas vide 
		// Il ya risque de conflit entre sku si les produits ont un sku qui commence de la meme façon
		if (!homePage.isCartEmpty()) {
			cartPage = homePage.goToCart();
			cartPage.emptyCart();
			homePage = cartPage.goToHomePage();
		}

		// On ajoute un produit à partir du catalogue
		// Veiller à ce que les sku soient bien différent
		catalogPage = homePage.goToCategory(0);
		cartPage = catalogPage.addProductToCart(1);

		// On en ajoute un autre à partir de la page produit
		homePage = cartPage.goToHomePage();
		catalogPage = homePage.goToCategory(0);
		productPage = catalogPage.clickOnProduct(1);
		// On recupere son sku
		String idProduct2 = productPage.getProductSku();
		cartPage = productPage.addProductToCart(2);

		// On recupere le nombre de produits dans le panier apres les ajouts
		int nbProductBefore = cartPage.getNumberOfProductsInCart();
		
		// On supprime un produit
		cartPage.removeProduct(idProduct2);
		// On recupere le nombre de produit après suppression
		int nbProductAfter = cartPage.getNumberOfProductsInCart();
		
		// On test qu'un produit a été enlevé
		assertEquals("Produit supprimé", nbProductBefore - 1, nbProductAfter);
		// Et que le produit supprimé n'est plus résent dans le panier
		assertFalse(cartPage.isProductInCart(idProduct2));

		log.info(":: Test Thème Rwd :: suppression produit du panier ::");

	}

	@Test
	public void testViderPanier() throws Exception {
		log.info(":: Test Thème Rwd :: vider panier ::");
		// on ajoute unproduit dans le panier
		homePage = new HomePageRwd(driver);
		catalogPage = homePage.goToCategory(0);
		cartPage = catalogPage.addProductToCart(1);
		// Puis un deuxième
		homePage = cartPage.goToHomePage();
		catalogPage = homePage.goToCategory(0);
		cartPage = catalogPage.addProductToCart(1);

		// On vide le panier
		cartPage.emptyCart();

		// On test que le panier est vide
		assertTrue(cartPage.getNumberOfProductsInCart() == 0);
		assertTrue("Panier vidé", cartPage.isCartEmpty());

		log.info(":: Test Validé :: vider panier ::");

	}

}
