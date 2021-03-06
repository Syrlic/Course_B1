package ru.stqa.pft.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public class RegistrationTests extends TestBase {


  @BeforeMethod
  public void startMailServer () {
    app.mail().start();
  }

  @Test
  public void testRegisration() throws IOException, MessagingException {
    long now = System.currentTimeMillis();
    String email = String.format("user%s@localhost.localdomain", now);
    String password = String.format("password%s",now);
    String user = String.format("user%s",now);
  //  app.james().createUser(user, password);
    app.regisration().start(user, email);
    List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
//    List<MailMessage> mailMessages = app.james().waitForMail(user, password, 80000);
    String link = findConfirmationLink(mailMessages, email);
    app.regisration().finish(link, password, user);

    Assert.assertTrue(app.newSession().login(user, password));
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage message = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(message.text);
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer(){
    app.mail().stop();
  }
}
