package com.reeltwo.plot;

/**
 * StringFormatter.java
 *
 *
 * @author Richard Littin (richard@reeltwo.com) 
 * @version $Revision$
 */

public class StringFormatter implements LabelFormatter {
  private final String [] mLabels;

  public StringFormatter(String [] labels) {
    if (labels == null || labels.length == 0) {
      throw new NullPointerException("empty Y Tic labels.");
    }
    for (int l = 0; l < labels.length; l++) {
      if (labels[l] == null) {
        throw new NullPointerException("null Y Tics label.");
      }
    }
    mLabels = labels;
  }

  public String format(float f) {
    return mLabels[((int) Math.abs(f)) % mLabels.length];
  }
}
