package tests.test_aomagento.BRANDER.tests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;


import base.DesiredCapabilitiesTestNG;
import tests.test_aomagento.BRANDER.PO.CatalogSearchPage;
import tests.test_aomagento.BRANDER.PO.HomePage;

public class RechercheTexte extends DesiredCapabilitiesTestNG {
	private static Log log = LogFactory.getLog(ConnectionClient.class);
	
	HomePage homePage;
	CatalogSearchPage searchResultPage;
	
	String searchFor = "apple";
	
	@Test(description="Recherche Texte")
	public void testRechercheTexte() throws Exception {
//		ExtentTest testReporter = ComplexReportFactory.getTest();
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
