package com.reeltwo.plot;

/**
 * Structure to hold attributes of a box and whisker line on a 2D graph.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */

public class BWPlot2D extends Plot2D {
  public static final int STANDARD = 0;
  public static final int MINIMAL = 1;
  public static final int JOINED = 2;
  
  private int mType = STANDARD;

  private int mWidth = 20;

  /** Default constructor. */
  public BWPlot2D() {
    super();
  }


  /**
   * Constructor setting whether to uses the y2 axis when plotting.
   * Default is to use the y1 axis.
   *
   * @param x x axis to use
   * @param y y axis to use
   */
  public BWPlot2D(int x, int y) {
    super(x, y);
  }


  /**
   * Sets the style of plot to produce.
   *
   * @param type box and whisker type
   */
  public void setType(int type) {
    if (type < STANDARD || type > JOINED) {
      throw new IllegalArgumentException("Invalid type: " + type);
    }
    mType = type;
  }


  /**
   * Returns the style of the plot.
   *
   * @return plot style
   */
  public int getType() {
    return mType;
  }


  /**
   * Sets the width of the box in screen units.
   *
   * @param pixels width in screen units
   */
  public void setWidth(int pixels) {
    if (pixels < 1) {
      throw new IllegalArgumentException("width must be >= 1: " + pixels);
    }
    mWidth = pixels;
  }


  /**
   * Returns the width of the box in screen units.
   *
   * @return screen units
   */
  public int getWidth() {
    return mWidth;
  }


  /**
   * Sets the data used in this plot.
   *
   * @param data an array of Datum2D's
   */
  public void setData(Datum2D[] data) {
    if (!(data instanceof BWPoint2D[])) {
      throw new UnsupportedOperationException("Cannot set " + data.getClass().getName() + " in BWPlot2D.");
    }
    super.setData(data);
  }
}
