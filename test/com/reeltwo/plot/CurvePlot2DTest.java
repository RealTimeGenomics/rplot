package com.reeltwo.plot;

import junit.framework.Test;

import junit.framework.TestSuite;

/**
 * JUnit tests for the PointPlot2D class.
 *
 * @author Richard Littin
 */

public class CurvePlot2DTest extends AbstractFillablePlot2DTest {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public CurvePlot2DTest(String name) {
    super(name);
  }


  @Override
  public Plot2D getPlot() {
    return new CurvePlot2D();
  }


  @Override
  public Plot2D getPlot(int x, int y) {
    return new CurvePlot2D(x, y);
  }


  @Override
  public Datum2D[] getData() {
    return new Point2D[]{new Point2D(1, 2), new Point2D(5, 6)};
  }


  public void testCurves() {
    final CurvePlot2D plot = (CurvePlot2D) getPlot();
    assertEquals(CurvePlot2D.BSPLINE, plot.getType());
    plot.setType(CurvePlot2D.BEZIER);
    assertEquals(CurvePlot2D.BEZIER, plot.getType());
    plot.setType(CurvePlot2D.CUBIC_BEZIER);
    assertEquals(CurvePlot2D.CUBIC_BEZIER, plot.getType());
    plot.setType(CurvePlot2D.BSPLINE);
    assertEquals(CurvePlot2D.BSPLINE, plot.getType());
  }

  public void testBadArgs() {
    final CurvePlot2D plot = (CurvePlot2D) getPlot();
    try {
      plot.setType(CurvePlot2D.BSPLINE - 1);
      fail("accepted bad type");
    } catch (final IllegalArgumentException e) {
      ; // expected
    }
    try {
      plot.setType(CurvePlot2D.CUBIC_BEZIER + 1);
      fail("accepted bad type");
    } catch (final IllegalArgumentException e) {
      ; // expected
    }
  }

  public static Test suite() {
    return new TestSuite(CurvePlot2DTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
