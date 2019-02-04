package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import java.util.List;

public class ContactDeletionTests extends TestBase {

  @Test
   public void testContactDeletion() {
    app.getNavigationHelper().goToHome();
    List<ContactData> before = app.getContactHelper().getContactList();
    if (! app.getContactHelper().isThereAContact())
    {
      app.getContactHelper().createContact(new ContactData("Santa",
              "Minny", "Claus", "Red", "Christmas corp.",
              "2512 Everywhere Avenue", "+55512349876", "test1","1",
              "January", "1900", "Ho Ho Ho!!!"), true);
    }
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectedContact();
    app.getNavigationHelper().assertConfirmation();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size()-1);

    before.remove(0);
    Assert.assertEquals(before, after);
  }


}
