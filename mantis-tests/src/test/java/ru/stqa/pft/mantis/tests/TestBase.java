package ru.stqa.pft.mantis.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import ru.stqa.pft.mantis.appmanager.ApplicationManager;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.IssueBugify;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class TestBase {

  public static final ApplicationManager app
          = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));


  @BeforeSuite(alwaysRun = true)
  public void setUp() throws Exception {
    app.init();
    app.ftp().upload(new File("src/test/resources/config_inc.php"),
            "config/config_inc.php", "config/config_inc.php.bak");
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws Exception {
    app.ftp().restore("config/config_inc.php.bak", "config/config_inc.php");
    app.stop();
  }

  public boolean isIssueOpenMantis(int issueId) throws MalformedURLException, RemoteException, ServiceException {
    Issue issue = app.soap().getIssue(issueId);
    if(issue.getStatus().equals("resolved") || issue.getStatus().equals("closed")
               && issue.getResolution().equals("fixed")){
      return false;
    }
    return true;

  }

  public void skipIfNotFixedMantis(int issueId) throws MalformedURLException, ServiceException, RemoteException {
    if (isIssueOpenMantis(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }

  public boolean isIssueOpenBugify(int issueId) {
    IssueBugify issue = app.rest().getIssue(issueId);
    String status = issue.getStatus().replaceAll("\"", "");
    if(status.equals("Resolved") || status.equals("Closed")){
      return false;
    }
    return true;

  }

  public void skipIfNotFixedBugify(int issueId) {
    if (isIssueOpenBugify(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }
}
