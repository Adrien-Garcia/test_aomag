package tests.test_aomagento.BRANDER.tests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.Constant;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.BRANDER.PO.AccountPage;
import tests.test_aomagento.BRANDER.PO.CatalogPage;
import tests.test_aomagento.BRANDER.PO.HomePage;
import tests.test_aomagento.BRANDER.PO.ProductPage;
import tests.test_aomagento.BRANDER.PO.ShoppingCartPage;
import tests.test_aomagento.BRANDER.PO.SignInPage;

public class ModifQtePanier extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(ModifQtePanier.class);
	HomePage homePage;
	SignInPage signInPage;
	AccountPage accountPage;
	CatalogPage catalogPage;
	ProductPage productPage;
	ShoppingCartPage cartPage;

	@Test(description="Modification de la quantité d'un produit dans le panier")
	public void testModifQtePanier() throws Exception {
		homePage = new HomePage(driver);

		// On rajoute un produit avec une quantité de 4 (peu importe la quantité
		// de base)
		catalogPage = homePage.goToCategory(Constant.Category2);
		productPage = catalogPage.clickOnProduct(Constant.Product1);
		String produit1 = productPage.getProductSku();
		cartPage = productPage.addProductToCart(4);

		// On change la quantité du produit à 55
		cartPage.changeQuantityOfProduct(produit1, 55);
		// On vérifie que sa nouvelle quantité est de 55
		Assert.assertTrue(cartPage.getQuantityOfProduct(produit1) == 55);

		cartPage.changeQuantityOfProduct(produit1, 2);
		Assert.assertTrue(cartPage.getQuantityOfProduct(produit1) == 2);
	}

}
