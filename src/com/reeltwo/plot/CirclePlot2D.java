package com.reeltwo.plot;

/**
 * Structure to hold attributes of a group of labels on a 2D graph.
 *
 * @author Richard Littin (richard@reeltwo.com)
 * @version $Revision$
 */

public class CirclePlot2D extends Plot2D {
  /** whether to fill circles */
  private boolean mFill = true;

  public CirclePlot2D() {
    super();
  }


  public CirclePlot2D(final int x, final int y) {
    super(x, y);
  }

  /**
   * Sets the data used in this plot.
   *
   * @param data an array of Datum2D's
   */
  public void setData(Datum2D[] data) {
    if (!(data instanceof Circle2D[])) {
      throw new UnsupportedOperationException("Cannot set " + data.getClass().getName() + " in Circle2D.");
    }
    super.setData(data);
  }


  /**
   * Sets whether or not to fill circles. Default is to fill circles.
   *
   * @param flag whether to fill circles.
   */
  public void setFill(final boolean flag) {
    mFill = flag;
  }


  /**
   * Returns whether or not to fill circles.
   *
   * @return whether to fill circles.
   */
  public boolean getFill() {
    return mFill;
  }
}
