package com.reeltwo.plot;

/**
 * Point data used in data plotting structures.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */
public class Point2D implements Datum2D {
  /** co-ordinate */
  private float mX, mY;


  /**
   * Sets x and y co-ordinates.
   *
   * @param x x co-ordinate.
   * @param y y co-ordinate.
   */
  public Point2D(float x, float y) {
    mX = x;
    mY = y;
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
   * Sets the y co-ordinate.
   *
   * @param y y co-ordinate.
   */
  public void setY(float y) {
    mY = y;
  }


  /**
   * Returns y co-ordinate.
   *
   * @return y co-ordinate.
   */
  public float getY() {
    return mY;
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
    return getY();
  }


  /**
   * Returns the upper bound of the point in the y dimension.
   *
   * @return a number
   */
  public float getYHi() {
    return getY();
  }


  /**
   * Returns a string representation of this object.
   *
   * @return a co-ordinate string
   */
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append('(').append(getX()).append(',').append(getY());
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
    if (!(object instanceof Point2D)) {
      return false;
    }
    Point2D pobj = (Point2D) object;
    if (mX == pobj.getX() && mY == pobj.getY()) {
      return true;
    }
    return false;
  }


  public int hashCode() {
    return super.hashCode();
  }
}
