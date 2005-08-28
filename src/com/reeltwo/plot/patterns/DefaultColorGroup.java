package com.reeltwo.plot.patterns;

import java.awt.Color;
import java.awt.Paint;

/**
 * Default set of colors used in colored plots.
 *
 * @author Richard Littin (richard@reeltwo.com) 
 * @version $Revision$
 */
public class DefaultColorGroup implements PatternGroup {

  private Color[] mColors = null;

  /**
   * Creates a new <code>DefaultColorGroup</code>.
   */
  public DefaultColorGroup() {    
  }

  /** {@inheritDoc} */
  public Paint[] getPatterns() {
    if (mColors == null) {
      mColors = new Color[] {
        Color.RED,
        new Color(0.0F, 0.8F, 0.0F),  // green
        Color.BLUE,
        Color.MAGENTA,
        Color.CYAN,
        new Color(0.62F, 0.32F, 0.18F),  // brown
        Color.ORANGE,
        Color.GRAY,
      };
    }
    Color[] colors = new Color[mColors.length];
    System.arraycopy(mColors, 0, colors, 0, colors.length);
    return colors;
  }

  /** {@inheritDoc} */
  public String getName() {
    return "Default Colors";
  }

  /** {@inheritDoc} */
  public String getDescription() {
    return "Default colors that are visible on a white background.";
  }
}
