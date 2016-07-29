package tests.test_aomagento.BRANDER.utilitaries;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import tests.test_aomagento.BRANDER.PO.AccountPage;
import tests.test_aomagento.BRANDER.PO.CatalogPage;
import tests.test_aomagento.BRANDER.PO.HomePage;
import tests.test_aomagento.BRANDER.PO.ProductPage;
import tests.test_aomagento.BRANDER.PO.ShoppingCartPage;
import tests.test_aomagento.BRANDER.PO.SignInPage;
import tests.test_aomagento.BRANDER.PO._BasePage;


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
		
		if (!basePage.isUserLoggedIn()) {
			signInPage = basePage.clickConnectionClient();
			accountPage = signInPage.SignInAction(Constant.Email, Constant.Password);
		} else {
			accountPage=basePage.goToAccountPage();
		}

		Assert.assertTrue(accountPage.isUserLoggedIn());

		return accountPage;
	}

}
