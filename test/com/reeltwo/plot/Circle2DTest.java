package com.reeltwo.plot;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * JUnit tests for the Circle2D class.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */

public class Circle2DTest extends AbstractDatum2DTest {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public Circle2DTest(String name) {
    super(name);
  }


  public Datum2D getDatum() {
    return new Circle2D(1, 2, 3);
  }


  public void test1() {
    Circle2D cpoint = new Circle2D(1, 2, 3);
    assertTrue(cpoint instanceof Point2D);
    assertTrue(3 == cpoint.getDiameter());

    cpoint.setDiameter(5);
    assertTrue(5 == cpoint.getDiameter());
  }

  public void testBadArgs() {
    try {
      Circle2D c = new Circle2D(1, 2, -1.0f);
      fail("accepted bad diameter");
    } catch (IllegalArgumentException e) {
      ; // expected
    }
    try {
      Circle2D c = new Circle2D(1, 2, 1.0f);
      c.setDiameter(0.0f);
      fail("accepted bad diameter");
    } catch (IllegalArgumentException e) {
      ; // expected
    }
  }

  public static Test suite() {
    return new TestSuite(Circle2DTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
