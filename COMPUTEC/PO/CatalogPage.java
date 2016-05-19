package tests.test_aomagento.COMPUTEC.PO;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

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
 * <h1>Page du Catalogue Produit</h1> Classe représentant une page du catalogue
 * produit du thème Rwd pour le projet aomagento
 *
 * @author jerivoalan
 * @version 1.0
 * @since 2015-04-25
 */
public class CatalogPage extends _BasePage {

	/**
	 * Logger pour cette classe
	 */
	private static Log log = LogFactory.getLog(CatalogPage.class);

	// Elements du DOM

	/**
	 * Body du catalogue
	 */
	@FindBy(css = ".catalog-category-view")
	private WebElement catalogBody;

	/**
	 * Recupere les li de chaque produit de la page catalogue
	 */
	@FindBy(css = ".sel-item-product")
	private List<WebElement> itemProducts;
	/**
	 * Titre des produits de la page catalogue
	 */
	@FindBy(css = ".sel-product-name a")
	private List<WebElement> nameProducts;
	/**
	 * Liste des liens "ajouter à la Wishlist"
	 */
	@FindBy(css = ".sel-link-wishlist")
	private List<WebElement> addToWishlistLink;
	/**
	 * Liste des liens "ajouter au comparateur"
	 */
	@FindBy(css = ".sel-link-compare")
	private List<WebElement> addToComparatorLink;
	
	/**
	 * Lien permettant d'ouvrir la fenêtre du comparateur
	 */
	@FindBy(css = ".compare-link")
	private WebElement openComparatorBtn;
	/**
	 * Nombres d'elements comparateur
	 */
	@FindBy(css = ".compare-link .count")
	private WebElement nbComparator;

	// Fonctions / Methodes de la page

	/**
	 * <h1>Constructeur des pages du catalogue</h1> Initialise les éléments du
	 * DOM déclarés sous la forme Page Factory de Selenium vérifie que le body
	 * propre à la page est présent sinon il renvoie une erreur
	 * 
	 * @param _driver
	 *            WebDriver Selenium
	 * @throws PageObjectException
	 */
	public CatalogPage(RemoteWebDriver _driver) throws PageObjectException {
		super(_driver);
		PageFactory.initElements(driver, this);

		if (!isElementPresent(catalogBody)) {
			throw new PageObjectException(this.driver,"Ce n'est pas la page attendue : " + driver.getCurrentUrl());
		}
	}

	/**
	 * Récupere le titre du produit voulu
	 * 
	 * @param nProduit
	 *            numéro du produit dont on veut récuperer le titre
	 * @return le titre du produit
	 * @throws PageObjectException
	 */
	public String getProductName(int nProduit) throws PageObjectException {
		try {
			return nameProducts.get(nProduit).getText();
		} catch (Exception e) {
			throw new PageObjectException(this.driver,"Les produits ne sont pas accessibles ou inexistants " + e);
		}
	}

	/**
	 * Test si le produit n'est pas épuisé
	 * 
	 * @param nProduit
	 *            numero du produit
	 * @return <b>boolean</b>
	 * @throws PageObjectException
	 */
	public boolean isProductInStock(int nProduit) throws PageObjectException {
		if (itemProducts.size() > 0) {
			String nomProduit = getProductName(nProduit);
			if (itemProducts.get(nProduit).findElements(By.cssSelector(".out-of-stock")).isEmpty()) {
				return true;
			} else {
				return false;
			}

		} else {
			throw new PageObjectException(this.driver,"Aucun produit n'est présent ou visible sur cette page");
		}
	}


	/**
	 * Ajoute le produit à la wishlist
	 * 
	 * @param nProduit
	 *            numéro du produit qu'on veut ajouter à la wishlist
	 * @return page de la wishlist
	 * @throws PageObjectException
	 */
	public WishlistPage addProductToWishlist(int nProduit) throws PageObjectException {
		if (itemProducts.size() > 0) {
			try {
				addToWishlistLink.get(nProduit).click();
				return new WishlistPage(driver);
			} catch (Exception e) {
				throw new PageObjectException(this.driver,
						"Le bouton d'ajout à la liste d'envie n'est pas accessible pour ce produit", e);
			}
		} else {
			throw new PageObjectException(this.driver,"Aucun produit n'est présent ou visible sur cette page");
		}
	}

	/**
	 * Ajoute un produit au comparateur </br>
	 * clique sur le lien d'ajout du produit au comparateur<br>
	 * attend que le produit soit ajouté
	 * 
	 * @param nProduit
	 *            numéro du produit qu'on veut ajouter au comparateur
	 * @throws PageObjectException
	 */
	public void addProductToComparator(int nProduit) throws PageObjectException {
		if (itemProducts.size() > 0) {
			try {
				addToComparatorLink.get(nProduit).click();
				new WebDriverWait(driver, 1);
				log.info(getProductName(nProduit) + " ajouté au comparateur");
			} catch (Exception e) {
				throw new PageObjectException(this.driver,"Le produit ne s'est pas ajouté au comparateur");
			}
		} else {
			throw new PageObjectException(this.driver,"Aucun produit n'est présent sur cette page");
		}
	}

	/**
	 * Récupere le nombre de produits présents dans le bloc du comparateur
	 * 
	 * @return le nombre de produits présents dans le bloc du comparateur
	 */
	public int getNumberProductsInComparator() {
		int nb = Integer.parseInt(nbComparator.getText());
		return nb;
	}


	/**
	 * Ouvre la fenêtre du comparateur
	 * 
	 * @return page comparateur
	 * @throws PageObjectException
	 */
	public ComparatorPage openComparator() throws PageObjectException {
		if (getNumberProductsInComparator() > 0) {
			openComparatorBtn.click();
			return new ComparatorPage(driver);
		} else {
			throw new PageObjectException(this.driver,"Le comparateur est vide");
		}
	}

	/**
	 * Renvoi la page d'un produit du catalogue </br>
	 * Clique sur le titre du produit pour renvoyer à sa page
	 * 
	 * @param nProduit
	 *            numero du produit dans la liste des produits
	 * @return page du produit
	 * @throws PageObjectException
	 */
	public ProductPage clickOnProduct(int nProduit) throws PageObjectException {
		if (itemProducts.size() > 0) {
			nameProducts.get(nProduit).click();
			return new ProductPage(driver);
		} else {
			throw new PageObjectException(this.driver,"Produit ou lien non disponible");
		}
	}

}
