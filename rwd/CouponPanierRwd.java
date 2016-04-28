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
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.*;

public class CouponPanierRwd extends DesiredCapabilitiesTest {

	private static Log log = LogFactory.getLog(CouponPanierRwd.class);

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

	public CouponPanierRwd(String browser, String version, String plateform) throws MalformedURLException {
		super(browser, version, plateform);
	}

	@Test
	public void testCouponPanierRwd() throws Exception {

		log.info(":: Test Thème Rwd :: application coupon au panier ::");

		homePage = new HomePageRwd(driver);

		// On se connecte
		signInPage = homePage.clickConnectionClient();
		accountPage = signInPage.SignInAction(Constant.Email, Constant.Password);

		// On ajoute un produit au panier
		homePage = accountPage.goToHomePage();
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
		assertTrue(prixAvtRemise + montantRemise == prixAprRemise);

		// On s'assure que ce sont bien 10% du panier qui sont déduit propre à
		// la remise
		DecimalFormat df = new DecimalFormat("######.##");
		float remiseCalculee = Float.parseFloat(df.format(prixAvtRemise * 0.9));
		assertTrue(remiseCalculee == prixAprRemise);

		// On annule la réduction après test
		cartPage.cancelDiscount();

		log.info(":: Test validé:: application coupon au panier ::");

	}
}