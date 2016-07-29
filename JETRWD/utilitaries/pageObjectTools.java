package tests.test_aomagento.JETRWD.utilitaries;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import tests.test_aomagento.JETRWD.PO.AccountPageRwd;
import tests.test_aomagento.JETRWD.PO.HomePageRwd;
import tests.test_aomagento.JETRWD.PO.ProductPageRwd;
import tests.test_aomagento.JETRWD.PO.ShoppingCartPageRwd;
import tests.test_aomagento.JETRWD.PO.SignInPageRwd;
import tests.test_aomagento.JETRWD.PO._BasePageRwd;
import tests.test_aomagento.JETRWD.PO.CatalogPageRwd;
/**
 * Classe permettant de regrouper des suites d'actions effectuées dans les tests afin d'optimiser le code
 * @author airone
 *
 */
public class pageObjectTools {

	public RemoteWebDriver driver;

	_BasePageRwd basePage;
	HomePageRwd homePage;
	SignInPageRwd signInPage;
	AccountPageRwd accountPage;
	CatalogPageRwd catalogPage;
	ProductPageRwd productPage;	
	ShoppingCartPageRwd cartPage;

	/**
	 *  Vérifie si un utilisateur est déjà connecté, et connecte l'utilisateur voulu
	 * @param _driver
	 * @return Page Compte Client
	 * @throws Exception
	 */
	public AccountPageRwd connectionClient(RemoteWebDriver _driver, String email, String password) throws Exception {
		basePage = new _BasePageRwd(_driver);
		// On teste si l'utilisateur n'est pas déjà connecté
		if (basePage.isUserLoggedIn()) {
			basePage.logOutUser();
		}
		signInPage = basePage.clickConnectionClient();

		accountPage = signInPage.SignInAction(email, password);
		

		Assert.assertTrue(accountPage.isUserLoggedIn());

		return accountPage;
	}
	

}
