package com.reeltwo.plot;

/**
 * Box data used in data plotting structures.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */

public class Box2D implements Datum2D {
  /** co-ordinates of box */
  private float mLeft, mRight;
  private float mTop, mBottom;


  /**
   * Creates a box for the given co-ordinates. If right < left their
   * values are swapped. If top < bottom their values are swapped.
   *
   * @param left left edge of box
   * @param top top edge of box
   * @param right right edge of box
   * @param bottom bottom edge of box
   */
  public Box2D(float left, float bottom, float right, float top) {
    setXRange(left, right);
    setYRange(bottom, top);
  }


  /**
   * Sets the left and right edges of the box. If right < left their
   * values are swapped.
   *
   * @param left left edge of box
   * @param right right edge of box
   */
  public void setXRange(float left, float right) {
    if (left <= right) {
      mLeft = left;
      mRight = right;
    } else {
      mLeft = right;
      mRight = left;
    }
  }


  /**
   * Sets the bottom and top edges of the box. If top < bottom their
   * values are swapped.
   *
   * @param bottom bottom edge of box
   * @param top top edge of box
   */
  public void setYRange(float bottom, float top) {
    if (bottom <= top) {
      mTop = top;
      mBottom = bottom;
    } else {
      mTop = bottom;
      mBottom = top;
    }
  }


  /**
   * Returns the left edge of the box.
   *
   * @return a number
   */
  public float getLeft() {
    return mLeft;
  }


  /**
   * Returns the right edge of the box.
   *
   * @return a number
   */
  public float getRight() {
    return mRight;
  }


  /**
   * Returns the bottom edge of the box.
   *
   * @return a number
   */
  public float getBottom() {
    return mBottom;
  }


  /**
   * Returns the top edge of the box.
   *
   * @return a number
   */
  public float getTop() {
    return mTop;
  }


  /**
   * Returns the lower bound of the box in the x dimension.
   *
   * @return a number
   */
  public float getXLo() {
    return getLeft();
  }


  /**
   * Returns the upper bound of the box in the x dimension.
   *
   * @return a number
   */
  public float getXHi() {
    return getRight();
  }


  /**
   * Returns the lower bound of the box in the y dimension.
   *
   * @return a number
   */
  public float getYLo() {
    return getBottom();
  }


  /**
   * Returns the upper bound of the box in the y dimension.
   *
   * @return a number
   */
  public float getYHi() {
    return getTop();
  }


  /**
   * Returns a string representation of this object.
   *
   * @return a co-ordinate string
   */
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("[(").append(getLeft()).append(',').append(getTop());
    sb.append(")(").append(getRight()).append(',').append(getBottom());
    sb.append(")]");
    return sb.toString();
  }

}
