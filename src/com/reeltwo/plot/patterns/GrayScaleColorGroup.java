package com.reeltwo.plot.patterns;

import java.awt.Color;
import java.awt.Paint;

/**
 * Set of colors used in gray scale plots.
 *
 * @author Richard Littin
 */
public class GrayScaleColorGroup implements PatternGroup {

  private final int mNumShades;
  private Color[] mColors = null;

  /**
   * Creates a new <code>GrayScaleColorGroup</code>setting the number
   * of different <code>shades</code> of gray to 10.
   *
   */
  public GrayScaleColorGroup() {
    this(10);
  }

  /**
   * Creates a new <code>GrayScaleColorGroup</code> setting the number
   * of different <code>shades</code> of gray to produce.
   *
   * @param shades number of shades
   */
  public GrayScaleColorGroup(int shades) {
    if (shades <= 0) {
      throw new IllegalArgumentException("must have at least one shade.");
    }
    mNumShades = shades;
  }


  /** {@inheritDoc} */
  @Override
  public Paint[] getPatterns() {
    if (mColors == null) {
      mColors = new Color[mNumShades];
      final int step = 255 / mNumShades;
      for (int i = 0; i < mNumShades; i++) {
        final int shade = i * step;
        mColors[i] = new Color(shade, shade, shade);
      }
    }
    final Color[] colors = new Color[mColors.length];
    System.arraycopy(mColors, 0, colors, 0, colors.length);
    return colors;
  }

  /** {@inheritDoc} */
  @Override
  public String getName() {
    return "Grayscale " + mNumShades;
  }

  /** {@inheritDoc} */
  @Override
  public String getDescription() {
    return "Gray scaled shades.";
  }

}
