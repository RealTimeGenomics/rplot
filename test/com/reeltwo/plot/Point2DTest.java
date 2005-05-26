package com.reeltwo.plot;

import junit.framework.Test;

import junit.framework.TestSuite;

/**
 * JUnit tests for the Point2D class.
 *
 * @author Richard Littin (richard@reeltwo.com)
 * @version $Revision$
 */

public class Point2DTest extends AbstractDatum2DTest {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public Point2DTest(String name) {
    super(name);
  }


  public Datum2D getDatum() {
    return new Point2D(1, 2);
  }


  public void test1() {
    float x = 10.0f;
    float y = 111.3f;

    Point2D pt = new Point2D(x, y);
    assertTrue(pt.getX() == x);
    assertTrue(pt.getY() == y);

    x = 123.4f;
    y = 0.987f;
    pt.setX(x);
    pt.setY(y);
    assertTrue(pt.getX() == x);
    assertTrue(pt.getY() == y);

    assertTrue(pt.getXLo() == pt.getXHi());
    assertTrue(pt.getXLo() == pt.getX());
    assertTrue(pt.getYLo() == pt.getYHi());
    assertTrue(pt.getYLo() == pt.getY());

    Point2D pt2 = new Point2D(x, y);
    assertTrue(pt2.getX() == x);
    assertTrue(pt2.getY() == y);

    assertTrue(pt.equals(pt));
    assertTrue(pt != pt2);
    assertTrue(pt.equals(pt2));
  }


  public static Test suite() {
    return new TestSuite(Point2DTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
