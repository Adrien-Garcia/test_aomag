package tests.test_aomagento.pageobjects;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.PageObjectException;

/**
 * <h1>Page Liste d'envie</h1> Classe représentant la Page de la liste d'envie
 * de l'utilisateur connecté du thème Rwd/ projet aomagento
 *
 * @author jerivoalan
 * @version 1.0
 * @since 2015-04-25
 */
public class WishlistPageRwd extends _BasePageRwd {
	/**
	 * Logger pour cette classe
	 */
	private static Log log = LogFactory.getLog(WishlistPageRwd.class);

	// Elements du DOM

	/**
	 * Body de la liste d'envie
	 */
	@FindBy(css = "body.wishlist-index-index")
	private WebElement wishlistBody;
	/**
	 * Liste des noms des produits de la WishList
	 */
	@FindBy(css = ".sel-product-name-wishlist")
	private List<WebElement> wishlistProductsName;
	/**
	 * Lien suppression produit de la wishlist
	 */
	@FindBy(css = ".sel-remove-product-wishlist")
	private List<WebElement> removeProducts;

	// Fonctions / Methodes de la page

	/**
	 * <h1>Constructeur de la WishList</h1> initialise les éléments du DOM
	 * déclarés sous la forme de Page Factory de Selenium vérifie que le body
	 * propre à la page est présent sinon il renvoie une erreur
	 * 
	 * @param _driver
	 *            WebDriver Selenium
	 * @throws PageObjectException
	 */
	public WishlistPageRwd(RemoteWebDriver _driver) throws PageObjectException {
		super(_driver);
		PageFactory.initElements(driver, this);

		if (!isElementPresent(wishlistBody)) {
			throw new PageObjectException("Ce n'est pas la page attendue : " + driver.getCurrentUrl());
		}
	}

	/**
	 * Renvoi le nombre de produits différents présents dans la wishlist
	 * 
	 * @return <b>int</b>
	 */
	public int getNumberProductsInWishlist() {
		return wishlistProductsName.size();
	}

	/**
	 * Vide la liste d'envie
	 * 
	 * @throws PageObjectException
	 */
	public void emptyWishlist() throws PageObjectException {
		try {
			int i = removeProducts.size();
			while (i != 0) {
				removeProducts.get(0).click();
				driver.switchTo().alert().accept();
				implicitWait(1);
				i--;
			}
			log.info("La wishlist a été vidée");
		} catch (Exception e) {
			throw new PageObjectException("Il y a eu un problème lors du vidage de la wishlist", e);
		}
	}

	/**
	 * Test si un produit est dans la wishlist
	 * 
	 * @param product
	 *            produit à tester la présence
	 * @return <b>boolean</b>
	 */
	public boolean isProductInWishlist(String product) {
		for (WebElement products : wishlistProductsName){
			if (product.equalsIgnoreCase(products.getText()))
				return true;
		}
		return false;
	}

}
