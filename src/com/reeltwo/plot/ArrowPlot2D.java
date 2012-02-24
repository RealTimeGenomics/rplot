package com.reeltwo.plot;

/**
 * Structure to hold attributes of an array of arrows on a 2D graph.
 *
 * @author Richard Littin
 */

public class ArrowPlot2D extends Plot2D {
  /**
   * Arrow head types.
   */
  public enum ArrowHead {
    /** Render arrows with an open head. */
    OPEN,
    /** Render arrows with an arrow shaped head */
    ARROW,
    /** Render rarows with a triangular head. */
    TRIANGLE,
    /** Render arrows with a diamond shaped head. */
    DIAMOND,
  }
  /** The default head to render. */
  public static final ArrowHead DEFAULT_HEAD = ArrowHead.OPEN;

  /**
   * Direction of arrow.
   */
  public enum ArrowDirection {
    /** Put head on front of arrow. */
    FORWARD,
    /** Put head on rear of arrow. */
    REVERSE,
    /** Put head on both ends of arrow. */
    BOTH
  }

  /** The default head location. */
  public static final ArrowDirection DEFAULT_DIRECTION = ArrowDirection.FORWARD;

  /** type of arrow head */
  private ArrowHead mHeadType = DEFAULT_HEAD;
  /** mode of arrow (end it appears on) */
  private ArrowDirection mDirection = DEFAULT_DIRECTION;

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
   *
   * @param type the head type
   */
  public void setHeadType(ArrowHead type) {
    mHeadType = type;
  }

  /**
   * Returns the head type for the arrows.
   *
   * @return the head type
   */
  public ArrowHead getHeadType() {
    return mHeadType;
  }

  /**
   * Sets the direction that the arrows point.
   *
   * @param dir the arrow direction
   */
  public void setDirection(ArrowDirection dir) {
    mDirection = dir;
  }

  /**
   * Returns the arrow direction.
   *
   * @return the arrow direction
   */
  public ArrowDirection getDirection() {
    return mDirection;
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
