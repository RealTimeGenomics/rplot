package com.reeltwo.plot;

/**
 * Abstract class for those graphs that can fill regions with color or patterns.
 *
 * @author Richard Littin
 */
public abstract class FillablePlot2D extends Plot2D {

  /**
   * Possible fill styles.
   */
  public enum FillStyle {
    /** Do not fill plot. */
    NONE,
    /** Fill plot with solid color. */
    COLOR,
    /** Fill plot with a pattern. */
    PATTERN,
  }

  private FillStyle mFill = FillStyle.NONE;

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
  public FillablePlot2D(AxisSide x, AxisSide y) {
    super(x, y);
  }


  /**
   * Sets the fill type for this plot. Valid fill types are
   * <code>NO_FILL</code> (the default), <code>COLOR_FILL</code> and
   * <code>PATTERN_FILL</code>.
   *
   * @param fill the fill type
   */
  public void setFill(FillStyle fill) {
    mFill = fill;
  }

  /**
   * Returns the fill type of this plot.
   *
   * @return the fill type
   */
  public FillStyle getFill() {
    return mFill;
  }

  /**
   * Convenience method that returns whether the is any fill at all.
   *
   * @return whether their is some form of filling
   */
  public boolean isFill() {
    return mFill != FillStyle.NONE;
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
