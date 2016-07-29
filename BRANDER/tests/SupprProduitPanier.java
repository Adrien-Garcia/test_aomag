package tests.test_aomagento.BRANDER.tests;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import tests.test_aomagento.BRANDER.utilitaries.Constant;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.BRANDER.PO.AccountPage;
import tests.test_aomagento.BRANDER.PO.CatalogPage;
import tests.test_aomagento.BRANDER.PO.HomePage;
import tests.test_aomagento.BRANDER.PO.ProductPage;
import tests.test_aomagento.BRANDER.PO.ShoppingCartPage;
import tests.test_aomagento.BRANDER.PO.SignInPage;

public class SupprProduitPanier extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(SupprProduitPanier.class);
	
	HomePage homePage;
	SignInPage signInPage;
	AccountPage accountPage;
	CatalogPage catalogPage;
	ProductPage productPage;
	ShoppingCartPage cartPage;

	@Test(description="Supression d'un produit dans le panier")
	public void testSupprProduitPanier() throws Exception {
		homePage = new HomePage(driver);

		// Il faut vider le paniers'il n'est pas vide 
		// Il ya risque de conflit entre sku si les produits ont un sku qui commence de la meme façon
		if (!homePage.isCartEmpty()) {
			cartPage = homePage.goToCart();
			cartPage.emptyCart();
			homePage = cartPage.goToHomePage();
		}

		// On ajoute un produit à partir du catalogue
		// Veiller à ce que les sku soient bien différent
		catalogPage = homePage.goToCategory(1);
		productPage = catalogPage.clickOnProduct(0);
		cartPage = productPage.addProductToCart(1);

		// On en ajoute un autre à partir de la page produit
		homePage = cartPage.goToHomePage();
		catalogPage = homePage.goToCategory(1);
		productPage = catalogPage.clickOnProduct(0);
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
		Assert.assertEquals(nbProductBefore - 1, nbProductAfter);
		// Et que le produit supprimé n'est plus résent dans le panier
		Assert.assertFalse(cartPage.isProductInCart(idProduct2));

	}

	@Test(description="Vidage complet du panier")
	public void testViderPanier() throws Exception {
		// on ajoute unproduit dans le panier
		homePage = new HomePage(driver);
		catalogPage = homePage.goToCategory(1);
		productPage = catalogPage.clickOnProduct(0);
		cartPage = productPage.addProductToCart(1);
		// Puis un deuxième
		homePage = cartPage.goToHomePage();
		catalogPage = homePage.goToCategory(1);
		productPage = catalogPage.clickOnProduct(1);
		cartPage = productPage.addProductToCart(1);

		// On vide le panier
		cartPage.emptyCart();

		// On test que le panier est vide
		Assert.assertTrue(cartPage.getNumberOfProductsInCart() == 0);
		Assert.assertTrue(cartPage.isCartEmpty());

	}

}
