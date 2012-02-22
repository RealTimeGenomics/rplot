package com.reeltwo.plot;

/**
 * Structure to hold attributes of an array of arrows on a 2D graph.
 *
 * @author Richard Littin
 */

public class ArrowPlot2D extends Plot2D {
  /** Render arrows with an open head. */
  public static final int OPEN_HEAD = 0;
  /** Render arrows with an arrow shaped head */
  public static final int ARROW_HEAD = 1;
  /** Render rarows with a triangular head. */
  public static final int TRIANGLE_HEAD = 2;
  /** Render arrows with a diamond shaped head. */
  public static final int DIAMOND_HEAD = 3;
  /** The default head to render. */
  public static final int DEFAULT_HEAD = OPEN_HEAD;

  /** Put head on front of arrow. */
  public static final int FORWARD_MODE = 1;
  /** Put head on rear of arrow. */
  public static final int REVERSE_MODE = 2;
  /** Put head on both ends of arrow. */
  public static final int BOTH_MODE = FORWARD_MODE | REVERSE_MODE;
  /** The default head location. */
  public static final int DEFAULT_MODE = FORWARD_MODE;

  /** type of arrow head */
  private int mHeadType = DEFAULT_HEAD;
  /** mode of arrow (end it appears on) */
  private int mMode = DEFAULT_MODE;

  private float mHeadWidth = 4.0f;
  private float mHeadHeight = 8.0f;

  /**
   * Default constructor setting the default axes used to the bottom
   * x axis and the left y axis.
   */
  public ArrowPlot2D() {
    super();
  }


  /**
   * Constructor setting which axes to use in the plot.
   *
   * @param x x axis to use.
   * @param y y axis to use.
   */
  public ArrowPlot2D(AxisSide x, AxisSide y) {
    super(x, y);
  }


  /**
   * Sets the head type to render for the arrows in this plot.
   * Valid head types are <code>OPEN_HEAD</code> (the default),
   * <code>ARROW_HEAD</code>, <code>TRIANGLE_HEAD</code> and
   * <code>DIAMOND_HEAD</code>.
   *
   * @param type the head type
   */
  public void setHeadType(final int type) {
    if (type < OPEN_HEAD || type > DIAMOND_HEAD) {
      throw new IllegalArgumentException("Invalid arrow head type: " + type);
    }
    mHeadType = type;
  }

  /**
   * Returns the head type for the arrows.
   *
   * @return the head type
   */
  public int getHeadType() {
    return mHeadType;
  }

  /**
   * Sets the direction that the arrows point.
   * Valid mode values are <code>FORWARD_MODE</code> (the default),
   * <code>REVERSE_MODE</code> and <code>BOTH_MODE</code>.
   *
   * @param mode the arrow direction
   */
  public void setMode(final int mode) {
    if (mode < FORWARD_MODE || mode > BOTH_MODE) {
      throw new IllegalArgumentException("Invalid arrow mode: " + mode);
    }
    mMode = mode;
  }

  /**
   * Returns the arrow direction.
   *
   * @return the arrow direction
   */
  public int getMode() {
    return mMode;
  }

  /**
   * Sets the width of the arrow head in screen units.  The defualt is 4.
   *
   * @param w arrow head width
   */
  public void setHeadWidth(final float w) {
    if (w < 0.0f) {
      throw new IllegalArgumentException("Invalid arrow head width: " + w);
    }
    mHeadWidth = w;
  }

  /**
   * Returns the arrow head width.
   *
   * @return arrow head width
   */
  public float getHeadWidth() {
    return mHeadWidth;
  }

  /**
   * Sets the height, or length, of the arrow head in screen units.  The defualt is 8.
   *
   * @param h arrow head height
   */
  public void setHeadHeight(final float h) {
    if (h < 0.0f) {
      throw new IllegalArgumentException("Invalid arrow head height: " + h);
    }
    mHeadHeight = h;
  }

  /**
   * Returns the arrow head height.
   *
   * @return arrow head height
   */
  public float getHeadHeight() {
    return mHeadHeight;
  }

  /**
   * Sets the data used in this plot.
   *
   * @param data an array of Datum2D's
   */
  @Override
  public void setData(Datum2D[] data) {
    if (!(data instanceof Arrow2D[])) {
      throw new UnsupportedOperationException("Cannot set " + data.getClass().getName() + " in ArrowPlot2D.");
    }
    super.setData(data);
  }
}
