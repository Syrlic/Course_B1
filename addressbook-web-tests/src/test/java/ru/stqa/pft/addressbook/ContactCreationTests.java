package ru.stqa.pft.addressbook;

import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class ContactCreationTests {
  private WebDriver wd;

  @BeforeMethod(alwaysRun = true)
  public void setUp() throws Exception {
    wd = new FirefoxDriver();
    wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    login("admin", "secret");
  }

  private void login(String username, String password) {
    wd.get("http://localhost/addressbook/group.php");
    wd.findElement(By.name("user")).clear();
    wd.findElement(By.name("user")).sendKeys(username);
    wd.findElement(By.xpath("//form[@id='LoginForm']/label[2]")).click();
    wd.findElement(By.name("pass")).clear();
    wd.findElement(By.name("pass")).sendKeys(password);
    wd.findElement(By.cssSelector("input[type=\"submit\"]")).click();
  }

  @Test
  public void testContactCreation() throws Exception {

    initNewContactCreation();
    fillContactForm(new ContactData("Santa", "Saint", "Claus", "Red", "Christmas corp.", "2512 Everywhere Avenue", "+55512349876", "1", "January", "1900", "Ho Ho Ho!!!"));
    submitNewContactCreation();
    //wd.findElement(By.linkText("Logout")).click();
  }

  private void submitNewContactCreation() {
    wd.findElement(By.xpath("(//input[@name='submit'])[2]")).click();
  }

  private void fillContactForm(ContactData contactData) {
    wd.findElement(By.name("firstname")).clear();
    wd.findElement(By.name("firstname")).sendKeys(contactData.getFirstname());
    wd.findElement(By.name("middlename")).clear();
    wd.findElement(By.name("middlename")).sendKeys(contactData.getMiddlename());
    wd.findElement(By.name("lastname")).clear();
    wd.findElement(By.name("lastname")).sendKeys(contactData.getLastname());
    wd.findElement(By.name("nickname")).clear();
    wd.findElement(By.name("nickname")).sendKeys(contactData.getNickname());
    wd.findElement(By.name("company")).clear();
    wd.findElement(By.name("company")).sendKeys(contactData.getCompany());
    wd.findElement(By.name("address")).clear();
    wd.findElement(By.name("address")).sendKeys(contactData.getAddress());
    wd.findElement(By.name("mobile")).clear();
    wd.findElement(By.name("mobile")).sendKeys(contactData.getMobile());
    new Select(wd.findElement(By.name("bday"))).selectByVisibleText(contactData.getDay());
    new Select(wd.findElement(By.name("bmonth"))).selectByVisibleText(contactData.getMonth());
    wd.findElement(By.name("byear")).clear();
    wd.findElement(By.name("byear")).sendKeys(contactData.getYear());
    wd.findElement(By.name("notes")).clear();
    wd.findElement(By.name("notes")).sendKeys(contactData.getNotes());
  }

  private void initNewContactCreation() {
    wd.findElement(By.linkText("add new")).click();
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() throws Exception {
    wd.quit();
  }

  private boolean isElementPresent(By by) {
    try {
      wd.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      wd.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

}
