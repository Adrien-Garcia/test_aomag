package tests.test_aomagento.COMPUTEC.utilitaries;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import tests.test_aomagento.COMPUTEC.PO.AccountPage;
import tests.test_aomagento.COMPUTEC.PO.CatalogPage;
import tests.test_aomagento.COMPUTEC.PO.HomePage;
import tests.test_aomagento.COMPUTEC.PO.ProductPage;
import tests.test_aomagento.COMPUTEC.PO.ShoppingCartPage;
import tests.test_aomagento.COMPUTEC.PO.SignInPage;
import tests.test_aomagento.COMPUTEC.PO._BasePage;


/**
 * Classe permettant de regrouper des suites d'actions effectuées dans les tests afin d'optimiser le code
 * @author airone
 *
 */
public class pageObjectTools {

	public RemoteWebDriver driver;

	_BasePage basePage;
	HomePage homePage;
	SignInPage signInPage;
	AccountPage accountPage;
	CatalogPage catalogPage;
	ProductPage productPage;
	ShoppingCartPage cartPage;

	/**
	 *  Vérifie si un utilisateur est déjà connecté, et connecte l'utilisateur voulu
	 * @param _driver
	 * @return Page Compte Client
	 * @throws Exception
	 */
	public AccountPage connectionClient(RemoteWebDriver _driver, String email, String password) throws Exception {
		basePage = new _BasePage(_driver);
		// On teste si l'utilisateur n'est pas déjà connecté
		if (basePage.isUserLoggedIn()) {
			basePage.logOutUser();
		}
		signInPage = basePage.clickConnectionClient();

		accountPage = signInPage.SignInAction(email, password);
		
		Assert.assertTrue(accountPage.isUserLoggedIn());

		return accountPage;
	}
	

	public ProductPage getToProductPage(RemoteWebDriver _driver, int nbCategory, int nbProduct, boolean emptyCart ) throws Exception {
		basePage = new _BasePage(_driver);
		// On va au panier pour recuperer le nombre de produits presents avant ajout
		cartPage = basePage.goToCart();
		if (emptyCart){
			cartPage.emptyCart();
			Thread.sleep(1000); // Vider le panier fait apparaitre un popup overlay j2t de chargement
		}
		// On va ajouter un produit au panier
		homePage = cartPage.goToHomePage();
		catalogPage = homePage.goToCategory(nbCategory);
		productPage =  catalogPage.clickOnProduct(nbProduct);
		return productPage;
	}

}
