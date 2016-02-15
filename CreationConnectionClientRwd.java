package tests.test_aomagento;

import java.sql.Timestamp;
import java.util.Date;
import base.DesiredCapabilitiesTest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class CreationConnectionClientRwd extends DesiredCapabilitiesTest {
  protected String baseUrl;

   
  @Parameters
  public static Collection<Object[]> desiredCapabilities() {
    return Arrays.asList (new Object[][] {     
      { "firefox", "", "ANY" }
    });
  }
   
  public CreationConnectionClientRwd(String browser, String version, String plateform) throws MalformedURLException {
    super(browser, version, plateform);
    this.hubURL = new URL("http://127.0.0.1:4444/wd/hub");
    //this.baseUrl = "http://aomagento.addonline.biz/";
    this.baseUrl = "http://aomagento.jetpulp.dev";
  }
  @Test
  public void testConnection() throws Exception {
	  
	java.util.Date date= new java.util.Date();  
	Timestamp timestamp = new Timestamp(date.getTime());
	// le theme rwd a �t� appliqu� au client_4  : c'est donc � modifi� !!
    driver.get(baseUrl + "/client_4/customer/account/login/");
    String email = "nicolas.tiran"+timestamp.hashCode()+"@jetpulp.fr";
      
    fluentWait(By.cssSelector("a.sel-create-account"), 1, 10);
    driver.findElement(By.cssSelector("a.sel-create-account")).click();
    
    fluentWait(By.cssSelector("button.sel-register"), 1, 10);
    assertTrue("Bouton creation de compte present",isElementPresent(By.cssSelector("button.sel-register")));

    // on rempli le formulaire 
    new Select(driver.findElement(By.id("prefix"))).selectByVisibleText("M.");
    driver.findElement(By.id("firstname")).clear();
    driver.findElement(By.id("firstname")).sendKeys("Nicolas");
    driver.findElement(By.id("lastname")).clear();
    driver.findElement(By.id("lastname")).sendKeys("Tiran");
    driver.findElement(By.id("email_address")).clear();
    driver.findElement(By.id("email_address")).sendKeys(email);
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("HBnm124!");
    driver.findElement(By.id("confirmation")).clear();
    driver.findElement(By.id("confirmation")).sendKeys("HBnm124!");
    driver.findElement(By.id("is_subscribed")).click();
    driver.findElement(By.cssSelector(".sel-register")).click();
    
    fluentWait(By.cssSelector("a.sel-account"), 1, 10);
    driver.findElement(By.cssSelector("a.sel-account")).click();
    driver.findElement(By.cssSelector("a.sel-logout")).click();
    //fluentWait(By.cssSelector("a.sel-create-account"), 1, 10);
    
    // le theme rwd a �t� appliqu� au client_4  : c'est donc � modifi� !!
    driver.get(baseUrl + "/client_4/customer/account/login/");
    fluentWait(By.cssSelector("button.sel-login"), 1, 10);
    assertTrue("Bouton connexion au compte present",isElementPresent(By.cssSelector("button.sel-login")));
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys(email);
    driver.findElement(By.id("pass")).clear();
    driver.findElement(By.id("pass")).sendKeys("HBnm124!");
    driver.findElement(By.cssSelector("button.sel-login")).click();
  }


}
