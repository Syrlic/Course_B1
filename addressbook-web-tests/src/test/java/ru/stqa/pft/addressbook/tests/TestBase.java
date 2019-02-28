package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class TestBase {

  Logger logger = LoggerFactory.getLogger(TestBase.class);

  public static final ApplicationManager app
          = new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX));


  @BeforeSuite(alwaysRun = true)
  public void setUp() throws Exception {
    app.init();
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws Exception {
    app.stop();
  }

  @BeforeMethod
  public void logTestStart(Method m, Object[] p){
    logger.info("Start test "+m.getName()+"with parametrs: "+ Arrays.asList(p));
  }

  @AfterMethod(alwaysRun = true)
  public void logTestStop(Method m){
    logger.info("Stop test "+m.getName());
  }

  public void verifyGroupListInUi() {
    if(Boolean.getBoolean("verifyUI")) {
      Groups dbGroup = app.db().groups();
      Groups uiGroups = app.group().all();
      assertThat(uiGroups, equalTo(dbGroup.stream()
              .map((g) -> new GroupData().withId(g.getId()).withName(g.getName())).collect(Collectors.toSet())));
    }
  }

  public void verifyContactListUI() {
    if(Boolean.getBoolean("verifyUI")){
      Contacts dbContacts = app.db().contacts();
      Contacts uiContacts = app.contact().allContacts();

      assertThat(uiContacts, equalTo(dbContacts.stream().map((c)-> new ContactData().withId(c.getId())
              .withFirstname(c.getFirstname()).withLastname(c.getLastname()).withAddress(c.getAddress())
              .withAllEmails(mergeEmails(c)).withAllPhones(mergePhones(c)))
              .collect(Collectors.toSet())));
    }
  }
  public String mergeEmails(ContactData contact){
    return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3()).stream()
            .filter((s)-> !s.equals("")).map((s)-> s.trim()).collect(Collectors.joining("\n"));

  }
  public String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone()).stream()
            .filter((s)-> ! s.equals("")).map(ContactPhoneTests::clened)
            .collect(Collectors.joining("\n"));
  }

  public static String clened(String phone){
    return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
  }

}
