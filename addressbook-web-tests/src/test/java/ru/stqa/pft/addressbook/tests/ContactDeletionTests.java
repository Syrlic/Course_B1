package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactDeletionTests extends TestBase {

 @BeforeMethod
 public void ensurePreconditions(){
  app.goTo().goHome();
  if(app.db().contacts().size() == 0){
   app.contact().create(new ContactData().withFirstname("Santa").withMiddlename("Saint")
           .withLastname("Claus").withNickname("Red").withCompany("Christmas corp.")
           .withAddress("2512 Everywhere Avenue").withMobilePhone("+55512349876")
           .withDay("1").withMonth("January").withYear("1900").withNotes("Ho Ho Ho!!!"), true);
  }
 }

  @Test
   public void testContactDeletion() {

    Contacts before = app.db().contacts();
    ContactData cd = before.iterator().next();
    app.contact().selectContactById(cd.getId());
    app.contact().deleteSelectedContact();
    app.goTo().assertConfirmation();
    Contacts after = app.db().contacts();

   assertThat(after.size(), equalTo(before.size()-1));

   assertThat(after, equalTo(before.without(cd)));
   verifyContactListUI();
  }


}
