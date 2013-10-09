package com.reeltwo.plot;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * JUnit tests for the BoxPlot2D class.
 *
 * @author Richard Littin
 */
public class BoxPlot2DTest extends AbstractFillablePlot2DTest {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public BoxPlot2DTest(String name) {
    super(name);
  }


  @Override
  public Plot2D getPlot() {
    return new BoxPlot2D();
  }


  @Override
  public Plot2D getPlot(Edge x, Edge y) {
    return new BoxPlot2D(x, y);
  }


  @Override
  public Datum2D[] getData() {
    return new Box2D[]{new Box2D(1, 2, 3, 4), new Box2D(5, 6, 7, 8)};
  }

  @Override
  public Collection<Datum2D> getDataCollection() {
    final ArrayList<Datum2D> res = new ArrayList<Datum2D>();
    res.add(new Box2D(1, 2, 3, 4));
    res.add(new Box2D(5, 6, 7, 8));
    return res;
  }


  public static Test suite() {
    return new TestSuite(BoxPlot2DTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
