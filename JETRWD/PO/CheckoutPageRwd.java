package tests.test_aomagento.JETRWD.PO;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.PageObjectException;

/**
 * <h1>Page Commande</h1> Classe représentant la Page de commande du thème Rwd/
 * projet aomagento
 *
 * @author jerivoalan
 * @version 1.0
 * @since 2015-04-25
 */
public class CheckoutPageRwd extends _BasePageRwd {
	/**
	 * Logger pour cette page
	 */
	private static Log log = LogFactory.getLog(CheckoutPageRwd.class);

	// Elements du DOM

	/**
	 * Body page commande
	 */
	@FindBy(css = "body.checkout-onepage-index")
	private WebElement checkoutBody;

	// Input guest ou register for checkout
	/**
	 * Option radio commande pour guest
	 */
	@FindBy(css = ".sel-radio-guest")
	private WebElement guestCheckout;
	/**
	 * Option radio commande pour guest
	 */
	@FindBy(css = ".sel-radio-register")
	private WebElement registerCheckout;
	/**
	 * Bouton continuer commande sans connection utilisateur
	 */
	@FindBy(css = ".sel-checkout-register")
	private WebElement registerCheckoutBtn;

	// Input de connection client
	/**
	 * Input email compte
	 */
	@FindBy(css = ".sel-login-email")
	private WebElement logEmailInput;
	/**
	 * Input mot de passe compte
	 */
	@FindBy(css = ".sel-login-password")
	private WebElement passInput;

	// Inputs du formulaire de l'adresse de facturation
	/**
	 * Select dans le cas où une adresse est déjà enregistrée
	 */
	@FindBy(css = ".sel-bill-address-select select")
	private WebElement selectBillingAddress;
	/**
	 * Input choix préfixe adresse facturation
	 */
	@FindBy(css = ".sel-prefix-input")
	private WebElement prefixInput;
	/**
	 * Input prénom adresse facturation
	 */
	@FindBy(css = ".sel-firstname-input")
	private WebElement firstnameInput;
	/**
	 * Input nom adresse facturation
	 */
	@FindBy(css = ".sel-lastname-input")
	private WebElement lastnameInput;
	/**
	 * Input email adresse facturation
	 */
	@FindBy(css = ".sel-bill-email")
	private WebElement emailInput;
	/**
	 * Input adresse adresse facturation
	 */
	@FindBy(css = ".sel-bill-street1")
	private WebElement streetInput;
	/**
	 * Input ville adresse facturation
	 */
	@FindBy(css = ".sel-bill-city")
	private WebElement cityInput;
	/**
	 * Input code postal adresse facturation
	 */
	@FindBy(css = ".sel-bill-zip")
	private WebElement postcodeInput;
	/**
	 * Input télèphone adresse facturation
	 */
	@FindBy(css = ".sel-bill-phone")
	private WebElement telInput;
	/**
	 * Checkbox utiliser adresse facturation pour adresse livraison
	 */
	@FindBy(css = ".sel-bill-use_for_ship")
	private WebElement shippingInput;
	/**
	 * Bouton validation adresse facturation
	 */
	@FindBy(css = ".sel-billing-save")
	private WebElement adrSaveBtn;

	// Choix de la méthode de livraison
	/**
	 * Liste choix méthode livraison
	 */
	@FindBy(css = "form.sel-form-method-shipping input.radio")
	private List<WebElement> chooseShip;
	/**
	 * Bouton validation méthode livraison
	 */
	@FindBy(css = ".sel-shipping-method-save")
	private WebElement shipSaveBtn;

	// choix du mode de payement
	/**
	 * Liste choix méthode de payement
	 */
	@FindBy(css = "form.sel-form-payment input.radio")
	private List<WebElement> payOptions;
	/**
	 * Bouton de validation méthode de payement
	 */
	@FindBy(css = ".sel-payment-save")
	private WebElement paySaveBtn;

	/**
	 * Bouton validation commande
	 */
	@FindBy(css = ".sel-btn-checkout")
	private WebElement checkoutBtn;

	/**
	 * Message succés commande
	 */
	@FindBy(css = ".checkout-onepage-success")
	private WebElement successCheckoutBody;

	/**
	 * 
	 */
	@FindBy(css=".sel-order-number")
	private WebElement orderNumber;
	
	// Fonctions / Methodes de la page

	/**
	 * <h1>Constructeur de la page commande</h1> initialise les éléments du DOM
	 * déclarés sous la forme de Page Factory de Selenium vérifie que le body
	 * propre à la page est présent sinon il renvoie une erreur
	 * 
	 * @param _driver
	 *            WebDriver Selenium
	 * @throws PageObjectException
	 */
	public CheckoutPageRwd(RemoteWebDriver _driver) throws PageObjectException {
		super(_driver);
		PageFactory.initElements(this.driver, this);

		if (!isElementPresent(checkoutBody)) {
			throw new PageObjectException(this.driver,"Ce n'est pas la page attendue : " + driver.getCurrentUrl());
		}
	}

	/**
	 * Choisit la commande sans compte
	 * 
	 * @throws PageObjectException
	 */
	public void checkoutAsGuest() throws PageObjectException {
		try {
			guestCheckout.click();
			registerCheckoutBtn.click();
			log.info("Checkout as guest");
		} catch (Exception e) {
			throw new PageObjectException(this.driver,"No checkout as guest ", e);
		}
	}

