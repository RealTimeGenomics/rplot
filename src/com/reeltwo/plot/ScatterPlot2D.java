package com.reeltwo.plot;

/**
 * Structure to hold attributes of a group of labels on a 2D graph.
 *
 * @author Richard Littin
 */

public class ScatterPlot2D extends Plot2D {
  /** the scatter factor for this plot */
  private float mScatterFactor = 0.0f;

  /**
   * Default constructor setting the default axes used to the bottom
   * x axis and the left y axis.
   */
  public ScatterPlot2D() {
    super();
  }


  /**
   * Constructor setting which axes to use in the plot.
   *
   * @param x x axis to use.
   * @param y y axis to use.
   */
  public ScatterPlot2D(AxisSide x, AxisSide y) {
    super(x, y);
  }


  /**
   * Sets the scatter factor that is used when randomly scattering the
   * points.
   *
   * @param sf scatter factor
   */
  public void setScatterFactor(float sf) {
    mScatterFactor = Math.abs(sf);
  }


  /**
   * Returns the scatter factor.
   *
   * @return scatter factor;
   */
  public float getScatterFactor() {
    return mScatterFactor;
  }


  /**
   * Sets the data used in this plot.
   *
   * @param data an array of Datum2D's
   */
  @Override
  public void setData(Datum2D[] data) {
    if (!(data instanceof ScatterPoint2D[])) {
      throw new UnsupportedOperationException("Cannot set " + data.getClass().getName() + " in ScatterPlot2D.");
    }
    super.setData(data);
  }
}
