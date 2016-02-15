package tests.test_aomagento;

import base.DesiredCapabilitiesTest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.*;

public class CommandeRwd extends DesiredCapabilitiesTest {
  protected String baseUrl;

   
  @Parameters
  public static Collection<Object[]> desiredCapabilities() {
    return Arrays.asList (new Object[][] {     
      { "firefox", "", "ANY" }
    });
  }
   
  public CommandeRwd(String browser, String version, String plateform) throws MalformedURLException {
    super(browser, version, plateform);
    this.hubURL = new URL("http://127.0.0.1:4444/wd/hub");
    this.baseUrl = "http://aomagento.jetpulp.dev/";
  }
  @Test
  public void testCommande() throws Exception {
	  
	// le theme rwd a été appliqué au client_4
    driver.get(baseUrl + "client_4/");
    
    //Page d'accueil
    fluentWait(By.cssSelector("body.cms-index-index"), 1, 10);
    
    //Click sur la premiere categorie : au premier niveau : si c'est pas suffisant faire un click sur la premiere catégorie qui renvoie vers un rayon
    driver.findElement(By.cssSelector("nav > ol > li.level0 > a")).click();
    fluentWait(By.cssSelector(".sel-item-product"), 1, 10);
    
    //click sur l'ajout au panier sur la page liste
    driver.findElement(By.cssSelector(".sel-add-to-cart")).click();
    //on attend la popin
    fluentWait(By.cssSelector(".sel-j2tajax-confirm"), 1, 10);
    // on attend le chargement du contenu dans la popin
    fluentWait(By.cssSelector(".sel-j2t-checkout-link"), 1, 10);
    
    //click vers le panier
    driver.findElement(By.cssSelector(".sel-j2t-checkout-link")).click();
    //On attend la page panier
    fluentWait(By.cssSelector("body.checkout-cart-index"), 1, 10);
    assertTrue("Bouton Commander present",isElementPresent(By.cssSelector("button.sel-btn-proceed-checkout")));
    
    //Click sur le bouton Passer ma commande
    driver.findElement(By.cssSelector(".sel-btn-proceed-checkout")).click();
    fluentWait(By.id("login-password"), 1, 10);
    // Connexion : changer les identifiant si le compte n'existe pas.
    driver.findElement(By.id("login-password")).clear();
    driver.findElement(By.id("login-password")).sendKeys("HBnm124!");
    driver.findElement(By.id("login-email")).clear();
    driver.findElement(By.id("login-email")).sendKeys("nicolas.tiran@jetpulp.fr");
    driver.findElement(By.cssSelector("button.sel-login")).click();
    
    //on attend la liste des adresses de facturation
    fluentWait(By.cssSelector("button.sel-billing-save"), 1, 10);
    
    /* Si on veut tester la creation d'addresse : mais c'est pour un nouveaux compte sans adresse
     * if(isElementPresent(By.id("billing:street1"))) {
	    driver.findElement(By.id("billing:street1")).clear();
	    driver.findElement(By.id("billing:street1")).sendKeys("12 av Tony Garnier");
	    driver.findElement(By.id("billing:city")).clear();
	    driver.findElement(By.id("billing:city")).sendKeys("Lyon");
	    driver.findElement(By.id("billing:postcode")).clear();
	    driver.findElement(By.id("billing:postcode")).sendKeys("69007");
	    driver.findElement(By.id("billing:telephone")).clear();
	    driver.findElement(By.id("billing:telephone")).sendKeys("0688767196");
    }*/
    
    driver.findElement(By.cssSelector("button.sel-billing-save")).click();
    //On attend la liste des methode de livraisons
    fluentWait(By.cssSelector(".sel-form-method-shipping input.radio"), 1, 10);
    
    driver.findElement(By.cssSelector(".sel-form-method-shipping input.radio")).click();
    // Sauvegarde de la methode de livraison 
    driver.findElement(By.cssSelector("button.sel-shipping-method-save")).click();

    
    //On attend les methode de paiement 
    fluentWait(By.id("dt_method_checkmo"), 1, 10);
    
    //paiement par cheque  
    if(isElementPresent(By.id("p_method_checkmo"))) {
    	driver.findElement(By.id("p_method_checkmo")).click();
    	
    } else {
    	// sinon mettre son paiement 
    	
    }
    
    // Sauvegarde de la methode de paiement    	
    driver.findElement(By.cssSelector("button.sel-payment-save")).click();
    
    fluentWait(By.cssSelector("button.sel-btn-checkout"), 1, 10);
    //On valide la commande
    driver.findElement(By.cssSelector("button.sel-btn-checkout")).click();
    //On attend la page de confirmation
    fluentWait(By.cssSelector("body.checkout-onepage-success"), 1, 15);
    assertTrue("Page de confirmation de commande afficher",isElementPresent(By.cssSelector("body.checkout-onepage-success")));
  }


}
