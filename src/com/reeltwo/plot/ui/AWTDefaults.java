package com.reeltwo.plot.ui;

import java.awt.Color;


/**
 * AWT default settings useful in graph plotting.
 *
 * @author Richard Littin (richard@reeltwo.com) 
 * @version $Revision$
 */

public class AWTDefaults {
  private AWTDefaults() {
  }
  

  /** default colors used to plot lines */
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

}
