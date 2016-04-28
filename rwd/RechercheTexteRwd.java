package tests.test_aomagento.rwd;

import base.DesiredCapabilitiesTest;
import tests.test_aomagento.pageobjects.AccountPageRwd;
import tests.test_aomagento.pageobjects.CatalogPageRwd;
import tests.test_aomagento.pageobjects.HomePageRwd;
import tests.test_aomagento.pageobjects.SearchResultPageRwd;
import tests.test_aomagento.pageobjects.SignInPageRwd;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

public class RechercheTexteRwd extends DesiredCapabilitiesTest {
	private static Log log = LogFactory.getLog(ConnectionClientRwd.class);
	HomePageRwd homePage;
	SearchResultPageRwd searchResultPage;

	@Parameters
	public static Collection<Object[]> desiredCapabilities() {
		return Arrays.asList(new Object[][] { { "firefox", "", "ANY" } });
	}

	public RechercheTexteRwd(String browser, String version, String plateform) throws MalformedURLException {
		super(browser, version, plateform);
	}

	@Test
	public void testRechercheTexteRwd() throws Exception {
		log.info(":: Test Thème Rwd :: Recherche texte ::");
		homePage = new HomePageRwd(driver);

		// Choisir une chaine avec laquelle on va effectuer la recherche
		String stringToSearch = "chrome";

		// On lance la recherche
		searchResultPage = homePage.searchForResults(stringToSearch);
		assertTrue(searchResultPage.isSearchFound(stringToSearch));
		assertTrue(searchResultPage.getH1Title().toLowerCase().contains(stringToSearch.toLowerCase()));
		log.info(":: Test Validé :: Recherche texte ::");

	}

}
