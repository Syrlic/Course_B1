package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactEmailTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().goHome();
    if(app.db().contacts().size() == 0){
      app.contact().create(new ContactData().withFirstname("Santa")
              .withMiddlename("Saint").withLastname("Mouse").withNickname("Red").withCompany("Christmas corp.")
              .withAddress("2512 Everywhere Avenue").withMobilePhone("+55512349876").withHomePhone("98765-4321")
              .withWorkPhone("23-23-23").withEmail("jybi123@mailinator-dfc.com")
              .withEmail2("vydi.red@mailinator.net").withEmail3("pofenaAndreychenko_29.12.1999@mailinator.ru")
              .withDay("1").withMonth("January").withYear("1900").withNotes("Ho Ho Ho!!!"), true);
    }
  }

  @Test
  public void testContactEmails(){
    app.goTo().goHome();
    ContactData contactData = app.contact().allContacts().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contactData);

    assertThat(contactData.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
  }



}
