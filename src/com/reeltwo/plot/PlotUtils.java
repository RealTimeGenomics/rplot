package com.reeltwo.plot;

/**
 * Utility functions for plot data.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */

public class PlotUtils {

  private PlotUtils() { }

  // ln(10)
  private static final double L10 = Math.log(10.0);


  /**
   *  Returns the log base 10 of <code>a</code> double value.
   *
   * @param a a number greater than 0.0
   * @return the value log<sub>10</sub>(<code>a</code>)
   */
  public static double log10(double a) {
    return Math.log(a) / L10;
  }


  /**
   * Returns the largest (closest to positive infinity) double value
   * that is a power of 10 not greater than the argument.  Special
   * cases: 
   * <ul>
   *   <li>If the argument value is already equal to a power of 10,
   *   then the result is the same as the argument.</li> 
   *   <li>If the argument is NaN or an infinity or positive zero or
   *   negative zero, then the result is the same as the
   *   argument.</li> 
   * </ul>
   *
   * @param a a number greater than 0.0
   * @return the largest double value that is a power of 10 not
   * greater than the argument 
   */
  public static double floor10(double a) {
    return Math.pow(10, Math.floor(log10(a)));
  }


  /**
   * Returns the smallest (closest to negative infinity) double value
   * that is a power of 10 not less than the argument.  Special
   * cases: 
   * <ul>
   *   <li>If the argument value is already equal to a power of 10,
   *   then the result is the same as the argument.</li> 
   *   <li>If the argument is NaN or an infinity or positive zero or
   *   negative zero, then the result is the same as the
   *   argument.</li> 
   * </ul>
   *
   * @param a a number greater than 0.0
   * @return the smallest double value that is a power of 10 not
   * less than the argument
   */
  public static double ceil10(double a) {
    return Math.pow(10, Math.ceil(log10(a)));
  }

  /**
   * Raises first argument to the power of the second argument.
   *
   * @param x value to raise
   * @param y amount to raise to
   * @return x<sup>y</sup>
   */
  public static float pow(float x, int y) {
    float val = 1.0f;
    for (int i = 0; i < (int) Math.abs(y); i++) {
      val *= x;
    }
    if (y < 0) {
      return 1.0f / val;
    }
    return val;
  }

}
