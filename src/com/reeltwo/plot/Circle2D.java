package com.reeltwo.plot;

/**
 * Draws a circle of a specified diameter at a Point2D.
 *
 * @author Richard Littin (richard@reeltwo.com)
 * @version $Revision$
 */
public class Circle2D extends Point2D {
  /** text to be displayed */
  private float mDiameter;


  /**
   * Sets circle size and co-ordinates to display it at.
   *
   * @param x x co-ordinate.
   * @param y y co-ordinate.
   * @param diameter diameter of the circle in screen units.
   */
  public Circle2D(float x, float y, float diameter) {
    super(x, y);
    setDiameter(diameter);
  }


  /**
   * Sets the diameter of the circle.
   *
   * @param diameter diameter of the circle in screen units.
   */
  public void setDiameter(float diameter) {
    if (diameter <= 0.0f) {
      throw new IllegalArgumentException("diameter must be > 0: " + diameter);
    }
    mDiameter = Math.abs(diameter);
  }


  /**
   * Returns the diameter of the circle.
   *
   * @return a float.
   */
  public float getDiameter() {
    return mDiameter;
  }


  /**
   * Returns a string representation of this object.
   *
   * @return a co-ordinate string
   */
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append('(').append(getX()).append(',').append(getY());
    sb.append(',').append(getDiameter());
    sb.append(')');
    return sb.toString();
  }

}
