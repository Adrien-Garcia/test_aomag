package tests.test_aomagento.BRANDER.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import base.ComplexReportFactory;
import tests.test_aomagento.BRANDER.utilitaries.Constant;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.BRANDER.PO.AccountPage;
import tests.test_aomagento.BRANDER.PO.CatalogPage;
import tests.test_aomagento.BRANDER.PO.CheckoutPage;
import tests.test_aomagento.BRANDER.PO.HomePage;
import tests.test_aomagento.BRANDER.PO.ProductPage;
import tests.test_aomagento.BRANDER.PO.ShoppingCartPage;
import tests.test_aomagento.BRANDER.PO.SignInPage;

public class Commande extends DesiredCapabilitiesTestNG {
	// private static Log log = LogFactory.getLog(CommandeRwd.class);
	HomePage homePage;
	SignInPage signInPage;
	AccountPage accountPage;
	CatalogPage catalogPage;
	ProductPage productPage;
	ShoppingCartPage cartPage;
	CheckoutPage billingPage;

	@Test(description = "Tunnel de commande (utilisateur inscrit)", priority = 0)
	public void testCommandeUtilisateurInscrit() throws Exception {
		ExtentTest testReporter = ComplexReportFactory.getTest();
		homePage = new HomePage(driver);

		if (homePage.isUserLoggedIn())
			homePage.logOutUser();

		// Click sur la premiere categorie : au premier niveau
		catalogPage = homePage.goToCategory(1);
		productPage = catalogPage.clickOnProduct(0);
		cartPage = productPage.addProductToCart(1);
		testReporter.log(LogStatus.PASS, "Ajout d'un produit au panier");

		// On s'assure que le bouton commander est présent
		Assert.assertTrue(cartPage.isOrderPossible());

		billingPage = cartPage.proceedToCheckout();
		testReporter.log(LogStatus.PASS, "Accés au tunnel de commande");
		
		// On vérifie si l'utilisateur est connecté sinon on le connecte
		billingPage.signInBeforeCheckout(Constant.Email, Constant.Password);

		// On fournit l'adresse de facturation
		billingPage.fillBillingAddress(Constant.Street, Constant.City, Constant.Postcode, Constant.Phone);
		testReporter.log(LogStatus.PASS, "Addresse de facturation renseignée");

		// On fournit l'adresse de livraison
		//billingPage.fillShippingAddress();
		//testReporter.log(LogStatus.PASS, "Addresse de livraison renseignée");

		// On choisit la methode de livraisons
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
		testReporter.log(LogStatus.PASS, "Commande validée avec numéro " + orderNumber);

		// On vérifie qu'il est bien présent dans les commandes de l'utilisateur
		accountPage = billingPage.goToAccountPage();
		Assert.assertTrue(accountPage.isOrderSaved(orderNumber));
	}

	@Test(description = "Tunnel de commande (Utilisateur sans compte)")
	public void testCommandeSansCompte() throws Exception {
		ExtentTest testReporter = ComplexReportFactory.getTest();
		homePage = new HomePage(driver);

		if (homePage.isUserLoggedIn())
			homePage.logOutUser();

		catalogPage = homePage.goToCategory(1);
		productPage = catalogPage.clickOnProduct(0);
		cartPage = productPage.addProductToCart(1);
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
		// On fournit l'adresse de livraison
		billingPage.fillShippingAddress();
		testReporter.log(LogStatus.PASS, "Addresse de livraison renseignée");
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
