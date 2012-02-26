package com.reeltwo.plot;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.reeltwo.plot.ArrowPlot2D.ArrowDirection;
import com.reeltwo.plot.ArrowPlot2D.ArrowHead;

/**
 * JUnit tests for the ArrowPlot2D class.
 *
 * @author Richard Littin
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


  @Override
  public Plot2D getPlot() {
    return new ArrowPlot2D();
  }


  @Override
  public Plot2D getPlot(Edge x, Edge y) {
    return new ArrowPlot2D(x, y);
  }


  @Override
  public Datum2D[] getData() {
    return new Arrow2D[]{new Arrow2D(1, 2, 3, 4), new Arrow2D(5, 6, 7, 8)};
  }


  public void test1() {
    final ArrowPlot2D plot = (ArrowPlot2D) getPlot();

    assertTrue(4.0f == plot.getHeadWidth());
    plot.setHeadWidth(34.9f);
    assertTrue(34.9f == plot.getHeadWidth());

    assertTrue(8.0f == plot.getHeadHeight());
    plot.setHeadHeight(34.9f);
    assertTrue(34.9f == plot.getHeadHeight());

    assertEquals(ArrowHead.OPEN, ArrowPlot2D.DEFAULT_HEAD);
    assertTrue(ArrowPlot2D.DEFAULT_HEAD == plot.getHeadType());
    plot.setHeadType(ArrowHead.OPEN);
    assertTrue(ArrowHead.OPEN == plot.getHeadType());
    plot.setHeadType(ArrowHead.ARROW);
    assertTrue(ArrowHead.ARROW == plot.getHeadType());
    plot.setHeadType(ArrowHead.TRIANGLE);
    assertTrue(ArrowHead.TRIANGLE == plot.getHeadType());
    plot.setHeadType(ArrowHead.DIAMOND);
    assertTrue(ArrowHead.DIAMOND == plot.getHeadType());
    plot.setHeadType(ArrowPlot2D.DEFAULT_HEAD);
    assertTrue(ArrowPlot2D.DEFAULT_HEAD == plot.getHeadType());

    assertEquals(ArrowDirection.FORWARD, ArrowPlot2D.DEFAULT_DIRECTION);
    assertTrue(ArrowPlot2D.DEFAULT_DIRECTION == plot.getDirection());
    plot.setDirection(ArrowDirection.FORWARD);
    assertTrue(ArrowDirection.FORWARD == plot.getDirection());
    plot.setDirection(ArrowDirection.REVERSE);
    assertTrue(ArrowDirection.REVERSE == plot.getDirection());
    plot.setDirection(ArrowDirection.BOTH);
    assertTrue(ArrowDirection.BOTH == plot.getDirection());
    plot.setDirection(ArrowPlot2D.DEFAULT_DIRECTION);
    assertTrue(ArrowPlot2D.DEFAULT_DIRECTION == plot.getDirection());

    assertTrue(34.9f == plot.getHeadWidth());
    assertTrue(34.9f == plot.getHeadHeight());
    assertTrue(ArrowPlot2D.DEFAULT_HEAD == plot.getHeadType());
    assertTrue(ArrowPlot2D.DEFAULT_DIRECTION == plot.getDirection());
  }

  public void testBadArguments() {
    final ArrowPlot2D plot = (ArrowPlot2D) getPlot();
    try {
      plot.setHeadWidth(-1.0f);
      fail("Accepted bad argumant.");
    } catch (final IllegalArgumentException iae) {
      ; // should get here
    }
    try {
      plot.setHeadHeight(-1.0f);
      fail("Accepted bad argumant.");
    } catch (final IllegalArgumentException iae) {
      ; // should get here
    }
  }

  public void testArrowHeads() {
    // some of the rendering math relies on these values
    assertEquals(0, ArrowHead.OPEN.ordinal());
    assertEquals(1, ArrowHead.ARROW.ordinal());
    assertEquals(2, ArrowHead.TRIANGLE.ordinal());
    assertEquals(3, ArrowHead.DIAMOND.ordinal());
  }


  public static Test suite() {
    return new TestSuite(ArrowPlot2DTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
