package com.reeltwo.plot;

import java.util.Arrays;


/**
 * Point data used for y axis box and whisker plots.
 *
 * @author Richard Littin (richard@reeltwo.com)
 * @version $Revision$
 */

public class BWPoint2D implements Datum2D {
  /** co-ordinate */
  private float mX;
  private float [] mYs = new float[5];

  /**
   * Sets x and y co-ordinates for a box and whisker point.
   *
   * @param x x co-ordinate.
   * @param ymin the minimum y value.
   * @param ylq the value of the lowest quartile.
   * @param ymedian the median y value.
   * @param yuq the value of they y upper quartile.
   * @param ymax the maximum y value.
   */
  public BWPoint2D(float x, float ymin, float ylq, float ymedian, float yuq, float ymax) {
    mX = x;
    mYs[0] = ymin;
    mYs[1] = ylq;
    mYs[2] = ymedian;
    mYs[3] = yuq;
    mYs[4] = ymax;

    sort();
  }


  private void sort() {
    Arrays.sort(mYs);
  }


  /**
   * Sets the x co-ordinate.
   *
   * @param x x co-ordinate.
   */
  public void setX(float x) {
    mX = x;
  }


  /**
   * Returns x co-ordinate.
   *
   * @return x co-ordinate.
   */
  public float getX() {
    return mX;
  }


  /**
   * Sets the <code>i</code><sup>th</sup> y co-ordinate.
   *
   * @param i ith y value
   * @param y y co-ordinate.
   */
  public void setY(int i, float y) {
    mYs[i] = y;
    sort();
  }


  /**
   * Returns <code>i</code><sup>th</sup> y co-ordinate.
   *
   * @param i ith y value
   * @return y co-ordinate.
   */
  public float getY(int i) {
    return mYs[i];
  }


  /**
   * Returns the lower bound of the point in the x dimension.
   *
   * @return a number
   */
  public float getXLo() {
    return getX();
  }


  /**
   * Returns the upper bound of the point in the x dimension.
   *
   * @return a number
   */
  public float getXHi() {
    return getX();
  }


  /**
   * Returns the lower bound of the point in the y dimension.
   *
   * @return a number
   */
  public float getYLo() {
    return mYs[0];
  }


  /**
   * Returns the upper bound of the point in the y dimension.
   *
   * @return a number
   */
  public float getYHi() {
    return mYs[mYs.length - 1];
  }


  /**
   * Returns a string representation of this object.
   *
   * @return a co-ordinate string
   */
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append('(').append(getX());
    for (int i = 0; i < mYs.length; i++) {
      sb.append(',').append(getY(i));
    }
    sb.append(')');
    return sb.toString();
  }


  /**
   * Overrides Object.equals
   *
   * @param object object to compare to
   * @return result of comparison
   */
  public boolean equals(Object object) {
    if (!(object instanceof BWPoint2D)) {
      return false;
    }
    BWPoint2D pobj = (BWPoint2D) object;
    if (mX != pobj.getX()) {
      return false;
    }
    for (int i = 0; i < mYs.length; i++) {
      if (mYs[i] != pobj.getY(i)) {
        return false;
      }
    }
    return true;
  }

  /** {@inheritDoc} */
  public int hashCode() {
    return (int) (mX + mYs[0] + mYs[1] + mYs[2] + mYs[3] + mYs[4]);
  }
}
