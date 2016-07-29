package tests.test_aomagento.JETRWD.PO;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import base.ComplexReportFactory;
import base.PageObjectException;

/**
 * <h1>Page d'accueil</h1> Classe représentant la Page d'accueil du thème Rwd du
 * projet aomagento
 *
 * @author jerivoalan
 * @version 1.0
 * @since 2015-04-25
 */
public class HomePageRwd extends _BasePageRwd {
	/**
	 * Logger pour la classe
	 */
	private static Log log = LogFactory.getLog(HomePageRwd.class);
	ExtentTest testReporter = ComplexReportFactory.getTest();

	// Elements du DOM

	/**
	 * Body de la page d'accueil
	 */
	@FindBy(css = "body.cms-index-index")
	private WebElement homeBody;

	/**
	 * Liste des "li" du menu au niveau 0
	 */
	@FindBy(css = "li.level0")
	private List<WebElement> menuL0;

	// Fonctions / Methodes de la page

	/**
	 * <h1>Constructeur de la page d'accueil</h1> Instancie le driver
	 * directement sur l'URL de la page d'accueil, initialise les éléments du
	 * DOM déclarés sous la forme de Page Factory de Selenium vérifie que le
	 * body propre à la page d'accueil est présent sinon il renvoie une erreur
	 * 
	 * @param _driver
	 *            WebDriver Selenium
	 * @throws PageObjectException
	 */
	public HomePageRwd(RemoteWebDriver _driver) throws PageObjectException {
		super(_driver);
		driver.get("http://aomagento.jerivoalan.jetpulp.dev/" + "rwd/");
		PageFactory.initElements(this.driver, this);
		
		if (!isElementPresent(homeBody)) {
			throw new PageObjectException(this.driver, "Ce n'est pas la page attendue : " + driver.getCurrentUrl());
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
	public CatalogPageRwd goToCategory(int nCategory) throws PageObjectException {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(menuL0.get(nCategory)).click().perform();
			log.info("Catégorie " + nCategory + " clickée, accés à sa page...");
			return new CatalogPageRwd(driver);
		} catch (Exception e) {
			if (menuL0.size() == 0)
				throw new PageObjectException(this.driver,"Le menu n'est pas accessible ou n'existe pas", e);
			if (nCategory > menuL0.size())
				throw new PageObjectException(this.driver,"La catégorie demandée est hors menu", e);
			throw e;
		}
	}

	/**
	 * Navigue vers la catégorie du sous-menu niveau1 <br>
	 * Mime un mouse hover sur la catégorie du menu niveau0 pour cliquer sur le
	 * lien de la catégorie niveau1
	 * 
	 * @param nCategory
	 *            numero de la categorie niveau0 (commence à 0)
	 * @param subCategory
	 *            numero de la sous-categorie niveau1 (commence à 0)
	 * @return
	 * @throws PageObjectException
	 */
	public CatalogPageRwd goToSubCategory(int nCategory, int subCategory) throws PageObjectException {

		Actions action = new Actions(driver);
		action.moveToElement(menuL0.get(nCategory)).perform();
		List<WebElement> subMenu = menuL0.get(nCategory).findElements(By.cssSelector(".level0 > .level1"));
		subMenu.get(subCategory).click();
		log.info("Sous Catégorie " + subCategory + " du Menu " + nCategory + " clickée, accés à sa page...");
		return new CatalogPageRwd(driver);
	}

	/**
	 * Renvoi le nombre d'éléments dans le menu niveau 0
	 * 
	 * @return nbOfCategories nombre de catégories dans le menu niveau0
	 * @throws PageObjectException
	 */
	public int getNumberOfCategories() throws PageObjectException {
		if (menuL0.size() == 0)
			throw new PageObjectException(this.driver,"Le menu n'est pas accessible ou n'existe pas");
		else {
			int nbOfCategories = menuL0.size();
			log.info(" Le menu principal comporte " + nbOfCategories + " catégories");
			return nbOfCategories;
		}
	}

	/**
	 * Renvoi le nombre d'éléments dans le sous-menu niveau1 de la catégorie
	 * souhaitée
	 * 
	 * @param nCategory
	 *            numero de la categorie (commence à 0)
	 * @return nombre d'éléments dans le sous menu
	 * @throws PageObjectException
	 */
	public int getNumberOfSubCategories(int nCategory) throws PageObjectException {
		Actions action = new Actions(driver);
		action.moveToElement(menuL0.get(nCategory)).perform();
		List<WebElement> subMenu = menuL0.get(nCategory).findElements(By.cssSelector(".level0 > .level1"));
		return subMenu.size();
	}

	/**
	 * Renvoi le nom de la catégorie du menu niveau0
	 * 
	 * @param nCategory
	 *            numero de la categorie (commence à 0)
	 * @return nom de la catégorie
	 * @throws PageObjectException
	 */
	public String getNameCategory(int nCategory) throws PageObjectException {
		if (menuL0.size() == 0)
			throw new PageObjectException(this.driver,"Le menu n'est pas accessible ou n'existe pas");
		else {
			String nameOfCategory = menuL0.get(nCategory).getText();
			return nameOfCategory;
		}
	}

	/**
	 * Renvoi le nom de la sous-catégorie du menu niveau1
	 * 
	 * @param nCategory
	 *            numero de la categorie niveau0 (commence à 0)
	 * @param subCategory
	 *            numero de la sous-categorie niveau1 (commence à 0)
	 * @return le texte de la sous-catégorie
	 * @throws PageObjectException
	 */
	public String getNameSubCategory(int nCategory, int subCategory) throws PageObjectException {
		Actions action = new Actions(driver);
		action.moveToElement(menuL0.get(nCategory)).perform();
		List<WebElement> subMenu = menuL0.get(nCategory).findElements(By.cssSelector(".level0 > .level1"));
		System.out.println(subMenu);
		return subMenu.get(subCategory).getText();
	}
}
