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
              .withAddress("2512 Everywhere Avenue").withMobilePhone("+55512349876")
              .withDay("1").withMonth("January").withYear("1900").withNotes("Ho Ho Ho!!!"), true);
    }
  }

  @Test
  public void testContactModification() throws Exception {
    Contacts before = app.db().contacts();
    ContactData modifyContact = before.iterator().next();
    ContactData cd = new ContactData().withId(modifyContact.getId())
            .withFirstname("SS").withMiddlename("SSSS").withLastname("SSS")
            .withNickname("Red").withCompany("фабрика Масленница").withAddress("2512 Everywhere Avenue")
            .withMobilePhone("+3333333333").withHomePhone("222222").withEmail("tre@tre").withEmail3("pop@df.ru")
            .withDay("11").withMonth("July").withYear("2010");
    app.contact().initContactModificationById(modifyContact.getId());
    app.contact().fillContactForm(cd, false);
    app.contact().submitContactModification();
    Contacts after = app.db().contacts();

    assertThat(after.size(), equalTo(before.size()));

    assertThat(after, equalTo(before.without(modifyContact).withAdded(cd)));
    verifyContactListUI();

  }

}
