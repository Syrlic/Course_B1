package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class RegisrationHelper extends HelperBase {


  public RegisrationHelper(ApplicationManager app) {
    super(app);
  }

  public void start(String username, String email) {
    wd.get(app.getProperty("web.baseURL")+"/signup_page.php");
    type(By.name("username"), username);
    type((By.name("email")), email);
    click(By.cssSelector("input[value='Зарегистрироваться']"));
  }

  public void finish(String link, String password, String name) {
    wd.get(link);
    type(By.name("realname"), name);
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    click(By.cssSelector("button[type='submit']"));
  }
}
