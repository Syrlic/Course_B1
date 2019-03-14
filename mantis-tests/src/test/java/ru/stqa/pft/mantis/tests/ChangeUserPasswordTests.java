package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.User;

import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.*;

public class ChangeUserPasswordTests extends TestBase {

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testUserPasswordChanging() throws IOException, ServiceException {
    skipIfNotFixedMantis(2);
    app.goTO().home();
    String passwordNew = "123456789";
    app.user().login(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
    app.goTO().manageUsersMenu();
    User user = app.user().chooseUser();
    app.user().chooseUserById(user.getId());
    app.user().resetPassword();
    List<MailMessage> mail = app.mail().waitForMail(1, 20000);
    String link = app.user().findConfirmationLink(mail, user.getEmail());
    app.user().changePassword(link, user, passwordNew);

    assertTrue(app.newSession().login(user.getName(), passwordNew, true));
  }


  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}