	/**
	 * Connecte l'uitlisateur pour la commande
	 * 
	 * @param username
	 *            adresse mail de l'utilisateur
	 * @param password
	 *            mot de passe de l'utilisateur
	 * @throws PageObjectException
	 */
	public void signInBeforeCheckout(String username, String password) throws PageObjectException {
		try {
			logEmailInput.clear();
			logEmailInput.sendKeys(username);
			passInput.clear();
			passInput.sendKeys(password);
			passInput.submit();
			log.info("Checkout as user : " + username);
		} catch (Exception e) {
			throw new PageObjectException(this.driver,"La connection utilisateur ne s'est pas faite ", e);
		}
	}

	/**
	 * Renvoi le Select du Dom sous forme de Select utilisable par Selenium
	 * 
	 * @param select
	 *            du Dom
	 * @return Select utilisable par Selenium
	 * @throws PageObjectException
	 */
	private Select getSelect(WebElement select) throws PageObjectException {
		try {
			return new Select(select);
		} catch (UnexpectedTagNameException e) {
			return null;
		}
	}

	/**
	 * Rempli les informations relatives à l'adresse de facturation<br>
	 * Choisit par défaut d'utiliser la même adresse pour la livraison<br>
	 * (Si une adresse est déjà renseignée il choisit la première d'entres
	 * elles)
	 * 
	 * @param street
	 *            adresse facturation
	 * @param city
	 *            ville facturation
	 * @param postcode
	 *            code postale adresse facturation
	 * @param phone
	 *            télèphone facturation
	 * @throws PageObjectException
	 */
	public void fillBillingAddress(String street, String city, String postcode, String phone)
			throws PageObjectException {
		try {
			fluentWait(adrSaveBtn, 1, 6);
			if (isElementPresent(selectBillingAddress)) {
				getSelect(selectBillingAddress).selectByIndex(1);
				log.info("Adresse de facturation préexistante selectionnée");
			} else {
				getSelect(prefixInput).selectByIndex(1);
				streetInput.clear();
				streetInput.sendKeys(street);
				cityInput.clear();
				cityInput.sendKeys(city);
				postcodeInput.clear();
				postcodeInput.sendKeys(postcode);
				telInput.clear();
				telInput.sendKeys(phone);
				shippingInput.click();
				log.info("Adresse de facturation renseignée");
			}
			adrSaveBtn.click();
		} catch (Exception e) {
			throw new PageObjectException(this.driver,"Il y a eu un problème avec le formulaire d'adresse de facturation", e);
		}
	}

	/**
	 * Rempli l'adresse de facturation pour un utilisateur non connecté
	 * 
	 * @param firstname
	 *            prenom
	 * @param lastname
	 *            nom
	 * @param email
	 *            email
	 * @param street
	 *            rue adresse
	 * @param city
	 *            ville adresse
	 * @param postcode
	 *            code postale adresse
	 * @param phone
	 *            télèphone
	 * @throws PageObjectException
	 */
	public void fillBillingAddressAsGuest(String firstname, String lastname, String email, String street, String city,
			String postcode, String phone) throws PageObjectException {
		try {
			fluentWait(adrSaveBtn, 1, 6);

			getSelect(prefixInput).selectByIndex(1);
			firstnameInput.clear();
			firstnameInput.sendKeys(firstname);
			lastnameInput.clear();
			lastnameInput.sendKeys(lastname);
			emailInput.clear();
			emailInput.sendKeys(email);
			streetInput.clear();
			streetInput.sendKeys(street);
			cityInput.clear();
			cityInput.sendKeys(city);
			postcodeInput.clear();
			postcodeInput.sendKeys(postcode);
			telInput.clear();
			telInput.sendKeys(phone);
			shippingInput.click();
			log.info("Adresse de facturation renseignée pour utilisateur invité");

			adrSaveBtn.click();
		} catch (Exception e) {
			throw new PageObjectException(this.driver,"Il y a eu un problème avec le formulaire d'adresse de facturation", e);
		}
	}

	/**
	 * Choisit une méthode de livraison<br>
	 * S'il y a plusieurs méthodes disponible il choisit la deuxième par défaut
	 * 
	 * @throws PageObjectException
	 */
	public void chooseShippingMethod() throws PageObjectException {
		try {
			fluentWait(shipSaveBtn, 1, 10);
			if (chooseShip.size() > 0)
				chooseShip.get(1).click();
			shipSaveBtn.click();
			log.info("Moyen de livraison choisi");
		} catch (Exception e) {
			throw new PageObjectException(this.driver,e);
		}
	}

	/**
	 * Choisit une méthode de payement<br>
	 * S'il y a plusieurs méthodes disponible il choisit la deuxième par défaut
	 * 
	 * @throws PageObjectException
	 */
	public void choosePaymentMethod() throws PageObjectException {
		try {
			fluentWait(paySaveBtn, 1, 10);
			if (payOptions.size() > 0) {
				payOptions.get(1).click();
			}
			paySaveBtn.click();
			log.info("Moyen de payement choisi");
		} catch (Exception e) {
			throw new PageObjectException(this.driver,e);
		}
	}

	/**
	 * Valide la commande
	 * 
	 * @throws PageObjectException
	 */
	public void checkoutOrder() throws PageObjectException {
		try {
			fluentWait(checkoutBtn, 1, 8);
			checkoutBtn.click();
		} catch (Exception e) {
			throw new PageObjectException(this.driver,e);
		}

	}

	/**
	 * Vérifie que la page affiche un message de réussite après validation
	 * 
	 * @return <b>boolean</b>
	 */
	public boolean verifyOrderSuccess() {
		try {
			fluentWait(successCheckoutBody, 1, 10);
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}
	
	public String getOrderNumber(){
		return orderNumber.getText();
	}

}
