package tests.test_aomagento.COMPUTEC.PO;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.PageObjectException;

/**
 * <h1>Page Produit</h1> Classe représentant une page produit du thème Rwd pour
 * le projet aomagento
 *
 * @author jerivoalan
 * @version 1.0
 * @since 2015-04-25
 */
public class ProductPage extends _BasePage {
	/**
	 * Logger pour cette classe
	 */
	private static Log log = LogFactory.getLog(ProductPage.class);

	// Elements du DOM

	/**
	 * Body de la page produit
	 */
	@FindBy(css = "body.catalog-product-view")
	private WebElement productViewBody;
	/**
	 * div du produit sur lequel on va récuperer l'id (sku)
	 */
	@FindBy(css = ".sel-product-view")
	private WebElement productView;
	/**
	 * Titre du produit
	 */
	@FindBy(css = ".sel-product-name h1")
	private WebElement productName;
	/**
	 * Bouton d'ajout du produit au panier
	 */
	@FindBy(css = ".sel-add-to-cart")
	private WebElement addToCartBtn;
	/**
	 * Lien d'ajout du produit à la liste d'envie
	 */
	@FindBy(css = ".sel-link-wishlist")
	private WebElement addToWishlistLink;
	/**
	 * Lien d'ajout du produit au comparateur
	 */
	@FindBy(css = ".sel-link-compare")
	private WebElement addToComparator;
	/**
	 * Input permettant de renseigner la quantité du produit à ajouter au panier
	 */
	@FindBy(css = ".sel-qty")
	private WebElement qtyInput;
	/**
	 * Select permettant de choisir un type du produit
	 */
	@FindBy(css = ".sel-option-select select")
	private WebElement typeSelect;

	/**
	 * Bouton accéder au panier dans le popup de confirmation après ajout
	 */
	@FindBy(css = ".sel-j2t-checkout-link")
	private WebElement confirmBtn;
	
	/**
	 * Lien s'affichant s'il n'y a encore aucun commentaire pour le produit
	 */
	@FindBy(css = ".sel-no-rating a")
	private WebElement noReviewLink;
	
	/**
	 * Lien d'ajout de commentaire s'il y en a déjà de présent
	 */
	@FindBy(css = ".sel-add-rating")
	private WebElement addReviewLink;
	
	/**
	 * Input pour la note du commentaire
	 */
	@FindBy(css = ".sel-review-rating")
	private List<WebElement> reviewRatingInput;
	/**
	 * Input pour le commentaire
	 */
	@FindBy(css = ".sel-review-content")
	private WebElement reviewInput;
	/**
	 * Input pour le titre du commentaire
	 */
	@FindBy(css = ".sel-review-title")
	private WebElement reviewTitleInput;
	/**
	 * Input pour le nom du commentateur
	 */
	@FindBy(css = ".sel-review-nickname")
	private WebElement nicknameInput;

	// Fonctions / Methodes de la page

	/**
	 * <h1>Constructeur des pages produits</h1> Initialise les éléments du DOM
	 * déclarés sous la forme Page Factory de Selenium, vérifie que le body
	 * propre à la page est présent sinon il renvoie une erreur
	 * 
	 * @param _driver
	 *            WebDriver Selenium
	 * @throws PageObjectException
	 */
	public ProductPage(RemoteWebDriver _driver) throws PageObjectException {
		super(_driver);
		PageFactory.initElements(driver, this);

		if (!isElementPresent(productViewBody)) {
			throw new PageObjectException(this.driver,"Ce n'est pas la page attendue : " + driver.getCurrentUrl());
		}
	}

	/**
	 * Retourne le titre du produit
	 * 
	 * @return titre du produit
	 * @throws PageObjectException
	 */
	public String getProductName() throws PageObjectException {
		try {
			return productName.getText();
		} catch (Exception e) {
			throw new PageObjectException(this.driver,e);
		}
	}

	/**
	 * Retourne le sku du produit
	 * 
	 * @return sku du produit
	 * @throws PageObjectException
	 */
	public String getProductSku() throws PageObjectException {
		try {
			return productView.getAttribute("id");
		} catch (Exception e) {
			throw new PageObjectException(this.driver,e);
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
	public boolean isProductInStock() throws PageObjectException {
		if (isElementPresent(addToCartBtn)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Ajoute une quanitité du produit au panier
	 * 
	 * @param quantity
	 *            quantité du produit à ajouter au panier
	 * @return page du panier
	 * @throws PageObjectException
	 */
	public ShoppingCartPage addProductToCart(int quantity) throws Exception {
		if (isProductInStock() == true){
			String product = getProductName();
			// On test si l'input qte est present
			if (isElementPresent(qtyInput)) {
				qtyInput.clear();
				qtyInput.sendKeys("" + quantity);
			}
			// On test s'il y a des options produits, auquel cas on prend la
			// premiere
			if (isElementPresent(typeSelect)) {
				Select dropdownOption = new Select(typeSelect);
				dropdownOption.selectByIndex(1);
			}
			addToCartBtn.click();
			Thread.sleep(2000);
			if (isElementPresent(confirmBtn))
				confirmBtn.click();
			log.info("Ajout du produit " + product + " au panier");
			return new ShoppingCartPage(driver);
		} else {
			throw new PageObjectException(this.driver,"Le produit est épuisé");
		}
	}

	/**
	 * Ajoute le produit à la wishlist
	 * 
	 * @return page wishlist
	 * @throws PageObjectException
	 */
	public WishlistPage addProductToWishlist() throws PageObjectException {
		try {
			addToWishlistLink.click();
			return new WishlistPage(driver);
		} catch (Exception e) {
			throw new PageObjectException(this.driver,"Ajout du produit à la liste d'envie échoué" + e);
		}
	}

	/**
	 * Ajoute le produit au comparateur
	 * 
	 * @throws PageObjectException
	 */
	public void addProductToComparator() throws PageObjectException {
		try {
			addToComparator.click();
			log.info(getProductName() + " ajouté au comparateur");
		} catch (Exception e) {
			throw new PageObjectException(this.driver,"L'ajout au comparteur ne s'est pas effectué", e);
		}
	}

	/**
	 * Ajoute un commentaire au produit<br>
	 * L'ajout se fait différement dans le cas s'il ya deja un commentaire ou
	 * s'il n'y en a pas
	 * 
	 * @param review
	 *            contenu du commentaire
	 * @param reviewTitle
	 *            titre du commentaire
	 * @param nickname
	 *            nom du commentateur
	 * @throws PageObjectException
	 */
	public void addReviewToProduct(String review, String reviewTitle, String nickname) throws PageObjectException {
//		try {
			if (isElementPresent(noReviewLink))
				noReviewLink.click();
			else
				addReviewLink.click();
			fluentWait(reviewInput);
			if (reviewRatingInput.size() >0)
				System.out.println("yalla");
				reviewRatingInput.get(4).click();
			reviewInput.clear();
			reviewInput.sendKeys(review);
			reviewTitleInput.clear();
			reviewTitleInput.sendKeys(reviewTitle);
			nicknameInput.clear();
			nicknameInput.sendKeys(nickname);
			nicknameInput.submit();
			log.info("Commentaire envoyé");
//		} catch (Exception e) {
//			throw new PageObjectException(this.driver,"L'envoi du commentaire a echoué", e);
//		}
	}

}
