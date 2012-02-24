package com.reeltwo.plot;

/**
 * Renders a single vertical or horizontal line across the graph at a
 * specified location.
 *
 * @author Richard Littin
 */
public class GraphLine extends Plot2D {

  /**
   * Possible line orientations.
   */
  public enum LineOrientation {
    /** Horizontal line */
    HORIZONTAL,
    /** Vertical line */
    VERTICAL
  }

  /**
   * Possible line styles.
   */
  public enum LineStyle {
    /** Dashed line */
    DASHES,
    /** Dotted line */
    DOTS,
    /** Solid line */
    SOLID
  }

  private final float mLocation;
  private final LineOrientation mOrientation;

  private LineStyle mStyle = LineStyle.DASHES;

  /**
   * Creates a new <code>GraphLine</code> at the given
   * <code>location</code> on the appropriate axis for the
   * <code>orientation</code>.  Valid orientations are
   * <code>HORIZONTAL</code> and <code>VERTICAL</code>.
   *
   * @param location position on axis
   * @param orientation horizontal or vertical
   */
  public GraphLine(float location, LineOrientation orientation) {
    this(0, 0, location, orientation);
  }

  private GraphLine(int x, int y, float location, LineOrientation orientation) {
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
  public LineOrientation getOrientation() {
    return mOrientation;
  }

  /**
   * Sets the type of line to render.  Valid types are
   * <code>DASHES</code> (the default), <code>DOTS</code> and
   * <code>SOLID</code>.
   *
   * @param type line type
   */
  public void setStyle(LineStyle type) {
    mStyle = type;
  }

  /**
   * Returns the line type.
   *
   * @return line type
   */
  public LineStyle getStyle() {
    return mStyle;
  }

  /** {@inheritDoc} */
  @Override
  public void setData(Datum2D[] data) {
    throw new UnsupportedOperationException("not applicable.");
  }

  /** {@inheritDoc} */
  @Override
  public Datum2D[] getData() {
    throw new UnsupportedOperationException("not applicable.");
  }

  /** {@inheritDoc} */
  @Override
  public float getLo(Axis2D axis) {
    throw new UnsupportedOperationException("not applicable.");
  }

  /** {@inheritDoc} */
  @Override
  public float getHi(Axis2D axis) {
    throw new UnsupportedOperationException("not applicable.");
  }

  /** {@inheritDoc} */
  @Override
  public void setTitle(String title) {
    throw new UnsupportedOperationException("not applicable.");
  }

  /** {@inheritDoc} */
  @Override
  public void setColor(int color) {
    throw new UnsupportedOperationException("not applicable.");
  }
}
