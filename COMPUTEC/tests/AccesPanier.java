package tests.test_aomagento.COMPUTEC.tests;

import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.COMPUTEC.PO.AccountPage;
import tests.test_aomagento.COMPUTEC.PO.CatalogPage;
import tests.test_aomagento.COMPUTEC.PO.HomePage;
import tests.test_aomagento.COMPUTEC.PO.ProductPage;
import tests.test_aomagento.COMPUTEC.PO.ShoppingCartPage;
import tests.test_aomagento.COMPUTEC.PO.SignInPage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AccesPanier extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(AccesPanier.class);

	HomePage homePage;
	SignInPage signInPage;
	AccountPage accountPage;
	CatalogPage catalogPage;
	ProductPage productPage;
	ShoppingCartPage cartPage;

	@Test(description="Accés au panier par le header")
	public void testAccesPanierLienHeaderRwd() throws Exception {
		log.info(":: Test Thème Rwd :: accès au panier par le lien du header ::");
		
		// On accéde au panier directement par le lien du header
		homePage = new HomePage(driver);
		cartPage = homePage.goToCart();
		
		Assert.assertTrue(cartPage.getPageTitle().equalsIgnoreCase("Mon Panier"));
	}

}
