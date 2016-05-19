package tests.test_aomagento.RWD.PO;

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
public class AddressBookPageRwd extends _BasePageRwd {
	/**
	 * Logger pour cette page
	 */
	private static Log log = LogFactory.getLog(AddressBookPageRwd.class);

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
	@FindBy(id = "prefix")
	private WebElement prefixSelect;
	/**
	 * Input prénom
	 */
	@FindBy(id = "firstname")
	private WebElement firstnameInput;
	/**
	 * Input nom
	 */
	@FindBy(id = "lastname")
	private WebElement lastnameInput;
	/**
	 * Input télèphone
	 */
	@FindBy(id = "telephone")
	private WebElement phoneInput;
	/**
	 * Input adresse
	 */
	@FindBy(id = "street_1")
	private WebElement streetInput;
	/**
	 * Input ville adresse
	 */
	@FindBy(id = "city")
	private WebElement cityInput;
	/**
	 * Input code postal adresse
	 */
	@FindBy(id = "zip")
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
	public AddressBookPageRwd(RemoteWebDriver _driver) throws PageObjectException {
		super(_driver);
		PageFactory.initElements(driver, this);

		if (!isElementPresent(addressBody)) {
			throw new PageObjectException(this.driver,"Ce n'est pas la page attendue : " + driver.getCurrentUrl());
		}
	}

	/**
	 * Récupere le Select prefix sous forme utilisable par Selenium
	 * @return Select Selenium
	 * @throws PageObjectException
	 */
	private Select getPrefixSelect() throws PageObjectException {
		try {
			fluentWait(prefixSelect);
			return new Select(prefixSelect);
		} catch (Exception e) {
			throw new PageObjectException(this.driver,e);
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
			getPrefixSelect().selectByIndex(1);
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