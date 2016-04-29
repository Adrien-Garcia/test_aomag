package tests.test_aomagento.pageobjects;

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
public class CatalogPageRwd extends _BasePageRwd {

	/**
	 * Logger pour cette classe
	 */
	private static Log log = LogFactory.getLog(CatalogPageRwd.class);

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
	 * Popup ajax s'affichant après ajout au panier
	 */
	@FindBy(css = ".sel-j2tajax-confirm")
	private WebElement confirmAddToCartPopup;
	/**
	 * Lien de la popup ajax permettant de la fermer
	 */
	@FindBy(css = ".sel-j2t-continue-link")
	private WebElement continueShoppingBtn;
	/**
	 * Lien de la popup ajax permettant d'accéder au panier
	 */
	@FindBy(css = ".sel-j2t-checkout-link")
	private WebElement confirmCheckoutBtn;
	/**
	 * Liste des li contenus dans le bloc "Comparer des produits"
	 */
	@FindBy(css = ".sel-compare-item")
	private List<WebElement> comparatorItems;
	/**
	 * Liste des noms des produits contenus dans le comparateur
	 */
	@FindBy(css = ".sel-compare-item-name")
	private List<WebElement> comparatorItemsNames;
	/**
	 * Lien permettant de supprimer tout les éléments du bloc
	 * "comparer des produits"
	 */
	@FindBy(css = ".sel-empty-comparator")
	private WebElement emptyComparatorLink;
	/**
	 * Lien permettant d'ouvrir la fenêtre du comparateur
	 */
	@FindBy(css = ".sel-open-comparator")
	private WebElement openComparatorBtn;

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
	public CatalogPageRwd(RemoteWebDriver _driver) throws PageObjectException {
		super(_driver);
		PageFactory.initElements(driver, this);

		if (!isElementPresent(catalogBody)) {
			throw new PageObjectException("Ce n'est pas la page attendue : " + driver.getCurrentUrl());
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
			throw new PageObjectException("Les produits ne sont pas accessibles ou inexistants " + e);
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
			throw new PageObjectException("Aucun produit n'est présent ou visible sur cette page");
		}
	}

	/**
	 * Clique sur le bouton d'ajout au panier et redirige vers le panier. <br />
	 * récupere le WebElement du bouton d'ajout dans le li en reference au
	 * produit avant de cliquer dessus </br>
	 * test si le bouton ajout est présent au cas ou si le produit est
	 * épuisé</br>
	 * passe par la page produit s'il y a redirection
	 * 
	 * @param nProduit
	 *            numéro du produit qu'on veut ajouter au panier
	 * @return page du panier
	 * @throws PageObjectException
	 */
	public ShoppingCartPageRwd addProductToCart(int nProduit) throws PageObjectException {
		if (itemProducts.size() > 0) {

			String nomProduit = getProductName(nProduit);
			// On test que le produit est en stock
			if (isProductInStock(nProduit) == true) {
				itemProducts.get(nProduit).findElement(By.cssSelector(".sel-add-to-cart")).click();
			} else {
				throw new PageObjectException("le produit demandé n'est pas en stock");
			}

			// s'il ya redirection sur la page produit
			if (getPageTitle().contains(nomProduit)) {
				ProductPageRwd productPage = new ProductPageRwd(driver);
				productPage.addProductToCart(1);
			} else {
				fluentWait(confirmAddToCartPopup, 1, 5);
				fluentWait(confirmCheckoutBtn, 1, 5);
				confirmCheckoutBtn.click();
			}

			log.info("Ajout du produit " + nomProduit + " au panier");
			return new ShoppingCartPageRwd(driver);

		} else {
			throw new PageObjectException("Aucun produit n'est présent ou visible sur cette page");
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
	public WishlistPageRwd addProductToWishlist(int nProduit) throws PageObjectException {
		if (itemProducts.size() > 0) {
			try {
				addToWishlistLink.get(nProduit).click();
				return new WishlistPageRwd(driver);
			} catch (Exception e) {
				throw new PageObjectException(
						"Le bouton d'ajout à la liste d'envie n'est pas accessible pour ce produit", e);
			}
		} else {
			throw new PageObjectException("Aucun produit n'est présent ou visible sur cette page");
		}
	}

	/**
	 * Vide le comparateur </br>
	 * clique sur le lien pour vider les produits du comparateur</br>
	 * valide la fenetre de confirmation
	 * 
	 * @throws PageObjectException
	 */
	public void emptyComparator() throws PageObjectException {
		if (comparatorItems.size() > 0) {
			try {
				emptyComparatorLink.click();
				driver.switchTo().alert().accept();
				implicitWait(4);
			} catch (Exception e) {
				throw new PageObjectException("La liste du comparateur n'a pas pu être vidée");
			}
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
				implicitWait(2);
				log.info(getProductName(nProduit) + " ajouté au comparateur");
			} catch (Exception e) {
				throw new PageObjectException("Le produit ne s'est pas ajouté au comparateur");
			}
		} else {
			throw new PageObjectException("Aucun produit n'est présent sur cette page");
		}
	}

	/**
	 * Récupere le nombre de produits présents dans le bloc du comparateur
	 * 
	 * @return le nombre de produits présents dans le bloc du comparateur
	 */
	public int getNumberProductsInComparator() {
		if (comparatorItems.size() > 0) {
			return comparatorItems.size();
		} else {
			return 0;
		}
	}

	/**
	 * Test si un produit est présent dans le comparateur </br>
	 * passe en revue tout les elements du comprateur et compare leur titre
	 * 
	 * @param product
	 *            nom du produit à chercher dans le comparateur
	 * @return <b>booleen</b> test si produit est dans le comparateur
	 */
	public boolean isProductInComparator(String product) {
		for (WebElement products : comparatorItemsNames) {
			if (product.equalsIgnoreCase(products.getText()))
				return true;
		}
		return false;
	}

	/**
	 * Ouvre la fenêtre du comparateur
	 * 
	 * @return page comparateur
	 * @throws PageObjectException
	 */
	public ComparatorPageRwd openComparator() throws PageObjectException {
		if (comparatorItems.size() > 0) {
			openComparatorBtn.click();
			return new ComparatorPageRwd(driver);
		} else {
			throw new PageObjectException("Le comparateur est vide");
		}
	}

	/**
	 * Renvoi le sku du produit voulu
	 * 
	 * @param nProduit
	 *            numero du produit dans la liste des produits
	 * @return sku du produit
	 */
	public String getProductSku(int nProduit) {
		return itemProducts.get(nProduit).getAttribute("id");
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
	public ProductPageRwd clickOnProduct(int nProduit) throws PageObjectException {
		if (itemProducts.size() > 0) {
			nameProducts.get(nProduit).click();
			return new ProductPageRwd(driver);
		} else {
			throw new PageObjectException("Produit ou lien non disponible");
		}
	}

}
