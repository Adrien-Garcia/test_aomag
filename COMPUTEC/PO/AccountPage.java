package tests.test_aomagento.COMPUTEC.PO;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.PageObjectException;

/**
 * <h1>Page Compte</h1> Classe représentant la Page compte du thème Rwd/ projet
 * aomagento
 *
 * @author jerivoalan
 * @version 1.0
 * @since 2015-04-25
 */
public class AccountPage extends _BasePage {
	/**
	 * Logger pour cette classe
	 */
	private static Log log = LogFactory.getLog(AccountPage.class);

	// Elements du DOM
	/**
	 * Body page compte
	 */
	@FindBy(css = "body.customer-account-index")
	private WebElement accountBody;
	/**
	 * Liens tableau de bord
	 */
	@FindBy(css = ".sel-account a")
	private WebElement accountLink;
	/**
	 * Lien informations du compte
	 */
	@FindBy(css = ".sel-account_edit a")
	private WebElement accountEditLink;
	/**
	 * Lien Carnet d'adresse
	 */
	@FindBy(css = ".sel-address_book a")
	private WebElement addressBookLink;
	/**
	 * Lien Commandes
	 */
	@FindBy(css = ".sel-orders a")
	private WebElement ordersLink;
	/**
	 * Lien liste d'envie
	 */
	@FindBy(css = ".sel-wishlist a")
	private WebElement wishlistLink;
	/**
	 * Lien abonnement newsletter
	 */
	@FindBy(css = ".sel-newsletter a")
	private WebElement newsletterLink;

	/**
	 * Liste des numéros de commande
	 */
	@FindBy(css = ".sel-order-number")
	private List<WebElement> orderNumbers;

	/**
	 * Checkbox inscription à la newsletter
	 */
	@FindBy(css = ".sel-subscribe-newsletter input")
	private WebElement subscriptionCheckBox;

	/**
	 * Message succés
	 */
	@FindBy(css = "li.success-msg")
	private WebElement successMsg;

	// Fonctions / Methodes de la page

	/**
	 * <h1>Constructeur de la page compte</h1> initialise les éléments du DOM
	 * déclarés sous la forme de Page Factory de Selenium vérifie que le body
	 * propre à la page est présent sinon il renvoie une erreur
	 * 
	 * @param _driver
	 *            WebDriver Selenium
	 * @throws PageObjectException
	 */
	public AccountPage(RemoteWebDriver _driver) throws PageObjectException {
		super(_driver);
		PageFactory.initElements(driver, this);

		if (!isElementPresent(accountBody)) {
			throw new PageObjectException(this.driver,"Ce n'est pas la page attendue : " + driver.getCurrentUrl());
		}
	}

	/**
	 * Navigue vers la page d'informations du compte
	 * 
	 * @return <b>AccountEditPageRwd</b> Page informations du compte
	 * @throws PageObjectException
	 */
	public AccountEditPage goToInfoAccount() throws PageObjectException {
		try {
			accountEditLink.click();
			return new AccountEditPage(driver);

		} catch (Exception e) {
			throw new PageObjectException(this.driver,e);
		}
	}

	/**
	 * Test si l'utilisateur est inscrit à la Newsletter
	 * 
	 * @return <b>boolean</b>
	 * @throws PageObjectException
	 */
	public boolean isUserSubscribedToNewsletter() throws PageObjectException {
		try {
			newsletterLink.click();
			boolean isUserSubscribedToNewsletter = subscriptionCheckBox.isSelected();
			accountLink.click();
			return isUserSubscribedToNewsletter;
		} catch (Exception e) {
			throw new PageObjectException(this.driver,e);
		}
	}

	/**
	 * Inscrit l'utilisateur à la Newsletter s'il ne l'est pas
	 */
	public void subscribeToNewsletter() {
		newsletterLink.click();
		if (subscriptionCheckBox.isSelected() == false)
			subscriptionCheckBox.click();
		subscriptionCheckBox.submit();
	}

	/**
	 * Parcours la liste des numéros de commandes et vérifie s'il celle voulue
	 * est présente
	 * 
	 * @param nbOrder
	 *            numéro de commande à vérifier
	 * @return <b>boolean</b>
	 */
	public boolean isOrderSaved(String nbOrder) {
		ordersLink.click();
		for (WebElement orderNb : orderNumbers) {
			if (orderNb.getText().contains(nbOrder)) {
				log.info("Commande N°" + nbOrder + " validée");
				accountLink.click();
				return true;
			}
		}
		accountLink.click();
		return false;
	}

	/**
	 * Désinscrit l'utilisateur de la Newsletter s'il ne l'est pas
	 */
	public void unsubscribeToNewsletter() {
		newsletterLink.click();
		if (subscriptionCheckBox.isSelected() == true)
			subscriptionCheckBox.click();
		subscriptionCheckBox.submit();
	}

	/**
	 * Navigue vers le carnet d'adresse du compte
	 * 
	 * @return <b>AccountAddressPageRwd</b>
	 * @throws PageObjectException
	 */
	public AddressBookPage goToAddressMenuLink() throws PageObjectException {
		addressBookLink.click();
		return new AddressBookPage(driver);
	}

	/**
	 * Navigue vers la liste d'envie
	 * 
	 * @return <b>AccountWishlistPageRwd</b> page liste d'envie
	 * @throws PageObjectException
	 */
	public WishlistPage goToWishlist() throws PageObjectException {
		Actions action = new Actions(driver);
		action.moveToElement(wishlistLink).click(wishlistLink).perform();
		new WebDriverWait(driver, 2);
		return new WishlistPage(driver);
	}

}