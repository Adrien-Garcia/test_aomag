package tests.test_aomagento.COMPUTEC.PO;

import java.util.NoSuchElementException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.server.handler.FindElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.PageObjectException;

/**
 * <h1>Page de Connection client</h1> Classe représentant la page de connection
 * du thème Rwd pour le projet aomagento
 *
 * @author jerivoalan
 * @version 1.0
 * @since 2015-04-27
 */
public class SignInPage extends _BasePage {
	/**
	 * Logger pour cette classe
	 */
	private static Log log = LogFactory.getLog(SignInPage.class);

	// Elements du DOM

	/**
	 * Body de la page connection
	 */
	@FindBy(css = ".customer-account-login")
	private WebElement signInBody;

	// CONNECTION
	/**
	 * Input email client
	 */
	@FindBy(css = ".sel-email-login")
	private WebElement emailLogin;
	/**
	 * Input mot de passe client
	 */
	@FindBy(css = ".sel-pass-login")
	private WebElement passLogin;
	/**
	 * bouton connexion
	 */
	@FindBy(css = ".sel-login")
	private WebElement loginBtn;
	// CREATION DE COMPTE
	/**
	 * Sélecteur préfixe
	 */
	@FindBy(css = ".sel-prefix-input")
	private WebElement prefixSelect;
	/**
	 * Input prénom
	 */
	@FindBy(css = ".sel-firstname-input")
	private WebElement firstnameInput;
	/**
	 * Input nom
	 */
	@FindBy(css = ".sel-lastname-input")
	private WebElement lastnameInput;
	/**
	 * Input adresse mail
	 */
	@FindBy(css = ".sel-email-input")
	private WebElement emailInput;
	/**
	 * Input mot de passe
	 */
	@FindBy(css = ".sel-pass-input")
	private WebElement passwordInput;
	/**
	 * Input de confirmation de mot de passe
	 */
	@FindBy(css = ".sel-confirm-pass-input")
	private WebElement confirmationInput;
	/**
	 * Bouton création compte
	 */
	@FindBy(css = ".sel-register")
	private WebElement registerBtn;

	// Fonctions / Methodes de la page

	/**
	 * <h1>Constructeur de la page connection</h1> Initialise les éléments du
	 * DOM déclarés sous la forme Page Factory de Selenium, vérifie que le body
	 * propre à la page est présent sinon il renvoie une erreur.
	 * 
	 * @param _driver
	 *            WebDriver Selenium
	 * @throws PageObjectException
	 */
	public SignInPage(RemoteWebDriver _driver) throws PageObjectException {
		super(_driver);
		PageFactory.initElements(driver, this);

		if (!isElementPresent(signInBody)) {
			throw new PageObjectException(this.driver,"Ce n'est pas la page attendue : " + driver.getCurrentUrl());
		}
	}

	/**
	 * Rempli le formulaire de connection avec l'identifiant et le mot de passe
	 * et l'envoi.
	 * 
	 * @param username
	 *            adresse mail de l'utilisateur
	 * @param password
	 *            mot de passe de l'utilisateur
	 * @return AccountPageRwd page compte utilisateur
	 * @throws PageObjectException
	 */
	public AccountPage SignInAction(String username, String password) throws PageObjectException {
		try {
			emailLogin.clear();
			emailLogin.sendKeys(username);
			passLogin.clear();
			passLogin.sendKeys(password);
			loginBtn.click();
			log.info("Connection de l'utilisateur " + username + " en cours...");
			return new AccountPage(driver);
		} catch (NoSuchElementException e) {
			throw new PageObjectException(this.driver,"La connection utilisateur ne s'est pas faite ", e);
		}
	}

	/**
	 * Clique sur "créer un compte"
	 * 
	 * @return RegisterPageRwd page de création de compte
	 * @throws PageObjectException
	 */
	public RegisterPage registerNewAccount(String firstname, String lastname, String email, String pass) throws PageObjectException {
		if (isElementPresent(registerBtn)) {
			Select select = new Select(prefixSelect);
			select.selectByIndex(1);
			firstnameInput.clear();
			firstnameInput.sendKeys(firstname);
			lastnameInput.clear();
			lastnameInput.sendKeys(lastname);
			emailInput.clear();
			emailInput.sendKeys(email);
			passwordInput.clear();
			passwordInput.sendKeys(pass);
			confirmationInput.clear();
			confirmationInput.sendKeys(pass);
			registerBtn.click();
			return new RegisterPage(driver);
		} else {
			throw new PageObjectException(this.driver,"Le bouton de création de compte n'est pas présent ou pas accessible");
		}
	}

}