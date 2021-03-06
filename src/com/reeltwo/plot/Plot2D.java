package com.reeltwo.plot;

import java.lang.reflect.Array;
import java.util.Collection;


/**
 * Structure to hold attributes of a single plot on a 2D graph.
 *
 * @author Richard Littin
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

  private final Edge mXAxis;
  private final Edge mYAxis;

  /** data points in plot */
  private Datum2D[] mData = null;


  /**
   * Default constructor setting the default axes used to the bottom
   * x axis and the left y axis.
   */
  public Plot2D() {
    this(Edge.MAIN, Edge.MAIN);
  }


  /**
   * Constructor setting which axes to use in the plot.
   *
   * @param x x axis to use.
   * @param y y axis to use.
   */
  public Plot2D(Edge x, Edge y) {
    mXAxis = x;
    mYAxis = y;
  }

  /**
   * Returns whether plot is using the specified side for the given axis.
   *
   * @param axis axis to check
   * @param side is using this side
   * @return using side for axis
   */
  public boolean uses(Axis axis, Edge side) {
    return (axis == Axis.X ? mXAxis : mYAxis) == side;
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
   * Sets the data to use in this plot.
   * 
   * @param data a collection of Datum2D objects
   * @param <T> something of type Datum2D
   */
  @SuppressWarnings("unchecked")
  public <T extends Datum2D> void setData(Collection<T> data) {
    T[] x = null;
    if (data != null && data.size() != 0) {
      Class<?> c = null;
      for (T d : data) {
        c = d.getClass();
      }
      x = data.toArray((T[]) Array.newInstance(c, data.size()));
    }
    setData(x);
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
   * Returns the lower bound of the axis range.
   *
   * @param axis graph axis
   * @return a number
   */
  public float getLo(Axis axis) {
    return axis == Axis.X ? mXLo : mYLo;
  }


  /**
   * Returns the upper bound of the axis range.
   *
   * @param axis graph axis
   * @return a number
   */
  public float getHi(Axis axis) {
    return axis == Axis.X ? mXHi : mYHi;
  }
}
