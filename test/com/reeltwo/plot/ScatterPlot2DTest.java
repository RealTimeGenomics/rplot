package com.reeltwo.plot;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * JUnit tests for the ScatterPlot2D class.
 *
 * @author Richard Littin
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


  @Override
  public Plot2D getPlot() {
    return new ScatterPlot2D();
  }


  @Override
  public Plot2D getPlot(AxisSide x, AxisSide y) {
    return new ScatterPlot2D(x, y);
  }


  @Override
  public Datum2D[] getData() {
    return new ScatterPoint2D[]{new ScatterPoint2D(1, 2, 3), new ScatterPoint2D(5, 6, 7)};
  }


  public void test1() {
    final ScatterPlot2D splot = (ScatterPlot2D) getPlot();

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
