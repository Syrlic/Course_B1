package ru.stqa.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  Point dot1 = new Point(4, 7);
  Point dot2 = new Point(6, 2);

  @Test
  public void distanceX() {

    Assert.assertEquals(Math.pow(dot2.x - dot1.x, 2), 4);
  }

  @Test
  public void distanceY() {

    Assert.assertEquals(Math.pow(dot2.y - dot1.y, 2), 25);
  }

  @Test
  public void distanceSummTest() {

    Assert.assertEquals((Math.pow(dot2.x - dot1.x, 2)+ Math.pow(dot2.y - dot1.y, 2)), 29);
  }

  @Test
  public void distanceTest1() {

    Assert.assertEquals(dot1.distance(dot2), 5.385164807134504);
  }

  @Test
  public void distanceTest2() {

    Assert.assertEquals(dot1.distance(dot2), dot2.distance(dot1));
  }
}
