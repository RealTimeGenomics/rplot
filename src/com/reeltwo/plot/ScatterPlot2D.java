package com.reeltwo.plot;

/**
 * Structure to hold attributes of a group of labels on a 2D graph.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */

public class ScatterPlot2D extends Plot2D {
  /** the scatter factor for this plot */
  private float mScatterFactor = 0.0f;

  public ScatterPlot2D() {
    super();
  }
  
  
  public ScatterPlot2D(int x, int y) {
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
  public void setData(Datum2D[] data) {
    if (!(data instanceof ScatterPoint2D[])) {
      throw new UnsupportedOperationException("Cannot set " + data.getClass().getName() + " in ScatterPlot2D.");
    }
    super.setData(data);
  }
}
