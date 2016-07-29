package tests.test_aomagento.JETRWD.PO;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.PageObjectException;

/**
 * <h1>Page Panier</h1> Classe représentant la page panier du thème Rwd pour le
 * projet aomagento
 *
 * @author jerivoalan
 * @version 1.0
 * @since 2015-04-26
 */
public class ShoppingCartPageRwd extends _BasePageRwd {
	/**
	 * Logger pour cette classe
	 */
	private static Log log = LogFactory.getLog(ShoppingCartPageRwd.class);

	// Elements du DOM
	/**
	 * Body de la page panier
	 */
	@FindBy(css = "body.checkout-cart-index")
	private WebElement shoppingCartBody;
	/**
	 * Bouton commander
	 */
	@FindBy(css = ".sel-btn-proceed-checkout")
	private WebElement checkoutBtn;
	/**
	 * Lignes produits du panier
	 */
	@FindBy(css = ".sel-cart-item")
	private List<WebElement> cartProducts;
	/**
	 * Prix total ttc
	 */
	@FindBy(css = ".sel-price-ttc .price")
	private WebElement ttcPrice;
	/**
	 * Montant de la remise
	 */
	@FindBy(css = ".sel-price-discount .price")
	private WebElement discountPrice;
	/**
	 * Input pour renseigner le code de remise
	 */
	@FindBy(css = ".sel-discount-code")
	private WebElement discountInput;
	/**
	 * Lien pour valider le code de remise
	 */
	@FindBy(css = ".sel-discount-apply")
	private WebElement discountApply;
	/**
	 * Lien pour annuler la remise
	 */
	@FindBy(css = ".sel-discount-cancel")
	private WebElement discountCancel;
	/**
	 * Lien pour vider le panier
	 */
	@FindBy(css = ".sel-empty-cart")
	private WebElement emptyCart;

	// Fonctions / Methodes de la page

	/**
	 * <h1>Constructeur de la page panier</h1> Initialise les éléments du DOM
	 * déclarés sous la forme Page Factory de Selenium, vérifie que le body
	 * propre à la page est présent sinon il renvoie une erreur
	 * 
	 * @param _driver
	 *            WebDriver Selenium
	 * @throws PageObjectException
	 */
	public ShoppingCartPageRwd(RemoteWebDriver _driver) throws PageObjectException {
		super(_driver);
		PageFactory.initElements(driver, this);

		if (!isElementPresent(shoppingCartBody)) {
			throw new PageObjectException(this.driver,"Ce n'est pas la page attendue : " + driver.getCurrentUrl());
		}
	}

	/**
	 * Valide la commande<br>
	 * clique sur le boton commander
	 * 
	 * @return Page Commande
	 * @throws PageObjectException
	 */
	public CheckoutPageRwd proceedToCheckout() throws PageObjectException {
		try {
			checkoutBtn.click();
			return new CheckoutPageRwd(driver);
		} catch (Exception e) {
			throw new PageObjectException(this.driver,e);
		}

	}

	/**
	 * Renvoi le nombre de produits différents présents dans le panier<br>
	 * (test les éléments visibles, sinon erreur de test car doublon invisibles)
	 * 
	 * @return nombre de produits différents du panier
	 */
	public int getNumberOfProductsInCart() {
		int nbProductsCart = 0;
		if (cartProducts.size() > 0) {
			for (WebElement products : cartProducts) {
				if (products.isDisplayed())
					nbProductsCart++;
			}
		}
		log.info("Le panier contient " + nbProductsCart + " produits");
		return nbProductsCart;
	}

