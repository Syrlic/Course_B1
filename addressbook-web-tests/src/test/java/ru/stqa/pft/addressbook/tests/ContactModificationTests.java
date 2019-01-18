package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() throws Exception {
    app.getNavigationHelper().goToHome();
    if(! app.getContactHelper().isThereAContact())
    {
      app.getContactHelper().createContact(new ContactData("Santa",
              "Saint", "Mouse", "Red", "Christmas corp.",
              "2512 Everywhere Avenue", "+55512349876", "test1","1",
              "January", "1900", "Ho Ho Ho!!!"), true);
    }
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("Peter",
            "Pen", "Claus", "Red", "Christmas corp.",
            "2512 Everywhere Avenue", "+55512349876", null, "1",
            "January", "1900", "Ho Ho Ho!!!"), false);
    app.getContactHelper().submitContactModification();
  }


}
