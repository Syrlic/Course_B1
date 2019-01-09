package ru.stqa.sandbox;

public class Main {
  public static void main(String[] args) {

    Point dot1 = new Point(3,4);
    Point dot2 = new Point(7, 5);
    System.out.println("Расстояние равно: " + distance(dot1, dot2));

    Point p = new Point(8, 6);
    System.out.println("Расстояние равно: " + p.distance(dot1));
    System.out.println("Расстояние равно: " + p.distance(dot2));
    System.out.println("Расстояние равно: " + dot2.distance(dot1));
  }

  public static double distance(Point p1, Point p2){
    double distX = Math.pow((p2.x-p1.x), 2);
    double distY = Math.pow((p2.y-p1.y), 2);
    double len = Math.sqrt(distX+distY);
    return len;
  }
}
