package tests.test_aomagento.JETRWD.PO;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.sun.istack.internal.NotNull;

import base.ComplexReportFactory;
import base.PageObjectException;

/**
 * <h1>Page de base</h1> Classe parent de toutes les Pages du thème Rwd/ projet
 * aomagento<br>
 * Contient les méthodes accessibles de toute part sur le site. Ici misent en
 * commun.
 *
 * @author jerivoalan
 * @version 1.0
 * @since 2015-04-25
 */
public class _BasePageRwd {
	/**
	 * Logger pour cette classe
	 */
	private static Log log = LogFactory.getLog(_BasePageRwd.class);
	ExtentTest testReporter = ComplexReportFactory.getTest();

	protected boolean acceptNextAlert = true;
	protected StringBuffer verificationErrors = new StringBuffer();
	/**
	 * Driver Selenium
	 */
	protected RemoteWebDriver driver;

	/**
	 * Logo du header
	 */
	@FindBy(css = ".sel-header-logo")
	private WebElement logoHome;
	/**
	 * Titre principal des pages
	 */
	@FindBy(css = ".page-title h1")
	private WebElement pageTitle;
	/**
	 * Message succés
	 */
	@FindBy(css = "li.success-msg")
	private WebElement successMsg;
	/**
	 * Barre de recherche
	 */
	@FindBy(css = ".sel-catalog-search")
	private WebElement searchInput;
	/**
	 * Bouton compte header
	 */
	@FindBy(css = ".sel-header-account")
	private WebElement accountBtn;

	// Selecteur spécifique à définir
	/**
	 * Lien "Panier" sous menu compte header
	 */
	@FindBy(css = "a.top-link-cart")
	private WebElement cartLink;
	/**
	 * Lien "mon compte" sous menu compte header
	 */
	@FindBy(css = ".sel-myaccount")
	private WebElement accountLink;
	/**
	 * Lien "connexion" sous menu compte header
	 */
	@FindBy(css = ".sel-login")
	private WebElement signInLink;
	/**
	 * Lien "déconnexion" sous menu compte header
	 */
	@FindBy(css = ".sel-logout")
	private WebElement logoutLink;
	/**
	 * Bouton panier header
	 */
	@FindBy(css = ".sel-header-minicart")
	private WebElement cartBtn;
	/**
	 * Message s'affichant si panier vide
	 */
	@FindBy(css = ".sel-cart-empty")
	private WebElement cartEmpty;

	/**
	 * <h1>Constructeur commun aux pages</h1> initialise les éléments du DOM
	 * déclarés sous la forme de Page Factory<br>
	 * Fait appel à une fonction qui vérifie que la page est chargée
	 * 
	 * @param _driver
	 *            WebDriver Selenium
	 * @throws PageObjectException
	 */
	public _BasePageRwd(RemoteWebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
		checkPageIsReady();
		// driver.manage().window().maximize();
	}

	public RemoteWebDriver getDriver(){
		return this.driver;
	}
	
	/**
	 * Utilise le lien du logo du header pour accéder à la page d'accueil
	 * 
	 * @return <b>HomePageRwd</b> Page d'accueil
	 * @throws PageObjectException
	 */
	public HomePageRwd goToHomePage() throws PageObjectException {
		logoHome.click();
		return new HomePageRwd(driver);
	}

	/**
	 * Test si le message succés est affiché
	 * 
	 * @return <b>boolean</b>
	 */
	public boolean checkIfSuccess() {
		return isElementPresent(successMsg);
	}

	/**
	 * Test si l'utilisateur est connecte<br>
	 * En vérifiant si le lien de connexion est présent
	 * 
	 * @return <b>boolean</b>
	 */
	public boolean isUserLoggedIn() {
		accountBtn.click();
		boolean logged;
		if (isElementPresent(signInLink)) {
			log.info("Aucun utilisateur connecté");
			logged = false;

		} else {
			log.info("L'utilisateur est connecté");
			logged = true;
		}
		accountBtn.click();
		new WebDriverWait(driver, 2);
		return logged;
	}

