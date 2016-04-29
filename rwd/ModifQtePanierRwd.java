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

public class ModifQtePanierRwd extends DesiredCapabilitiesTest {
	private static Log log = LogFactory.getLog(ModifQtePanierRwd.class);
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

	public ModifQtePanierRwd(String browser, String version, String plateform) throws MalformedURLException {
		super(browser, version, plateform);
	}

	@Test
	public void testModifQtePanier() throws Exception {
		log.info(":: Test Thème Rwd :: modifier la quantité d'un produit dans le panier ::");
		homePage = new HomePageRwd(driver);

		// On rajoute un produit avec une quantité de 4 (peu importe la quantité
		// de base)
		catalogPage = homePage.goToCategory(1);
		productPage = catalogPage.clickOnProduct(0);
		String produit1 = productPage.getProductSku();
		cartPage = productPage.addProductToCart(4);

		// On change la quantité du produit à 55
		cartPage.changeQuantityOfProduct(produit1, 55);
		// On vérifie que sa nouvelle quantité est de 55
		assertTrue(cartPage.getQuantityOfProduct(produit1) == 55);

		log.info(":: Test Validé :: modifier la quantité d'un produit dans le panier ::");
	}

}
