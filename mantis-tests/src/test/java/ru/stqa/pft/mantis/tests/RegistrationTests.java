package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.*;

public class RegistrationTests extends TestBase {


    @BeforeMethod
    public void startMailServer () {
      app.mail().start();
    }

    @Test
    public void testRegisration() throws IOException, MessagingException, ServiceException {
      skipIfNotFixedMantis(2);
      long now = System.currentTimeMillis();
      String email = String.format("user%s@localhost.localdomain", now);
  //    String email = String.format("user%s@localhost", now);
      String password = String.format("password%s",now); //пароль для почты
      String user = String.format("user%s",now);
  //    app.james().createUser(user, password);
      app.regisration().start(user, email);
      List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
  //    List<MailMessage> mailMessages = app.james().waitForMail(user, password, 80000);
      String link = app.user().findConfirmationLink(mailMessages, email);
      app.regisration().finish(link, password, user);
      assertTrue(app.newSession().login(user, password, true));
    }



    @AfterMethod(alwaysRun = true)
    public void stopMailServer(){
      app.mail().stop();
    }
  }