	/**
	 * Effectue une recherche dans la barre de recherche
	 * 
	 * @param search
	 *            chaine de caractere à chercher
	 * @return <b>SearchResultPageRwd</b>
	 */
	public CatalogSearchPageRwd searchForResults(String search) {
		searchInput.clear();
		searchInput.sendKeys(search);
		searchInput.submit();
		return new CatalogSearchPageRwd(driver);
	}

	/**
	 * Navigue vers la page de connection
	 * 
	 * @return <b>SignInPageRwd</b> page de connection
	 * @throws PageObjectException
	 */
	public SignInPageRwd clickConnectionClient() throws PageObjectException {
		accountBtn.click();
		if (isElementPresent(signInLink))
			signInLink.click();
		return new SignInPageRwd(driver);
	}

	/**
	 * Test si le panier est vide
	 * 
	 * @return <b>boolean</b>
	 */
	public boolean isCartEmpty() {
		cartBtn.click();
		boolean empty;
		if (isElementPresent(cartEmpty))
			empty = true;
		else
			empty = false;
		cartBtn.click();
		return empty;
	}

	/**
	 * Navigue vers le panier
	 * 
	 * @return <b>ShoppingCartPageRwd</b> Page panier
	 * @throws PageObjectException
	 */
	public ShoppingCartPageRwd goToCart() throws PageObjectException {
		accountBtn.click();
		if (isElementPresent(cartLink)) {
			cartLink.click();
			return new ShoppingCartPageRwd(driver);
		} else {
			return null;
		}
	}

	/**
	 * Navigue vers le compte client
	 * 
	 * @return <b>AccountPageRwd</b> Page compte
	 * @throws PageObjectException
	 */
	public AccountPageRwd goToAccountPage() throws PageObjectException {
		accountBtn.click();
		if (isUserLoggedIn() == true) {
			accountLink.click();
			return new AccountPageRwd(driver);
		} else {
			throw new PageObjectException(this.driver,"L'utilisateur n'est pas connecté et ne peut pas accéder à son compte");
		}
	}

	/**
	 * Déconnecte l'utilisateur
	 * 
	 * @return <b>HomePageRwd</b> Page d'accueil
	 * @throws PageObjectException
	 */
	public HomePageRwd logOutUser() throws PageObjectException {
		accountBtn.click();
		fluentWait(logoutLink, 1, 5);
		logoutLink.click();
		log.info("Déconnection de l'utilisateur");
		return new HomePageRwd(driver);
	}

	/**
	 * Retourn le titre de la Page en cours
	 * 
	 * @return <b>String</b> titre de la page
	 */
	public String getPageTitle() {
		String title = driver.getTitle();
		return title;

	}

	/**
	 * Retourne le titre principal affiché sur la page
	 * 
	 * @return <b>String</b> titre H1 de la page en cours
	 * @throws PageObjectException
	 */
	public String getH1Title() throws PageObjectException {
		try {
			return pageTitle.getText();
		} catch (Exception e) {
			throw new PageObjectException(this.driver,"La page ne contient pas de titre H1, revoir le test", e);
		}
	}

	/**
	 * Test si un WebElement est présent sur la page
	 * 
	 * @param WE
	 *            WebElement
	 * @return
	 */
	protected boolean isElementPresent(WebElement WE) {
		try {
			WE.isDisplayed();
			return true;
		} catch (NoSuchElementException e) {
			log.debug("l'élement recherché : " + WE
					+ " n'est pas accessible, ou alors le selecteur n'est pas bien nommé");
			return false;
		}
	}

	/*
	 * protected boolean isAlertPresent() { try { driver.switchTo().alert();
	 * return true; } catch (NoAlertPresentException e) { return false; } }
	 */

	/*
	 * protected String closeAlertAndGetItsText() { try { Alert alert =
	 * driver.switchTo().alert(); String alertText = alert.getText(); if
	 * (acceptNextAlert) { alert.accept(); } else { alert.dismiss(); } return
	 * alertText; } finally { acceptNextAlert = true; } }
	 */

	/*
	 * protected Boolean isAttributePresent(WebElement el, String attribute) {
	 * try { String v = el.getAttribute(attribute); if (v != null) { return
	 * true; } return false; } catch (Exception e) { return false; } }
	 */

