package com.reeltwo.plot;

/**
 * Structure to hold attributes of a single plot on a 2D graph.
 *
 * @author Richard Littin (richard@reeltwo.com)
 * @version $Revision$
 */

public abstract class Plot2D {

  /** the title of this plot */
  private String mTitle = "";
  /** the color of this plot */
  private int mColor = -1;
  /** width of lines in rendering units */
  private int mLineWidth = 1;

  /** range bounds of points in plot */
  private float mXLo, mXHi;
  private float mYLo, mYHi;

  private int mXAxis = 0;
  private int mYAxis = 0;

  /** Use the bottom X axis on the graph. */
  public static final int BOTTOM_X_AXIS = 0;
  /** Use the top X axis on the graph. */
  public static final int TOP_X_AXIS = 1;
  /** Use the left Y axis on the graph. */
  public static final int LEFT_Y_AXIS = 0;
  /** Use the right Y axis on the graph. */
  public static final int RIGHT_Y_AXIS = 1;

  /** data points in plot */
  private Datum2D[] mData = null;


  /**
   * Default constructor setting the default axes used to the bottom
   * x axis and the left y axis.
   */
  public Plot2D() { }


  /**
   * Constructor setting which axes to use in the plot.
   *
   * @param x x axis to use.
   * @param y y axis to use.
   */
  public Plot2D(int x, int y) {
    setXAxis(x);
    setYAxis(y);
  }


  private void setXAxis(int x) {
    if (x < 0 || x > Graph2D.NUM_X_AXES) {
      throw new IllegalArgumentException("X axis index out of range.");
    }
    mXAxis = x;
  }


  /**
   * Returns the X axis to use for this plot.
   *
   * @return X axis value
   */
  public int getXAxis() {
    return mXAxis;
  }


  private void setYAxis(int y) {
    if (y < 0 || y > Graph2D.NUM_Y_AXES) {
      throw new IllegalArgumentException("Y axis index out of range.");
    }
    mYAxis = y;
  }


  /**
   * Returns the Y axis to use for this plot.
   *
   * @return Y axis value
   */
  public int getYAxis() {
    return mYAxis;
  }


  /**
   * Sets the plot's title.
   *
   * @param title some text
   */
  public void setTitle(String title) {
    if (title != null) {
      mTitle = title;
    }
  }


  /**
   * Returns the plot's title.
   *
   * @return some text
   */
  public String getTitle() {
    return mTitle;
  }


  /**
   * Sets the plot's color.
   *
   * @param color a Color
   */
  public void setColor(int color) {
    mColor = color;
  }


  /**
   * Returns the plot's color.
   *
   * @return a Color
   */
  public int getColor() {
    return mColor;
  }


  /**
   * Sets the plot's line width to <code>width</code> rendering units.
   *
   * @param width an <code>int</code> value
   * @exception IllegalArgumentException if <code>width</code> is less than 1
   */
  public void setLineWidth(int width) {
    if (width < 1) {
      throw new IllegalArgumentException("Line width must be greater than or equal to 1: " + width);
    }
    mLineWidth = width;
  }


  /**
   * Returns the plot's rendering line width.
   *
   * @return line width
   */
  public int getLineWidth() {
    return mLineWidth;
  }


  /**
   * Sets the data used in this plot.  Override this to check 
   * data is valid.
   *
   * @param data an array of Datum2D's
   */
  public void setData(Datum2D[] data) {
    if (data == null || data.length == 0) {
      mXLo = mXHi = 0.0f;
      mYLo = mYHi = 0.0f;
    } else {
      Datum2D d = data[0];
      float xLo = d.getXLo();
      float xHi = d.getXHi();
      float yLo = d.getYLo();
      float yHi = d.getYHi();
      for (int i = 1; i < data.length; i++) {
        d = data[i];
        if (d.getXLo() < xLo) {
          xLo = d.getXLo();
        }
        if (d.getXHi() > xHi) {
          xHi = d.getXHi();
        }
        if (d.getYLo() < yLo) {
          yLo = d.getYLo();
        }
        if (d.getYHi() > yHi) {
          yHi = d.getYHi();
        }
      }
      checkValid(xLo);
      checkValid(yLo);
      checkValid(xHi);
      checkValid(yHi);
      
      mXLo = xLo;
      mXHi = xHi;
      mYLo = yLo;
      mYHi = yHi;
    }
    mData = data;
  }


  /**
   * Returns the data used in this plot.
   *
   * @return an array of Datum2D's
   */
  public Datum2D[] getData() {
    return mData;
  }


  private void checkValid(float f) {
    if (!isValid(f)) {
      throw new IllegalArgumentException("Bad data value: " + f);
    }
  }

  /**
   * Returns validity of <code>f</code> as co-ordinate in a plot.
   *
   * @param f value to check
   * @return is valid in plot
   */
  public static boolean isValid(float f) {
    return !Float.isNaN(f) && !Float.isInfinite(f);
  }

  /**
   * Returns validity of an array of values <code>f</code> as co-ordinates in a plot.
   *
   * @param f values to check
   * @return is valid in plot
   */
  public static boolean isValid(float[] f) {
    for (int i = 0; f != null && i < f.length; i++) {
      if (!isValid(f[i])) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns the lower bound of the x range.
   *
   * @return a number
   */
  public float getXLo() {
    return mXLo;
  }


  /**
   * Returns the upper bound of the x range.
   *
   * @return a number
   */
  public float getXHi() {
    return mXHi;
  }


  /**
   * Returns the lower bound of the y range.
   *
   * @return a number
   */
  public float getYLo() {
    return mYLo;
  }


  /**
   * Returns the upper bound of the y range.
   *
   * @return a number
   */
  public float getYHi() {
    return mYHi;
  }
}
