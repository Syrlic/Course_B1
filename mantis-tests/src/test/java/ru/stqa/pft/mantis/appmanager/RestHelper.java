package ru.stqa.pft.mantis.appmanager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import ru.stqa.pft.mantis.model.IssueBugify;
import java.io.IOException;
import java.util.Set;


public class RestHelper {

  private ApplicationManager app;

  public RestHelper(ApplicationManager app) {
    this.app = app;
  }

  public IssueBugify getIssue(int issueId) {
    String s = String.format("http://bugify.stqa.ru/api/issues/%s.json", String.valueOf(issueId));
    String json = RestAssured.get(s).asString();
    JsonElement parse = new JsonParser().parse(json);
    JsonElement issues = parse.getAsJsonObject().get("issues");
    JsonArray jsonArray = issues.getAsJsonArray();
    IssueBugify ib = new IssueBugify();
    Object[] res = jsonArray.get(0).getAsJsonObject().entrySet().toArray();
    for (Object o : res) {
      String[] value = String.valueOf(o).split("=");
      switch (value[0]) {
        case ("id"):
          ib.withId(Integer.parseInt(value[1]));
        case ("state_name"):
          ib.withStatus(value[1]);
        case ("subject"):
          ib.withSubject(value[1]);
        case ("description"):
          ib.withDescription(value[1]);
      }
    }
    System.out.println(ib);
    return ib;

  }

  public int createIssue(IssueBugify issue) throws IOException {
    String json = RestAssured.given()
            .param("subject", issue.getSubject())
            .param("description", issue.getDescription())
            .param("state_name", issue.getStatus())
            .post("http://bugify.stqa.ru/api/issues.json").asString();
    JsonElement parse = new JsonParser().parse(json);

    return parse.getAsJsonObject().get("issue_id").getAsInt();

  }

  public Set<IssueBugify> getIssues() throws IOException {
    String json = RestAssured.get("http://bugify.stqa.ru/api/issues.json?page=5&limit=500").asString();
    JsonElement parse = new JsonParser().parse(json);
    JsonElement issues = parse.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues, new TypeToken<Set<IssueBugify>>() {
    }.getType());
    //http://bugify.stqa.ru/api/issues.json
  }
}
