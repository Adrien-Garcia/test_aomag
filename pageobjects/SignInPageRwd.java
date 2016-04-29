package tests.test_aomagento.pageobjects;

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
public class SignInPageRwd extends _BasePageRwd {
	/**
	 * Logger pour cette classe
	 */
	private static Log log = LogFactory.getLog(SignInPageRwd.class);

	// Elements du DOM

	/**
	 * Body de la page connection
	 */
	@FindBy(css = ".customer-account-login")
	private WebElement signInBody;

	/**
	 * Input email client
	 */
	@FindBy(css = ".sel-email-login")
	private WebElement emailInput;
	/**
	 * Input mot de passe client
	 */
	@FindBy(css = ".sel-pass-login")
	private WebElement passInput;
	/**
	 * bouton connexion
	 */
	//@FindBy(css = ".sel-login")
	@FindBy(css = ".sel-user-login")
	private WebElement signInBtn;
	/**
	 * Bouton création compte
	 */
	@FindBy(css = ".sel-create-account")
	private WebElement createAccountBtn;

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
	public SignInPageRwd(RemoteWebDriver _driver) throws PageObjectException {
		super(_driver);
		PageFactory.initElements(driver, this);

		if (!isElementPresent(signInBody)) {
			throw new PageObjectException("Ce n'est pas la page attendue : " + driver.getCurrentUrl());
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
	public AccountPageRwd SignInAction(String username, String password) throws PageObjectException {
		try {
			emailInput.clear();
			emailInput.sendKeys(username);
			passInput.clear();
			passInput.sendKeys(password);
			signInBtn.click();
			log.info("Connection de l'utilisateur " + username + " en cours...");
			return new AccountPageRwd(driver);
		} catch (NoSuchElementException e) {
			throw new PageObjectException("La connection utilisateur ne s'est pas faite ", e);
		}
	}

	/**
	 * Clique sur "créer un compte"
	 * 
	 * @return RegisterPageRwd page de création de compte
	 * @throws PageObjectException
	 */
	public RegisterPageRwd clickCreateAccount() throws PageObjectException {
		if (isElementPresent(createAccountBtn)) {
			createAccountBtn.click();
			return new RegisterPageRwd(driver);
		} else {
			throw new PageObjectException("Le bouton de création de compte n'est pas présent ou pas accessible");
		}
	}

}