package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserHelper extends HelperBase {

  public UserHelper(ApplicationManager app) {
    super(app);
  }

  public void login(String username, String password) {
    type(By.id("username"), username);
    click(By.cssSelector("input[type='submit']"));
    type(By.id("password"), password);
    click(By.cssSelector("input[type='submit']"));
  }

  public User chooseUser() { //выбрать одного пользователя из списка кроме администратора
    Set<User> users = app.db().allUsersFromDB();
    User user;
    if (users.size() > 1) {
      user = users.iterator().next();
      int id = user.getId();
      while (id == 1) {
        user = users.iterator().next();
        id = user.getId();
      }
      click(By.cssSelector("a[href='manage_user_edit_page.php?user_id="+id+"']"));
      return user;
    }
    return null;
  }

  public String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage message = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(message.text);
  }
  public Set<User> allUsers() {
    Set<User> users = new HashSet<>();
    List<WebElement> element = wd.findElements(By.xpath(
            "//table[@class='table table-striped table-bordered table-condensed table-hover'//tbody//tr"));
    if (!element.isEmpty()) {
      System.out.println("LIST USERS FROM WD:");
      for (WebElement we : element) {
        List<WebElement> cells = we.findElements(By.tagName("td"));
        String email = cells.get(2).getText();
        String name = cells.get(0).getText();
        int id = Integer.parseInt(cells.get(0).getAttribute("href").split("=")[1]);
        User user = new User().setEmail(email).setName(name).setId(id);
        users.add(user);
        System.out.println(user);
        }
      }
        return users;
      }

  public void resetPassword() {
    click(By.cssSelector("input[value='Reset Password'"));
  }

  public void changePassword(String link, User user, String passwordNew) {
    wd.get(link);
    type(By.name("realname"), user.getName());
    type(By.name("password"), passwordNew);
    type(By.name("password_confirm"), passwordNew);
    click(By.cssSelector("button[type='submit']"));
  }
}

