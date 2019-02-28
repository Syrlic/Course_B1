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
    if(contactData.getFirstname() != null){
      type(By.name("firstname"), contactData.getFirstname());
    } else {
      contactData.withFirstname("");
      type(By.name("firstname"), contactData.getFirstname());
    }
    if(contactData.getMiddlename() != null){
      type(By.name("middlename"), contactData.getMiddlename());
    }else {
      contactData.withMiddlename("");
      type(By.name("middlename"), contactData.getMiddlename());
    }
    if(contactData.getLastname() != null){
      type(By.name("lastname"), contactData.getLastname());
    }else {
      contactData.withLastname("");
      type(By.name("lastname"), contactData.getLastname());
    }
    if(contactData.getNickname() != null){
      type(By.name("nickname"), contactData.getNickname());
    }else {
      contactData.withNickname("");
      type(By.name("nickname"), contactData.getNickname());
    }
    if(contactData.getPhoto() != null){
      attach(By.name("photo"), contactData.getPhoto());
    }
    if(contactData.getCompany() != null){
      type(By.name("company"), contactData.getCompany());
    }else {
      contactData.withCompany("");
      type(By.name("company"), contactData.getCompany());
    }
    if(contactData.getAddress() != null){
      type(By.name("address"), contactData.getAddress());
    }else {
      contactData.withAddress("");
      type(By.name("address"), contactData.getAddress());
    }
    if(contactData.getMobilePhone() != null){
      type(By.name("mobile"), contactData.getMobilePhone());
    }else {
      contactData.withMobilePhone("");
      type(By.name("mobile"), contactData.getMobilePhone());
    }
    if(contactData.getHomePhone() != null){
      type(By.name("home"), contactData.getHomePhone());
    }else {
      contactData.withHomePhone("");
      type(By.name("home"), contactData.getHomePhone());
    }
    if(contactData.getWorkPhone() != null){
      type(By.name("work"), contactData.getWorkPhone());
    } else {
      contactData.withWorkPhone("");
      type(By.name("work"), contactData.getWorkPhone());
    }
    if(contactData.getEmail() != null){
      type(By.name("email"), contactData.getEmail());
    }else {
      contactData.withEmail("");
      type(By.name("email"), contactData.getEmail());
    }
    if(contactData.getEmail2() != null){
      type(By.name("email2"), contactData.getEmail2());
    }else {
      contactData.withEmail2("");
      type(By.name("email2"), contactData.getEmail2());
    }
    if(contactData.getEmail3() != null){
      type(By.name("email3"), contactData.getEmail3());
    }else {
      contactData.withEmail3("");
      type(By.name("email3"), contactData.getEmail3());
    }
    if(contactData.getDay() != null){
      select(By.name("bday"), contactData.getDay());
    }
    if(contactData.getMonth() != null){
      select(By.name("bmonth"), contactData.getMonth());
    }else {
      contactData.withMonth("-");
      select(By.name("bmonth"), contactData.getMonth());
    }
    if(contactData.getYear() != null){
      type(By.name("byear"), contactData.getYear());
    }else {
      contactData.withYear("");
      type(By.name("byear"),contactData.getYear());
    }
    if(contactData.getNotes() != null){
      type(By.name("notes"), contactData.getNotes());
    }else {
      contactData.withNotes("");
      type(By.name("notes"), contactData.getNotes());
    }
    if(contactData.getTitle() != null){
      type(By.name("title"), contactData.getTitle());
    }else {
      contactData.withTitle("");
      type(By.name("title"), contactData.getTitle());
    }
    if(contactData.getHomepage() != null){
      type(By.name("homepage"), contactData.getHomepage());
    }else {
      contactData.withHomepage("");
      type(By.name("homepage"), contactData.getHomepage());
    }

    if(creation){
      if(contactData.getGroups().size() > 0) {
        Assert.assertTrue(contactData.getGroups().size() == 1);
        new Select(wd.findElement(By.name("new_group")))
                .selectByVisibleText(contactData.getGroups().iterator().next().getName());
      }
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
    String address = wd.findElement(By.name("address")).getText();
    String modilePhone = wd.findElement(By.name("mobile")).getAttribute("value");
    String workPhone = wd.findElement(By.name("work")).getAttribute("value");
    String homePhone = wd.findElement(By.name("home")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withLastname(lastName).withFirstname(firstName)
            .withHomePhone(homePhone).withMobilePhone(modilePhone).withWorkPhone(workPhone).withEmail(email)
            .withEmail2(email2).withEmail3(email3).withAddress(address);

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
        String allemails = cells.get(4).getText();
        ContactData contact = new ContactData()
                .withId(Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value")))
                .withLastname(cells.get(1).getText()).withFirstname(cells.get(2).getText())
                .withAddress(cells.get(3).getText()).withAllPhones(allPhones).withAllEmails(allemails);


        contacts.add(contact);
      }
    }
    return contacts;
  }

  public void removeFromGroup() {
    click(By.cssSelector("input[name='remove']"));
  }
}
