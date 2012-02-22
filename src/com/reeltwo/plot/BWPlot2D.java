package com.reeltwo.plot;

/**
 * Structure to hold attributes of a box and whisker line on a 2D graph.
 *
 * @author Richard Littin
 */

public class BWPlot2D extends Plot2D {
  /** Render a standard looking box and whisker plot. */
  public static final int STANDARD = 0;
  /** Render a minimal box and whisker plot. */
  public static final int MINIMAL = 1;
  /** Render a box and whisker plot where consecutive nodes are joined. */
  public static final int JOINED = 2;

  private int mType = STANDARD;

  private int mWidth = 20;

  /**
   * Default constructor setting the default axes used to the bottom
   * x axis and the left y axis.
   */
  public BWPlot2D() {
    super();
  }


  /**
   * Constructor setting which axes to use in the plot.
   *
   * @param x x axis to use.
   * @param y y axis to use.
   */
  public BWPlot2D(AxisSide x, AxisSide y) {
    super(x, y);
  }


  /**
   * Sets the style of plot to produce.
   * Valid styles are <code>STANDARD</code> (the default),
   * <code>MINIMAL</code> and <code>JOINED</code>.
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
  @Override
  public void setData(Datum2D[] data) {
    if (!(data instanceof BWPoint2D[])) {
      throw new UnsupportedOperationException("Cannot set " + data.getClass().getName() + " in BWPlot2D.");
    }
    super.setData(data);
  }
}
