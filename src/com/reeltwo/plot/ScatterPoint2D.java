package com.reeltwo.plot;

/**
 * A point which is actually a group of points. Will be drawn as a
 * scattering of points centered on the point co-ordinates.
 *
 * @author Richard Littin (richard@reeltwo.com)
 * @version $Revision$
 */

public class ScatterPoint2D extends Point2D {
  private int mNumPoints = 1;


  /**
   * Sets number of points, scatter and co-ordinates to display it at.
   *
   * @param x x co-ordinate.
   * @param y y co-ordinate.
   * @param numPoints number of points at this point
   */
  public ScatterPoint2D(float x, float y, int numPoints) {
    super(x, y);
    setNumberOfPoints(numPoints);
  }


  /**
   * Sets the number of points.
   *
   * @param numPoints number of points.
   */
  public void setNumberOfPoints(int numPoints) {
    if (numPoints <= 0) {
      throw new IllegalArgumentException("number of points must be > 0: " + numPoints);
    }
    mNumPoints = numPoints;
  }


  /**
   * Returns the number of points.
   *
   * @return number of points.
   */
  public int getNumberOfPoints() {
    return mNumPoints;
  }

  /** {@inheritDoc} */
  public boolean equals(Object object) {
    if (!(object instanceof ScatterPoint2D)) {
      return false;
    }
    ScatterPoint2D pobj = (ScatterPoint2D) object;
    return super.equals(pobj) && (mNumPoints == pobj.getNumberOfPoints());
  }

  /** {@inheritDoc} */
  public int hashCode() {
    return super.hashCode() * mNumPoints;
  }


  /**
   * Returns a string representation of this object.
   *
   * @return a co-ordinate string
   */
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append('(').append(getX()).append(',').append(getY());
    sb.append(',').append(getNumberOfPoints());
    sb.append(')');
    return sb.toString();
  }

}
