package com.reeltwo.plot;

import junit.framework.Test;

import junit.framework.TestSuite;

/**
 * JUnit tests for the PointPlot2D class.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */

public class CurvePlot2DTest extends AbstractPlot2DTest {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public CurvePlot2DTest(String name) {
    super(name);
  }


  public Plot2D getPlot() {
    return new CurvePlot2D();
  }


  public Plot2D getPlot(int x, int y) {
    return new CurvePlot2D(x, y);
  }


  public Datum2D[] getData() {
    return new Point2D[]{new Point2D(1, 2), new Point2D(5, 6)};
  }


  public void test1() {
    CurvePlot2D plot = (CurvePlot2D) getPlot();
    assertTrue(!plot.getFill());
    plot.setFill(false);
    assertTrue(!plot.getFill());
    plot.setFill(true);
    assertTrue(plot.getFill());

    assertEquals(CurvePlot2D.BSPLINE, plot.getType());
    plot.setType(CurvePlot2D.BEZIER);
    assertEquals(CurvePlot2D.BEZIER, plot.getType());
    plot.setType(CurvePlot2D.CUBIC_BEZIER);
    assertEquals(CurvePlot2D.CUBIC_BEZIER, plot.getType());
    plot.setType(CurvePlot2D.BSPLINE);
    assertEquals(CurvePlot2D.BSPLINE, plot.getType());
  }

  public void testBadArgs() {
    CurvePlot2D plot = (CurvePlot2D) getPlot();
    try {
      plot.setType(CurvePlot2D.BSPLINE - 1);
      fail("accepted bad type");
    } catch (IllegalArgumentException e) {
      ; // expected
    }
    try {
      plot.setType(CurvePlot2D.CUBIC_BEZIER + 1);
      fail("accepted bad type");
    } catch (IllegalArgumentException e) {
      ; // expected
    }
  }

  public static Test suite() {
    return new TestSuite(PointPlot2DTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
