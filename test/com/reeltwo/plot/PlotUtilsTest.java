package com.reeltwo.plot;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit tests for the PlotUtils class.
 *
 * @author Richard Littin
 */

public class PlotUtilsTest extends TestCase {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public PlotUtilsTest(String name) {
    super(name);
  }

  public void testFloor10() {
    assertTrue(10.0 == PlotUtils.floor10(10));
    assertTrue(0.0 == PlotUtils.floor10(0.0));
    assertTrue(-0.0 == PlotUtils.floor10(-0.0));
    assertTrue(Double.isNaN(PlotUtils.floor10(-0.1)));
    assertTrue(Double.isNaN(PlotUtils.floor10(-10.0)));
    assertTrue(Double.isNaN(PlotUtils.floor10(Double.NaN)));

    assertTrue(1.0 == PlotUtils.floor10(2));
    assertTrue(100.0 == PlotUtils.floor10(123));
    assertTrue(0.1 == PlotUtils.floor10(0.5));
  }


  public void testCeil10() {
    assertTrue(10.0 == PlotUtils.ceil10(10));
    assertTrue(0.0 == PlotUtils.ceil10(0.0));
    assertTrue(-0.0 == PlotUtils.ceil10(-0.0));
    assertTrue(Double.isNaN(PlotUtils.ceil10(-0.1)));
    assertTrue(Double.isNaN(PlotUtils.ceil10(-10.0)));
    assertTrue(Double.isNaN(PlotUtils.ceil10(Double.NaN)));

    assertTrue(10.0 == PlotUtils.ceil10(2));
    assertTrue(1000.0 == PlotUtils.ceil10(123));
    assertTrue(1.0 == PlotUtils.ceil10(0.5));
  }


  public static Test suite() {
    return new TestSuite(PlotUtilsTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
