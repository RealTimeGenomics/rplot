package com.reeltwo.plot.patterns;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * JUnit tests for the GrayScaleColorGroup class.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */
public class GrayScaleColorGroupTest extends AbstractPatternGroupTest {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public GrayScaleColorGroupTest(String name) {
    super(name);
  }


  public void setUp() {
    super.setUp();
  }


  public void tearDown() {
    super.tearDown();
  }

  public PatternGroup getPatternGroup() {
    return new GrayScaleColorGroup();
  }

  public static Test suite() {
    return new TestSuite(GrayScaleColorGroupTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
