package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class DeleteContactFromGroupTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    if(app.db().contacts().size() == 0){
      app.goTo().goHome();
      app.contact().create(new ContactData().withFirstname("Santa").withMiddlename("Saint")
              .withLastname("Claus").withNickname("Red").withCompany("Christmas corp.")
              .withAddress("2512 Everywhere Avenue").withMobilePhone("+55512349876")
              .withDay("1").withMonth("January").withYear("1900").withNotes("Ho Ho Ho!!!"), true);
    }
    if(app.db().groups().size() == 0){
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test8"));
    }
    if(app.db().groups().stream().filter((g)-> g.getContacts().size() > 0)
            .collect(Collectors.toSet()).size() == 0){
      ContactData contact = app.db().contacts().iterator().next();
      app.goTo().goHome();
      app.contact().selectContactById(contact.getId());
      app.group().selectGroupFromListById(app.db().groups().iterator().next().getId());
      app.group().submitAddToGroup();
    }
    app.goTo().goHome();
  }
  @Test
  public void testDeleteContactFromGroup(){

    Set<GroupData> listGroups = app.db().groups().stream()
            .filter((g)-> g.getContacts().size() > 0).collect(Collectors.toSet());
    int idGroup = listGroups.iterator().next().getId();
    app.group().selectGroupWithContactsById(idGroup);
    Set<ContactData> listCont = app.db().contacts().stream()
            .filter((c)-> c.getGroups().size() > 0).filter((c)-> c.getGroups().iterator().next().getId()== idGroup)
            .collect(Collectors.toSet());;
    Contacts before = new Contacts(listCont);
    ContactData contact = before.iterator().next();
    app.contact().selectContactById(contact.getId());
    app.contact().removeFromGroup();
    Set<ContactData> listC = app.db().contacts().stream()
            .filter((c)-> c.getGroups().size() > 0)
            .filter((c)-> c.getGroups().iterator().next().getId()== idGroup).collect(Collectors.toSet());
    Contacts after = new Contacts(listC);

    assertThat(after.size(), equalTo(before.size()-1));
    assertThat(after, equalTo(before.without(contact)));

  }
}
