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

  private final int mNumShades;
  private Color[] mColors = null;

  public GrayScaleColorGroup() {
    this(10);
  }

  public GrayScaleColorGroup(int shades) {
    if (shades <= 0) {
      throw new IllegalArgumentException("must have at least one shade.");
    }
    mNumShades = shades;
  }


  public Paint [] getPatterns() {
    if (mColors == null) {
      mColors = new Color[mNumShades];
      final int step = 255 / mNumShades;
      for (int i = 0; i < mNumShades; i++) {
        final int shade = i * step;
        mColors[i] = new Color(shade, shade, shade);
      }
    }
    Color [] colors = new Color[mColors.length];
    System.arraycopy(mColors, 0, colors, 0, mColors.length);
    return colors;
  }

  public String getName() {
    return "Grayscale " + mNumShades;
  }

  public String getDescription() {
    return "Gray scaled shades.";
  }

}
