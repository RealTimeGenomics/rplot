package com.reeltwo.plot;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * JUnit tests for the ScatterPlot2D class.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */

public class ScatterPlot2DTest extends AbstractPlot2DTest {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public ScatterPlot2DTest(String name) {
    super(name);
  }


  public Plot2D getPlot() {
    return new ScatterPlot2D();
  }


  public Plot2D getPlot(int x, int y) {
    return new ScatterPlot2D(x, y);
  }


  public Datum2D[] getData() {
    return new ScatterPoint2D[]{new ScatterPoint2D(1, 2, 3), new ScatterPoint2D(5, 6, 7)};
  }


  public void test1() {
    ScatterPlot2D splot = (ScatterPlot2D) getPlot();

    assertTrue(0.0f == splot.getScatterFactor());
    splot.setScatterFactor(10.0f);
    assertTrue(10.0f == splot.getScatterFactor());
    splot.setScatterFactor(-12.3f);
    assertTrue(12.3f == splot.getScatterFactor());
  }


  public static Test suite() {
    return new TestSuite(ScatterPlot2DTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
