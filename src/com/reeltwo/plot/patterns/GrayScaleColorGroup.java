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
    System.arraycopy(mColors, 0, colors, 0, colors.length);
    return colors;
  }

  /** {@inheritDoc} */
  public String getName() {
    return "Grayscale " + mNumShades;
  }

  /** {@inheritDoc} */
  public String getDescription() {
    return "Gray scaled shades.";
  }

}
