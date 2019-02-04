package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    List<WebElement> tdElements = wd.findElements(By.xpath("//tbody//tr[@class]//td"));
    List<String> data = new ArrayList<>();
    List<List<String>> dataList = new ArrayList<>();
    if(!tdElements.isEmpty()) {
    int count = 10; // количество столбцов в строке
    int n = 1;      // номер тэга по порядку
    for (WebElement w : tdElements) {
      count--;

      if (w.getAttribute("class").equals("center")) {
        if(count == 9) {
         String id = w.findElement(By.xpath("//tr[@class]["+n+"]//td/input")).getAttribute("value");
         data.add(id);
         n++;
        }
        }else
        {
          data.add(w.getText());
        }
        if (count == 0) {
          count = 10;
          List<String> temp = data.stream().collect(Collectors.toList());
          dataList.add(temp);
          data.clear();
        }
      }
    //создаем контакты из списка данных
      for (int i = 0; i < dataList.size(); i++){
        ContactData contact = new ContactData(0, null, null, null, null,
                null, null, null, null,
                null, null, null, null);
        for (int j = 0; j < dataList.get(i).size(); j++){
        switch (j) {
          case 0:
            contact.setId(Integer.parseInt(dataList.get(i).get(j)));
          case 1:
            contact.setLastname(dataList.get(i).get(j));
            break;
          case 2:
            contact.setFirstname(dataList.get(i).get(j));
            break;
          case 3:
            contact.setAddress(dataList.get(i).get(j));
            break;
          case 5:
            contact.setMobile(dataList.get(i).get(j));
            break;
        }
        }
        contacts.add(contact);
      }}
        return contacts;
  }
}
