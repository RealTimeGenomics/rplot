package com.reeltwo.plot;

/**
 * Structure to hold attributes of a group of boxes on a 2D graph.
 *
 * @author Richard Littin
 */
public class BoxPlot2D extends FillablePlot2D {

  /**
   * Default constructor setting the default axes used to the bottom
   * x axis and the left y axis.
   */
  public BoxPlot2D() {
    super();
  }


  /**
   * Constructor setting which axes to use in the plot.
   *
   * @param x x axis to use.
   * @param y y axis to use.
   */
  public BoxPlot2D(int x, int y) {
    super(x, y);
  }


  /**
   * Sets the data used in this plot.
   *
   * @param data an array of Datum2D's
   */
  @Override
  public void setData(Datum2D[] data) {
    if (!(data instanceof Box2D[])) {
      throw new UnsupportedOperationException("Cannot set " + data.getClass().getName() + " in BoxPlot2D.");
    }
    super.setData(data);
  }

}
