package tests.test_aomagento.RWD.PO;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.PageObjectException;

/**
 * <h1>Page du comparateur</h1> Classe représentant la Page popup du comparateur
 * du thème Rwd du projet aomagento
 *
 * @author jerivoalan
 * @version 1.0
 * @since 2015-04-25
 */
public class ComparatorPageRwd extends _BasePageRwd {
	/**
	 * Logger pour cette classe
	 */
	private static Log log = LogFactory.getLog(ComparatorPageRwd.class);
	/**
	 * Handle de la page qui a appelée la popup
	 */
	private String callingPageHandle;

	// Elements du DOM

	/**
	 * Body du comparateur
	 */
	@FindBy(css = "body.catalog-product-compare-index")
	private WebElement comparatorBody;
	/**
	 * Titre des produits
	 */
	@FindBy(css = ".sel-product-name")
	private List<WebElement> nameProducts;
	/**
	 * Bouton de fermeture du comparateur
	 */
	@FindBy(css = ".sel-close-comparator")
	private WebElement closeBtn;

	// Fonctions / Methodes de la page

	/**
	 * <h1>Constructeur de la page du comparateur (popup)</h1> <br>
	 * initialise les éléments du DOM déclarés sous la forme de Page Factory de
	 * Selenium vérifie que le body propre à la page d'accueil est présent sinon
	 * il renvoie une erreur
	 * 
	 * @param _driver
	 *            WebDriver Selenium
	 * @throws PageObjectException
	 */
	public ComparatorPageRwd(RemoteWebDriver _driver) throws PageObjectException {
		super(_driver);
		this.callingPageHandle = driver.getWindowHandle();
		try {
			// Fonction pour changer le driver de fenetre
			waitForNewWindowAndSwitchToIt(driver);
		} catch (Exception e) {
			throw new PageObjectException(this.driver,e);
		}
		PageFactory.initElements(this.driver, this);

		if (!isElementPresent(comparatorBody)) {
			throw new PageObjectException(this.driver,"Ce n'est pas la page attendue : " + driver.getCurrentUrl());
		}
	}

	/**
	 * Renvoi le nombre de produits dans le comparateur
	 * 
	 * @return <b>int</b> nombre de produits dans le comparateur
	 */
	public int getNumberProductsInComparator() {
		return nameProducts.size();
	}

	/**
	 * Test si un produit est dans le comparateur
	 * 
	 * @param product
	 *            nom du produit à tester
	 * @return <b>boolean</b> resultat du test
	 */
	public boolean isProductInComparator(String product) {
		for (WebElement products : nameProducts) {
			if (product.equalsIgnoreCase(products.getText())) {
				log.info(product + " présent dans le comparateur");
				return true;
			}
		}
		return false;
	}

	/**
	 * Ferme la fenetre du comparateur
	 * 
	 * @return <b>CatalogPageRwd</b> Page catalogue appelant
	 * @throws PageObjectException
	 */
	public CatalogPageRwd closeComparator() throws PageObjectException {
		try {
			closeAllOtherWindows(driver, this.callingPageHandle);
			return new CatalogPageRwd(driver);
		} catch (Exception e) {
			throw new PageObjectException(this.driver,"La popup du comparateur ne s'est pas fermée", e);
		}
	}

}
