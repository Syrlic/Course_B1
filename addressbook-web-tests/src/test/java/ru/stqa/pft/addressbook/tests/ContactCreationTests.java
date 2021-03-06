package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase{

  @DataProvider
  public Iterator<Object[]> validContactsFromXml() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")));
    String xml = "";
    String line = reader.readLine();
    while (line != null){
      xml += line;
      line = reader.readLine();
    }
    reader.close();
    XStream xStream = new XStream();
    xStream.processAnnotations(ContactData.class);
    List<ContactData> contacts = (List<ContactData>) xStream.fromXML(xml);
    return contacts.stream().map((c)-> new Object[]{c}).collect(Collectors.toList()).iterator();
  }

  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")));
    String json = "";
    String line = reader.readLine();
    while (line != null){
      json += line;
      line = reader.readLine();
    }
    reader.close();
    Gson gson = new Gson();
    List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {}.getType());
    return contacts.stream().map((c)-> new Object[]{c}).collect(Collectors.toList()).iterator();
  }

    @Test(dataProvider = "validContactsFromXml")
  public void testContactCreationFromFile(ContactData contact) throws Exception {

    Contacts before = app.db().contacts();
  //  File photo = new File("src/test/resources/robot.JPG");
    app.contact().create(contact, true);
    Contacts after = app.db().contacts();

    assertThat(after.size(), equalTo(before.size()+1));

    assertThat(after, equalTo(before
            .withAdded(contact.withId(after.stream().mapToInt((c)-> (c.getId())).max().getAsInt()))));
      verifyContactListUI();
  }

  @Test
  public void testContactCreation(){
    Groups groups = app.db().groups();
    File photo = new File("src/test/resources/robot.JPG");
    ContactData cd = new ContactData().withPhoto(photo).inGroups(groups.iterator().next())
            .withFirstname("Tommy").withLastname("Tom").withEmail("tom@tom");
    Contacts before = app.db().contacts();
    app.goTo().goHome();
    app.contact().create(cd, true);
    Contacts after = app.db().contacts();

    assertThat(after.size(), equalTo(before.size()+1));

    assertThat(after, equalTo(before
            .withAdded(cd.withId(after.stream().mapToInt((c)-> (c.getId())).max().getAsInt()))));

  }
}
