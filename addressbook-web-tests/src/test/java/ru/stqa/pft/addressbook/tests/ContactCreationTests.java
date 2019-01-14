package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() throws Exception {

    app.getContactHelper().initNewContactCreation();
    app.getContactHelper().fillContactForm(new ContactData("Santa", "Saint", "Claus", "Red", "Christmas corp.", "2512 Everywhere Avenue", "+55512349876", "1", "January", "1900", "Ho Ho Ho!!!"));
    app.getContactHelper().submitNewContactCreation();
  }

}
