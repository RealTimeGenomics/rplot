package com.reeltwo.plot.renderer;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Test class for all tests in this directory. Run from the command
 * line with:<p>
 *
 * java com.reeltwo.plot.AllTests
 *
 * @author <a href="mailto:rlittin@reeltwo.com">Richard Littin</a>
 * @version $Revision$
 */
public class AllTests extends TestSuite {

  public static Test suite() {
    TestSuite suite = new TestSuite();

    suite.addTest(GraphicsRendererTest.suite());
    suite.addTest(ImageWriterTest.suite());
    suite.addTest(MappingTest.suite());
    suite.addTest(TextRendererTest.suite());
    return suite;
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
}
