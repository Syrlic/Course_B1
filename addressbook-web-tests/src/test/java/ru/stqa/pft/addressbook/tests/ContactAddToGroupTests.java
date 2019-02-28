package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactAddToGroupTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    if(app.db().contacts().size() == 0){
      app.goTo().goHome();
      app.contact().create(new ContactData().withFirstname("Santa").withMiddlename("Saint")
              .withLastname("Claus").withNickname("Red").withCompany("Christmas corp.")
              .withAddress("2512 Everywhere Avenue").withMobilePhone("+55512349876").withHomePhone("987654321")
              .withDay("1").withMonth("January").withYear("1900").withNotes("Ho Ho Ho!!!"), true);
    }
    if(app.db().groups().size() == 0){
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test8"));
    }
    app.goTo().goHome();
  }
  @Test
  public void testAddContactToGroup(){
    Contacts contListBD = app.db().contacts();
    GroupData group = app.db().groups().iterator().next();
    int idgroup = group.getId();
    Contacts beforeC = group.getContacts();
    ContactData cd;
    if(beforeC.size() == contListBD.size()){
      cd = new ContactData().withLastname("Garry").withFirstname("Dresden")
              .withEmail2("gr@dr.ru");
      app.contact().create(cd, true);
      cd = app.db().contacts().stream().max(Comparator.comparingInt(ContactData::getId)).get();
    }else {
      Set<ContactData> contacts = contListBD.stream()
              .filter((c) -> c.getGroups().size() > 0 && c.getGroups().iterator().next().getId() != idgroup ||
                      c.getGroups().size() == 0).collect(Collectors.toSet());
      cd = contacts.iterator().next();
    }
      Groups beforeG = cd.getGroups();
      int idCD = cd.getId();
      app.contact().selectContactById(cd.getId());
      app.group().selectGroupFromListById(idgroup);
      app.group().submitAddToGroup();
      Set<ContactData> contacts= app.db().contacts().stream().filter((c)-> c.getId() == idCD)
              .collect(Collectors.toSet());
      Groups afterG = contacts.iterator().next().getGroups();
      Set<GroupData> groups = app.db().groups().stream().filter((g)-> g.getId() == idgroup)
              .collect(Collectors.toSet());
      Contacts afterC = groups.iterator().next().getContacts();

      assertThat(afterC.size(), equalTo(beforeC.size()+1));
      assertThat(afterG, equalTo(beforeG.withAdded(group)));


  }


}
