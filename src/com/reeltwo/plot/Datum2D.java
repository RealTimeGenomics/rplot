package com.reeltwo.plot;

/**
 * Structure to hold attributes for a single data point on a 2D plot.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */

public interface Datum2D {
  /**
   * Returns the lower bound of the datum in the x dimension.
   *
   * @return a number
   */
  float getXLo();


  /**
   * Returns the upper bound of the datum in the x dimension.
   *
   * @return a number
   */
  float getXHi();


  /**
   * Returns the lower bound of the datum in the y dimension.
   *
   * @return a number
   */
  float getYLo();


  /**
   * Returns the upper bound of the datum in the y dimension.
   *
   * @return a number
   */
  float getYHi();
}
