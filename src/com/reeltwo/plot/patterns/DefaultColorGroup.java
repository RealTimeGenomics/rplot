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

  public static final Color[] COLORS = new Color[] {
    Color.RED,
    new Color(0.0F, 0.8F, 0.0F),  // green
    Color.BLUE,
    Color.MAGENTA,
    Color.CYAN,
    new Color(0.62F, 0.32F, 0.18F),  // brown
    Color.ORANGE,
    Color.GRAY,
  };

  public DefaultColorGroup() {    
  }

  public int getPatternCount() {
    return COLORS.length;
  }

  public Paint [] getPatterns() {
    Color [] colors = new Color[COLORS.length];
    System.arraycopy(COLORS, 0, colors, 0, COLORS.length);
    return colors;
  }

  public Paint getPattern(int index) {
    return COLORS[index];
  }
}
