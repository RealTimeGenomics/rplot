package com.reeltwo.plot;

/**
 * Structure to hold attributes of an array of arrows on a 2D graph.
 *
 * @author Richard Littin (richard@reeltwo.com)
 * @version $Revision$
 */

public class ArrowPlot2D extends Plot2D {
  public static final int OPEN_HEAD = 0;
  public static final int ARROW_HEAD = 1;
  public static final int TRIANGLE_HEAD = 2;
  public static final int DIAMOND_HEAD = 3;
  public static final int DEFAULT_HEAD = OPEN_HEAD;

  public static final int FORWARD_MODE = 1;
  public static final int REVERSE_MODE = 2;
  public static final int BOTH_MODE = FORWARD_MODE | REVERSE_MODE;
  public static final int DEFAULT_MODE = FORWARD_MODE;

  /** type of arrow head */
  private int mHeadType = DEFAULT_HEAD;
   /** mode of arrow (end it appears on) */
  private int mMode = DEFAULT_MODE;

  private float mHeadWidth = 4.0f;
  private float mHeadHeight = 8.0f;

  /** Default constructor. */
  public ArrowPlot2D() {
    super();
  }


  /**
   * Constructor setting whether to uses the y2 axis when plotting.
   * Default is to use the y1 axis.
   *
   * @param x TODO Description.
   * @param y TODO Description.
   */
  public ArrowPlot2D(final int x, final int y) {
    super(x, y);
  }


  public void setHeadType(final int type) {
    if (type < OPEN_HEAD || type > DIAMOND_HEAD) {
      throw new IllegalArgumentException("Invalid arrow head type: " + type);
    }
    mHeadType = type;
  }

  public int getHeadType() {
    return mHeadType;
  }

  public void setMode(final int mode) {
    if (mode < FORWARD_MODE || mode > BOTH_MODE) {
      throw new IllegalArgumentException("Invalid arrow mode: " + mode);
    }
    mMode = mode;
  }

  public int getMode() {
    return mMode;
  }

  public void setHeadWidth(final float w) {
    if (w < 0.0f) {
      throw new IllegalArgumentException("Invalid arrow head width: " + w);
    }
    mHeadWidth = w;
  }

  public float getHeadWidth() {
    return mHeadWidth;
  }

  public void setHeadHeight(final float h) {
    if (h < 0.0f) {
      throw new IllegalArgumentException("Invalid arrow head height: " + h);
    }
    mHeadHeight = h;
  }

  public float getHeadHeight() {
    return mHeadHeight;
  }

  /**
   * Sets the data used in this plot.
   *
   * @param data an array of Datum2D's
   */
  public void setData(Datum2D[] data) {
    if (!(data instanceof Arrow2D[])) {
      throw new UnsupportedOperationException("Cannot set " + data.getClass().getName() + " in ArrowPlot2D.");
    }
    super.setData(data);
  }
}
