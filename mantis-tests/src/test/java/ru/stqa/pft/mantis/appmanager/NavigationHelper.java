package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class NavigationHelper extends HelperBase {


  public NavigationHelper(ApplicationManager app) {
    super(app);
  }

  public void home() {
    wd.get(app.getProperty("web.baseURL")+"/login.php");
  }

  private void manageMenu() {
    if (isElementPresent(By.cssSelector("div#sidebar"))) {
      click(By.cssSelector("i.menu-icon.fa.fa-gears"));
    }
  }
  public void manageUsersMenu(){
    manageMenu();
    click(By.linkText("Manage Users"));
  }
}
