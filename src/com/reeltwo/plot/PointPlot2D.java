package com.reeltwo.plot;

/**
 * Structure to hold attributes of a single plot on a 2D graph.
 *
 * @author Richard Littin
 */

public class PointPlot2D extends FillablePlot2D {
  /** whether to draw points */
  private boolean mPoints = true;
  /** whether to draw lines */
  private boolean mLines = false;
  /** whether to draw dots */
  private boolean mDots = false;

  /**
   * Default constructor setting the default axes used to the bottom
   * x axis and the left y axis.
   */
  public PointPlot2D() {
    super();
  }


  /**
   * Constructor setting which axes to use in the plot.
   *
   * @param x x axis to use.
   * @param y y axis to use.
   */
  public PointPlot2D(Edge x, Edge y) {
    super(x, y);
  }


  /**
   * Sets whether or not to draw dots (single pixel points). Default is not to
   * draw dots.
   *
   * @param flag whether to draw dots.
   */
  public void setDots(boolean flag) {
    mDots = flag;
  }


  /**
   * Returns whether or not to draw single pixel dots.
   *
   * @return whether to draw dots.
   */
  public boolean isDots() {
    return mDots;
  }


  /**
   * Sets whether or not so show points on plot. Default is to show
   * points.
   *
   * @param flag whether to show points.
   */
  public void setPoints(boolean flag) {
    mPoints = flag;
  }


  /**
   * Returns whether or not so show points on plot.
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
  @Override
  public void setData(Datum2D[] data) {
    if (!(data instanceof Point2D[])) {
      throw new UnsupportedOperationException("Cannot set " + data.getClass().getName() + " in PointPlot2D.");
    }
    super.setData(data);
  }
}
