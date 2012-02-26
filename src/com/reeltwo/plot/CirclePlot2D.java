package com.reeltwo.plot;

/**
 * Structure to hold attributes of a group of circles on a 2D graph.
 *
 * @author Richard Littin
 */

public class CirclePlot2D extends FillablePlot2D {

  /**
   * Default constructor setting the default axes used to the bottom
   * x axis and the left y axis.
   */
  public CirclePlot2D() {
    super();
  }


  /**
   * Constructor setting which axes to use in the plot.
   *
   * @param x x axis to use.
   * @param y y axis to use.
   */
  public CirclePlot2D(Edge x, Edge y) {
    super(x, y);
  }

  /**
   * Sets the data used in this plot.
   *
   * @param data an array of Datum2D's
   */
  @Override
  public void setData(Datum2D[] data) {
    if (!(data instanceof Circle2D[])) {
      throw new UnsupportedOperationException("Cannot set " + data.getClass().getName() + " in Circle2D.");
    }
    super.setData(data);
  }
}
