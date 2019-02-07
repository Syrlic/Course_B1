package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() throws Exception {
    app.goTo().goToHome();
    if(! app.getContactHelper().isThereAContact())
    {
      app.getContactHelper().createContact(new ContactData("Santa",
              "Saint", "Mouse", "Red", "Christmas corp.",
              "2512 Everywhere Avenue", "+55512349876", "test1","1",
              "January", "1900", "Ho Ho Ho!!!"), true);
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().initContactModification(0);
    ContactData cd = new ContactData(before.get(0).getId(), "Kkkk",
            "Aaaa", "Kkkkk", "Red", "Christmas corp.",
            "2512 Everywhere Avenue", "+55512349876", null, "1",
            "January", "1900", "Ho Ho Ho!!!");
    app.getContactHelper().fillContactForm(cd, false);
    app.getContactHelper().submitContactModification();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(before.size(), after.size());

    before.remove(0);
    before.add(cd);

    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }


}
