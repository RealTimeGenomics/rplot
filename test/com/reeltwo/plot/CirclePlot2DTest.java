package com.reeltwo.plot;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * JUnit tests for the CirclePlot2D class.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */

public class CirclePlot2DTest extends AbstractFillablePlot2DTest {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public CirclePlot2DTest(String name) {
    super(name);
  }


  public Plot2D getPlot() {
    return new CirclePlot2D();
  }


  public Plot2D getPlot(int x, int y) {
    return new CirclePlot2D(x, y);
  }


  public Datum2D[] getData() {
    return new Circle2D[]{new Circle2D(1, 2, 3), new Circle2D(5, 6, 7)};
  }


  public static Test suite() {
    return new TestSuite(CirclePlot2DTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
