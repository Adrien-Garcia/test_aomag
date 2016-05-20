package tests.test_aomagento.RWD.PO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.PageObjectException;

/**
 * <h1>Page Informations compte</h1> Classe représentant la Page d'édition des
 * informations du compte du Compte Utilisateur du thème Rwd/ projet aomagento
 *
 * @author jerivoalan
 * @version 1.0
 * @since 2015-04-25
 */
public class AccountEditPageRwd extends _BasePageRwd {
	/**
	 * Logger pour cette classe
	 */
	private static Log log = LogFactory.getLog(AccountEditPageRwd.class);

	// Elements du DOM
	/**
	 * Body de la page
	 */
	@FindBy(css = "body.customer-account")
	private WebElement accountBody;
	/**
	 * Préfixe utilisateur
	 */
	@FindBy(id = "prefix")
	private WebElement prefixSelect;
	/**
	 * Prénom utilisateur
	 */
	@FindBy(id = "firstname")
	private WebElement firstnameInput;
	/**
	 * Nom utilisateur
	 */
	@FindBy(id = "lastname")
	private WebElement lastnameInput;
	/**
	 * Email utilisateur
	 */
	@FindBy(id = "email")
	private WebElement emailInput;
	/**
	 * CheckBox Changer MDP
	 */
	@FindBy(css = ".sel-edit-password")
	private WebElement passCheckBox;
	/**
	 * Input MDP actuel
	 */
	@FindBy(css = ".sel-current-password")
	private WebElement curentPassInput;
	/**
	 * Input nouveau MDP
	 */
	@FindBy(css = ".sel-new-password")
	private WebElement newPassInput;
	/**
	 * Input confirmation nouveau MDP
	 */
	@FindBy(css = ".sel-conf-new-password")
	private WebElement cNewPassInput;
	
	/**
	 * Bouton validation infos compte
	 */
	@FindBy(css = ".sel-save-info")
	private WebElement saveInfoBtn;

	// Fonctions / Methodes de la page
	
	/**
	 * <h1>Constructeur de la page infos du compte</h1> initialise les éléments
	 * du DOM déclarés sous la forme de Page Factory de Selenium vérifie que le
	 * body propre à la page est présent sinon il renvoie une erreur
	 * 
	 * @param _driver
	 *            WebDriver Selenium
	 * @throws PageObjectException
	 */
	public AccountEditPageRwd(RemoteWebDriver _driver) throws PageObjectException {
		super(_driver);
		PageFactory.initElements(driver, this);
		if (!isElementPresent(accountBody)) {
			throw new PageObjectException(this.driver,"Ce n'est pas la page attendue : " + driver.getCurrentUrl());
		}
	}

	/**
	 * Récupere le Select prefix sous forme utilisable par Selenium
	 * 
	 * @return Select Selenium
	 * @throws PageObjectException
	 */
	private Select getPrefixSelect() {
		fluentWait(prefixSelect);
		return new Select(prefixSelect);
	}

	/**
	 * Modifie les infos du compte
	 * 
	 * @param firstname
	 * @param lastname
	 * @param email
	 * @return <b>AccountPageRwd</b> page compte
	 * @throws PageObjectException
	 */
	public AccountPageRwd editAccountInfos(String firstname, String lastname, String email) throws PageObjectException {
//		try {
			getPrefixSelect().selectByIndex(1);
			firstnameInput.clear();
			firstnameInput.sendKeys(firstname);
			lastnameInput.clear();
			lastnameInput.sendKeys(lastname);
			emailInput.clear();
			emailInput.sendKeys(email);
			firstnameInput.submit();
			new WebDriverWait(driver, 2);
			// saveInfoBtn.click();
			return new AccountPageRwd(this.driver);
//		} catch (Exception e) {
//			throw new PageObjectException(this.driver,e);
//		}
	}
	
	/**
	 * Modifie le MDP du compte
	 * 
	 * @param currentPass
	 * @param newPass
	 * @return <b>AccountPageRwd</b> page compte
	 * @throws PageObjectException
	 */
	public AccountPageRwd changePassword(String currentPass, String newPass) throws PageObjectException {
		try {
			passCheckBox.click();
			curentPassInput.clear();
			curentPassInput.sendKeys(currentPass);
			newPassInput.clear();
			newPassInput.sendKeys(newPass);
			cNewPassInput.clear();
			cNewPassInput.sendKeys(newPass);
			saveInfoBtn.click();
			return new AccountPageRwd(this.driver);
		} catch (Exception e) {
			throw new PageObjectException(this.driver,e);
		}
	}

	/**
	 * Test que les infos modifiée ont été enregistrées
	 * 
	 * @param firstname
	 * @param lastname
	 * @param email
	 * @return <b>boolean</b>
	 * @throws PageObjectException
	 */
	public boolean checkEditionAccount(String firstname, String lastname, String email) throws PageObjectException {
		try {
			if (firstnameInput.getText().contains(lastname) & lastnameInput.getAttribute("value").contains(lastname)
					& emailInput.getAttribute("value").contains(email))
				return true;
			else
				return false;
		} catch (Exception e) {
			throw new PageObjectException(this.driver,e);
		}
	}

}