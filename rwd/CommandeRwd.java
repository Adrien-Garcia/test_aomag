package tests.test_aomagento.rwd;

import base.Constant;
import base.DesiredCapabilitiesTest;
import tests.test_aomagento.pageobjects.AccountPageRwd;
import tests.test_aomagento.pageobjects.CheckoutPageRwd;
import tests.test_aomagento.pageobjects.CatalogPageRwd;
import tests.test_aomagento.pageobjects.ShoppingCartPageRwd;
import tests.test_aomagento.pageobjects.HomePageRwd;
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

public class CommandeRwd extends DesiredCapabilitiesTest {
	private static Log log = LogFactory.getLog(CommandeRwd.class);
	HomePageRwd homePage;
	SignInPageRwd signInPage;
	AccountPageRwd accountPage;
	CatalogPageRwd catalogPage;
	ShoppingCartPageRwd cartPage;
	CheckoutPageRwd billingPage;

	@Parameters
	public static Collection<Object[]> desiredCapabilities() {
		return Arrays.asList(new Object[][] { { "firefox", "", "ANY" } });
	}

	public CommandeRwd(String browser, String version, String plateform) throws MalformedURLException {
		super(browser, version, plateform);
	}

	@Test
	public void testCommandeUtilisateurInscrit() throws Exception {
		log.info(":: Test Thème Rwd :: tunnel de commande utilisateur inscrit::");
		homePage = new HomePageRwd(driver);

		// Click sur la premiere categorie : au premier niveau
		catalogPage = homePage.goToCategory(0);

		// click sur l'ajout au panier sur la page liste
		cartPage = catalogPage.addProductToCart(1);

		// On s'assure que le bouton commander est présent
		assertTrue("Bouton Commander present", cartPage.isOrderPossible());

		billingPage = cartPage.proceedToCheckout();

		// On vérifie si l'utilisateur est connecté sinon on le connecte
		if (!cartPage.isUserLoggedIn())
			billingPage.signInBeforeCheckout(Constant.Email, Constant.Password);

		// On fournit l'adresse de facturation/livraison
		billingPage.fillBillingAddress(Constant.Street, Constant.City, Constant.Postcode, Constant.Phone);

		// On choisit methode de livraisons
		billingPage.chooseShippingMethod();

		// On attend les methode de paiement
		billingPage.choosePaymentMethod();

		// On valide la commande
		billingPage.checkoutOrder();

		Thread.sleep(1600);
		// On attend la page de confirmation
		assertTrue(billingPage.verifyOrderSuccess());
		
		// On recupere le numero de commande
		String orderNumber = billingPage.getOrderNumber();
		System.out.println(orderNumber);
		
		// On vérifie qu'il est bien présent dans les commandes de l'utilisateur
		accountPage = billingPage.goToAccountPage();
		assertTrue(accountPage.isOrderSaved(orderNumber));
		
		log.info(":: Test validé :: tunnel de commande utilisateur inscrit::");
	}

	@Test
	public void testCommandeSansCompte() throws Exception {
		log.info(":: Test Thème Rwd :: tunnel de commande sans compte::");
		homePage = new HomePageRwd(driver);

		// Click sur la premiere categorie : au premier niveau
		catalogPage = homePage.goToCategory(0);

		// click sur l'ajout au panier sur la page liste
		cartPage = catalogPage.addProductToCart(1);

		// On s'assure que le bouton commander est présent
		assertTrue("Bouton Commander present", cartPage.isOrderPossible());

		billingPage = cartPage.proceedToCheckout();

		billingPage.checkoutAsGuest();

		// On fournit l'adresse de facturation/livraison
		billingPage.fillBillingAddressAsGuest(Constant.Firstname, Constant.Lastname, Constant.Email, Constant.Street,
				Constant.City, Constant.Postcode, Constant.Phone);

		// On choisit methode de livraisons
		billingPage.chooseShippingMethod();

		// On attend les methode de paiement
		billingPage.choosePaymentMethod();

		// On valide la commande
		billingPage.checkoutOrder();

		Thread.sleep(1600);
		// On attend la page de confirmation
		assertTrue("Page de confirmation de commande afficher", billingPage.verifyOrderSuccess());
		log.info(":: Test validé :: tunnel de commande sans compte::");
	}

}
