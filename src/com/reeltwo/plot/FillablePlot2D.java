package com.reeltwo.plot;

/**
 * Abstract class for those graphs that can fill regions with color or patterns.
 *
 * @author Richard Littin (richard@reeltwo.com) 
 * @version $Revision$
 */
public abstract class FillablePlot2D extends Plot2D {
  public static final int NO_FILL = 0;
  public static final int COLOR_FILL = 1;
  public static final int PATTERN_FILL = 2;

  private int mFill = NO_FILL;

  private boolean mBorder = false;

  /** Default constructor. */
  public FillablePlot2D() {
    super();
  }


  /**
   * Constructor setting whether to uses the y2 axis when plotting.
   * Default is to use the y1 axis.
   *
   * @param x TODO Description.
   * @param y TODO Description.
   */
  public FillablePlot2D(int x, int y) {
    super(x, y);
  }


  public void setFill(int fill) {
    if (fill < NO_FILL || fill > PATTERN_FILL) {
      throw new IllegalArgumentException("Illegal fill type: " + fill);
    }
    mFill = fill;
  }

  public int getFill() {
    return mFill;
  }

  public boolean isFill() {
    return mFill != NO_FILL;
  }

  /**
   * Sets whether to draw a foreground colored border around the box.  Default is
   * not to draw the border.  Will only draw the border if fill boxes is enabled.
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
  public boolean getBorder() {
    return mBorder;
  }

}
