package com.reeltwo.plot;

/**
 * Point data used in data plotting structures.
 *
 * @author Richard Littin (richard@reeltwo.com)
 * @version $Revision$
 */

public class Arrow2D implements Datum2D {
  /** co-ordinates */
  private float mX1, mY1;
  private float mX2, mY2;


  /**
   * Sets x and y co-ordinates.
   *
   * @param x1 start x co-ordinate.
   * @param y1 start y co-ordinate.
   * @param x2 end x co-ordinate.
   * @param y2 end y co-ordinate.
   */
  public Arrow2D(final float x1, final float y1, final float x2, final float y2) {
    if (x1 == x2 && y1 == y2) {
      throw new IllegalArgumentException("Ends of arrow must be different: (" + x1 + "," + y1 + ") (" + x2 + "," + y2 + ")");
    }
    mX1 = x1;
    mY1 = y1;
    mX2 = x2;
    mY2 = y2;
  }


  /**
   * Sets the x1 co-ordinate.
   *
   * @param x x co-ordinate.
   */
  public void setX1(final float x) {
    if (mY1 == mY2 && mX2 == x) {
      throw new IllegalArgumentException("Ends of arrow must be different: " + mX2 + " : " + x);
    }
    mX1 = x;
  }


  /**
   * Returns x1 co-ordinate.
   *
   * @return x co-ordinate.
   */
  public float getX1() {
    return mX1;
  }


  /**
   * Sets the y1 co-ordinate.
   *
   * @param y y co-ordinate.
   */
  public void setY1(final float y) {
    if (mX1 == mX2 && mY2 == y) {
      throw new IllegalArgumentException("Ends of arrow must be different: " + mY2 + " : " + y);
    }
    mY1 = y;
  }


  /**
   * Returns y1 co-ordinate.
   *
   * @return y co-ordinate.
   */
  public float getY1() {
    return mY1;
  }


  /**
   * Sets the x2 co-ordinate.
   *
   * @param x x co-ordinate.
   */
  public void setX2(final float x) {
    if (mY1 == mY2 && mX1 == x) {
      throw new IllegalArgumentException("Ends of arrow must be different: " + mX1 + " : " + x);
    }
    mX2 = x;
  }


  /**
   * Returns x2 co-ordinate.
   *
   * @return x co-ordinate.
   */
  public float getX2() {
    return mX2;
  }


  /**
   * Sets the y2 co-ordinate.
   *
   * @param y y co-ordinate.
   */
  public void setY2(final float y) {
    if (mX1 == mX2 && mY1 == y) {
      throw new IllegalArgumentException("Ends of arrow must be different: " + mY1 + " : " + y);
    }
    mY2 = y;
  }


  /**
   * Returns y2 co-ordinate.
   *
   * @return y co-ordinate.
   */
  public float getY2() {
    return mY2;
  }


  /**
   * Returns the lower bound of the point in the x dimension.
   *
   * @return a number
   */
  public float getXLo() {
    return Math.min(getX1(), getX2());
  }


  /**
   * Returns the upper bound of the point in the x dimension.
   *
   * @return a number
   */
  public float getXHi() {
    return Math.max(getX1(), getX2());
  }


  /**
   * Returns the lower bound of the point in the y dimension.
   *
   * @return a number
   */
  public float getYLo() {
    return Math.min(getY1(), getY2());
  }


  /**
   * Returns the upper bound of the point in the y dimension.
   *
   * @return a number
   */
  public float getYHi() {
    return Math.max(getY1(), getY2());
  }


  /**
   * Returns a string representation of this object.
   *
   * @return a co-ordinate string
   */
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append('(').append(getX1()).append(',').append(getY2());
    sb.append(" -> ");
    sb.append(getX1()).append(',').append(getY2()).append(')');
    return sb.toString();
  }

  /**
   * Overrides Object.equals
   *
   * @param object object to compare to
   * @return result of comparison
   */
  public boolean equals(Object object) {
    if (!(object instanceof Arrow2D)) {
      return false;
    }
    Arrow2D pobj = (Arrow2D) object;
    return (mX1 == pobj.getX1() && mY1 == pobj.getY1()
            && mX2 == pobj.getX2() && mY2 == pobj.getY2());
  }


  public int hashCode() {
    return super.hashCode();
  }
}
