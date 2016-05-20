package tests.test_aomagento.COMPUTEC.tests;

import base.ComplexReportFactory;
import base.Constant;
import base.DesiredCapabilitiesTest;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.RWD.PO.AccountPageRwd;
import tests.test_aomagento.RWD.PO.CatalogPageRwd;
import tests.test_aomagento.RWD.PO.CheckoutPageRwd;
import tests.test_aomagento.RWD.PO.HomePageRwd;
import tests.test_aomagento.RWD.PO.ShoppingCartPageRwd;
import tests.test_aomagento.RWD.PO.SignInPageRwd;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class CommandeRwd extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(CommandeRwd.class);
	HomePageRwd homePage;
	SignInPageRwd signInPage;
	AccountPageRwd accountPage;
	CatalogPageRwd catalogPage;
	ShoppingCartPageRwd cartPage;
	CheckoutPageRwd billingPage;

	@Test(description="Tunnel de commande (utilisateur inscrit)", priority=0)
	public void testCommandeUtilisateurInscrit() throws Exception {
		ExtentTest testReporter = ComplexReportFactory.getTest();
		homePage = new HomePageRwd(driver);
		testReporter.log(LogStatus.PASS, ":: Test Thème Rwd :: tunnel de commande utilisateur inscrit::");

		// Click sur la premiere categorie : au premier niveau
		catalogPage = homePage.goToCategory(0);
		// click sur l'ajout au panier sur la page liste
		cartPage = catalogPage.addProductToCart(1);
		testReporter.log(LogStatus.PASS, "Ajout d'un produit au panier");
		
		// On s'assure que le bouton commander est présent
		Assert.assertTrue(cartPage.isOrderPossible());

		billingPage = cartPage.proceedToCheckout();
		testReporter.log(LogStatus.PASS, "Accés au tunnel de commande");
		// On vérifie si l'utilisateur est connecté sinon on le connecte
		if (!cartPage.isUserLoggedIn())
			billingPage.signInBeforeCheckout(Constant.Email, Constant.Password);

		// On fournit l'adresse de facturation/livraison
		billingPage.fillBillingAddress(Constant.Street, Constant.City, Constant.Postcode, Constant.Phone);
		testReporter.log(LogStatus.PASS, "Addresse de facturation renseignée");

		// On choisit methode de livraisons
		billingPage.chooseShippingMethod();
		testReporter.log(LogStatus.PASS, "Méthode de livraison renseignée");

		// On attend les methode de paiement
		billingPage.choosePaymentMethod();
		testReporter.log(LogStatus.PASS, "Méthode de payement renseignée");
		
		// On valide la commande
		billingPage.checkoutOrder();
		
		// On attend la page de confirmation
		Assert.assertTrue(billingPage.verifyOrderSuccess());
		
		// On recupere le numero de commande
		String orderNumber = billingPage.getOrderNumber();
		testReporter.log(LogStatus.PASS, "Commande validée avec numéro "+orderNumber);
		
		// On vérifie qu'il est bien présent dans les commandes de l'utilisateur
		accountPage = billingPage.goToAccountPage();
		Assert.assertTrue(accountPage.isOrderSaved(orderNumber));
	}

	@Test(description="Tunnel de commande (Utilisateur sans compte)")
	public void testCommandeSansCompte() throws Exception {
		ExtentTest testReporter = ComplexReportFactory.getTest();
		homePage = new HomePageRwd(driver);
		testReporter.log(LogStatus.PASS, ":: Test Thème Rwd :: tunnel de commande utilisateur sans compte::");
		
		if (homePage.isUserLoggedIn())
			homePage.logOutUser();

		catalogPage = homePage.goToCategory(0);
		cartPage = catalogPage.addProductToCart(1);
		testReporter.log(LogStatus.PASS, "Ajout d'un produit au panier");

		// On s'assure que le bouton commander est présent
		Assert.assertTrue(cartPage.isOrderPossible());

		billingPage = cartPage.proceedToCheckout();
		testReporter.log(LogStatus.PASS, "Accés au tunnel de commande");
		billingPage.checkoutAsGuest();

		// On fournit l'adresse de facturation/livraison
		billingPage.fillBillingAddressAsGuest(Constant.Firstname, Constant.Lastname, Constant.Email, Constant.Street,
				Constant.City, Constant.Postcode, Constant.Phone);
		testReporter.log(LogStatus.PASS, "Addresse de facturation renseignée");
		// On choisit methode de livraisons
		billingPage.chooseShippingMethod();
		testReporter.log(LogStatus.PASS, "Méthode de livraison renseignée");
		// On attend les methode de paiement
		billingPage.choosePaymentMethod();
		testReporter.log(LogStatus.PASS, "Méthode de payement renseignée");
		// On valide la commande
		billingPage.checkoutOrder();

		// On attend la page de confirmation
		Assert.assertTrue(billingPage.verifyOrderSuccess());
		testReporter.log(LogStatus.PASS, "Commande validée");
	}

}
