package com.reeltwo.plot;

/**
 * Methods that all point objects must implement.
 *
 * @author Richard Littin
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
