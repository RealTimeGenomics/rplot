package com.reeltwo.plot;

import java.text.NumberFormat;

/**
 * Default formatter for floating point numbers.
 *
 * @author Richard Littin (richard@reeltwo.com) 
 * @version $Revision$
 */
public class DefaultFormatter implements LabelFormatter {
  private static DefaultFormatter sInstance = null;

  /** a number formatter */
  private final NumberFormat mNF = NumberFormat.getInstance();

  /**
   * Private to prevent instansiation.
   */
  private DefaultFormatter() {
  }

  /**
   * Sets the number of decimal digits to display for a given tic size.
   *
   * @param ticSize tic size
   */
  public void setNumDecimalDigits(float ticSize) {
    int digits = 0;
    float tens = 1.0f;
    for (int i = 0; i < 5; i++) {
      if (ticSize < tens) {
        digits++;
      }
      tens /= 10.0f;
    }
    mNF.setMinimumFractionDigits(digits);
    mNF.setMaximumFractionDigits(digits);
  }

  /**
   * Returns the one instance of the <code>DefaultFormatter</code>
   *
   * @return a <code>DefaultFormatter</code>
   */
  public static DefaultFormatter getInstance() {
    if (sInstance == null) {
      sInstance = new DefaultFormatter();
    }
    return sInstance;
  }

  /**
   * Formats the given float as determined by the internal number
   * formatter. 
   *
   * @param f float to format
   * @return formatted float
   */
  public String format(float f) {
    return mNF.format(f);
  }
}
