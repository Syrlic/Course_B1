package ru.stqa.sandbox;

public class Point {
  public double x;
  public double y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public double distance(Point p) {
    double distX = Math.pow((p.x-this.x), 2);
    double distY = Math.pow((p.y-this.y), 2);
    double len = Math.sqrt(distX+distY);
    return len;
  }

}
