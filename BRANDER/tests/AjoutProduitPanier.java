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

public class AjoutProduitPanier extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(AjoutProduitPanier.class);
	
	HomePage homePage;
	SignInPage signInPage;
	AccountPage accountPage;
	CatalogPage catalogPage;
	ProductPage productPage;
	ShoppingCartPage cartPage;

	@Test(description="Ajout d'un produit au panier")
	public void testAjoutProduitPanier() throws Exception {
		homePage = new HomePage(driver);
		
		// On va au panier pour recuperer le nombre de produits presents avant
		// ajout
		cartPage = homePage.goToCart();
		cartPage.emptyCart();
		
		// On va ajouter un produit au panier
		homePage = cartPage.goToHomePage();
		catalogPage = homePage.goToCategory(Constant.Category1);
		productPage =  catalogPage.clickOnProduct(Constant.Product1);
		
		// On recupere son Sku
		String skuProduct = productPage.getProductSku();
		cartPage = productPage.addProductToCart(4);

		// On recupere le nombre de produits après ajout
		int nbProduitApresAjout = cartPage.getNumberOfProductsInCart();
		
		// On test
		Assert.assertTrue(nbProduitApresAjout == 1, "Il y a plus de produits prévus dans le panier");
		Assert.assertTrue(cartPage.isProductInCart(skuProduct), "Le produit n'est pas présent");
		Assert.assertTrue(cartPage.getQuantityOfProduct(skuProduct)==4, "La quantité ajoutée n'est pas la même");
		
	}

}
