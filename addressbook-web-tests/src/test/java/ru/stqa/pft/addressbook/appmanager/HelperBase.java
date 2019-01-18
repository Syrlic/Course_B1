package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class HelperBase {
  protected WebDriver wd;

  public HelperBase(WebDriver wd) {
    this.wd = wd;
  }

  protected void click(By locator) {
    wd.findElement(locator).click();
  }

  protected void type(By locator, String text) {
    if(text != null) {
      String existingText = wd.findElement(locator).getAttribute("value"); // получить текст из поля ввода (исключение)
      if (!text.equals(existingText)) {
        wd.findElement(locator).clear();
        wd.findElement(locator).sendKeys(text);
      }
    }
  }

  public boolean isAlertPresent() {
    try {
      wd.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  protected void select(By locator, String text) {
    new Select(wd.findElement(locator)).selectByVisibleText(text);
  }

  public void alertAccept() {
    if(isAlertPresent()) {
      wd.switchTo().alert().accept();
      wd.findElement(By.cssSelector("div.msgbox"));
    }

  }

  protected boolean isElementPresent(By locator) {
    try {
      wd.findElement(locator);
      return true;
    }catch (NoSuchElementException e){
      return false;
    }
  }
}
