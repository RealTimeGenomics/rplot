package com.reeltwo.plot.renderer;

import com.reeltwo.plot.PlotUtils;

/**
 * Provides mappings between world and screen co-ordinate systems in a
 * single dimension.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */

public class Mapping {
  // world and screen minimum and maximums
  private float mWmin, mWmax, mSmin, mSmax;
  // whether to take logs on world values
  private boolean mLog = false;


  /**
   * Initializes Mapping object with the world's <code>worldMin</code>
   * and <code>worldMax</code> and the screen's matching
   * <code>screenMin</code> and <code>screenMax</code>.
   *
   * @param worldMin world's minimum value
   * @param worldMax world's maximum value
   * @param screenMin screen's minimum value
   * @param screenMax screen's maximum value
   * @exception IllegalArgumentException if world values or screen
   * values are equal.
   */
  public Mapping(float worldMin, float worldMax, float screenMin, float screenMax) {
    this(worldMin, worldMax, screenMin, screenMax, false);
  }


  /**
   * Initializes Mapping object with the world's <code>worldMin</code>
   * and <code>worldMax</code> and the screen's matching
   * <code>screenMin</code> and <code>screenMax</code>.  If
   * <code>log</code> is set then a log<sub>10</sub> transformation on
   * world values is performed.
   *
   * @param worldMin world's minimum value
   * @param worldMax world's maximum value
   * @param screenMin screen's minimum value
   * @param screenMax screen's maximum value
   * @param log whether to take log<sub>10</sub> of world values
   * @exception IllegalArgumentException if world values or screen
   * values are equal.
   */
  public Mapping(float worldMin, float worldMax, float screenMin, float screenMax, boolean log) {
    if (worldMin == worldMax) {
      throw new IllegalArgumentException("World minimum and maximum are same: " + worldMin);
    }
    if (screenMin == screenMax) {
      throw new IllegalArgumentException("Screen minimum and maximum are same: " + screenMin);
    }
    mLog = log;
    mWmin = mLog ? (float) PlotUtils.log10(worldMin) : worldMin;
    mWmax = mLog ? (float) PlotUtils.log10(worldMax) : worldMax;
    mSmin = screenMin;
    mSmax = screenMax;
  }

  
  /**
   * Returns the world's minimum value.
   *
   * @return world minimum
   */
  public float getWorldMin() {
    return mLog ? (float) Math.pow(10, mWmin) : mWmin;
  }


  /**
   * Returns the world's maximum value.
   *
   * @return world maximum
   */
  public float getWorldMax() {
    return mLog ? (float) Math.pow(10, mWmax) : mWmax;
  }


  /**
   * Returns the screen's minimum value.
   *
   * @return screen minimum
   */
  public float getScreenMin() {
    return mSmin;
  }


  /**
   * Returns the screen's maximum value.
   *
   * @return screen maximum
   */
  public float getScreenMax() {
    return mSmax;
  }
  

  /**
   * Transforms the given value <code>p</code> from world co-ordinates
   * to a screen co-ordinate.
   *
   * @param p a world co-ordinate
   * @return corresponding screen co-ordinate
   */
  public float worldToScreen(float p) {
    float p2 = mLog ? (float) PlotUtils.log10(p) : p;
    return mSmin + (p2 - mWmin) * (mSmax - mSmin) / (mWmax - mWmin);
  }


  /**
   * Transforms the given value <code>p</code> from screen co-ordinates
   * to a world co-ordinate.
   *
   * @param p a screen co-ordinate
   * @return corresponding world co-ordinate
   */
  public float screenToWorld(float p) {
    float s = mWmin + (p - mSmin) * (mWmax - mWmin) / (mSmax - mSmin);
    return mLog ? (float) Math.pow(10, s) : s;
  }
}
