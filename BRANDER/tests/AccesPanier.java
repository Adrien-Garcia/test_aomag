package tests.test_aomagento.BRANDER.tests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.BRANDER.PO.HomePage;
import tests.test_aomagento.BRANDER.PO.ShoppingCartPage;

public class AccesPanier extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(AccesPanier.class);

	HomePage homePage;
	ShoppingCartPage cartPage;

	@Test(description="Accés au panier par le lien du header")
	public void testAccesPanierLienHeaderRwd() throws Exception {
		homePage = new HomePage(driver);
		cartPage = homePage.goToCart();
		
		Assert.assertTrue(cartPage.getPageTitle().equalsIgnoreCase("Mon Panier"));
	}

}
