package com.reeltwo.plot;

/**
 * Formats axis values from a preset array of strings.
 *
 * @author Richard Littin
 */
public class StringFormatter implements LabelFormatter {
  private final String[] mLabels;

  /**
   * Creates a new <code>StringFormatter</code> setting an array of
   * preset strings.
   *
   * @param labels an array of <code>String</code>
   */
  public StringFormatter(String[] labels) {
    if (labels == null || labels.length == 0) {
      throw new NullPointerException("empty labels.");
    }
    for (int l = 0; l < labels.length; l++) {
      if (labels[l] == null) {
        throw new NullPointerException("null label.");
      }
    }
    mLabels = labels;
  }

  /**
   * Returns the <code>(int) f</code>th string from a preset array of
   * strings.  Rotates through the array if <code>f</code> is larger
   * that the array length.
   *
   * @param f float to format
   * @return formatted float
   */
  @Override
  public String format(float f) {
    return mLabels[((int) Math.abs(f)) % mLabels.length];
  }
}
