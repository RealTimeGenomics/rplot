package com.reeltwo.plot;

/**
 * Renders a single vertical or horizontal line across the graph at a
 * specified location. 
 *
 * @author Richard Littin (richard@reeltwo.com) 
 * @version $Revision$
 */
public class GraphLine extends Plot2D {
  
  /** Horizontal */
  public static final int HORIZONTAL = 0;
  /** Vertical */
  public static final int VERTICAL = 1;

  public static final int DASHES = 0;
  public static final int DOTS = 1;
  public static final int SOLID = 2;

  private final float mLocation;
  private final int mOrientation;

  private int mType = DASHES;

  public GraphLine(float location, int orientation) {
    this(0, 0, location, orientation);
  }

  public GraphLine(int x, int y, float location, int orientation) {
    if (orientation < HORIZONTAL || orientation > VERTICAL) {
      throw new IllegalArgumentException("Invalid orientation: " + orientation);
    }
    mLocation = location;
    mOrientation = orientation;
  }

  public float getLocation() {
    return mLocation;
  }

  public int getOrientation() {
    return mOrientation;
  }

  public void setType(int type) {
    if (type < DASHES || type > SOLID) {
      throw new IllegalArgumentException("Invalid type: " + type);
    }
    mType = type;
  }

  public int getType() {
    return mType;
  }

  public void setData(Datum2D[] data) {
    throw new UnsupportedOperationException("not applicable.");
  }

  public Datum2D [] getData() {
    throw new UnsupportedOperationException("not applicable.");
  }

  public float getXLo() {
    throw new UnsupportedOperationException("not applicable.");
  }

  public float getXHi() {
    throw new UnsupportedOperationException("not applicable.");
  }

  public float getYLo() {
    throw new UnsupportedOperationException("not applicable.");
  }

  public float getYHi() {
    throw new UnsupportedOperationException("not applicable.");
  }

  public void setTitle(String title) {
    throw new UnsupportedOperationException("not applicable.");
  }

  public void setColor(int color) {
    throw new UnsupportedOperationException("not applicable.");
  }
}
