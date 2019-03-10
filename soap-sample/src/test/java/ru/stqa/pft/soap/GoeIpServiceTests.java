package ru.stqa.pft.soap;

import com.lavasoft.GeoIPService;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GoeIpServiceTests {

  @Test
  public void testMyIp(){
    String location = new GeoIPService().getGeoIPServiceSoap12().getLocation();
    Assert.assertEquals(location, "<GeoIP><Country>RU</Country><State>65</State></GeoIP>");
  }
}
