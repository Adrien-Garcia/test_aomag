package tests.test_aomagento.BRANDER.PO;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
public class ComparatorPage extends _BasePage {
	/**
	 * Logger pour cette classe
	 */
	private static Log log = LogFactory.getLog(ComparatorPage.class);

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
	 * Lien vider comparateur
	 */
	@FindBy(css = ".sel-empty-comparator")
	private WebElement emptyLink;
	
	/**
	 * Liste des "li" du menu au niveau 0
	 */
	@FindBy(css = "li.level0")
	private List<WebElement> menuL0;

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
	public ComparatorPage(RemoteWebDriver _driver) throws PageObjectException {
		super(_driver);
		PageFactory.initElements(this.driver, this);

		if (!isElementPresent(comparatorBody)) {
			throw new PageObjectException(this.driver,"Ce n'est pas la page attendue : " + driver.getCurrentUrl());
		}
	}
	
	/**
	 * Navigue vers la categorie du menu niveau0 voulue
	 * 
	 * @param nCategory
	 *            numero de la categorie (commence à 0)
	 * @return CatalogPageRwd page catalogue correspondante
	 * @throws PageObjectException
	 */
	public CatalogPage goToCategory(int nCategory) throws PageObjectException {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(menuL0.get(nCategory)).click().perform();
			log.info("Catégorie " + nCategory + " clickée, accés à sa page...");
			return new CatalogPage(driver);
		} catch (Exception e) {
			throw e;
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
	 * Renvoi le nombre de produits dans le comparateur
	 * 
	 * @return <b>int</b> nombre de produits dans le comparateur
	 */
	public void emptyComparator() {
		emptyLink.click();
		checkPageIsReady();
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
}
