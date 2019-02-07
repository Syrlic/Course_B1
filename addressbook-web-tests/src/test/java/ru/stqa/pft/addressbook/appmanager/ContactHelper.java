package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase{

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitNewContactCreation() {
    click(By.xpath("(//input[@name='submit'])[2]"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
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

    if(creation){
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    }else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }

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

  public void initContactModification(int i) {
    i++;
    click(By.xpath("(//img[@alt='Edit'])["+i+"]"));
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public void createContact(ContactData contactData, boolean creation) {
    initNewContactCreation();
    fillContactForm(contactData, true);
    submitNewContactCreation();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.xpath("//tr[2]/td/input"));
  }

  public List<ContactData> getContactList() {
    List<ContactData> contacts = new ArrayList<>();
    List<WebElement> element = wd.findElements(By.xpath("//tbody//tr[@*='entry']"));
    if(!element.isEmpty()) {
      for (WebElement we : element) {
        List<WebElement> cells = we.findElements(By.tagName("td"));
        ContactData contact = new ContactData(0, null, null, null, null,
                null, null, null, null,
                null, null, null, null);
        for (int i = 0; i < cells.size(); i++) {
          switch (i) {
            case 0:
              contact.setId(Integer.parseInt(cells.get(i).findElement(By.tagName("input"))
                      .getAttribute("value")));
              break;
            case 1:
              contact.setLastname(cells.get(i).getText());
              break;
            case 2:
              contact.setFirstname(cells.get(i).getText());
              break;
            case 3:
              contact.setAddress(cells.get(i).getText());
              break;
            case 5:
              contact.setMobile(cells.get(i).getText());
              break;
          }
        }
        contacts.add(contact);
      }

  }
    return contacts;
}}