	/**
	 * Retire un produit du panier<br>
	 * (récupere le lien supprimer directement dans la fonction)
	 * 
	 * @param idSku
	 *            sku du produit à supprimer
	 * @throws PageObjectException
	 */
	public void removeProduct(String idSku) throws PageObjectException {
		try {
			WebElement removeProduct = driver.findElementByCssSelector("tr[id^=" + idSku + "] .sel-remove-item");
			if (removeProduct.isDisplayed()) {
				removeProduct.click();
			}
			// On attend que l'élément soit bien supprimé
			WebDriverWait wait = new WebDriverWait(driver, 8);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("tr[id^=" + idSku + "]")));
			log.info("Produit sku = " + idSku + " supprimé");
		} catch (Exception e) {
			throw new PageObjectException(this.driver,e);
		}

	}

	/**
	 * Test s'il est possible de passer à la commande
	 * 
	 * @return booleen
	 */
	public boolean isOrderPossible() {
		return isElementPresent(checkoutBtn);
	}

	/**
	 * Récupère la quantité d'un produit présent dans le panier
	 * 
	 * @param idSku
	 *            sku du produit
	 * @return la quantité du produit
	 * @throws PageObjectException
	 */
	public int getQuantityOfProduct(String idSku) throws PageObjectException {
		try {
			WebElement productQtyInput = driver.findElementByCssSelector("tr[id^=" + idSku + "] .sel-qty");
			String quantityProduct = productQtyInput.getAttribute("value");
			return Integer.parseInt(quantityProduct);
		} catch (Exception e) {
			throw new PageObjectException(this.driver,e);
		}
	}

	/**
	 * Modifie la quantité d'un produit dans le panier
	 * 
	 * @param idSku
	 *            sku du produit
	 * @param quantity
	 *            nouvelle quantité du produit
	 * @throws PageObjectException
	 */
	public void changeQuantityOfProduct(String idSku, int quantity) throws PageObjectException {
		try {
			WebElement productQtyInput = driver.findElementByCssSelector("tr[id^=" + idSku + "] .sel-qty");
			productQtyInput.clear();
			productQtyInput.sendKeys("" + quantity);
			productQtyInput.submit();
			log.info("Modfication de la quantité du produit " + idSku + " à " + quantity);
		} catch (Exception e) {
			throw new PageObjectException(this.driver,e);
		}
	}

	/**
	 * Applique le code d'une remise au panier
	 * 
	 * @param discount
	 *            code remise
	 * @throws PageObjectException
	 */
	public void applyDiscount(String discount) throws PageObjectException {
		try {
			discountInput.clear();
			discountInput.sendKeys(discount);
			discountApply.click();
		} catch (Exception e) {
			throw new PageObjectException(this.driver,e);
		}
	}

	/**
	 * Retire la remise appliquée au panier
	 * 
	 * @throws PageObjectException
	 */
	public void cancelDiscount() throws PageObjectException {
		if (isElementPresent(discountCancel)) {
			try {
				discountCancel.click();
			} catch (Exception e) {
				throw new PageObjectException(this.driver,e);
			}
		}
	}

	/**
	 * Renvoi le montant de la remise soustrait au panier
	 * 
	 * @return montant de la remise (montant négatif)
	 * @throws PageObjectException
	 */
	public float getDiscountPrice() throws PageObjectException {
		try {
			String dPrice = discountPrice.getText().replace("€", "");
			dPrice = dPrice.replace(",", ".").replace(" ", "");
			return Float.parseFloat(dPrice);
		} catch (Exception e) {
			throw new PageObjectException(this.driver,e);
		}
	}

	/**
	 * Renvoi le prix total TTC du panier
	 * 
	 * @return total TTC
	 * @throws PageObjectException
	 */
	public float getTtcPrice() throws PageObjectException {
		try {
			String tPrice = ttcPrice.getText().replace("€", "");
			tPrice = tPrice.replace(",", ".").replace(" ", "");
			return Float.parseFloat(tPrice);
		} catch (Exception e) {
			throw new PageObjectException(this.driver,e);
		}
	}

	/**
	 * Test si un produit est présent dans le panier selon son sku
	 * 
	 * @param skuProduct
	 *            sku du produit
	 * @return booleen
	 * @throws PageObjectException
	 */
	public boolean isProductInCart(String skuProduct) throws PageObjectException {
		try {
			WebElement product = driver.findElementByCssSelector("tr[id^=" + skuProduct + "]");
			return (product.isDisplayed());
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Vide le panier
	 * 
	 * @throws PageObjectException
	 */
	public void emptyCart() throws PageObjectException {
		if (isElementPresent(emptyCart)) {
			try {
				emptyCart.click();
				log.info("Le panier a été vidé");
			} catch (Exception e) {
				throw new PageObjectException(this.driver,e);
			}
		}
	}

}
