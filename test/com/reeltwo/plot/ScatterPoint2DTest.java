package com.reeltwo.plot;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * JUnit tests for the ScatterPoint2D class.
 *
 * @author Richard Littin
 */

public class ScatterPoint2DTest extends AbstractDatum2DTest {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public ScatterPoint2DTest(String name) {
    super(name);
  }


  @Override
  public Datum2D getDatum() {
    return new ScatterPoint2D(1, 2, 3);
  }


  public void test1() {
    final ScatterPoint2D spoint = new ScatterPoint2D(1, 2, 3);
    assertTrue(spoint instanceof Point2D);
    assertTrue(3 == spoint.getNumberOfPoints());

    spoint.setNumberOfPoints(5);
    assertTrue(5 == spoint.getNumberOfPoints());
  }

  public void testBadArgs() {
    try {
      new ScatterPoint2D(1, 2, -1);
      fail("accepted bad number of points");
    } catch (final IllegalArgumentException e) {
      ; // expected
    }
    try {
      final ScatterPoint2D s = new ScatterPoint2D(1, 2, 1);
      s.setNumberOfPoints(0);
      fail("accepted bad number of points");
    } catch (final IllegalArgumentException e) {
      ; // expected
    }
  }

  public static Test suite() {
    return new TestSuite(ScatterPoint2DTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
