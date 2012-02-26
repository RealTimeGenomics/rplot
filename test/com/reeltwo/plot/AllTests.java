package com.reeltwo.plot;

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

    suite.addTest(Arrow2DTest.suite());
    suite.addTest(ArrowPlot2DTest.suite());
    suite.addTest(Box2DTest.suite());
    suite.addTest(BoxPlot2DTest.suite());
    suite.addTest(BWPoint2DTest.suite());
    suite.addTest(BWPlot2DTest.suite());
    suite.addTest(Circle2DTest.suite());
    suite.addTest(CirclePlot2DTest.suite());
    suite.addTest(CurvePlot2DTest.suite());
    suite.addTest(DefaultFormatterTest.suite());
    suite.addTest(Graph2DTest.suite());
    suite.addTest(PlotUtilsTest.suite());
    suite.addTest(Point2DTest.suite());
    suite.addTest(PointPlot2DTest.suite());
    suite.addTest(TextPlot2DTest.suite());
    suite.addTest(TextPoint2DTest.suite());
    suite.addTest(ScatterPlot2DTest.suite());
    suite.addTest(ScatterPoint2DTest.suite());

    suite.addTest(com.reeltwo.plot.patterns.AllTests.suite());
    suite.addTest(com.reeltwo.plot.renderer.AllTests.suite());

    return suite;
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
}
