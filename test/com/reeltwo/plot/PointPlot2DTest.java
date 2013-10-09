package com.reeltwo.plot;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.Test;
import junit.framework.TestSuite;

/*
 * Created: Mon May 21 16:35:49 2001
 *
 * $Log$
 * Revision 1.4  2005/05/26 20:31:41  richard
 * fixes to author tag
 *
 * Revision 1.3  2004/05/24 22:29:23  richard
 * moved FillablePlot2DTest to AbstractFillablePlot2DTest
 *
 * Revision 1.2  2004/05/24 20:52:22  richard
 * added concept of patterns to fillable plots
 *
 * Revision 1.1.1.1  2004/05/09 22:37:30  richard
 * initial import
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
 * Revision 1.2  2001/10/09 04:23:19  richard
 * Major rework of Graph2D and associated bits.
 *
 * Revision 1.1  2001/10/08 22:04:26  richard
 * Initial coding of tests, bug fixes to others.
 *
 */
/**
 * JUnit tests for the PointPlot2D class.
 *
 * @author Richard Littin
 */

public class PointPlot2DTest extends AbstractFillablePlot2DTest {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public PointPlot2DTest(String name) {
    super(name);
  }


  @Override
  public Plot2D getPlot() {
    return new PointPlot2D();
  }


  @Override
  public Plot2D getPlot(Edge x, Edge y) {
    return new PointPlot2D(x, y);
  }


  @Override
  public Datum2D[] getData() {
    return new Point2D[]{new Point2D(1, 2), new Point2D(5, 6)};
  }

  @Override
  public Collection<Datum2D> getDataCollection() {
    final ArrayList<Datum2D> res = new ArrayList<Datum2D>();
    for (Datum2D d : getData()) {
      res.add(d);
      res.add(d);
    }
    return res;
  }

  public void test1() {
    final PointPlot2D plot = (PointPlot2D) getPlot();

    assertTrue(plot.isPoints());
    plot.setPoints(false);
    assertTrue(!plot.isPoints());
    plot.setPoints(true);
    assertTrue(plot.isPoints());

    assertTrue(!plot.isLines());
    plot.setLines(false);
    assertTrue(!plot.isLines());
    plot.setLines(true);
    assertTrue(plot.isLines());
  }


  public static Test suite() {
    return new TestSuite(PointPlot2DTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
