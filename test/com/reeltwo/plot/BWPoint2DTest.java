package com.reeltwo.plot;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * JUnit tests for the BWPoint2D class.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */

public class BWPoint2DTest extends AbstractDatum2DTest {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public BWPoint2DTest(String name) {
    super(name);
  }


  public Datum2D getDatum() {
    return new BWPoint2D(1, 2, 3, 4, 5, 6);
  }


  public void test1() {
    float x = 10.0f;

    float ymin = 111.3f;
    float ylq = 112.3f;
    float ymed = 113.3f;
    float yuq = 114.3f;
    float ymax = 115.3f;

    BWPoint2D pt = new BWPoint2D(x, ymin, ylq, ymed, yuq, ymax);
    assertTrue(pt.getX() == x);
    assertTrue(pt.getY(0) == ymin);
    assertTrue(pt.getY(1) == ylq);
    assertTrue(pt.getY(2) == ymed);
    assertTrue(pt.getY(3) == yuq);
    assertTrue(pt.getY(4) == ymax);

    x = 123.4f;
    ymin = 211.3f;
    ylq = 212.3f;
    ymed = 213.3f;
    yuq = 214.3f;
    ymax = 215.3f;

    pt.setX(x);
    pt.setY(4, ymax);
    pt.setY(3, yuq);
    pt.setY(2, ymed);
    pt.setY(1, ylq);
    pt.setY(0, ymin);

    assertTrue(pt.getX() == x);
    assertTrue(pt.getY(0) == ymin);
    assertTrue(pt.getY(1) == ylq);
    assertTrue(pt.getY(2) == ymed);
    assertTrue(pt.getY(3) == yuq);
    assertTrue(pt.getY(4) == ymax);

    assertTrue(pt.getXLo() == pt.getXHi());
    assertTrue(pt.getXLo() == pt.getX());
    assertTrue(pt.getYLo() == pt.getY(0));
    assertTrue(pt.getYHi() == pt.getY(4));

    BWPoint2D pt2 = new BWPoint2D(x, ymin, ylq, ymed, yuq, ymax);

    assertTrue(pt2.getX() == x);
    assertTrue(pt2.getY(0) == ymin);
    assertTrue(pt2.getY(1) == ylq);
    assertTrue(pt2.getY(2) == ymed);
    assertTrue(pt2.getY(3) == yuq);
    assertTrue(pt2.getY(4) == ymax);

    assertTrue(pt.equals(pt));
    assertTrue(pt != pt2);
    assertTrue(pt.equals(pt2));

    pt.setY(4, 0.0f);
    assertTrue(!pt.equals(pt2));
  }

  public void testToString() {
    assertEquals("(1.0,2.0,3.0,4.0,5.0,6.0)", getDatum().toString());
  }


  public static Test suite() {
    return new TestSuite(BWPoint2DTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
