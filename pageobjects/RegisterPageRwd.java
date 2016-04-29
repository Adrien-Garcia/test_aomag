package tests.test_aomagento.pageobjects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import base.PageObjectException;

/**
 * <h1>Page Inscription</h1> Classe représentant la page de connection du
 * thème Rwd pour le projet aomagento
 *
 * @author jerivoalan
 * @version 1.0
 * @since 2015-04-27
 */
public class RegisterPageRwd extends _BasePageRwd {
	/**
	 * Logger pour cette classe
	 */
	private static Log log = LogFactory.getLog(RegisterPageRwd.class);

	// Elements du DOM

	/**
	 * Body pour la page d'inscription
	 */
	@FindBy(css = ".customer-account-create")
	private WebElement createAccountBody;
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
	 * CheckBox abonnement newsletter
	 */
	@FindBy(css = ".sel-subscribe-news")
	private WebElement newsletterCheck;
	/**
	 * Bouton création de compte
	 */
	@FindBy(css = ".sel-register")
	private WebElement registerBtn;

	// Fonctions / Methodes de la page

	/**
	 * <h1>Constructeur de la Page Inscription</h1> Initialise les éléments du
	 * DOM déclarés sous la forme Page Factory de Selenium vérifie que le body
	 * propre à la page est présent sinon il renvoie une erreur
	 * 
	 * @param _driver
	 *            WebDriver Selenium
	 * @throws PageObjectException
	 */
	public RegisterPageRwd(RemoteWebDriver _driver) throws PageObjectException {
		super(_driver);
		PageFactory.initElements(driver, this);

		if (!isElementPresent(createAccountBody)) {
			throw new PageObjectException("Ce n'est pas la page attendue : " + driver.getCurrentUrl());
		}
	}
	
	/**
	 * Renvoi l'element du DOM Select sous forme de Select utilisable par Selenium
	 * @return
	 */
	private Select getPrefixSelect() {
		return new Select(prefixSelect);
	}

	/**
	 * Rempli le formulaire d'inscription et l'envoi 
	 * @param firstname prénom de l'utilisateur
	 * @param lastname nom de l'utilisateur
	 * @param email email de l'uitlisateur
	 * @param pass mdp de l'utilisateur
	 * @param news choisir si inscription à la newsletter
	 * @return <b>AccountPageRwd</b> page compte utilisateur
	 * @throws PageObjectException
	 */
	public AccountPageRwd createAccount(String firstname, String lastname, String email, String pass, Boolean news)
			throws PageObjectException {
		try {
			// On sélectionne M. par défaut
			getPrefixSelect().selectByIndex(1);
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
			if (news == true)
				newsletterCheck.click();
			registerBtn.click();
			return new AccountPageRwd(driver);
		} catch (Exception e) {
			throw new PageObjectException(e);
		}
	}
}
