package com.reeltwo.plot.patterns;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Test class for all tests in this directory. Run from the command
 * line with:<p>
 *
 * java com.reeltwo.plot.AllTests
 *
 * @author <a href="mailto:rlittin@reeltwo.com">Richard Littin</a>
 */
public class AllTests extends TestSuite {

  public static Test suite() {
    final TestSuite suite = new TestSuite();

    suite.addTest(BW8x8PatternGroupTest.suite());
    suite.addTest(DefaultColorGroupTest.suite());
    suite.addTest(GrayScaleColorGroupTest.suite());

    return suite;
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
}
