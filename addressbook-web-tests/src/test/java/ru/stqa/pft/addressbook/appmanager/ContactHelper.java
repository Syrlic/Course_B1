package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

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
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("work"), contactData.getWorkPhone());
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

  public void selectContactById(int id) {
    click(By.cssSelector("input[value='"+id+"']"));
  }
  public void deleteSelectedContact() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void initContactModificationById(int id) {
    click(By.cssSelector("a[href='edit.php?id="+id+"']"));
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public void create(ContactData contactData, boolean creation) {
    initNewContactCreation();
    fillContactForm(contactData, true);
    submitNewContactCreation();
  }
  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstName = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastName = wd.findElement(By.name("lastname")).getAttribute("value");
    String modilePhone = wd.findElement(By.name("mobile")).getAttribute("value");
    String workPhone = wd.findElement(By.name("work")).getAttribute("value");
    String homePhone = wd.findElement(By.name("home")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withLastname(lastName).withFirstname(firstName)
            .withHomePhone(homePhone).withMobilePhone(modilePhone).withWorkPhone(workPhone);

  }
  public boolean isThereAContact() {
    return isElementPresent(By.xpath("//tr[2]/td/input"));
  }

  public Contacts allContacts() {
    Contacts contacts = new Contacts();
    List<WebElement> element = wd.findElements(By.xpath("//tbody//tr[@*='entry']"));
    if(!element.isEmpty()) {
      for (WebElement we : element) {
        List<WebElement> cells = we.findElements(By.tagName("td"));
        String allPhones = cells.get(5).getText();
     //   String[] phones = allPhones.split("\n");
        ContactData contact = new ContactData()
                .withId(Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value")))
                .withLastname(cells.get(1).getText()).withFirstname(cells.get(2).getText())
                .withAddress(cells.get(3).getText()).withAllPhones(allPhones);
             //   .withHomePhone(phones[0]).withMobilePhone(phones[1]).withWorkPhone(phones[2]);

        contacts.add(contact);
      }
    }
    return contacts;
  }

}
