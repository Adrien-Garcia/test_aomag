package tests.test_aomagento.rwd;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.*;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import base.Constant;
import base.DesiredCapabilitiesTest;
import tests.test_aomagento.pageobjects.AccountPageRwd;
import tests.test_aomagento.pageobjects.CatalogPageRwd;
import tests.test_aomagento.pageobjects.HomePageRwd;
import tests.test_aomagento.pageobjects.ProductPageRwd;
import tests.test_aomagento.pageobjects.SignInPageRwd;

public class AjoutCommProduitRwd extends DesiredCapabilitiesTest {
	private static Log log = LogFactory.getLog(AjoutCommProduitRwd.class);
	
	HomePageRwd homePage;
	SignInPageRwd signInPage;
	AccountPageRwd accountPage;
	CatalogPageRwd catalogPage;
	ProductPageRwd productPage;

	@Parameters
	public static Collection<Object[]> desiredCapabilities() {
		return Arrays.asList(new Object[][] { { "firefox", "", "ANY" } });
	}

	public AjoutCommProduitRwd(String browser, String version, String plateform) throws MalformedURLException {
		super(browser, version, plateform);
	}

	@Test
	public void testAjoutCommProduitRwd() throws Exception {
		log.info(":: Test Thème Rwd :: ajout de commentaire à un produit ::");
		
		homePage = new HomePageRwd(driver);

		// On va sur un produit d'une catégorie du catalogue
		catalogPage = homePage.goToCategory(0);
		productPage = catalogPage.clickOnProduct(0);
		
		// On lui ajoute un commentaire
		productPage.addReviewToProduct(Constant.Review, Constant.ReviewTitle, Constant.Firstname);
		
		// On test que le commentaire a bien été ajouté en modération
		assertTrue(productPage.checkIfSuccess());
		
		log.info("Test validé :: ajout de commentaire à un produit :: ");
	}
}
