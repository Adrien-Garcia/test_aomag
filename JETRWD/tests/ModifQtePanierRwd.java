package tests.test_aomagento.JETRWD.tests;

import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.JETRWD.PO.AccountPageRwd;
import tests.test_aomagento.JETRWD.PO.CatalogPageRwd;
import tests.test_aomagento.JETRWD.PO.HomePageRwd;
import tests.test_aomagento.JETRWD.PO.ProductPageRwd;
import tests.test_aomagento.JETRWD.PO.ShoppingCartPageRwd;
import tests.test_aomagento.JETRWD.PO.SignInPageRwd;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ModifQtePanierRwd extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(ModifQtePanierRwd.class);
	HomePageRwd homePage;
	SignInPageRwd signInPage;
	AccountPageRwd accountPage;
	CatalogPageRwd catalogPage;
	ProductPageRwd productPage;
	ShoppingCartPageRwd cartPage;

	@Test(description="Modification de la quantité d'un produit dans le panier")
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
		Assert.assertTrue(cartPage.getQuantityOfProduct(produit1) == 55);

		cartPage.changeQuantityOfProduct(produit1, 2);
		Assert.assertTrue(cartPage.getQuantityOfProduct(produit1) == 2);

		log.info(":: Test Validé :: modifier la quantité d'un produit dans le panier ::");
	}

}
