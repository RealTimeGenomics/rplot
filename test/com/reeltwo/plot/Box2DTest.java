package com.reeltwo.plot;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * JUnit tests for the Box2D class.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */

public class Box2DTest extends AbstractDatum2DTest {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public Box2DTest(String name) {
    super(name);
  }


  public Datum2D getDatum() {
    return new Box2D(1, 2, 3, 4);
  }


  public void test1() {
    float l = -1.23f;
    float r = 345.6f;
    float b = 7.89f;
    float t = 123.4f;

    Box2D box = new Box2D(0, 0, 0, 0);
    assertNotNull(box);
    assertTrue(box.getLeft() == 0);
    assertTrue(box.getRight() == 0);
    assertTrue(box.getTop() == 0);
    assertTrue(box.getBottom() == 0);

    box.setXRange(l, r);
    assertTrue(box.getLeft() == l);
    assertTrue(box.getRight() == r);
    assertTrue(box.getTop() == 0);
    assertTrue(box.getBottom() == 0);

    box.setXRange(r, l);
    assertTrue(box.getLeft() == l);
    assertTrue(box.getRight() == r);
    assertTrue(box.getTop() == 0);
    assertTrue(box.getBottom() == 0);

    box.setYRange(b, t);
    assertTrue(box.getLeft() == l);
    assertTrue(box.getRight() == r);
    assertTrue(box.getTop() == t);
    assertTrue(box.getBottom() == b);

    box.setYRange(t, b);
    assertTrue(box.getLeft() == l);
    assertTrue(box.getRight() == r);
    assertTrue(box.getTop() == t);
    assertTrue(box.getBottom() == b);

    box = new Box2D(l, b, r, t);
    assertNotNull(box);
    assertTrue(box.getLeft() == l);
    assertTrue(box.getRight() == r);
    assertTrue(box.getTop() == t);
    assertTrue(box.getBottom() == b);

    box = new Box2D(r, t, l, b);
    assertNotNull(box);
    assertTrue(box.getLeft() == l);
    assertTrue(box.getRight() == r);
    assertTrue(box.getTop() == t);
    assertTrue(box.getBottom() == b);

    assertTrue(box.getXLo() == l);
    assertTrue(box.getXHi() == r);
    assertTrue(box.getYLo() == b);
    assertTrue(box.getYHi() == t);
  }


  public static Test suite() {
    return new TestSuite(Box2DTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
