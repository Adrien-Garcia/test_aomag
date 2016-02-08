package tests.com.aomagento.tests;

import base.DesiredCapabilitiesTest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.*;

public class Connection extends DesiredCapabilitiesTest {
  protected String baseUrl;

  /** 
  @Parameters
  public static Collection<Object[]> desiredCapabilities() {
    return Arrays.asList (new Object[][] {     
      { "firefox", "", "ANY" }
    });
  }
   */
  public Connection(String browser, String version, String plateform) throws MalformedURLException {
    super(browser, version, plateform);
    //this.hubURL = new URL("http://127.0.0.1:4444/wd/hub");
    this.baseUrl = "http://aomagento.jetpulp.dev/";
  }
  @Test
  public void testConnection() throws Exception {
    driver.get(baseUrl + "/client_4/");
    driver.findElement(By.cssSelector("a.skip-link.skip-account > span.label")).click();
    driver.findElement(By.linkText("Mon compte")).click();
    driver.findElement(By.id("pass")).clear();
    driver.findElement(By.id("pass")).sendKeys("pk4L2_ZpWEHs");
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("ao-addonline_75174");
    driver.findElement(By.xpath("//ul[@id='nav']/li[4]/ul/li/a/span")).click();
    driver.findElement(By.cssSelector("a.button > span > span")).click();
    driver.findElement(By.id("email_address")).clear();
    driver.findElement(By.id("email_address")).sendKeys("ao-addonline_75174");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("pk4L2_ZpWEHs");
    new Select(driver.findElement(By.id("prefix"))).selectByVisibleText("M.");
    driver.findElement(By.id("firstname")).clear();
    driver.findElement(By.id("firstname")).sendKeys("Nicolas");
    driver.findElement(By.id("lastname")).clear();
    driver.findElement(By.id("lastname")).sendKeys("Tiran");
    driver.findElement(By.id("email_address")).clear();
    driver.findElement(By.id("email_address")).sendKeys("nicolas.tiran@jetpulp.fr");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("HBnm124!");
    driver.findElement(By.id("confirmation")).clear();
    driver.findElement(By.id("confirmation")).sendKeys("HBnm124!");
    driver.findElement(By.id("is_subscribed")).click();
    driver.findElement(By.cssSelector("div.buttons-set > button.button")).click();
    driver.findElement(By.cssSelector("a.skip-link.skip-account > span.label")).click();
    driver.findElement(By.linkText("Déconnexion")).click();
    driver.findElement(By.cssSelector("a.skip-link.skip-account > span.label")).click();
    driver.findElement(By.linkText("Mon compte")).click();
    driver.findElement(By.id("pass")).clear();
    driver.findElement(By.id("pass")).sendKeys("pk4L2_ZpWEHs");
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("nicolas.tiran@jetpulp.fr");
    driver.findElement(By.id("pass")).clear();
    driver.findElement(By.id("pass")).sendKeys("HBnm124!");
    driver.findElement(By.id("send2")).click();
  }


}
