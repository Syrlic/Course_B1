package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() throws Exception {

    List<ContactData> before = app.getContactHelper().getContactList();
    ContactData cd = new ContactData("Jjjjjj",
            "Saint", "Jouse", "Red", "Christmas corp.",
            "2512 Everywhere Avenue", "+55512349876", "test1", "1",
            "January", "1900", "Ho Ho Ho!!!");
    app.getContactHelper().createContact(cd, true);
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(),before.size()+1);

    before.add(cd);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(after, before);
  }

}
