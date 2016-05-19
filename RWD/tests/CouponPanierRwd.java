package tests.test_aomagento.RWD.tests;

import base.Constant;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.RWD.PO.AccountPageRwd;
import tests.test_aomagento.RWD.PO.CatalogPageRwd;
import tests.test_aomagento.RWD.PO.HomePageRwd;
import tests.test_aomagento.RWD.PO.ProductPageRwd;
import tests.test_aomagento.RWD.PO.ShoppingCartPageRwd;
import tests.test_aomagento.RWD.PO.SignInPageRwd;

import java.text.DecimalFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CouponPanierRwd extends DesiredCapabilitiesTestNG {

	private static Log log = LogFactory.getLog(CouponPanierRwd.class);

	HomePageRwd homePage;
	SignInPageRwd signInPage;
	AccountPageRwd accountPage;
	CatalogPageRwd catalogPage;
	ProductPageRwd productPage;
	ShoppingCartPageRwd cartPage;

	@Test(description = "Application coupon dans le panier")
	public void testCouponPanierRwd() throws Exception {

		log.info(":: Test Thème Rwd :: application coupon au panier ::");

		homePage = new HomePageRwd(driver);

		// On connecte l'utilisateur s'il ne l'est pas
		if (!homePage.isUserLoggedIn()) {
			signInPage = homePage.clickConnectionClient();
			accountPage = signInPage.SignInAction(Constant.Email, Constant.Password);
			Assert.assertTrue(accountPage.isUserLoggedIn());
			homePage = accountPage.goToHomePage();
		}

		// On ajoute un produit au panier
		catalogPage = homePage.goToCategory(1);
		productPage = catalogPage.clickOnProduct(0);
		cartPage = productPage.addProductToCart(4);

		// On annule la remise si existante
		cartPage.cancelDiscount();

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
		cartPage.cancelDiscount();

		log.info(":: Test validé:: application coupon au panier ::");

	}
}