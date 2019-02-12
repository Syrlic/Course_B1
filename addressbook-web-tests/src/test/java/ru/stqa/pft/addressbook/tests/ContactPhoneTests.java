package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().goHome();
    if(app.contact().allContacts().size() == 0)
    {
      app.contact().create(new ContactData().withFirstname("Santa")
              .withMiddlename("Saint").withLastname("Mouse").withNickname("Red").withCompany("Christmas corp.")
              .withAddress("2512 Everywhere Avenue").withMobilePhone("+55512349876").withHomePhone("98765-4321")
              .withWorkPhone("23-23-23").withGroup("test8")
              .withDay("1").withMonth("January").withYear("1900").withNotes("Ho Ho Ho!!!"), true);
    }
  }
  @Test(enabled = true)
  public void testContactPhones(){
    app.goTo().goHome();
    ContactData contactData = app.contact().allContacts().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contactData);

    assertThat(contactData.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
  }

  private String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone()).stream()
            .filter((s)-> ! s.equals("")).map(ContactPhoneTests::clened)
            .collect(Collectors.joining("\n"));
  }

  public static String clened(String phone){
    return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
  }
}
