package com.reeltwo.plot;

/**
 * Structure to hold attributes of a curve denoted by points on a 2D graph.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */

public class CurvePlot2D extends FillablePlot2D {
  /** Use a bspline curve through points. */
  public static final int BSPLINE = 0;
  /** Use a bezier curve through points. */
  public static final int BEZIER = 1;
  /** Use a cubic bezier curve through points. */
  public static final int CUBIC_BEZIER = 2;

  /** the curve type */
  private int mType = BSPLINE;

  /**
   * Default constructor setting the default axes used to the bottom
   * x axis and the left y axis.
   */
  public CurvePlot2D() {
    super();
  }


  /**
   * Constructor setting which axes to use in the plot.
   *
   * @param x x axis to use.
   * @param y y axis to use.
   */
  public CurvePlot2D(int x, int y) {
    super(x, y);
  }


  /**
   * Sets the type of curve to draw.
   * Valid types are <code>BSPLINE</code> (the default),
   * <code>BEZIER</code> and <code>CUBIC_BEZIER</code>.
   *
   * @param type a curve type
   */
  public void setType(int type) {
    if (type < BSPLINE || type > CUBIC_BEZIER) {
      throw new IllegalArgumentException("Invalid curve type: " + type);
    }
    mType = type;
  }


  /**
   * Returns the curve type.
   *
   * @return the curve type
   */
  public int getType() {
    return mType;
  }


  /**
   * Sets the data used in this plot.
   *
   * @param data an array of Datum2D's
   */
  public void setData(Datum2D[] data) {
    if (!(data instanceof Point2D[])) {
      throw new UnsupportedOperationException("Cannot set " + data.getClass().getName() + " in CurvePlot2D.");
    }
    super.setData(data);
  }
}
