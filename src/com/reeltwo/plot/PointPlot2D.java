package com.reeltwo.plot;

/**
 * Structure to hold attributes of a single line on a 2D graph.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */

public class PointPlot2D extends Plot2D {
  /** whether to draw points */
  private boolean mPoints = true;
  /** whether to draw lines */
  private boolean mLines = false;
  /** whether to do a polygon fill */
  private boolean mFill = false;


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
  public boolean getPoints() {
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
  public boolean getLines() {
    return mLines;
  }


  /**
   * Sets whether or not to treat points as a polygon and fill the
   * polygon. Default is not to fill polygon.
   *
   * @param flag whether to fill polygon.
   */
  public void setFill(boolean flag) {
    mFill = flag;
  }


  /**
   * Returns whether or not to treat points as a polygon and fill the
   * polygon.
   *
   * @return whether to fill polygon.
   */
  public boolean getFill() {
    return mFill;
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
