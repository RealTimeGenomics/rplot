package com.reeltwo.plot;

import junit.framework.Test;

import junit.framework.TestSuite;

/**
 * JUnit tests for the Arrow2D class.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */

public class Arrow2DTest extends AbstractDatum2DTest {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public Arrow2DTest(String name) {
    super(name);
  }


  public Datum2D getDatum() {
    return new Arrow2D(1, 2, 3, 4);
  }


  public void test1() {
    float x1 = 10.0f;
    float y1 = 111.3f;
    float x2 = 123.0f;
    float y2 = 1.111f;

    Arrow2D ar = new Arrow2D(x1, y1, x2, y2);
    assertTrue(ar.getX1() == x1);
    assertTrue(ar.getY1() == y1);
    assertTrue(ar.getX2() == x2);
    assertTrue(ar.getY2() == y2);

    assertTrue(ar.getXLo() == ar.getX1());
    assertTrue(ar.getYLo() == ar.getY2());
    assertTrue(ar.getXHi() == ar.getX2());
    assertTrue(ar.getYHi() == ar.getY1());

    x1 = 123.4f;
    y1 = 0.987f;
    ar.setX1(x1);
    ar.setY1(y1);
    assertTrue(ar.getX1() == x1);
    assertTrue(ar.getY1() == y1);
    assertTrue(ar.getX2() == x2);
    assertTrue(ar.getY2() == y2);

    assertTrue(ar.getXLo() == ar.getX2());
    assertTrue(ar.getYLo() == ar.getY1());
    assertTrue(ar.getXHi() == ar.getX1());
    assertTrue(ar.getYHi() == ar.getY2());

    Arrow2D ar2 = new Arrow2D(x1, y1, x2, y2);
    assertTrue(ar2.getX1() == x1);
    assertTrue(ar2.getY1() == y1);
    assertTrue(ar2.getX2() == x2);
    assertTrue(ar2.getY2() == y2);

    assertTrue(ar.equals(ar));
    assertTrue(ar != ar2);
    assertTrue(ar.equals(ar2));
  }

  public void testBadArgs() {
    try {
      new Arrow2D(0, 0, 0, 0);
      fail("Accepted bad arguments");
    } catch (IllegalArgumentException iae) {
      ; // should get here
    }

    Arrow2D ar = new Arrow2D(1, 2, 1, 4);

    try {
      ar.setY2(2);
      fail("Accepted bad arguments");
    } catch (IllegalArgumentException iae) {
      ; // should get here
    }
    try {
      ar.setY1(4);
      fail("Accepted bad arguments");
    } catch (IllegalArgumentException iae) {
      ; // should get here
    }

    ar = new Arrow2D(1, 2, 3, 2);

    try {
      ar.setX2(1);
      fail("Accepted bad arguments");
    } catch (IllegalArgumentException iae) {
      ; // should get here
    }
    try {
      ar.setX1(3);
      fail("Accepted bad arguments");
    } catch (IllegalArgumentException iae) {
      ; // should get here
    }
  }

  public static Test suite() {
    return new TestSuite(Arrow2DTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
