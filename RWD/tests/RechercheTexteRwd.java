package tests.test_aomagento.RWD.tests;

import base.ComplexReportFactory;
import base.DesiredCapabilitiesTest;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.RWD.PO.AccountPageRwd;
import tests.test_aomagento.RWD.PO.CatalogPageRwd;
import tests.test_aomagento.RWD.PO.HomePageRwd;
import tests.test_aomagento.RWD.PO.SearchResultPageRwd;
import tests.test_aomagento.RWD.PO.SignInPageRwd;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

public class RechercheTexteRwd extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(ConnectionClientRwd.class);
	
	HomePageRwd homePage;
	SearchResultPageRwd searchResultPage;
	
	@Test(description="Recherche Texte", dataProvider = "search")
	public void testRechercheTexteRwd(String searchFor) throws Exception {
		ExtentTest testReporter = ComplexReportFactory.getTest();
//		testReporter.log(LogStatus.INFO, ":: Test lancé :: Recherche texte :: "+ searchFor);
		
		homePage = new HomePageRwd(driver);
//		testReporter.log(LogStatus.PASS, "Accès à la page d'accueil ");
		
		searchResultPage = homePage.searchForResults(searchFor);
//		testReporter.log(LogStatus.PASS, "Recherche "+ searchFor+" effectuée");
		
		Assert.assertTrue(searchResultPage.isSearchFound(searchFor), searchFor+" ne donne pas de résultats;");
//		testReporter.log(LogStatus.PASS, "Recherche "+ searchFor+" trouvée");
		
		Assert.assertTrue(searchResultPage.getH1Title().toLowerCase().contains(searchFor.toLowerCase()));
	}

}
