package com.reeltwo.plot;

/**
 * Interface for axis label formatters.
 *
 * @author Richard Littin
 */
public interface LabelFormatter {

  /**
   * Returns a formated string for the given float value.  Should not
   * return null.
   *
   * @param f float value to format
   * @return formatted value
   */
  String format(float f);

}
