package com.reeltwo.plot;

/**
 * Structure to hold attributes of a single line on a 2D graph.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */

public class PointPlot2D extends FillablePlot2D {
  /** whether to draw points */
  private boolean mPoints = true;
  /** whether to draw lines */
  private boolean mLines = false;


  /** Default constructor. */
  public PointPlot2D() {
    super();
  }


  /**
   * Constructor setting whether to uses the y2 axis when plotting.
   * Default is to use the y1 axis.
   *
   * @param x TODO Description.
   * @param y TODO Description.
   */
  public PointPlot2D(int x, int y) {
    super(x, y);
  }


  /**
   * Sets whether or not so show points on line. Default is to show
   * points.
   *
   * @param flag whether to show points.
   */
  public void setPoints(boolean flag) {
    mPoints = flag;
  }


  /**
   * Returns whether or not so show points on line.
   *
   * @return whether to show points.
   */
  public boolean isPoints() {
    return mPoints;
  }


  /**
   * Sets whether or not to join points with a line. Default is not to
   * join points.
   *
   * @param flag whether to join points.
   */
  public void setLines(boolean flag) {
    mLines = flag;
  }


  /**
   * Returns whether or not to join points with a line.
   *
   * @return whether to join points.
   */
  public boolean isLines() {
    return mLines;
  }


  /**
   * Sets the data used in this plot.
   *
   * @param data an array of Datum2D's
   */
  public void setData(Datum2D[] data) {
    if (!(data instanceof Point2D[])) {
      throw new UnsupportedOperationException("Cannot set " + data.getClass().getName() + " in PointPlot2D.");
    }
    super.setData(data);
  }
}
