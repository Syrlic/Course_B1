package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().goHome();
    if(app.db().contacts().size() == 0){
      app.contact().create(new ContactData().withFirstname("Santa")
              .withMiddlename("Saint").withLastname("Mouse").withNickname("Red").withCompany("Christmas corp.")
              .withAddress("2512 Everywhere Avenue").withMobilePhone("+55512349876").withGroup("test8")
              .withDay("1").withMonth("January").withYear("1900").withNotes("Ho Ho Ho!!!"), true);
    }
    if(app.contact().allContacts().size() == 0)
    {

    }
  }

  @Test
  public void testContactModification() throws Exception {
    Contacts before = app.db().contacts();
    System.out.println("List before:");
    for(ContactData c : before) {
      System.out.println(c.toString());
    }
    ContactData modifyContact = before.iterator().next();
    ContactData cd = new ContactData().withId(modifyContact.getId())
            .withFirstname("Петр").withMiddlename("HHHH").withLastname("Петров")
            .withNickname("Red").withCompany("Christmas corp.").withAddress("2512 Everywhere Avenue")
            .withMobilePhone("+55512349876").withDay("1").withMonth("January").withYear("1900")
            .withNotes("Ho Ho Ho!!!");
    app.contact().initContactModificationById(modifyContact.getId());
    app.contact().fillContactForm(cd, false);
    app.contact().submitContactModification();
    Contacts after = app.db().contacts();
    System.out.println("List from base:");
    for(ContactData c : after) {
      System.out.println(c.toString());
    }

    assertThat(after.size(), equalTo(before.size()));

    assertThat(after, equalTo(before.without(modifyContact).withAdded(cd)));

  }


}
