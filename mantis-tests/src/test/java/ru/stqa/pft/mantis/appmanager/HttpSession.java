package ru.stqa.pft.mantis.appmanager;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

  public class HttpSession {
    private CloseableHttpClient httpClient;
    private ApplicationManager app;

    public HttpSession(ApplicationManager app) {
      this.app = app;
      //создание новой сессии для работы по протоколу HTTP
      httpClient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
    }

    public boolean login(String username, String password) throws IOException {
      //создание запроса с параметрами(пустой)
      HttpPost post = new HttpPost(app.getProperty("web.baseURL")+"/login.php");
      //создание параметров запроса
      List<NameValuePair> params = new ArrayList<>();
      params.add(new BasicNameValuePair("username", username));
      params.add(new BasicNameValuePair("password", password));
      params.add(new BasicNameValuePair("secure_session", "on"));
      params.add(new BasicNameValuePair("return", "index.php"));
      //параметры устанавливаются в запрос
      post.setEntity(new UrlEncodedFormEntity(params));
      //выполнение запроса
      CloseableHttpResponse response = httpClient.execute(post);
      //получение данных страницы из ответа
      String body = getTextFrom(response);
      //return body.contains(String.format("<span class=\"label hidden-xs label-default arrowed\">%s</span>", "reporter"));
      return body.contains(String.format("<a href=\"/mantisbt-2.19.0/account_page.php\">%s ( %s ) </a>", username, username));
    }

    private String getTextFrom(CloseableHttpResponse response) throws IOException {
      try {
        return EntityUtils.toString(response.getEntity());
      }finally {
        response.close();
      }
    }

    public boolean isLoggedInAs(String username) throws IOException {
      //создание запроса без параметров(пустой)
      HttpGet get = new HttpGet(app.getProperty("web.baseURL")+"/login.php");
      CloseableHttpResponse response = httpClient.execute(get);
      String body = getTextFrom(response);
      return body.contains(String.format("<span class=\"label hidden-xs label-default arrowed\">%s</span>", username));
    }
  }
