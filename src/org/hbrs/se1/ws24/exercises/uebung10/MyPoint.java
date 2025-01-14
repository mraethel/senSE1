package org.hbrs.se1.ws24.tests.uebung10;

public class MyPoint {
  private double x; private double y;
  
  public MyPoint(double x, double y) { this.x = x; this.y = y; }

  @Override
  public boolean equals(Object otherObject) {
    if (!(otherObject instanceof MyPoint))
      return false;

    MyPoint other = (MyPoint) otherObject;

    return this.x == other.x
        && this.y == other.y;
  }
}
