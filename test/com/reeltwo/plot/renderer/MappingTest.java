package com.reeltwo.plot.renderer;

import junit.framework.Test;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit tests for the Mapping class.
 *
 * @author Richard Littin
 */

public class MappingTest extends TestCase {

  /** world co-ordinatess */
  private float mWl, mWr;

  /** screen co-ordinatess */
  private float mSl, mSr;


  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public MappingTest(String name) {
    super(name);
  }


  @Override
  public void setUp() {
    mWl = -10.0f;
    mWr = 10.0f;
    mSl = 0.0f;
    mSr = 1.0f;
  }

  public void test1() {
    final Mapping map = new Mapping(mWl, mWr, mSl, mSr);

    final float wpt = 5.0f;
    final float spt = 0.75f;

    assertEquals(spt, map.worldToScreen(wpt), 0.0001f);
    assertEquals(wpt, map.screenToWorld(spt), 0.0001f);
  }

  public void testContructor() {
    try {
      new Mapping(0, 0, 1, 2);
      fail("accepted bad parameters");
    } catch (final IllegalArgumentException iae) {
      ; // expected
    }

    try {
      new Mapping(1, 2, 1, 1);
      fail("accepted bad parameters");
    } catch (final IllegalArgumentException iae) {
      ; // expected
    }
  }


  public static Test suite() {
    return new TestSuite(MappingTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
