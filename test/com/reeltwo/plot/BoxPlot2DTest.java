package com.reeltwo.plot;

import junit.framework.Test;

import junit.framework.TestSuite;

/*
 * Created: Mon May 21 16:35:49 2001
 *
 * $Log$
 * Revision 1.1  2004/05/09 22:37:30  richard
 * Initial revision
 *
 * Revision 1.5  2003/04/24 04:06:00  len
 * Run through the pretty printer.
 *
 * Revision 1.4  2002/04/15 22:54:34  sean
 * Removed extra imports
 *
 * Revision 1.3  2001/10/10 19:30:46  richard
 * Renamed Plot & Datum abstract tests.
 *
 * Revision 1.2  2001/10/09 04:23:12  richard
 * Major rework of Graph2D and associated bits.
 *
 * Revision 1.1  2001/10/08 22:04:26  richard
 * Initial coding of tests, bug fixes to others.
 *
 */
/**
 * JUnit tests for the BoxPlot2D class.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */

public class BoxPlot2DTest extends AbstractPlot2DTest {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public BoxPlot2DTest(String name) {
    super(name);
  }


  public Plot2D getPlot() {
    return new BoxPlot2D();
  }


  public Plot2D getPlot(int x, int y) {
    return new BoxPlot2D(x, y);
  }


  public Datum2D[] getData() {
    return new Box2D[]{new Box2D(1, 2, 3, 4), new Box2D(5, 6, 7, 8)};
  }


  public void test1() {
    BoxPlot2D plot = (BoxPlot2D) getPlot();
    assertTrue(plot.getFill());
    plot.setFill(false);
    assertTrue(!plot.getFill());
    plot.setFill(true);
    assertTrue(plot.getFill());
  }


  public static Test suite() {
    return new TestSuite(BoxPlot2DTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
