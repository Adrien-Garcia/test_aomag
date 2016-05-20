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
 * <h1>Page Adresse Utilisateur</h1> Classe représentant la Page Adresse du
 * Compte Utilisateur du thème Rwd/ projet aomagento
 *
 * @author jerivoalan
 * @version 1.0
 * @since 2015-04-25
 */
public class AddressBookPage extends _BasePage {
	/**
	 * Logger pour cette page
	 */
	private static Log log = LogFactory.getLog(AddressBookPage.class);

	// Elements du DOM

	/**
	 * Body page adresse
	 */
	@FindBy(css = "body.customer-address-index")
	private WebElement addressBody;
	/**
	 * Bouton ajout d'adresse
	 */
	@FindBy(css = ".sel-add-address")
	private WebElement addNewAddrBtn;

	/**
	 * Select préfixe
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
	 * Input télèphone
	 */
	@FindBy(css = ".sel-phone-input")
	private WebElement phoneInput;
	/**
	 * Input adresse
	 */
	@FindBy(css = "sel-street-input")
	private WebElement streetInput;
	/**
	 * Input ville adresse
	 */
	@FindBy(css = ".sel-city-input")
	private WebElement cityInput;
	/**
	 * Input code postal adresse
	 */
	@FindBy(css = ".sel-zip-input")
	private WebElement postcodeInput;
	/**
	 * Bouton valider adresse
	 */
	@FindBy(css = ".sel-save-address")
	private WebElement saveAddrBtn;

	// Fonctions / Methodes de la page

	/**
	 * <h1>Constructeur de la page adresse compte</h1> initialise les éléments du DOM
	 * déclarés sous la forme de Page Factory de Selenium vérifie que le body
	 * propre à la page est présent sinon il renvoie une erreur
	 * 
	 * @param _driver
	 *            WebDriver Selenium
	 * @throws PageObjectException
	 */
	public AddressBookPage(RemoteWebDriver _driver) throws PageObjectException {
		super(_driver);
		PageFactory.initElements(driver, this);

		if (!isElementPresent(addressBody)) {
			throw new PageObjectException(this.driver,"Ce n'est pas la page attendue : " + driver.getCurrentUrl());
		}
	}

	/**
	 * Ajoute une nouvelle adresse
	 * @param firstname prénom adresse
	 * @param lastname nom  adresse
	 * @param street rue  adresse
	 * @param city ville  adresse
	 * @param postcode code postal  adresse
	 * @param phone telephone  adresse
	 * @throws PageObjectException
	 */
	public void addNewAddress(String firstname, String lastname, String street, String city, String postcode,
			String phone) throws PageObjectException {
		try {
			addNewAddrBtn.click();
			fluentWait(saveAddrBtn);
			Select select = new Select(prefixSelect);
			select.selectByIndex(1);
			firstnameInput.clear();
			firstnameInput.sendKeys(firstname);
			lastnameInput.clear();
			lastnameInput.sendKeys(lastname);
			streetInput.clear();
			streetInput.sendKeys(street);
			cityInput.clear();
			cityInput.sendKeys(city);
			postcodeInput.clear();
			postcodeInput.sendKeys(postcode);
			phoneInput.clear();
			phoneInput.sendKeys(phone);
			firstnameInput.submit();
			// saveAddrBtn.click();
		} catch (Exception e) {
			throw new PageObjectException(this.driver,e);
		}
	}

}