package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase{

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitNewContactCreation() {
    click(By.xpath("(//input[@name='submit'])[2]"));
  }

  public void fillContactForm(ContactData contactData) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("middlename"), contactData.getMiddlename());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("nickname"), contactData.getNickname());
    type(By.name("company"), contactData.getCompany());
    type(By.name("address"), contactData.getAddress());
    type(By.name("mobile"), contactData.getMobile());
    select(By.name("bday"), contactData.getDay());
    select(By.name("bmonth"), contactData.getMonth());
    type(By.name("byear"), contactData.getYear());
    type(By.name("notes"), contactData.getNotes());
  }

  public void initNewContactCreation() {
    click(By.linkText("add new"));
  }

  public void selectContact() {
    click(By.xpath("//tr[2]/td/input"));
  }

  public void deleteSelectedContact() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void initContactModification() {
    click(By.xpath("(//img[@alt='Edit'])[2]"));
  }

  public void submitContactModification() {
    click(By.name("update"));
  }
}
