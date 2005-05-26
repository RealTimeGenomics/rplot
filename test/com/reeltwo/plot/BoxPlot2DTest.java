package com.reeltwo.plot;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * JUnit tests for the BoxPlot2D class.
 *
 * @author Richard Littin (richard@reeltwo.com)
 * @version $Revision$
 */
public class BoxPlot2DTest extends AbstractFillablePlot2DTest {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public BoxPlot2DTest(String name) {
    super(name);
  }


  public Plot2D getPlot() {
    return new BoxPlot2D();
  }


  public Plot2D getPlot(int x, int y) {
    return new BoxPlot2D(x, y);
  }


  public Datum2D[] getData() {
    return new Box2D[]{new Box2D(1, 2, 3, 4), new Box2D(5, 6, 7, 8)};
  }


  public static Test suite() {
    return new TestSuite(BoxPlot2DTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
