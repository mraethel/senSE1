package org.hbrs.se1.ws24.tests.uebung10;

import java.util.Arrays;
import java.util.List;

public class BoundingBoxFactory {
  public static MyPrettyRectangle getBoundingBox(MyPrettyRectangle[] rectsArray) {
    if (rectsArray == null)
      return null;

    List<MyPrettyRectangle> rects = Arrays.asList(rectsArray);

    if (rects.isEmpty())
      return new MyPrettyRectangle(0, 0, 0, 0);

    return rects
      .stream()
      .reduce(
          new MyPrettyRectangle(Double.MAX_VALUE, Double.MAX_VALUE,
                                Double.MIN_VALUE, Double.MIN_VALUE),
          (bound, rect) -> bound.fit(rect));
  } 
}
