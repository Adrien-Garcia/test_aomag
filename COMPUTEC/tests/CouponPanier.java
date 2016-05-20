package tests.test_aomagento.COMPUTEC.tests;

import base.Constant;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.COMPUTEC.PO.AccountPage;
import tests.test_aomagento.COMPUTEC.PO.CatalogPage;
import tests.test_aomagento.COMPUTEC.PO.HomePage;
import tests.test_aomagento.COMPUTEC.PO.ProductPage;
import tests.test_aomagento.COMPUTEC.PO.ShoppingCartPage;
import tests.test_aomagento.COMPUTEC.PO.SignInPage;

import java.text.DecimalFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CouponPanier extends DesiredCapabilitiesTestNG {

	private static Log log = LogFactory.getLog(CouponPanier.class);

	HomePage homePage;
	SignInPage signInPage;
	AccountPage accountPage;
	CatalogPage catalogPage;
	ProductPage productPage;
	ShoppingCartPage cartPage;

	@Test(description = "Application coupon dans le panier")
	public void testCouponPanierRwd() throws Exception {
		log.info(":: Test COMPUTEC :: application coupon au panier ::");

		homePage = new HomePage(driver);

		// On connecte l'utilisateur s'il ne l'est pas
		if (!homePage.isUserLoggedIn()) {
			signInPage = homePage.clickConnectionClient();
			accountPage = signInPage.SignInAction(Constant.Email, Constant.Password);
			Assert.assertTrue(accountPage.isUserLoggedIn());
			homePage = accountPage.goToHomePage();
		}
		// On vide le panier ce qui permet d'annuler une quelconque remise si déjà appiquee
		cartPage = homePage.goToCart();
		cartPage.emptyCart();
		homePage = cartPage.goToHomePage();

		// On ajoute un produit au panier
		catalogPage = homePage.goToCategory(1);
		productPage = catalogPage.clickOnProduct(0);
		cartPage = productPage.addProductToCart(4);

		// On recupere le prix ttc avant remise
		float prixAvtRemise = cartPage.getTtcPrice();

		// On applique la réduction
		cartPage.applyDiscount("CP14");

		// on recupere le prix ttc apres remise
		float prixAprRemise = cartPage.getTtcPrice();
		// On récupere le montant de la remise sur le panier
		float montantRemise = cartPage.getDiscountPrice();

		// On test que le prix après remise est bien le bon
		Assert.assertTrue(prixAvtRemise + montantRemise == prixAprRemise);

		// On s'assure que ce sont bien 10% du panier qui sont déduit propre à
		// la remise
		DecimalFormat df = new DecimalFormat("######.##");
		float remiseCalculee = Float.parseFloat(df.format(prixAvtRemise * 0.9).replace(',', '.'));
		Assert.assertTrue(remiseCalculee == prixAprRemise);

		// On annule la réduction après test
		cartPage.emptyCart();

	}
}