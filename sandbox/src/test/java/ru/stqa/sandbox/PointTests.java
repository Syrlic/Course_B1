package ru.stqa.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  Point dot1 = new Point(4, 7);
  Point dot2 = new Point(6, 2);
  Point dot3 = new Point(-2, 9);
  Point dot4 = new Point(0, 0);


  @Test
  public void distanceTest1() {

    Assert.assertEquals(dot1.distance(dot2), 5.385164807134504);
  }

  @Test
  public void distanceTest2() {

    Assert.assertEquals(dot2.distance(dot3), 10.63014581273465);
  }

  @Test
  public void distanceTest3() {

    Assert.assertEquals(dot3.distance(dot4), 9.219544457292887);
  }

  @Test
  public void distanceTest4() {

    Assert.assertEquals(dot4.distance(dot2), 6.324555320336759);
  }

  @Test
  public void distanceTest5() {

    Assert.assertEquals(dot1.distance(dot3), 6.324555320336759);
  }

  @Test
  public void distanceTest6() {

    Assert.assertEquals(dot1.distance(dot2), dot2.distance(dot1));
  }
}
