package com.reeltwo.plot;

/**
 * Abstract class for those graphs that can fill regions with color or patterns.
 *
 * @author Richard Littin
 */
public abstract class FillablePlot2D extends Plot2D {
  /** Do not fill plot. */
  public static final int NO_FILL = 0;
  /** Fill plot with solid color. */
  public static final int COLOR_FILL = 1;
  /** Fill plot with a pattern. */
  public static final int PATTERN_FILL = 2;

  private int mFill = NO_FILL;

  private boolean mBorder = false;

  /**
   * Default constructor setting the default axes used to the bottom
   * x axis and the left y axis.
   */
  public FillablePlot2D() {
    super();
  }


  /**
   * Constructor setting which axes to use in the plot.
   *
   * @param x x axis to use.
   * @param y y axis to use.
   */
  public FillablePlot2D(int x, int y) {
    super(x, y);
  }


  /**
   * Sets the fill type for this plot. Valid fill types are
   * <code>NO_FILL</code> (the default), <code>COLOR_FILL</code> and
   * <code>PATTERN_FILL</code>.
   *
   * @param fill the fill type
   */
  public void setFill(int fill) {
    if (fill < NO_FILL || fill > PATTERN_FILL) {
      throw new IllegalArgumentException("Illegal fill type: " + fill);
    }
    mFill = fill;
  }

  /**
   * Returns the fill type of this plot.
   *
   * @return the fill type
   */
  public int getFill() {
    return mFill;
  }

  /**
   * Convenience method that returns whether the is any fill at all.
   *
   * @return whether their is some form of filling
   */
  public boolean isFill() {
    return mFill != NO_FILL;
  }

  /**
   * Sets whether to draw a foreground colored border around the plto.  Default is
   * not to draw the border.  Will only draw the border if fill is enabled.
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
  public boolean isBorder() {
    return mBorder;
  }

}
