package com.reeltwo.plot.patterns;

import java.awt.Color;
import java.awt.Paint;

/**
 * Set of colors used in gray scale plots.
 *
 * @author Richard Littin (richard@reeltwo.com) 
 * @version $Revision$
 */
public class GrayScaleColorGroup implements PatternGroup {

  public final Color[] mColors;

  public GrayScaleColorGroup() {
    this(10);
  }

  public GrayScaleColorGroup(int shades) {
    if (shades <= 0) {
      throw new IllegalArgumentException("must have at least one shade.");
    }
    mColors = new Color[shades];
    final int step = 255 / shades;
    for (int i = 0; i < shades; i++) {
      final int shade = i * step;
      mColors[i] = new Color(shade, shade, shade);
    }
  }

  public int getPatternCount() {
    return mColors.length;
  }

  public Paint [] getPatterns() {
    Color [] colors = new Color[mColors.length];
    System.arraycopy(mColors, 0, colors, 0, mColors.length);
    return colors;
  }

  public Paint getPattern(int index) {
    return mColors[index];
  }
}
