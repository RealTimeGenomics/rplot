package com.reeltwo.plot;

/**
 * Object associated with a Point2D.
 *
 * @author Richard Littin
 */

public class ObjectPoint2D extends Point2D {

  /** Associated object. */
  private Object mObject = null;


  /**
   * Sets text and co-ordinates to display it at.
   *
   * @param x x co-ordinate.
   * @param y y co-ordinate.
   * @param obj text to display.
   */
  public ObjectPoint2D(float x, float y, Object obj) {
    super(x, y);
    setObject(obj);
  }

  /**
   * Compute the square of the distance from the point to another point (specified as x, y co-ordinates).
   * @param x co-ordinate of other point.
   * @param y co-ordinate of other point.
   * @return square of the distance.
   */
  public double distanceSq(double x, double y) {
    final double xd = x - getX();
    final double yd = y - getY();
    return xd * xd + yd * yd;
  }

  /**
   * Sets the text to display.
   *
   * @param obj some text.
   */
  public void setObject(Object obj) {
    mObject = obj;
  }


  /**
   * Returns the text to display.
   *
   * @return some text.
   */
  public Object getObject() {
    return mObject;
  }


  /** {@inheritDoc} */
  @Override
  public boolean equals(Object object) {
    if (!(object instanceof ObjectPoint2D)) {
      return false;
    }
    final ObjectPoint2D pobj = (ObjectPoint2D) object;
    return super.equals(pobj) && mObject.equals(pobj.getObject());
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return super.hashCode();
  }


  /**
   * Returns a string representation of this object.
   *
   * @return a co-ordinate string
   */
  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer();
    sb.append('(').append(getX()).append(',').append(getY());
    if (getObject() != null) {
      sb.append(',').append(getObject());
    }
    sb.append(')');
    return sb.toString();
  }

}
