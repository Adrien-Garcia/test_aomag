package tests.test_aomagento.COMPUTEC.PO;

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
public class RegisterPage extends _BasePage {
	/**
	 * Logger pour cette classe
	 */
	private static Log log = LogFactory.getLog(RegisterPage.class);

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
	 * Bouton création compte
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
	public RegisterPage(RemoteWebDriver _driver) throws PageObjectException {
		super(_driver);
		PageFactory.initElements(driver, this);

		if (!isElementPresent(createAccountBody)) {
			throw new PageObjectException(this.driver,"Ce n'est pas la page attendue : " + driver.getCurrentUrl());
		}
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
	public AccountPage createAccount(String firstname, String lastname, String email, String pass, Boolean news)
			throws PageObjectException {
		try {
			// On sélectionne M. par défaut
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
			this.goToAccountPage();
			return new AccountPage(driver);
		} catch (Exception e) {
			throw new PageObjectException(this.driver,e);
		}
	}
}
