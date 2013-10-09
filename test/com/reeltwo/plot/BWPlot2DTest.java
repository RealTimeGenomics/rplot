package com.reeltwo.plot;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.reeltwo.plot.BWPlot2D.BoxWhiskerStyle;

/**
 * JUnit tests for the BWPointPlot2D class.
 *
 * @author Richard Littin
 */

public class BWPlot2DTest extends AbstractPlot2DTest {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public BWPlot2DTest(String name) {
    super(name);
  }


  @Override
  public Plot2D getPlot() {
    return new BWPlot2D();
  }


  @Override
  public Plot2D getPlot(Edge x, Edge y) {
    return new BWPlot2D(x, y);
  }


  @Override
  public Datum2D[] getData() {
    return new BWPoint2D[]{new BWPoint2D(1, 2, 3, 4, 5, 6), new BWPoint2D(5, 6, 7, 8, 9, 10)};
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

  public void testSets() {
    final BWPlot2D plot = (BWPlot2D) getPlot();

    assertEquals(BoxWhiskerStyle.STANDARD, plot.getStyle());
    plot.setType(BoxWhiskerStyle.MINIMAL);
    assertEquals(BoxWhiskerStyle.MINIMAL, plot.getStyle());
    plot.setType(BoxWhiskerStyle.JOINED);
    assertEquals(BoxWhiskerStyle.JOINED, plot.getStyle());
    plot.setType(BoxWhiskerStyle.STANDARD);
    assertEquals(BoxWhiskerStyle.STANDARD, plot.getStyle());

    assertEquals(20, plot.getWidth());
    plot.setWidth(10);
    assertEquals(10, plot.getWidth());
  }

  public void testBadArgs() {
    final BWPlot2D plot = (BWPlot2D) getPlot();
    try {
      plot.setWidth(0);
      fail("accepted bad width");
    } catch (final IllegalArgumentException e) {
      ; // expected
    }
  }

  public static Test suite() {
    return new TestSuite(BWPlot2DTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
