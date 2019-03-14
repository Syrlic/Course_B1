package ru.stqa.pft.mantis.tests;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.IssueBugify;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.*;


public class RestTests extends TestBase{

  @BeforeClass
  public void init()
  {
    RestAssured.authentication = RestAssured.basic("288f44776e7bec4bf44fdfeb1e646490", "");
  }

  @Test
  public void testCreateIssue() throws IOException {
    skipIfNotFixedBugify(730);//525  //730
    Set<IssueBugify> oldIssues = app.rest().getIssues();
    IssueBugify issueNew = new IssueBugify().withSubject("Test ISSUE").withDescription("Test discription issue");
    int issueId = app.rest().createIssue(issueNew);
    Set<IssueBugify> newIssues = app.rest().getIssues();
    oldIssues.add(issueNew.withId(issueId));
    assertEquals(newIssues, oldIssues);
  }
  }
