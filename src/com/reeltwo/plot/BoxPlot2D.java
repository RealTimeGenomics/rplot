package com.reeltwo.plot;

/**
 * Structure to hold attributes of a group of boxes on a 2D graph.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */

public class BoxPlot2D extends FillablePlot2D {

  /** Default constructor. */
  public BoxPlot2D() {
    super();
  }


  /**
   * Constructor setting whether to uses the y2 axis when plotting.
   * Default is to use the y1 axis.
   *
   * @param x TODO Description.
   * @param y TODO Description.
   */
  public BoxPlot2D(int x, int y) {
    super(x, y);
  }


  /**
   * Sets the data used in this plot.
   *
   * @param data an array of Datum2D's
   */
  public void setData(Datum2D[] data) {
    if (!(data instanceof Box2D[])) {
      throw new UnsupportedOperationException("Cannot set " + data.getClass().getName() + " in BoxPlot2D.");
    }
    super.setData(data);
  }

}
