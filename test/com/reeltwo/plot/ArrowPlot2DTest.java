package com.reeltwo.plot;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * JUnit tests for the ArrowPlot2D class.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */

public class ArrowPlot2DTest extends AbstractPlot2DTest {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public ArrowPlot2DTest(String name) {
    super(name);
  }


  public Plot2D getPlot() {
    return new ArrowPlot2D();
  }


  public Plot2D getPlot(int x, int y) {
    return new ArrowPlot2D(x, y);
  }


  public Datum2D[] getData() {
    return new Arrow2D[]{new Arrow2D(1, 2, 3, 4), new Arrow2D(5, 6, 7, 8)};
  }


  public void test1() {
    ArrowPlot2D plot = (ArrowPlot2D) getPlot();

    assertTrue(4.0f == plot.getHeadWidth());
    plot.setHeadWidth(34.9f);
    assertTrue(34.9f == plot.getHeadWidth());

    assertTrue(8.0f == plot.getHeadHeight());
    plot.setHeadHeight(34.9f);
    assertTrue(34.9f == plot.getHeadHeight());

    assertTrue(ArrowPlot2D.DEFAULT_HEAD == ArrowPlot2D.OPEN_HEAD);
    assertTrue(ArrowPlot2D.DEFAULT_HEAD == plot.getHeadType());
    plot.setHeadType(ArrowPlot2D.OPEN_HEAD);
    assertTrue(ArrowPlot2D.OPEN_HEAD == plot.getHeadType());
    plot.setHeadType(ArrowPlot2D.ARROW_HEAD);
    assertTrue(ArrowPlot2D.ARROW_HEAD == plot.getHeadType());
    plot.setHeadType(ArrowPlot2D.TRIANGLE_HEAD);
    assertTrue(ArrowPlot2D.TRIANGLE_HEAD == plot.getHeadType());
    plot.setHeadType(ArrowPlot2D.DIAMOND_HEAD);
    assertTrue(ArrowPlot2D.DIAMOND_HEAD == plot.getHeadType());
    plot.setHeadType(ArrowPlot2D.DEFAULT_HEAD);
    assertTrue(ArrowPlot2D.DEFAULT_HEAD == plot.getHeadType());

    assertTrue(ArrowPlot2D.DEFAULT_MODE == ArrowPlot2D.FORWARD_MODE);
    assertTrue(ArrowPlot2D.BOTH_MODE == (ArrowPlot2D.FORWARD_MODE | ArrowPlot2D.REVERSE_MODE));
    assertTrue(ArrowPlot2D.DEFAULT_MODE == plot.getMode());
    plot.setMode(ArrowPlot2D.FORWARD_MODE);
    assertTrue(ArrowPlot2D.FORWARD_MODE == plot.getMode());
    plot.setMode(ArrowPlot2D.REVERSE_MODE);
    assertTrue(ArrowPlot2D.REVERSE_MODE == plot.getMode());
    plot.setMode(ArrowPlot2D.BOTH_MODE);
    assertTrue(ArrowPlot2D.BOTH_MODE == plot.getMode());
    plot.setMode(ArrowPlot2D.DEFAULT_MODE);
    assertTrue(ArrowPlot2D.DEFAULT_MODE == plot.getMode());

    assertTrue(34.9f == plot.getHeadWidth());
    assertTrue(34.9f == plot.getHeadHeight());
    assertTrue(ArrowPlot2D.DEFAULT_HEAD == plot.getHeadType());
    assertTrue(ArrowPlot2D.DEFAULT_MODE == plot.getMode());
  }

  public void testBadArguments() {
    ArrowPlot2D plot = (ArrowPlot2D) getPlot();
    try {
      plot.setHeadWidth(-1.0f);
      fail("Accepted bad argumant.");
    } catch (IllegalArgumentException iae) {
      ; // should get here
    }
    try {
      plot.setHeadHeight(-1.0f);
      fail("Accepted bad argumant.");
    } catch (IllegalArgumentException iae) {
      ; // should get here
    }
    try {
      plot.setHeadType(ArrowPlot2D.OPEN_HEAD - 1);
      fail("Accepted bad argumant.");
    } catch (IllegalArgumentException iae) {
      ; // should get here
    }
    try {
      plot.setHeadType(ArrowPlot2D.DIAMOND_HEAD + 1);
      fail("Accepted bad argumant.");
    } catch (IllegalArgumentException iae) {
      ; // should get here
    }
    try {
      plot.setMode(ArrowPlot2D.FORWARD_MODE - 1);
      fail("Accepted bad argumant.");
    } catch (IllegalArgumentException iae) {
      ; // should get here
    }
    try {
      plot.setMode(ArrowPlot2D.BOTH_MODE + 1);
      fail("Accepted bad argumant.");
    } catch (IllegalArgumentException iae) {
      ; // should get here
    }
  }


  public static Test suite() {
    return new TestSuite(ArrowPlot2DTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
