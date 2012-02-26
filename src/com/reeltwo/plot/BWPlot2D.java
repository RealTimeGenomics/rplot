package com.reeltwo.plot;

/**
 * Structure to hold attributes of a box and whisker line on a 2D graph.
 *
 * @author Richard Littin
 */

public class BWPlot2D extends Plot2D {
  /**
   * Box and Whisker plot rendering style.
   */
  public enum BoxWhiskerStyle {
    /** Render a standard looking box and whisker plot. */
    STANDARD,
    /** Render a minimal box and whisker plot. */
    MINIMAL,
    /** Render a box and whisker plot where consecutive nodes are joined. */
    JOINED
  }

  private BoxWhiskerStyle mStyle = BoxWhiskerStyle.STANDARD;

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
  public BWPlot2D(Edge x, Edge y) {
    super(x, y);
  }


  /**
   * Sets the style of plot to produce.
   * Valid styles are <code>STANDARD</code> (the default),
   * <code>MINIMAL</code> and <code>JOINED</code>.
   *
   * @param style box and whisker type
   */
  public void setType(BoxWhiskerStyle style) {
    mStyle = style;
  }


  /**
   * Returns the style of the plot.
   *
   * @return plot style
   */
  public BoxWhiskerStyle getStyle() {
    return mStyle;
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
