package tests.test_aomagento.JETRWD.PO;

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
 * <h1>Page de Recherche</h1> Classe représentant la page de connection du thème
 * Rwd pour le projet aomagento
 *
 * @author jerivoalan
 * @version 1.0
 * @since 2015-04-27
 */
public class CatalogSearchPageRwd extends _BasePageRwd {
	/**
	 * Logger pour cette classe
	 */
	private static Log log = LogFactory.getLog(CatalogSearchPageRwd.class);

	// Elements du DOM

	/**
	 * Body de la page recherche
	 */
	@FindBy(css = "body.catalogsearch-result-index")
	private WebElement searchResultBody;
	/**
	 * Produit trouvés par la recherche
	 */
	@FindBy(css = ".sel-item-product")
	private List<WebElement> searchResults;

	// Fonctions / Methodes de la page

	/**
	 * <h1>Constructeur de la page recherche</h1> Initialise les éléments du DOM
	 * déclarés sous la forme Page Factory de Selenium, vérifie que le body
	 * propre à la page est présent sinon il renvoie une erreur.
	 * 
	 * @param _driver
	 *            WebDriver Selenium
	 * @throws PageObjectException
	 */
	public CatalogSearchPageRwd(RemoteWebDriver _driver) {
		super(_driver);
		PageFactory.initElements(this.driver, this);
		fluentWait(searchResultBody);
	}

	/**
	 * Test si des produits ont été trouvé par la recherche
	 * 
	 * @return <b>booleen</b> test si produits trouvés
	 */
	public boolean isSearchFound() {
		if (searchResults.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Test si la chaine de caractere recherchée a donné des résutats
	 * 
	 * @param search
	 *            chaine de caractére recherchée
	 * @return
	 */
	public boolean isSearchFound(String search) {
		if (searchResults.size() > 0) {
			for (WebElement products : searchResults) {
				WebElement productName = products.findElement(By.cssSelector(".product-image"));
				if (productName.getAttribute("title").toLowerCase().contains(search.toLowerCase()))
					return true;
			}

		}
		return false;
	}

}
