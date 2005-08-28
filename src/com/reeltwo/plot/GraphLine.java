package com.reeltwo.plot;

/**
 * Renders a single vertical or horizontal line across the graph at a
 * specified location. 
 *
 * @author Richard Littin (richard@reeltwo.com) 
 * @version $Revision$
 */
public class GraphLine extends Plot2D {
  
  /** Horizontal line */
  public static final int HORIZONTAL = 0;
  /** Vertical line */
  public static final int VERTICAL = 1;

  /** Dashed line */
  public static final int DASHES = 0;
  /** Dotted line */
  public static final int DOTS = 1;
  /** Solid line */
  public static final int SOLID = 2;

  private final float mLocation;
  private final int mOrientation;

  private int mType = DASHES;

  /**
   * Creates a new <code>GraphLine</code> at the given
   * <code>location</code> on the appropriate axis for the
   * <code>orientation</code>.  Valid orientations are
   * <code>HORIZONTAL</code> and <code>VERTICAL</code>.
   *
   * @param location position on axis
   * @param orientation horizontal or vertical
   */
  public GraphLine(float location, int orientation) {
    this(0, 0, location, orientation);
  }
  
  private GraphLine(int x, int y, float location, int orientation) {
    if (orientation < HORIZONTAL || orientation > VERTICAL) {
      throw new IllegalArgumentException("Invalid orientation: " + orientation);
    }
    mLocation = location;
    mOrientation = orientation;
  }
  
  /**
   * Returns the location of the line.
   *
   * @return location
   */
  public float getLocation() {
    return mLocation;
  }

  /**
   * Returns the orientation of the line.
   *
   * @return orientation
   */
  public int getOrientation() {
    return mOrientation;
  }

  /**
   * Sets the type of line to render.  Valid types are
   * <code>DASHES</code> (the default), <code>DOTS</code> and
   * <code>SOLID</code>.
   *
   * @param type line type
   */
  public void setType(int type) {
    if (type < DASHES || type > SOLID) {
      throw new IllegalArgumentException("Invalid type: " + type);
    }
    mType = type;
  }

  /**
   * Returns the line type.
   *
   * @return line type
   */
  public int getType() {
    return mType;
  }

  /** {@inheritDoc} */
  public void setData(Datum2D[] data) {
    throw new UnsupportedOperationException("not applicable.");
  }

  /** {@inheritDoc} */
  public Datum2D[] getData() {
    throw new UnsupportedOperationException("not applicable.");
  }

  /** {@inheritDoc} */
  public float getXLo() {
    throw new UnsupportedOperationException("not applicable.");
  }

  /** {@inheritDoc} */
  public float getXHi() {
    throw new UnsupportedOperationException("not applicable.");
  }

  /** {@inheritDoc} */
  public float getYLo() {
    throw new UnsupportedOperationException("not applicable.");
  }

  /** {@inheritDoc} */
  public float getYHi() {
    throw new UnsupportedOperationException("not applicable.");
  }

  /** {@inheritDoc} */
  public void setTitle(String title) {
    throw new UnsupportedOperationException("not applicable.");
  }

  /** {@inheritDoc} */
  public void setColor(int color) {
    throw new UnsupportedOperationException("not applicable.");
  }
}
