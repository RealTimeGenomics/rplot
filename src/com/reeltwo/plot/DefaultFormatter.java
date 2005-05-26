package com.reeltwo.plot;

import java.text.NumberFormat;

/**
 * DefaultFormatter.java
 *
 *
 * @author Richard Littin (richard@reeltwo.com) 
 * @version $Revision$
 */

public class DefaultFormatter implements LabelFormatter {
  private static DefaultFormatter sInstance = null;

  /** a number formatter */
  private final NumberFormat mNF = NumberFormat.getInstance();

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

  public static DefaultFormatter getInstance() {
    if (sInstance == null) {
      sInstance = new DefaultFormatter();
    }
    return sInstance;
  }

  public String format(float f) {
    return mNF.format(f);
  }
}
