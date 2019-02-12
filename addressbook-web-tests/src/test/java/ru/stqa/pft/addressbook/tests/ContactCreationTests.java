package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() throws Exception {

    Contacts before = app.contact().allContacts();
    ContactData cd = new ContactData().withFirstname("Atlas").withMiddlename("SSS").withLastname("Petrov")
            .withNickname("Red").withCompany("Christmas corp.").withAddress("2512 Everywhere Avenue")
            .withMobilePhone("+55512349876").withHomePhone("12-34").withWorkPhone("6654").withGroup("[none]").withDay("1").withMonth("January")
            .withYear("1900").withNotes("Ho Ho Ho!!!");
    app.contact().create(cd, true);
    Contacts after = app.contact().allContacts();

    assertThat(after.size(), equalTo(before.size()+1));

    assertThat(after, equalTo(before
            .withAdded(cd.withId(after.stream().mapToInt((c)-> (c.getId())).max().getAsInt()))));
  }

}
