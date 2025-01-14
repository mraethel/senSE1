package org.hbrs.se1.ws24.tests.uebung10;

public class MyPrettyRectangle {
  private double x1; private double y1;
  private double x2; private double y2;

  public MyPrettyRectangle(double x1, double y1,
                           double x2, double y2) {
    this.x1 = x1; this.y1 = y1;
    this.x2 = x2; this.y2 = y2;
  };

  public boolean contains(MyPrettyRectangle other) {
    return this.x1 <= other.x1 && this.y1 <= other.y1 && other.x2 <= this.x2 && other.y2 <= this.y2;
  };

  public MyPoint getCenter() {
    return new MyPoint((x1 + x2) / 2,
                       (y1 + y2) / 2);
  }

  public double getArea() {
    return (x2 - x1) * (y2 - y1);
  }

  public double getPerimeter() {
    return (x2 - x1) * 2
         + (y2 - y1) * 2;
  }

  public MyPrettyRectangle fit(MyPrettyRectangle other) {
    return new MyPrettyRectangle(
        Math.min(this.x1, other.x1), Math.min(this.y1, other.y1),
        Math.max(this.x2, other.x2), Math.max(this.y2, other.y2));
  }

  @Override
  public boolean equals(Object otherObject) {
    if (!(otherObject instanceof MyPrettyRectangle))
      return false;

    MyPrettyRectangle other = (MyPrettyRectangle) otherObject;

    return this.x1 == other.x1 && this.y1 == other.y1
        && this.x2 == other.x2 && this.y2 == other.y2;
  }

  @Override
  public String toString() {
    return "x1: " + x1 + ", " +
           "y1: " + y1 + ", " +
           "x2: " + x2 + ", " +
           "y2: " + y2;
  }
}