	/**
	 * Wait For an element to appear (Checks every 2s for 20s max)
	 * 
	 * @param WebElement
	 * 
	 * @return WebElement
	 */
	protected void fluentWait(final WebElement WE) {
		new FluentWait<WebDriver>(driver).withTimeout(20, TimeUnit.SECONDS).pollingEvery(2, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class, ElementNotVisibleException.class)
				.ignoring(StaleElementReferenceException.class).until(new Function<WebDriver, Boolean>() {
					@NotNull
					@Override
					public Boolean apply(WebDriver webDriver) {
						return WE != null && WE.isDisplayed();
					}
				});
	};

	/**
	 * Wait for an element to appear with custom refresh Rate and max waiting
	 * Time
	 * 
	 * @param By
	 *            locator
	 * @param int
	 *            refreshRate in seconds
	 * @param int
	 *            maxTime in seconds
	 * @return WebElement
	 */
	protected void fluentWait(WebElement WE, int refreshRate, int maxTime) {
		new FluentWait<WebDriver>(driver).withTimeout(maxTime, TimeUnit.SECONDS)
				.pollingEvery(refreshRate, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class, ElementNotVisibleException.class)
				.ignoring(StaleElementReferenceException.class).until(new Function<WebDriver, Boolean>() {
					@NotNull
					@Override
					public Boolean apply(WebDriver webDriver) {
						return WE != null && WE.isDisplayed();
					}
				});
	};

	/**
	 * Dévie toute exception pendant un laps de temps
	 * 
	 * @param timeToWait
	 * @throws Error
	 */
	protected void implicitWait(int timeToWait) throws Error {
		driver.manage().timeouts().implicitlyWait(timeToWait, TimeUnit.SECONDS);
	}

	/**
	 * Attend qu'un élément soit visible sur la page
	 * 
	 * @param element
	 *            WebElement
	 * @throws Error
	 */
	protected void explicitWait(WebElement element) throws Error {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * Attend que la page soit chargée complétement<br>
	 * Essaye toute les secondes
	 */
	public void checkPageIsReady() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		if (js.executeScript("return document.readyState").toString().equals("complete")) {
			log.debug("Page chargée : " + this.getPageTitle());
			return;
		}
		for (int i = 0; i < 25; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			if (js.executeScript("return document.readyState").toString().equals("complete")) {
				break;
			}
		}
	}

	/**
	 * Switch automatiquement vers une nouvelle page si un lien en ouvre une
	 * nouvelle
	 * 
	 * @param driver
	 * @throws InterruptedException
	 */
	public static void waitForNewWindowAndSwitchToIt(WebDriver driver) throws InterruptedException {
		String cHandle = driver.getWindowHandle();
		String newWindowHandle = null;
		Set<String> allWindowHandles = driver.getWindowHandles();

		// Wait for 20 seconds for the new window and throw exception if not
		// found
		for (int i = 0; i < 20; i++) {
			if (allWindowHandles.size() > 1) {
				for (String allHandlers : allWindowHandles) {
					if (!allHandlers.equals(cHandle))
						newWindowHandle = allHandlers;
				}
				driver.switchTo().window(newWindowHandle);
				break;
			} else {
				Thread.sleep(1000);
			}
		}
		if (cHandle == newWindowHandle) {
			throw new RuntimeException("Time out - No window found");
		}
	}

	/**
	 * Ferme toutes les fenetres à part celle mis en parametre
	 * 
	 * @param driver
	 * @param openWindowHandle
	 *            Handle de la page que l'on veut garder ouverte
	 * @return
	 */
	public static boolean closeAllOtherWindows(WebDriver driver, String openWindowHandle) {
		Set<String> allWindowHandles = driver.getWindowHandles();
		for (String currentWindowHandle : allWindowHandles) {
			if (!currentWindowHandle.equals(openWindowHandle)) {
				driver.switchTo().window(currentWindowHandle);
				driver.close();
			}
		}

		driver.switchTo().window(openWindowHandle);
		if (driver.getWindowHandles().size() == 1)
			return true;
		else
			return false;
	}
}
