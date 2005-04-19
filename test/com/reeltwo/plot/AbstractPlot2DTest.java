package com.reeltwo.plot;

import junit.framework.TestCase;

/**
 * Abstract JUnit tests for the Plot2D classes.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */

public abstract class AbstractPlot2DTest extends TestCase {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public AbstractPlot2DTest(String name) {
    super(name);
  }


  public abstract Plot2D getPlot();


  public abstract Plot2D getPlot(int x, int y);


  public abstract Datum2D[] getData();


  protected void setUp() {
  }


  protected void tearDown() {
  }


  public void testBasics() {
    //System.err.println("In Plot2DTest testBasics()");
    Plot2D plot = getPlot();
    final String text = "test plot";
    assertNotNull(plot.getTitle());
    plot.setTitle(null); // title should be set to ""
    assertNotNull(plot.getTitle());
    plot.setTitle(text);
    assertTrue(plot.getTitle().equals(text));
    int color = 123;
    plot.setColor(color);
    assertTrue(plot.getColor() == color);

    Datum2D[] data = getData();
    assertNotNull(data);
    try {
      plot.setData(null);
      fail("plot accepted null data.");
    } catch (NullPointerException npe) {
      ; // should get here
    }
    plot.setData(data);
    assertTrue(plot.getData() == data);
    double xLo = 0.0;
    double xHi = 0.0;
    double yLo = 0.0;
    double yHi = 0.0;
    if (data.length != 0) {
      xLo = data[0].getXLo();
      xHi = data[0].getXHi();
      yLo = data[0].getYLo();
      yHi = data[0].getYHi();
      for (int i = 1; i < data.length; i++) {
        if (data[i].getXLo() < xLo) {
          xLo = data[i].getXLo();
        }
        if (data[i].getXHi() > xHi) {
          xHi = data[i].getXHi();
        }
        if (data[i].getYLo() < yLo) {
          yLo = data[i].getYLo();
        }
        if (data[i].getYHi() > yHi) {
          yHi = data[i].getYHi();
        }
      }
    }
    assertTrue(plot.getXLo() <= plot.getXHi());
    assertTrue(plot.getYLo() <= plot.getYHi());

    assertTrue(plot.getXLo() == xLo);
    assertTrue(plot.getXHi() == xHi);
    assertTrue(plot.getYLo() == yLo);
    assertTrue(plot.getYHi() == yHi);

    for (int y = 0; y < 2; y++) {
      for (int x = 0; x < 2; x++) {
        plot = getPlot(x, y);
        assertTrue(plot.getXAxis() == x);
        assertTrue(plot.getYAxis() == y);
      }
    }
  }

  public void testBadValues() {
    Point2D [] points = new Point2D[] {
      new Point2D(0.0f, 1.0f),
      new Point2D(2.0f, Float.POSITIVE_INFINITY),
      new Point2D(1.0f, 0.0f),
      new Point2D(Float.NEGATIVE_INFINITY, -3.0f),
      new Point2D(Float.NaN, -1.5f),
    };
    Plot2D plot = new Plot2D() {
      };

    assertTrue(plot.getXLo() == 0.0f);
    assertTrue(plot.getYLo() == 0.0f);
    assertTrue(plot.getXHi() == 0.0f);
    assertTrue(plot.getYHi() == 0.0f);

    try {
      plot.setData(points);
      fail("accepted bad data");
    } catch (IllegalArgumentException iae) {
      //System.err.println("IAE: " + iae.getMessage());
      ; // expected
    }

    assertTrue(plot.getXLo() == 0.0f);
    assertTrue(plot.getYLo() == 0.0f);
    assertTrue(plot.getXHi() == 0.0f);
    assertTrue(plot.getYHi() == 0.0f);
  }
}

