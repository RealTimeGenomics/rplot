package com.reeltwo.plot;

/**
 * Structure to hold attributes of a group of boxes on a 2D graph.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */

public class BoxPlot2D extends Plot2D {
  /** whether to fill boxes */
  private boolean mFill = true;

  /** whether to draw a black border */
  private boolean mBorder = false;

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
   * Sets whether or not to fill boxes. Default is to fill boxes.
   *
   * @param flag whether to fill boxes.
   */
  public void setFill(boolean flag) {
    mFill = flag;
  }


  /**
   * Returns whether or not to fill boxes.
   *
   * @return whether to fill boxes.
   */
  public boolean getFill() {
    return mFill;
  }


  /**
   * Sets whether to draw a black border around the box.  Default is
   * not to draw the border.  Will only draw the border if fill boxes
   * is enabled.
   *
   * @param flag whether to draw a border
   */
  public void setBorder(boolean flag) {
    mBorder = flag;
  }


  /**
   * Returns whether to draw a border.
   *
   * @return whether to draw a border
   */
  public boolean getBorder() {
    return mBorder;
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
