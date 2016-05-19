package tests.test_aomagento.COMPUTEC.tests;

import base.ComplexReportFactory;
import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.COMPUTEC.PO.HomePage;
import tests.test_aomagento.COMPUTEC.PO.SearchResultPage;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RechercheTexte extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(ConnectionClient.class);
	
	HomePage homePage;
	SearchResultPage searchResultPage;
	
	@Test(description="Recherche Texte", dataProvider = "search")
	public void testRechercheTexteRwd(String searchFor) throws Exception {
		ExtentTest testReporter = ComplexReportFactory.getTest();
//		testReporter.log(LogStatus.INFO, ":: Test lancé :: Recherche texte :: "+ searchFor);
		
		homePage = new HomePage(driver);
//		testReporter.log(LogStatus.PASS, "Accès à la page d'accueil ");
		
		searchResultPage = homePage.searchForResults(searchFor);
//		testReporter.log(LogStatus.PASS, "Recherche "+ searchFor+" effectuée");
		
		Assert.assertTrue(searchResultPage.isSearchFound(searchFor), searchFor+" ne donne pas de résultats;");
//		testReporter.log(LogStatus.PASS, "Recherche "+ searchFor+" trouvée");
		
		Assert.assertTrue(searchResultPage.getH1Title().toLowerCase().contains(searchFor.toLowerCase()));
	}

}
